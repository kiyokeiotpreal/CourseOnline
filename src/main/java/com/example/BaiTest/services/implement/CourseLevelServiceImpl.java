package com.example.BaiTest.services.implement;

import com.example.BaiTest.dtos.CourseLevelDto;
import com.example.BaiTest.exceptions.ResourceNotFoundException;
import com.example.BaiTest.model.Course;
import com.example.BaiTest.model.CourseLevel;
import com.example.BaiTest.repository.CourseLevelRepo;
import com.example.BaiTest.repository.CourseRepo;
import com.example.BaiTest.services.iservices.CourseLevelService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CourseLevelServiceImpl implements CourseLevelService {

    @Autowired
    private CourseLevelRepo courseLevelRepo;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CourseLevelDto createCourseLevel(CourseLevelDto courseLevelDto) {
        CourseLevel courseLevel = this.dtoToCourseLevel(courseLevelDto);
        CourseLevel createCourseLevel = this.courseLevelRepo.save(courseLevel);
        return this.courseLevelToDto(createCourseLevel);
    }

    @Override
    public CourseLevelDto updateCourseLevel(CourseLevelDto courseLevelDto, int courseLevelId) {
        CourseLevel courseLevel = this.courseLevelRepo.findById(courseLevelId).orElseThrow(()-> new ResourceNotFoundException("CourseLevel", "CourseLevelId", (long)courseLevelId));
        courseLevel.setName(courseLevelDto.getName());
        CourseLevel updateCourseLevel = this.courseLevelRepo.save(courseLevel);
        CourseLevelDto updateCourseLevelDto = this.courseLevelToDto(updateCourseLevel);
        return updateCourseLevelDto;
    }

    @Override
    public void deletedCourseLevel(int courseLevelId) {
        CourseLevel courseLevel = this.courseLevelRepo.findById(courseLevelId).orElseThrow(()-> new ResourceNotFoundException("CourseLevel", "CourseLevelId", (long)courseLevelId));
        for(Course course: courseRepo.findAll()){
            if(course.getCourseLevel().getId() == courseLevelId){
                course.setCourseLevel(null);
                course.setCourseType(null);
                this.courseRepo.deleteById(course.getId());
            }
        }
        this.courseLevelRepo.delete(courseLevel);

    }

    @Override
    public CourseLevelDto getCourseLevel(int courseLevelId) {
        CourseLevel courseLevel = this.courseLevelRepo.findById(courseLevelId).orElseThrow(()-> new ResourceNotFoundException("CourseLevel", "CourseLevelId", (long)courseLevelId));

        return this.courseLevelToDto(courseLevel);
    }

    @Override
    public List<CourseLevelDto> findByNameContaining(String keyword) {
        List<CourseLevel> courseLevels = this.courseLevelRepo.findByNameContaining(keyword);
        List<CourseLevelDto> courseLevelDto = courseLevels.stream().map((courseLevel)-> this.courseLevelToDto(courseLevel)).collect(Collectors.toList());
        return courseLevelDto;
    }

    public CourseLevel dtoToCourseLevel(CourseLevelDto courseLevelDto){
        return this.modelMapper.map(courseLevelDto, CourseLevel.class);
    }

    public CourseLevelDto courseLevelToDto(CourseLevel courseLevel){
        return this.modelMapper.map(courseLevel, CourseLevelDto.class);
    }
}
