package com.example.BaiTest.controllers;

import com.example.BaiTest.dtos.ApiResponse;
import com.example.BaiTest.dtos.UserLessonCheckPointDto;
import com.example.BaiTest.responses.UserLessonCheckPointResponse;
import com.example.BaiTest.services.iservices.UserLessonCheckPointService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/userLessonCheckPoint")
public class UserLessonCheckPointController {

    @Autowired
    private UserLessonCheckPointService userLessonCheckPointService;

    @PostMapping("/add")
    public ResponseEntity<UserLessonCheckPointDto> createUserLessonCheckPoint(@Valid @RequestBody UserLessonCheckPointDto userLessonCheckPointDto, @RequestParam int userId, @RequestParam int lessonId){
        UserLessonCheckPointDto createUserLessonCheckPointDto = this.userLessonCheckPointService.createUserLessonCheckpoint(userLessonCheckPointDto,userId,lessonId);
        return new ResponseEntity<UserLessonCheckPointDto>(createUserLessonCheckPointDto, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<UserLessonCheckPointDto> updateUserLessonCheckPointDto(@Valid @RequestBody UserLessonCheckPointDto userLessonCheckPointDto, int userLessonCheckPointId){
        UserLessonCheckPointDto updateUserLessonCheckPoint = this.userLessonCheckPointService.updateUserLessonCheckPoint(userLessonCheckPointDto,userLessonCheckPointId);
        return new ResponseEntity<UserLessonCheckPointDto>(updateUserLessonCheckPoint, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteUserLessonCheckPoint(int userLessonCheckPointId){
        this.userLessonCheckPointService.deleteUserLessonCheckPoint(userLessonCheckPointId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("UserlessonCheckPoint is deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<UserLessonCheckPointResponse> getAllUserLessonCheckPoint(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                                   @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                                   @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                                                   @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir){
        UserLessonCheckPointResponse userLessonCheckPointResponse = this.userLessonCheckPointService.getAllUserLessonCheckPoint(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<UserLessonCheckPointResponse>(userLessonCheckPointResponse, HttpStatus.OK);
    }
}
