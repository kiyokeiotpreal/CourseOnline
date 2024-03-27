package com.example.BaiTest.controllers;

import com.example.BaiTest.dtos.ApiResponse;
import com.example.BaiTest.dtos.UserLikeCommentLessonDto;
import com.example.BaiTest.responses.UserLikeCommentLessonResponse;
import com.example.BaiTest.services.iservices.UserLikeCommentLessonService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/userLikeCommentLesson")
public class UserLikeCommentLessonController {

    @Autowired
    private UserLikeCommentLessonService userLikeCommentLessonService;

    @PostMapping("/add")
    public ResponseEntity<UserLikeCommentLessonDto> createUserLikeCommentLesson(@Valid @RequestBody UserLikeCommentLessonDto userLikeCommentLessonDto, @RequestParam int userId, @RequestParam int commentId){
        UserLikeCommentLessonDto createUserLikeCommentLessonDto = this.userLikeCommentLessonService.createUserLikeCommentLesson(userLikeCommentLessonDto,userId,commentId);
        return new ResponseEntity<UserLikeCommentLessonDto>(userLikeCommentLessonDto, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<UserLikeCommentLessonDto> updateUserLikeCommentLesson(@Valid @RequestBody UserLikeCommentLessonDto userLikeCommentLessonDto,@RequestParam int userLikeCommentLessonId){
        UserLikeCommentLessonDto updateUserLikeCommentLessonDto = this.userLikeCommentLessonService.updateUserLikeCommentLesson(userLikeCommentLessonDto,userLikeCommentLessonId);
        return new ResponseEntity<UserLikeCommentLessonDto>(userLikeCommentLessonDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteUserLikeCommentLesson(@RequestParam int userLikeCommentLessonId){
        this.userLikeCommentLessonService.deleteUserLikeCommentLesson(userLikeCommentLessonId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("UserLikeCommentLesson is delete successfully", true), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<UserLikeCommentLessonResponse> getAllUserLikeCommentLesson(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                                     @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                                     @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                                                     @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir){
        UserLikeCommentLessonResponse userLikeCommentLessonResponse = this.userLikeCommentLessonService.getAllUserLikeCommentLesson(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<UserLikeCommentLessonResponse>(userLikeCommentLessonResponse, HttpStatus.OK);
    }

}
