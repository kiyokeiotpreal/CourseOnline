package com.example.BaiTest.services.iservices;


import com.example.BaiTest.dtos.UserLikeCommentLessonDto;
import com.example.BaiTest.responses.UserLikeCommentLessonResponse;

public interface UserLikeCommentLessonService {
    UserLikeCommentLessonDto createUserLikeCommentLesson(UserLikeCommentLessonDto userLikeCommentLessonDto, int userId, int commentLessonId);
    UserLikeCommentLessonDto updateUserLikeCommentLesson(UserLikeCommentLessonDto userLikeCommentLessonDto, int userLikeCommentLessonId);
    void deleteUserLikeCommentLesson(int userLikeCommentLessonId);
    UserLikeCommentLessonResponse getAllUserLikeCommentLesson(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

}
