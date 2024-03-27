package com.example.BaiTest.services.iservices;


import com.example.BaiTest.dtos.CourseTypeDto;
import com.example.BaiTest.responses.CourseTypeResponse;

import java.util.List;

public interface CourseTypeService {
    CourseTypeDto createCourseType(CourseTypeDto courseTypeDto);

    void deletedCourseType(int courseTypeId);
    CourseTypeDto getCourseTypeDto(int courseTypeId);

    CourseTypeDto updateCourseType (CourseTypeDto courseLevelDto, int courseTypeId);
    List<CourseTypeDto> findByNameContaining(String keyWord);
    List<Object[]> getTop3CourseType();



}
