package com.example.BaiTest.services.implement;

import com.example.BaiTest.dtos.MajorsDto;
import com.example.BaiTest.exceptions.ResourceNotFoundException;
import com.example.BaiTest.model.LearningExperience;
import com.example.BaiTest.model.Majors;
import com.example.BaiTest.repository.LearningExperienceRepo;
import com.example.BaiTest.repository.MajorsRepo;
import com.example.BaiTest.services.iservices.MajorsService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MajorsServiceImpl implements MajorsService {

    @Autowired
    private MajorsRepo majorsRepo;

    @Autowired
    private LearningExperienceRepo learningExperienceRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MajorsDto createMajors(MajorsDto majorsDto) {
        Majors majors = this.dtoToMajors(majorsDto);
        Majors createMajor = this.majorsRepo.save(majors);
        return this.majorsToDto(createMajor);
    }

    @Override
    public MajorsDto updateMajors(MajorsDto majorsDto, int majorsID) {
        Majors majors = this.majorsRepo.findById(majorsID).orElseThrow(()-> new ResourceNotFoundException("Majors", "majors ID", Long.valueOf(majorsID)));
        majors.setName(majorsDto.getName());
        Majors updateMajor = this.majorsRepo.save(majors);
        MajorsDto majorsDto1 = this.majorsToDto(updateMajor);
        return majorsDto1;
    }

    @Override
    public void deleteMajors(int majorsID) {
        Majors majors = this.majorsRepo.findById(majorsID).orElseThrow(()-> new ResourceNotFoundException("Majors", "Majors ID", (long) majorsID));

        for (LearningExperience learningExperience : learningExperienceRepo.findAll()) {
            if (learningExperience.getMajors().getId() == majorsID) {
                learningExperience.setMajors(null);
                this.learningExperienceRepo.deleteById(learningExperience.getId());
            }
        }
        this.majorsRepo.delete(majors);

    }

    @Override
    public MajorsDto getMajors(int majorsId) {
        Majors majors = this.majorsRepo.findById(majorsId).orElseThrow(()-> new ResourceNotFoundException("Majors", "Majors Id", (long) majorsId));
        return this.majorsToDto(majors);
    }

    public Majors dtoToMajors (MajorsDto majorsDto){
        return this.modelMapper.map(majorsDto, Majors.class);
    }

    public MajorsDto majorsToDto(Majors majors){
        return this.modelMapper.map(majors, MajorsDto.class);
    }

}
