package com.example.BaiTest.controllers;

import com.example.BaiTest.dtos.ApiResponse;
import com.example.BaiTest.dtos.LessonDto;
import com.example.BaiTest.responses.LessonResponse;

import com.example.BaiTest.services.iservices.LessonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/lesson")
public class LessonController {
    @Autowired
    private LessonService lessonService;

    @PostMapping("/addLesson")
    public ResponseEntity<LessonDto> createLesson(@Valid @RequestBody LessonDto lessonDto, @RequestParam int lessonTypeId){
        LessonDto createLessonDto = this.lessonService.createLesson(lessonDto,lessonTypeId);
        return new ResponseEntity<LessonDto>(createLessonDto, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<LessonDto> updateLesson(@Valid @RequestBody LessonDto lessonDto, @RequestParam int lessonId){
        LessonDto updateLesson = this.lessonService.updateLessonDto(lessonDto,lessonId);
        return new ResponseEntity<LessonDto>(updateLesson, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteLesson(@RequestParam int lessonId){
        this.lessonService.deleteLesson(lessonId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Lesson is deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/getId")
    public ResponseEntity<LessonDto> getLesson(@RequestParam int lessonId){
        LessonDto lessonDto = this.lessonService.getLesson(lessonId);
        return new ResponseEntity<LessonDto>(lessonDto, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<LessonResponse> getAllLesson(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                       @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                       @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                       @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir){
        LessonResponse lessonResponse = this.lessonService.getAllLesson(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<LessonResponse>(lessonResponse, HttpStatus.OK);
    }

    @GetMapping("/getaLessonTypeId/getLesson")
    public ResponseEntity<List<LessonDto>> getLessonByLessonType(@RequestParam int lessonTypeId){
        List<LessonDto> lessonDtos = this.lessonService.getLessonByLessonType(lessonTypeId);
        return new ResponseEntity<List<LessonDto>>(lessonDtos, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<LessonDto>> searchLesson(@RequestParam String keywords){
        List<LessonDto> lessonDtos = this.lessonService.findByNameOfLessonContaining(keywords);
        return new ResponseEntity<List<LessonDto>>(lessonDtos, HttpStatus.OK);
    }
}
