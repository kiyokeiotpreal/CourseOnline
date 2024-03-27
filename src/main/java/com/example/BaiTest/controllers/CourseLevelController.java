package com.example.BaiTest.controllers;

import com.example.BaiTest.dtos.ApiResponse;
import com.example.BaiTest.dtos.CourseLevelDto;
import com.example.BaiTest.services.iservices.CourseLevelService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/courseLevel")
public class CourseLevelController {
    @Autowired
    private CourseLevelService courseLevelService;

    @PostMapping("/add")
    public ResponseEntity<CourseLevelDto> createCourseLevel(@Valid @RequestBody CourseLevelDto courseLevelDto){
        CourseLevelDto createCourseLevel = this.courseLevelService.createCourseLevel(courseLevelDto);
        return new ResponseEntity<CourseLevelDto>(createCourseLevel, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<CourseLevelDto> updateCourseLevel(@Valid @RequestBody CourseLevelDto courseLevelDto, @RequestParam int courseLevelId){
        CourseLevelDto updateCourseLevelDto = this.courseLevelService.updateCourseLevel(courseLevelDto,courseLevelId);
        return new ResponseEntity<CourseLevelDto>(updateCourseLevelDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteCourseLevel(@RequestParam int courseLevelId){
        this.courseLevelService.deletedCourseLevel(courseLevelId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Majors is deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/getId")
    public ResponseEntity<CourseLevelDto> getCourseLevel(@RequestParam int courseLevelId){
        CourseLevelDto courseLevelDto = this.courseLevelService.getCourseLevel(courseLevelId);
        return new ResponseEntity<CourseLevelDto>(courseLevelDto,HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CourseLevelDto>> searchCourseLevelByName(@RequestParam String keywords){
        List<CourseLevelDto> courseLevelDtos = this.courseLevelService.findByNameContaining(keywords);
        return new ResponseEntity<List<CourseLevelDto>>(courseLevelDtos, HttpStatus.OK);
    }

}
