package com.example.BaiTest.services.iservices;


import com.example.BaiTest.dtos.UserCourseDto;
import com.example.BaiTest.responses.UserCourseResponse;

public interface UserCourseService {

    UserCourseDto createUserCourse(UserCourseDto userCourseDto, int userId, int courseId);
    UserCourseDto updateUserCourse(UserCourseDto userCourseDto, int userCourseId);
    void deleteUserCourse(int userCourseId);
    UserCourseResponse getAllUserCourse(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

}
