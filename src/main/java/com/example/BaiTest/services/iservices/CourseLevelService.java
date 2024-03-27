package com.example.BaiTest.services.iservices;



import com.example.BaiTest.dtos.CourseLevelDto;

import java.util.List;

public interface CourseLevelService {
    CourseLevelDto createCourseLevel(CourseLevelDto courseLevelDto);
    CourseLevelDto updateCourseLevel(CourseLevelDto courseLevelDto, int courseLevelId);
    void deletedCourseLevel(int courseLevelId);
    CourseLevelDto getCourseLevel(int courseLevelId);

    public List<CourseLevelDto> findByNameContaining(String keyword);

}
