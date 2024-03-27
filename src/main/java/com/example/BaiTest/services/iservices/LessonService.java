package com.example.BaiTest.services.iservices;

import com.example.BaiTest.dtos.LessonDto;
import com.example.BaiTest.responses.LessonResponse;


import java.util.List;

public interface LessonService {
    LessonDto createLesson(LessonDto lessonDto, int lessonTypeId);
    LessonDto updateLessonDto(LessonDto lessonDto, int lessonId);
    void deleteLesson(int lessonId);
    LessonDto getLesson(int lessonId);
    LessonResponse getAllLesson(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    List<LessonDto> getLessonByLessonType(int lessonTypeId);
    List<LessonDto> findByNameOfLessonContaining(String keyWord);
}
