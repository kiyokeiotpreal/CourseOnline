package com.example.BaiTest.services.implement;

import com.example.BaiTest.dtos.CourseDto;
import com.example.BaiTest.exceptions.ResourceNotFoundException;
import com.example.BaiTest.model.Course;
import com.example.BaiTest.model.CourseLevel;
import com.example.BaiTest.model.CourseType;
import com.example.BaiTest.model.UserCourse;
import com.example.BaiTest.repository.CourseLevelRepo;
import com.example.BaiTest.repository.CourseRepo;
import com.example.BaiTest.repository.CourseTypeRepo;
import com.example.BaiTest.repository.UserCourseRepo;
import com.example.BaiTest.responses.CourseResponse;
import com.example.BaiTest.services.iservices.CourseService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private CourseLevelRepo courseLevelRepo;

    @Autowired
    private CourseTypeRepo courseTypeRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserCourseRepo userCourseRepo;

    @Override
    public CourseDto createCourse(CourseDto courseDto, int courseLevelId, int courseTypeId) {
        CourseLevel courseLevel = this.courseLevelRepo.findById(courseLevelId).orElseThrow(()-> new ResourceNotFoundException("CourseLevel", "CourseLevelId", (long)courseLevelId));
        CourseType courseType = this.courseTypeRepo.findById(courseTypeId).orElseThrow(()-> new ResourceNotFoundException("CourseType", "CourseTypeId",(long)courseTypeId));

        Course course = this.dtoToCourse(courseDto);
        course.setImageCourse(courseDto.getImageCourse());
        course.setRegisterCount(courseDto.getRegisterCount());
        course.setDoneCount(1);
        course.setCourseLevel(courseLevel);
        course.setCourseType(courseType);


        Course createCourse = this.courseRepo.save(course);
        return this.courseToDto(createCourse);
    }

    @Override
    public CourseDto updateCourse(CourseDto courseDto, int courseId) {
        Course course = this.courseRepo.findById(courseId).orElseThrow(()-> new ResourceNotFoundException("Course", "CourseId", (long)courseId));

        course.setNameOfCourse(courseDto.getNameOfCourse());
        course.setCourseSubTile(courseDto.getCourseSubTile());
        course.setImageCourse(courseDto.getImageCourse());
        course.setLessonCount(courseDto.getLessonCount());
        course.setChapterCount(courseDto.getChapterCount());
        course.setTimeLessonTotal(courseDto.getTimeLessonTotal());
        course.setRegisterCount(courseDto.getRegisterCount());
        course.setDoneCount(courseDto.getDoneCount());
        Course updateCourse = this.courseRepo.save(course);

        return this.courseToDto(updateCourse);
    }

    @Override
    public void deletedCourse(int courseId) {
        Course course = this.courseRepo.findById(courseId).orElseThrow(()-> new ResourceNotFoundException("Course", "CourseId", (long)courseId));
        for(UserCourse userCourse: userCourseRepo.findAll()){
            if(userCourse.getCourse().getId() == courseId){
                userCourse.setCourse(null);
                this.userCourseRepo.deleteById(userCourse.getId());
            }
        }
        this.courseRepo.delete(course);
    }

    @Override
    public CourseResponse getAllCourse(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending(): Sort.by(sortBy).descending());

        PageRequest pageable = PageRequest.of(pageNumber, pageSize,sort);

        Page<Course> pageCourse = this.courseRepo.findAll(pageable);

        List<Course> allCourse = pageCourse.getContent();

        List<CourseDto> courseDtos = allCourse.stream().map((course)-> this.courseToDto(course)).collect(Collectors.toList());

        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setContent(courseDtos);
        courseResponse.setPageNumber(pageCourse.getNumber());
        courseResponse.setPageSize(pageCourse.getSize());
        courseResponse.setTotalElement(pageCourse.getTotalElements());
        courseResponse.setTotalPage(pageCourse.getTotalPages());
        courseResponse.setLastPage(pageCourse.isLast());

        return courseResponse;
    }

    @Override
    public CourseDto getCourse(int courseId) {
        Course course = this.courseRepo.findById(courseId).orElseThrow(()-> new ResourceNotFoundException("Course", "CourseId", (long)courseId));

        return this.courseToDto(course);
    }

    @Override
    public List<CourseDto> getCourseByCourseLevel(int courseLevelId) {
        CourseLevel courseLevel = this.courseLevelRepo.findById(courseLevelId).orElseThrow(()-> new ResourceNotFoundException("CourseLevel", "CourseLevelId", (long)courseLevelId));
        List<Course> courses = this.courseRepo.findByCourseLevel(courseLevel);
        List<CourseDto> coursesDtos = courses.stream().map((course)-> this.courseToDto(course)).collect(Collectors.toList());
        return coursesDtos;
    }

    @Override
    public List<CourseDto> getCourseByCourseType(int courseTypeId) {
        CourseType courseType = this.courseTypeRepo.findById(courseTypeId).orElseThrow(()-> new ResourceNotFoundException("CourseType", "CourseTypeId", (long)courseTypeId));
        List<Course> courses = this.courseRepo.findByCourseType(courseType);
        List<CourseDto> courseDtos = courses.stream().map((course) -> this.courseToDto(course)).collect(Collectors.toList());

        return courseDtos;
    }

    @Override
    public List<CourseDto> findByNameOfCourseContaining(String keyWord) {
        List<Course> courses = this.courseRepo.findByNameOfCourseContaining(keyWord);
        List<CourseDto> courseDtos = courses.stream().map((course)-> this.courseToDto(course)).collect(Collectors.toList());
        return courseDtos;
    }

    @Override
    public long getTotalCourseCount() {
        return courseRepo.count();
    }


    public Course dtoToCourse(CourseDto courseDto){
        return modelMapper.map(courseDto, Course.class);
    }

    public CourseDto courseToDto(Course course){
        return modelMapper.map(course, CourseDto.class);
    }

}
