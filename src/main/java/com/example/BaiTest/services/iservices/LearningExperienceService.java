package com.example.BaiTest.services.iservices;

import com.example.BaiTest.dtos.LearningExperienceDto;
import com.example.BaiTest.responses.LearningExperienceResponse;

import java.util.List;

public interface LearningExperienceService {
    LearningExperienceDto createLearningExperience(LearningExperienceDto learningExperienceDto, int userId, int majorId);
    LearningExperienceDto updateLearningExperience(LearningExperienceDto learningExperienceDto, int learningExperienceDtoId);
    void deleteLearningExperience(int learningExperienceId);
    LearningExperienceResponse getAllLearningExperience(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    LearningExperienceDto getLearningExperienceById(int learningExperienceId);
    List<LearningExperienceDto> getLearningExperienceByUser(int userId);
    List<LearningExperienceDto> getLearningExperienceByMajors(int majorId);


}
