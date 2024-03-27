package com.example.BaiTest.services.implement;

import com.example.BaiTest.dtos.LessonDto;
import com.example.BaiTest.exceptions.ResourceNotFoundException;
import com.example.BaiTest.model.*;
import com.example.BaiTest.repository.*;
import com.example.BaiTest.responses.LessonResponse;
import com.example.BaiTest.services.iservices.LessonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LessonRepo lessonRepo;

    @Autowired
    private LessonTypeRepo lessonTypeRepo;

    @Autowired
    private UserLikeCommentLessonRepo userLikeCommentLessonRepo;

    @Autowired
    private UserLessonCheckpointRepo userLessonCheckpointRepo;

    @Autowired
    private CommentLessonRepo commentLessonRepo;

    @Override
    public LessonDto createLesson(LessonDto lessonDto, int lessonTypeId) {
        LessonType lessonType = this.lessonTypeRepo.findById(lessonTypeId).orElseThrow(()-> new ResourceNotFoundException("lessonType","lessonTypeId", (long) lessonTypeId));
        Lesson lesson = this.dtoToLesson(lessonDto);

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        lesson.setCrateAt(currentTimestamp);
        lesson.setUpdateAt(null);
        lesson.setRemoveAt(null);

        lesson.setLessonType(lessonType);

        Lesson updateLesson = lessonRepo.save(lesson);

        return this.lessonToDto(updateLesson);
    }

    @Override
    public LessonDto updateLessonDto(LessonDto lessonDto, int lessonId) {
        Lesson lesson = this.lessonRepo.findById(lessonId).orElseThrow(()-> new ResourceNotFoundException("lesson","lessonId", (long)lessonId));
        lesson.setName(lessonDto.getName());
        lesson.setDescription(lessonDto.getDescription());

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        lesson.setUpdateAt(currentTimestamp);

        Lesson updateLesson = this.lessonRepo.save(lesson);

        return this.lessonToDto(updateLesson);
    }

    @Override
    public void deleteLesson(int lessonId) {
        Lesson lesson = this.lessonRepo.findById(lessonId).orElseThrow(()-> new ResourceNotFoundException("lesson","lessonId", (long)lessonId));
        for(UserLessonCheckpoint userLessonCheckpoint : userLessonCheckpointRepo.findAll()){
            if(userLessonCheckpoint.getLesson().getId() == lessonId){
                userLessonCheckpoint.setUser(null);
                userLessonCheckpoint.setLesson(null);
                userLessonCheckpointRepo.deleteById(userLessonCheckpoint.getId());
            }
        }
        for(CommentLesson commentLesson: commentLessonRepo.findAll()){
            if(commentLesson.getLesson().getId() == lessonId){
                for(UserLikeCommentLesson userLikeCommentLesson: userLikeCommentLessonRepo.findAll()){
                    if(userLikeCommentLesson.getCommentLesson().getId() == commentLesson.getId()){
                        userLikeCommentLesson.setCommentLesson(null);
                        userLikeCommentLesson.setUser(null);
                        userLikeCommentLessonRepo.deleteById(userLikeCommentLesson.getId());
                    }
                }
                commentLesson.setLesson(null);
                commentLesson.setUser(null);
                commentLessonRepo.deleteById(commentLesson.getId());
            }
        }
        lesson.setLessonType(null);
        this.lessonRepo.delete(lesson);
    }

    @Override
    public LessonDto getLesson(int lessonId) {
        Lesson lesson = this.lessonRepo.findById(lessonId).orElseThrow(()-> new ResourceNotFoundException("lesson","lessonId", (long)lessonId));

        return this.lessonToDto(lesson);
    }

    @Override
    public LessonResponse getAllLesson(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending(): Sort.by(sortBy).descending());

        PageRequest pageable = PageRequest.of(pageNumber, pageSize,sort);

        Page<Lesson> pageLesson = this.lessonRepo.findAll(pageable);

        List<Lesson> allLesson = pageLesson.getContent();

        List<LessonDto> lessonDtos = allLesson.stream().map(lesson -> this.lessonToDto(lesson)).collect(Collectors.toList());

        LessonResponse lessonResponse = new LessonResponse();
        lessonResponse.setContent(lessonDtos);
        lessonResponse.setPageNumber(pageLesson.getNumber());
        lessonResponse.setPageSize(pageLesson.getSize());
        lessonResponse.setTotalPage(pageLesson.getTotalPages());
        lessonResponse.setTotalElement(pageLesson.getTotalElements());
        lessonResponse.setLastPage(pageLesson.isLast());

        return lessonResponse;
    }

    @Override
    public List<LessonDto> getLessonByLessonType(int lessonTypeId) {
        LessonType lessonType = this.lessonTypeRepo.findById(lessonTypeId).orElseThrow(()-> new ResourceNotFoundException("lessonType","lessonTypeId", (long)lessonTypeId));
        List<Lesson> lessons = this.lessonRepo.findByLessonType(lessonType);
        List<LessonDto> lessonDtos = lessons.stream().map(lesson -> this.lessonToDto(lesson)).collect(Collectors.toList());
        return lessonDtos;
    }

    @Override
    public List<LessonDto> findByNameOfLessonContaining(String keyWord) {
        List<Lesson> lessons = this.lessonRepo.findByNameContaining(keyWord);
        List<LessonDto> lessonDtos = lessons.stream().map(lesson -> this.lessonToDto(lesson)).collect(Collectors.toList());
        return lessonDtos;
    }

    public Lesson dtoToLesson(LessonDto lessonDto){
        return modelMapper.map(lessonDto, Lesson.class);
    }

    public LessonDto lessonToDto(Lesson lesson){
        return modelMapper.map(lesson, LessonDto.class);
    }
}
