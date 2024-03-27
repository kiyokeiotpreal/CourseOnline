package com.example.BaiTest.controllers;

import com.example.BaiTest.dtos.ApiResponse;
import com.example.BaiTest.dtos.CommentLessonDto;
import com.example.BaiTest.responses.CommentLessonResponse;
import com.example.BaiTest.services.iservices.CommentLessonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/commentLesson")
public class CommentLessonController {

    @Autowired
    private CommentLessonService commentLessonService;

    @PostMapping("/add")
    public ResponseEntity<CommentLessonDto> createCommentLesson(@Valid @RequestBody CommentLessonDto commentLessonDto, @RequestParam int userId, @RequestParam int lessonId){
        CommentLessonDto createLessonDto = this.commentLessonService.createCommentLesson(commentLessonDto,userId,lessonId);
        return new ResponseEntity<CommentLessonDto>(commentLessonDto, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<CommentLessonDto> updateCommentLesson(@Valid @RequestBody CommentLessonDto commentLessonDto, @RequestParam int commentLessonId){
        CommentLessonDto updateCommentLessonDto = this.commentLessonService.updateCommentLesson(commentLessonDto, commentLessonId);
        return new ResponseEntity<CommentLessonDto>(commentLessonDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteCommentLesson(@RequestParam int commentLessonId){
        this.commentLessonService.deleteCommentLesson(commentLessonId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("CommentLesson is delete successfully", true), HttpStatus.OK);
    }

    @GetMapping("/getId")
    public ResponseEntity<CommentLessonDto> getCommentLesson(@RequestParam int commentLessonId){
        CommentLessonDto commentLessonDto = this.commentLessonService.getCommentLesson(commentLessonId);
        return new ResponseEntity<CommentLessonDto>(commentLessonDto, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<CommentLessonResponse> getAllCommentLesson(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                     @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                     @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                                     @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir){
        CommentLessonResponse commentLessonResponse = this.commentLessonService.getAllCommentLesson(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<CommentLessonResponse>(commentLessonResponse, HttpStatus.OK);
    }

    @GetMapping("/getUserId/getCommentLesson")
    public ResponseEntity<List<CommentLessonDto>> getCommentLessonByUser(@RequestParam int userId){
        List<CommentLessonDto> commentLessonDtos = this.commentLessonService.getCommentLessonByUser(userId);
        return new ResponseEntity<List<CommentLessonDto>>(commentLessonDtos, HttpStatus.OK);
    }

    @GetMapping("/LessonId/getCommentLesson")
    public ResponseEntity<List<CommentLessonDto>> getCommentLessonByLesson(@RequestParam int lessonId){
        List<CommentLessonDto> commentLessonDtos = this.commentLessonService.getCommentLessonByLesson(lessonId);
        return new ResponseEntity<List<CommentLessonDto>>(commentLessonDtos, HttpStatus.OK);
    }

    @PutMapping("/like")
    public ResponseEntity<CommentLessonDto> likeCommentLesson(@RequestParam int commentLessonId, @RequestParam int userId) {
        CommentLessonDto updatedCommentLesson = commentLessonService.handleLikeAction(commentLessonId, true, userId);
        return ResponseEntity.ok(updatedCommentLesson);
    }

    @PutMapping("/dislike")
    public ResponseEntity<CommentLessonDto> dislikeCommentLesson(@RequestParam int commentLessonId, @RequestParam int userId) {
        CommentLessonDto updatedCommentLesson = commentLessonService.handleLikeAction(commentLessonId, false, userId);
        return ResponseEntity.ok(updatedCommentLesson);
    }
}
