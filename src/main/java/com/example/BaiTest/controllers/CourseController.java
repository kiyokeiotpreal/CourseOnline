package com.example.BaiTest.controllers;


import com.example.BaiTest.dtos.ApiResponse;
import com.example.BaiTest.dtos.CourseDto;
import com.example.BaiTest.responses.CourseResponse;
import com.example.BaiTest.services.iservices.CourseService;
import com.example.BaiTest.services.iservices.FileService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/add")
    public ResponseEntity<CourseDto> createCourse(@Valid @RequestBody CourseDto courseDto,
                                                  @RequestParam int courseLevelId,
                                                  @RequestParam int courseTypeId){
        CourseDto createCourseDto = this.courseService.createCourse(courseDto, courseLevelId, courseTypeId);
        return new ResponseEntity<CourseDto>(createCourseDto, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<CourseDto> updateCourse(@Valid @RequestBody CourseDto courseDto,@RequestParam int courseId){
        CourseDto updateCourseDto = this.courseService.updateCourse(courseDto, courseId);
        return new ResponseEntity<CourseDto>(updateCourseDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteCourse(@RequestParam int courseId){
        this.courseService.deletedCourse(courseId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Course is deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/getCourseLevel/Course")
    public ResponseEntity<List<CourseDto>> getCourseByCourseLevel(@RequestParam int courseLevelId){
        List<CourseDto> courseDtos = this.courseService.getCourseByCourseLevel(courseLevelId);
        return new ResponseEntity<List<CourseDto>>(courseDtos, HttpStatus.OK);
    }

    @GetMapping("/getCourseType/Course")
    public ResponseEntity<List<CourseDto>> getCourseByCourseType(@RequestParam int courseTypeId){
        List<CourseDto> courseDtos = this.courseService.getCourseByCourseType(courseTypeId);
        return new ResponseEntity<List<CourseDto>>(courseDtos, HttpStatus.OK);
    }

    @GetMapping("/getAllCourse")
    public ResponseEntity<CourseResponse> getAllCourse(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                       @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                       @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                       @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir){
        CourseResponse courseResponse = this.courseService.getAllCourse(pageNumber,pageSize, sortBy, sortDir);
        return new ResponseEntity<CourseResponse>(courseResponse, HttpStatus.OK);
    }

    @GetMapping("/getCourseId")
    public ResponseEntity<CourseDto> getCourseId(@RequestParam int courseId){
        CourseDto courseDto = this.courseService.getCourse(courseId);
        return new ResponseEntity<CourseDto>(courseDto, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CourseDto>> searchCourseByNameOfCourse(@RequestParam String keyword){
        List<CourseDto> courseDtos = this.courseService.findByNameOfCourseContaining(keyword);
        return new ResponseEntity<List<CourseDto>>(courseDtos, HttpStatus.OK);
    }

    @PostMapping("/image/upload")
    private ResponseEntity<CourseDto> uploadImage(@RequestParam("image") MultipartFile image,
                                                @RequestParam int Id) throws IOException {

        CourseDto courseDto = this.courseService.getCourse(Id);

        String fileName = this.fileService.uploadImage(path, image);

        courseDto.setImageCourse(fileName);

        CourseDto updateCourse = this.courseService.updateCourse(courseDto,Id);

        return new ResponseEntity<CourseDto>(updateCourse, HttpStatus.OK);
    }

    @GetMapping(value = "/profiles/imageName", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downLoadImage(@RequestParam String imageName,
                              HttpServletResponse response) throws IOException{
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
    @GetMapping("/total-course-count")
    public  ResponseEntity<?> getTotalCourseCount(){
        try {
            var result = courseService.getTotalCourseCount();
            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error serve");
        }
    }

}
