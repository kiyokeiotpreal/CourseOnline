package com.example.BaiTest.services.iservices;



import com.example.BaiTest.dtos.CourseDto;
import com.example.BaiTest.model.Course;
import com.example.BaiTest.responses.CourseResponse;

import java.util.List;

public interface CourseService {
    CourseDto createCourse(CourseDto courseDto, int courseLevelId, int courseTypeId);
    CourseDto updateCourse(CourseDto courseDto, int courseId);
    void deletedCourse(int courseId);
    CourseResponse getAllCourse(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    CourseDto getCourse(int courseId);
    List<CourseDto> getCourseByCourseLevel(int courseLevelId);
    List<CourseDto> getCourseByCourseType(int courseTypeId);
    List<CourseDto> findByNameOfCourseContaining(String keyWord);
    long getTotalCourseCount();

}
