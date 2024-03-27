package com.example.BaiTest.services.iservices;

import com.example.BaiTest.dtos.LessonTypeDto;

import java.util.List;

public interface LessonTypeService {
    LessonTypeDto createLessonType(LessonTypeDto lessonTypeDto);
    LessonTypeDto updateLessonType(LessonTypeDto lessonTypeDto, int lessonTypeId);

    void deleteLessonType(int lessonTypeId);

    LessonTypeDto getLessonType(int lessonTypeId);
    List<LessonTypeDto> findByNameLessonType(String keyWord);
}
