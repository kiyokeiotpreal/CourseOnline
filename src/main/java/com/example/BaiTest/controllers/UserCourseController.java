package com.example.BaiTest.controllers;

import com.example.BaiTest.dtos.ApiResponse;
import com.example.BaiTest.dtos.UserCourseDto;
import com.example.BaiTest.responses.UserCourseResponse;
import com.example.BaiTest.services.iservices.UserCourseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/userCourse")
@RequiredArgsConstructor
public class UserCourseController {

    @Autowired
    private UserCourseService userCourseService;

    @PostMapping("/add")
    public ResponseEntity<UserCourseDto> createUserCourse(@Valid @RequestBody UserCourseDto userCourseDto,
                                                          @RequestParam int userId,
                                                          @RequestParam int courseId){
        UserCourseDto createUserCourseDto = this.userCourseService.createUserCourse(userCourseDto,userId, courseId);
        return new ResponseEntity<UserCourseDto>(createUserCourseDto, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<UserCourseDto> updateUserCourse(@Valid @RequestBody UserCourseDto userCourseDto, @RequestParam int userCourseId){
        UserCourseDto updateUserCourseDto = this.userCourseService.updateUserCourse(userCourseDto,userCourseId);
        return new ResponseEntity<UserCourseDto>(updateUserCourseDto, HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<ApiResponse> deleteUserCourse(@RequestParam int userCourseId){
        this.userCourseService.deleteUserCourse(userCourseId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("UserCourse is deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<UserCourseResponse> getAllUserCourseDtoResponseEntity(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                                @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                                @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                                                @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir){
        UserCourseResponse userCourseResponse = this.userCourseService.getAllUserCourse(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<UserCourseResponse>(userCourseResponse, HttpStatus.OK);
    }

}
