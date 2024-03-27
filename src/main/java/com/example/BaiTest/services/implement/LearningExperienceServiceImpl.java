package com.example.BaiTest.services.implement;


import com.example.BaiTest.dtos.LearningExperienceDto;
import com.example.BaiTest.exceptions.ResourceNotFoundException;
import com.example.BaiTest.model.LearningExperience;
import com.example.BaiTest.model.Majors;
import com.example.BaiTest.model.User;
import com.example.BaiTest.repository.LearningExperienceRepo;
import com.example.BaiTest.repository.MajorsRepo;
import com.example.BaiTest.repository.UserRepo;
import com.example.BaiTest.responses.LearningExperienceResponse;
import com.example.BaiTest.services.iservices.LearningExperienceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LearningExperienceServiceImpl implements LearningExperienceService {
    @Autowired
    private LearningExperienceRepo learningExperienceRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MajorsRepo majorsRepo;

    @Autowired
    private ModelMapper modelMapper;

//    @Override
//    public LearningExperienceDto createLearningExperience(LearningExperienceDto learningExperienceDto, Long userId, int majorId) {
//        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));
//        Majors majors = this.majorsRepo.findById(majorId).orElseThrow(()-> new ResourceNotFoundException("Majors", "Majors Id", (long) majorId));
//        LearningExperience learningExperience = this.dtoToDLearningExperience(learningExperienceDto);
//
//        learningExperience.setUser(user);
//        learningExperience.setMajors(majors);
//
//
//         LearningExperience createLearningExperience = this.learningExperienceRepo.save(learningExperience);
//        return this.learningExperienceToDto(createLearningExperience);
//    }
//
//    @Override
//    public LearningExperienceDto updateLearningExperience(LearningExperienceDto learningExperienceDto, int learningExperienceDtoId) {
//        LearningExperience learningExperience = this.learningExperienceRepo.findById(learningExperienceDtoId).orElseThrow(()-> new ResourceNotFoundException("LearningExperience", "LearningExperience Id", (long) learningExperienceDtoId));
//
//        learningExperience.setFromDate(learningExperienceDto.getFromDate());
//        learningExperience.setToDate(learningExperienceDto.getToDate());
//        LearningExperience updateLearningExperience = this.learningExperienceRepo.save(learningExperience);
//        return this.learningExperienceToDto(updateLearningExperience);
//    }




    @Override
    public LearningExperienceDto createLearningExperience(LearningExperienceDto learningExperienceDto, int userId, int majorId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User Id", (long)userId));
        Majors majors = this.majorsRepo.findById(majorId).orElseThrow(() -> new ResourceNotFoundException("Majors", "Majors Id", (long) majorId));
        LearningExperience learningExperience = this.dtoToDLearningExperience(learningExperienceDto);

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        learningExperience.setFromDate(currentTimestamp);

// Adding 20 days (or any other duration) to the current timestamp
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimestamp.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, 20);
        Timestamp toDate = new Timestamp(calendar.getTimeInMillis());
        learningExperience.setToDate(toDate);

//        Timestamp fromDate = learningExperience.getFromDate();
//        Timestamp toDate = learningExperience.getToDate();

        learningExperience.setUser(user);
        learningExperience.setMajors(majors);

        LearningExperience createdLearningExperience = this.learningExperienceRepo.save(learningExperience);
        return this.learningExperienceToDto(createdLearningExperience);
    }

    @Override
    public LearningExperienceDto updateLearningExperience(LearningExperienceDto learningExperienceDto, int learningExperienceDtoId) {
        LearningExperience learningExperience = this.learningExperienceRepo.findById(learningExperienceDtoId).orElseThrow(() -> new ResourceNotFoundException("LearningExperience", "LearningExperience Id", (long) learningExperienceDtoId));

        Timestamp fromDate = learningExperience.getFromDate();
        Timestamp toDate = learningExperience.getToDate();

        if (fromDate.after(toDate)) {
            throw new IllegalArgumentException("fromDate cannot be after toDate");
        }

        learningExperience.setFromDate(fromDate);
        learningExperience.setToDate(toDate);

        LearningExperience updatedLearningExperience = this.learningExperienceRepo.save(learningExperience);
        return this.learningExperienceToDto(updatedLearningExperience);
    }



    @Override
    public void deleteLearningExperience(int learningExperienceId) {
        LearningExperience learningExperience1 = this.learningExperienceRepo.findById(learningExperienceId).orElseThrow(()-> new ResourceNotFoundException("learningExperience", "learningExperienceId", (long)learningExperienceId));
        this.learningExperienceRepo.delete(learningExperience1);


        }


    @Override
    public LearningExperienceResponse getAllLearningExperience(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending(): Sort.by(sortBy).descending());

        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<LearningExperience> pageLearningExperience = this.learningExperienceRepo.findAll(pageable);

        List<LearningExperience> allLearningExperiences = pageLearningExperience.getContent();

        List<LearningExperienceDto> learningExperienceDto = allLearningExperiences.stream().map((learningExperience) -> this.learningExperienceToDto(learningExperience)).collect(Collectors.toList());

        LearningExperienceResponse learningExperienceResponse = new LearningExperienceResponse();
        learningExperienceResponse.setContent(learningExperienceDto);
        learningExperienceResponse.setPageNumber(pageLearningExperience.getNumber());
        learningExperienceResponse.setPageSize(pageLearningExperience.getSize());
        learningExperienceResponse.setTotalElement(pageLearningExperience.getTotalElements());
        learningExperienceResponse.setTotalPage(pageLearningExperience.getTotalPages());
        learningExperienceResponse.setLastPage(pageLearningExperience.isLast());

        return learningExperienceResponse;
    }

    @Override
    public LearningExperienceDto getLearningExperienceById(int learningExperienceId) {
        LearningExperience learningExperience = this.learningExperienceRepo.findById(learningExperienceId).orElseThrow(()-> new ResourceNotFoundException("LearningExperience", "Id", (long)learningExperienceId));
        return this.learningExperienceToDto(learningExperience);
    }

    @Override
    public List<LearningExperienceDto> getLearningExperienceByUser(int userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "UserID", (long)userId));
        List<LearningExperience> learningExperiences = this.learningExperienceRepo.findByUser(user);
        List<LearningExperienceDto> learningExperienceDto = learningExperiences.stream().map((learningExperience) -> this.learningExperienceToDto(learningExperience)).collect(Collectors.toList());
        return learningExperienceDto;
    }

    @Override
    public List<LearningExperienceDto> getLearningExperienceByMajors(int majorId) {
        Majors majors = this.majorsRepo.findById(majorId).orElseThrow(()-> new ResourceNotFoundException("Majors", "MajorId", (long) majorId));
        List<LearningExperience> learningExperiences = this.learningExperienceRepo.findByMajors(majors);
        List<LearningExperienceDto> learningExperienceDto = learningExperiences.stream().map((learningExperience)-> this.learningExperienceToDto(learningExperience)).collect(Collectors.toList());
        return learningExperienceDto;
    }

    public LearningExperience dtoToDLearningExperience(LearningExperienceDto learningExperienceDto){
        return this.modelMapper.map(learningExperienceDto, LearningExperience.class);
    }

    public LearningExperienceDto learningExperienceToDto(LearningExperience learningExperience){
        return this.modelMapper.map(learningExperience, LearningExperienceDto.class);
    }

}
