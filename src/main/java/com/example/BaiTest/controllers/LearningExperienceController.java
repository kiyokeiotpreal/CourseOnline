package com.example.BaiTest.controllers;

import com.example.BaiTest.dtos.ApiResponse;
import com.example.BaiTest.dtos.LearningExperienceDto;
import com.example.BaiTest.responses.LearningExperienceResponse;
import com.example.BaiTest.services.iservices.LearningExperienceService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/learningExperience")
public class LearningExperienceController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LearningExperienceService learningExperienceService;

    @PostMapping("/add")
    public ResponseEntity<LearningExperienceDto> createLearningExperience(@Valid @RequestBody LearningExperienceDto learningExperienceDto,
                                                                          @RequestParam int userId,
                                                                          @RequestParam int majorId){
        LearningExperienceDto createLearningExperienceDto = this.learningExperienceService.createLearningExperience(learningExperienceDto,userId,majorId);
        return new ResponseEntity<LearningExperienceDto>(createLearningExperienceDto, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<LearningExperienceDto> updateLearningExperience(@Valid @RequestBody LearningExperienceDto learningExperienceDto, @RequestParam int learningExperienceId){
        LearningExperienceDto updateLearningExperienceDto = this.learningExperienceService.updateLearningExperience(learningExperienceDto, learningExperienceId);
        return new ResponseEntity<LearningExperienceDto>(updateLearningExperienceDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deletedLearningExperience(@RequestParam int learningExperienceId){
        this.learningExperienceService.deleteLearningExperience(learningExperienceId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("LearningExperience is deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/getUser/getLearningExperience")
    public ResponseEntity<List<LearningExperienceDto>> getLearningExperienceByUser(@RequestParam int userId){
        List<LearningExperienceDto> learningExperienceDtos = this.learningExperienceService.getLearningExperienceByUser(userId);
        return new ResponseEntity<List<LearningExperienceDto>>(learningExperienceDtos, HttpStatus.OK);
    }

    @GetMapping("/getMajors/getLearningExperience")
    public ResponseEntity<List<LearningExperienceDto>> getLearningExperienceByMajors(@RequestParam int majorId){
        List<LearningExperienceDto> learningExperienceDtos = this.learningExperienceService.getLearningExperienceByMajors(majorId);
        return new ResponseEntity<List<LearningExperienceDto>>(learningExperienceDtos, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<LearningExperienceResponse> getAllLearningExperience(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                               @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                                               @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String  sortBy,
                                                                               @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String  sortDir){
        LearningExperienceResponse learningExperienceResponse = this.learningExperienceService.getAllLearningExperience(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<LearningExperienceResponse>(learningExperienceResponse, HttpStatus.OK);
    }

    @GetMapping("/getLearningExperienceID")
    public ResponseEntity<LearningExperienceDto> getLearningExperienceId(@RequestParam int learningExperienceId){
        LearningExperienceDto learningExperienceDto = this.learningExperienceService.getLearningExperienceById(learningExperienceId);
        return new ResponseEntity<LearningExperienceDto>(learningExperienceDto, HttpStatus.OK);
    }

    




}
