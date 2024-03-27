package com.example.BaiTest.services.implement;

import com.example.BaiTest.dtos.LessonTypeDto;
import com.example.BaiTest.exceptions.ResourceNotFoundException;
import com.example.BaiTest.model.*;
import com.example.BaiTest.repository.*;
import com.example.BaiTest.services.iservices.LessonTypeService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonTypeServiceImpl implements LessonTypeService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LessonTypeRepo lessonTypeRepo;

    @Autowired
    private LessonRepo lessonRepo;

    @Autowired
    private UserLessonCheckpointRepo userLessonCheckpointRepo;

    @Autowired
    private CommentLessonRepo commentLessonRepo;

    @Autowired
    private UserLikeCommentLessonRepo userLikeCommentLessonRepo;

    @Autowired
    private UserRepo userRepo;


    @Override
    public LessonTypeDto createLessonType(LessonTypeDto lessonTypeDto) {
        LessonType lessonType = this.dtoToLessonType(lessonTypeDto);
        LessonType createLessonType = this.lessonTypeRepo.save(lessonType);
        return this.lessonTypeToDto(createLessonType);
    }

    @Override
    public LessonTypeDto updateLessonType(LessonTypeDto lessonTypeDto, int lessonTypeId) {
        LessonType lessonType = this.lessonTypeRepo.findById(lessonTypeId).orElseThrow(()-> new ResourceNotFoundException("lessonType", "lessonTypeId", (long)lessonTypeId));
        lessonType.setName(lessonTypeDto.getName());
        LessonType updateLessonType = this.lessonTypeRepo.save(lessonType);

        return this.lessonTypeToDto(updateLessonType);
    }

    @Override
    public void deleteLessonType(int lessonTypeId) {
        LessonType lessonType = this.lessonTypeRepo.findById(lessonTypeId).orElseThrow(() -> new ResourceNotFoundException("lessonType", "lessonTypeId", (long) lessonTypeId));

        // Delete associated data in a controlled order
        // 1. Delete UserLessonCheckpoint records
        List<UserLessonCheckpoint> userLessonCheckpoints = userLessonCheckpointRepo.findAllByLessonLessonTypeId(lessonTypeId);
        for (UserLessonCheckpoint userLessonCheckpoint : userLessonCheckpoints) {
            userLessonCheckpoint.setUser(null);
            userLessonCheckpoint.setLesson(null);
            this.userLessonCheckpointRepo.deleteById(userLessonCheckpoint.getId());
        }

        // 2. Delete UserLikeCommentLesson records
        List<CommentLesson> commentLessons = commentLessonRepo.findAllByLessonLessonTypeId(lessonTypeId);
        for (CommentLesson commentLesson : commentLessons) {
            List<UserLikeCommentLesson> userLikeCommentLessons = userLikeCommentLessonRepo.findAllByCommentLessonId(commentLesson.getId());
            for (UserLikeCommentLesson userLikeCommentLesson : userLikeCommentLessons) {
                userLikeCommentLesson.setCommentLesson(null);
                userLikeCommentLesson.setUser(null);
                this.userLikeCommentLessonRepo.deleteById(userLikeCommentLesson.getId());
            }
            commentLesson.setLesson(null);
            commentLesson.setUser(null);
            this.commentLessonRepo.deleteById(commentLesson.getId());
        }

        // 3. Delete Lesson records
        List<Lesson> lessons = lessonRepo.findAllByLessonTypeId(lessonTypeId);
        for (Lesson lesson : lessons) {
            lesson.setLessonType(null);
            this.lessonRepo.deleteById(lesson.getId());
        }

        // Finally, delete the LessonType
        this.lessonTypeRepo.delete(lessonType);
    }


    @Override
    public LessonTypeDto getLessonType(int lessonTypeId) {
        LessonType lessonType = this.lessonTypeRepo.findById(lessonTypeId).orElseThrow(()-> new ResourceNotFoundException("lessonType", "lessonTypeId", (long)lessonTypeId));

        return this.lessonTypeToDto(lessonType);
    }

    @Override
    public List<LessonTypeDto> findByNameLessonType(String keyWord) {
        List<LessonType> lessonTypes = this.lessonTypeRepo.findByNameContaining(keyWord);
        List<LessonTypeDto> lessonTypeDtos = lessonTypes.stream().map(lessonType -> this.lessonTypeToDto(lessonType)).collect(Collectors.toList());

        return lessonTypeDtos;
    }

    public LessonType dtoToLessonType(LessonTypeDto lessonTypeDto){
        return modelMapper.map(lessonTypeDto, LessonType.class);
    }

    public LessonTypeDto lessonTypeToDto(LessonType lessonType){
        return modelMapper.map(lessonType, LessonTypeDto.class);
    }

}
