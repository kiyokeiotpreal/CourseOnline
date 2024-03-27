package com.example.BaiTest.services.implement;


import com.example.BaiTest.dtos.CourseTypeDto;
import com.example.BaiTest.exceptions.ResourceNotFoundException;
import com.example.BaiTest.model.Course;
import com.example.BaiTest.model.CourseType;
import com.example.BaiTest.repository.CourseRepo;
import com.example.BaiTest.repository.CourseTypeRepo;
import com.example.BaiTest.responses.CourseTypeResponse;
import com.example.BaiTest.services.iservices.CourseTypeService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseTypeServiceImpl implements CourseTypeService {


    @Autowired
    private CourseTypeRepo courseTypeRepo;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CourseTypeDto createCourseType(CourseTypeDto courseTypeDto) {
        CourseType courseType = this.dtoToCourseType(courseTypeDto);
        CourseType createCourseType = this.courseTypeRepo.save(courseType);
        return this.courseTypeToDto(createCourseType);
    }

    @Override
    public CourseTypeDto updateCourseType(CourseTypeDto courseLevelDto, int courseTypeId) {
        CourseType courseType = this.courseTypeRepo.findById(courseTypeId).orElseThrow(()-> new ResourceNotFoundException("CourseType", "CourseTypeId", (long)courseTypeId));
        courseType.setName(courseLevelDto.getName());
        CourseType updateCourseType = this.courseTypeRepo.save(courseType);
        CourseTypeDto updateCourseTypeDto = this.courseTypeToDto(updateCourseType);
        return updateCourseTypeDto;
    }

    @Override
    public void deletedCourseType(int courseTypeId) {
        CourseType courseType = this.courseTypeRepo.findById(courseTypeId).orElseThrow(()-> new ResourceNotFoundException("CourseType", "CourseTypeId", (long)courseTypeId));
        for (Course course: courseRepo.findAll()){
            if(course.getCourseType().getId() == courseTypeId){
                course.setCourseType(null);
                course.setCourseLevel(null);
                this.courseRepo.deleteById(course.getId());
            }
        }
        this.courseTypeRepo.delete(courseType);
    }

    @Override
    public CourseTypeDto getCourseTypeDto(int courseTypeId) {
        CourseType courseType = this.courseTypeRepo.findById(courseTypeId).orElseThrow(()-> new ResourceNotFoundException("CourseType", "CourseTypeId", (long)courseTypeId));
        return this.courseTypeToDto(courseType);
    }

    @Override
    public List<CourseTypeDto> findByNameContaining(String keyWord) {
        List<CourseType> courseTypes = this.courseTypeRepo.findByNameContaining(keyWord);
        List<CourseTypeDto> courseTypeDtos = courseTypes.stream().map((courseType)-> this.courseTypeToDto(courseType)).collect(Collectors.toList());
        return courseTypeDtos;
    }

    @Override
    public List<Object[]> getTop3CourseType() {
        return courseTypeRepo.getTop3CourseTypes();


    }


    public CourseType dtoToCourseType(CourseTypeDto courseTypeDto){
        return modelMapper.map(courseTypeDto, CourseType.class);
    }

    public CourseTypeDto courseTypeToDto(CourseType courseType){
        return modelMapper.map(courseType, CourseTypeDto.class);
    }


}
