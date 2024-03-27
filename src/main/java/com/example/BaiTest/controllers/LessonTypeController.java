package com.example.BaiTest.controllers;

import com.example.BaiTest.dtos.ApiResponse;
import com.example.BaiTest.dtos.LessonTypeDto;
import com.example.BaiTest.services.iservices.LessonTypeService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/lessonType")
public class LessonTypeController {

    @Autowired
    private LessonTypeService lessonTypeService;

    @PostMapping("/add")
    public ResponseEntity<LessonTypeDto> createLessonType(@Valid @RequestBody LessonTypeDto lessonTypeDto){
        LessonTypeDto createLessonTypeDto = this.lessonTypeService.createLessonType(lessonTypeDto);
        return new ResponseEntity<LessonTypeDto>(createLessonTypeDto, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<LessonTypeDto> updateLessonType(@Valid @RequestBody LessonTypeDto lessonTypeDto,@RequestParam int lessonTypeId){
        LessonTypeDto updateLessonTypeDto = this.lessonTypeService.updateLessonType(lessonTypeDto,lessonTypeId);
        return new ResponseEntity<LessonTypeDto>(updateLessonTypeDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteLessonType(@RequestParam int lessonTypeId){
        this.lessonTypeService.deleteLessonType(lessonTypeId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("LessonType is deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/getId")
    public ResponseEntity<LessonTypeDto> getLessonType(@RequestParam int lessonTypeId){
        LessonTypeDto lessonTypeDto = this.lessonTypeService.getLessonType(lessonTypeId);
        return new ResponseEntity<>(lessonTypeDto, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<LessonTypeDto>> searchLessonType(@RequestParam String keywords){
        List<LessonTypeDto> lessonTypeDtos = this.lessonTypeService.findByNameLessonType(keywords);
        return new ResponseEntity<List<LessonTypeDto>>(lessonTypeDtos, HttpStatus.OK);
    }
}
