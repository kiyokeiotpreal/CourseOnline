package com.example.BaiTest.services.iservices;

import com.example.BaiTest.dtos.CommentLessonDto;
import com.example.BaiTest.responses.CommentLessonResponse;

import java.util.List;

public interface CommentLessonService {
    CommentLessonDto createCommentLesson(CommentLessonDto commentLessonDto, int userId, int lessonId);
    CommentLessonDto updateCommentLesson(CommentLessonDto commentLessonDto, int commentLessonId);
    void deleteCommentLesson(int commentLessonId);
    CommentLessonDto getCommentLesson(int commentLessonId);
    CommentLessonResponse getAllCommentLesson(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    CommentLessonDto handleLikeAction(int commentLessonId, boolean like, int userId);
    List<CommentLessonDto> getCommentLessonByUser(int userId);
    List<CommentLessonDto> getCommentLessonByLesson(int lessonId);
}
