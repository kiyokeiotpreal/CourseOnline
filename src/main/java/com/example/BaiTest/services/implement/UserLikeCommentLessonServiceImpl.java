package com.example.BaiTest.services.implement;

import com.example.BaiTest.dtos.UserLikeCommentLessonDto;
import com.example.BaiTest.exceptions.ResourceNotFoundException;
import com.example.BaiTest.model.CommentLesson;
import com.example.BaiTest.model.User;
import com.example.BaiTest.model.UserLikeCommentLesson;
import com.example.BaiTest.repository.CommentLessonRepo;
import com.example.BaiTest.repository.UserLikeCommentLessonRepo;
import com.example.BaiTest.repository.UserRepo;
import com.example.BaiTest.responses.UserLikeCommentLessonResponse;
import com.example.BaiTest.services.iservices.UserLikeCommentLessonService;

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
public class UserLikeCommentLessonServiceImpl implements UserLikeCommentLessonService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CommentLessonRepo commentLessonRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserLikeCommentLessonRepo userLikeCommentLessonRepo;

    @Override
    public UserLikeCommentLessonDto createUserLikeCommentLesson(UserLikeCommentLessonDto userLikeCommentLessonDto, int userId, int commentLessonId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "userID",(long) userId));
        CommentLesson commentLesson = this.commentLessonRepo.findById(commentLessonId).orElseThrow(()-> new ResourceNotFoundException("CommentLesson", "commentLessonId", (long)commentLessonId));

        UserLikeCommentLesson userLikeCommentLesson = this.dtoToUserLikeCommentLesson(userLikeCommentLessonDto);
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        userLikeCommentLesson.setCreateAt(currentTimestamp);
        userLikeCommentLesson.setDeleted(false);
        userLikeCommentLesson.setUser(user);
        userLikeCommentLesson.setCommentLesson(commentLesson);

        UserLikeCommentLesson createUserLikeCommentLesson = this.userLikeCommentLessonRepo.save(userLikeCommentLesson);

        return this.userLikeCommentLessonToDto(createUserLikeCommentLesson);
    }

    @Override
    public UserLikeCommentLessonDto updateUserLikeCommentLesson(UserLikeCommentLessonDto userLikeCommentLessonDto, int userLikeCommentLessonId) {
        UserLikeCommentLesson userLikeCommentLesson = this.userLikeCommentLessonRepo.findById(userLikeCommentLessonId).orElseThrow(()-> new ResourceNotFoundException("UserLikeCommentLesson", "userLikeCommentLessonId", (long) userLikeCommentLessonId));
        userLikeCommentLesson.setDeleted(userLikeCommentLessonDto.isDeleted());
        UserLikeCommentLesson updateUserLikeCommentLesson = this.userLikeCommentLessonRepo.save(userLikeCommentLesson);

        return this.userLikeCommentLessonToDto(updateUserLikeCommentLesson);
    }

    @Override
    public void deleteUserLikeCommentLesson(int userLikeCommentLessonId) {
        UserLikeCommentLesson userLikeCommentLesson = this.userLikeCommentLessonRepo.findById(userLikeCommentLessonId).orElseThrow(()-> new ResourceNotFoundException("UserLikeCommentLesson", "userLikeCommentLessonId", (long) userLikeCommentLessonId));
        userLikeCommentLesson.setCommentLesson(null);
        userLikeCommentLesson.setUser(null);
        this.userLikeCommentLessonRepo.delete(userLikeCommentLesson);
    }

    @Override
    public UserLikeCommentLessonResponse getAllUserLikeCommentLesson(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending(): Sort.by(sortBy).descending());

        PageRequest pageable = PageRequest.of(pageNumber, pageSize,sort);

        Page<UserLikeCommentLesson> pageUserLikeCommentLessons = this.userLikeCommentLessonRepo.findAll(pageable);

        List<UserLikeCommentLesson> allUserLessonCheckpoints = pageUserLikeCommentLessons.getContent();

        List<UserLikeCommentLessonDto> userLikeCommentLessonDtos = allUserLessonCheckpoints.stream().map(lesson -> this.userLikeCommentLessonToDto(lesson)).collect(Collectors.toList());

        UserLikeCommentLessonResponse userLikeCommentLessonResponse = new UserLikeCommentLessonResponse();
        userLikeCommentLessonResponse.setContent(userLikeCommentLessonDtos);
        userLikeCommentLessonResponse.setPageNumber(pageUserLikeCommentLessons.getNumber());
        userLikeCommentLessonResponse.setPageSize(pageUserLikeCommentLessons.getSize());
        userLikeCommentLessonResponse.setTotalPage(pageUserLikeCommentLessons.getTotalPages());
        userLikeCommentLessonResponse.setTotalElement(pageUserLikeCommentLessons.getTotalElements());
        userLikeCommentLessonResponse.setLastPage(pageUserLikeCommentLessons.isLast());

        return null;
    }

    public UserLikeCommentLesson dtoToUserLikeCommentLesson(UserLikeCommentLessonDto userLikeCommentLessonDto){
        return modelMapper.map(userLikeCommentLessonDto, UserLikeCommentLesson.class);
    }

    public UserLikeCommentLessonDto userLikeCommentLessonToDto(UserLikeCommentLesson userLikeCommentLesson){
        return modelMapper.map(userLikeCommentLesson, UserLikeCommentLessonDto.class);
    }

}

