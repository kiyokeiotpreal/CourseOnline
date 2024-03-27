package com.example.BaiTest.controllers;

import com.example.BaiTest.dtos.ApiResponse;
import com.example.BaiTest.dtos.CourseTypeDto;
import com.example.BaiTest.model.CourseType;
import com.example.BaiTest.repository.CourseTypeRepo;
import com.example.BaiTest.responses.CourseTypeResponse;
import com.example.BaiTest.services.implement.CourseTypeServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/courseType")
public class CourseTypeController {

    @Autowired
    private CourseTypeServiceImpl courseTypeService;
    @Autowired
    private CourseTypeRepo courseTypeRepo;

    @PostMapping("/add")
    public ResponseEntity<CourseTypeDto> createCourseType(@Valid @RequestBody CourseTypeDto courseTypeDto){
        CourseTypeDto createTypeDto = this.courseTypeService.createCourseType(courseTypeDto);
        return new ResponseEntity<CourseTypeDto>(createTypeDto, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<CourseTypeDto> updateCourseType(@Valid @RequestBody CourseTypeDto courseLevelDto, @RequestParam int courseTypeId){
        CourseTypeDto updateCourseTypeDto = this.courseTypeService.updateCourseType(courseLevelDto,courseTypeId);
        return new ResponseEntity<CourseTypeDto>(updateCourseTypeDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteCourseType(@RequestParam int courseTypeId){
        this.courseTypeService.deletedCourseType(courseTypeId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Majors is deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/getId")
    public ResponseEntity<CourseTypeDto> getCourseType(@RequestParam int courseTypeId){
        CourseTypeDto courseLevelDto = this.courseTypeService.getCourseTypeDto(courseTypeId);
        return new ResponseEntity<CourseTypeDto>(courseLevelDto,HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CourseTypeDto>> searchCourseTypeByTitle(@RequestParam String keywords){
        List<CourseTypeDto> courseLevelDtos = this.courseTypeService.findByNameContaining(keywords);
        return new ResponseEntity<List<CourseTypeDto>>(courseLevelDtos, HttpStatus.OK);
    }

    @GetMapping("/top3CourseType")
    public List<Object[]> getTop3CourseType(){
        return courseTypeService.getTop3CourseType();

    }
    @GetMapping("/getAllCourseType")
    public List<CourseType> getAll(){
        return courseTypeRepo.getAll();
    }



}
