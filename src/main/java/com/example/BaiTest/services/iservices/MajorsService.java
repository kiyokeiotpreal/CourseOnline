package com.example.BaiTest.services.iservices;


import com.example.BaiTest.dtos.MajorsDto;

public interface MajorsService {
    public MajorsDto createMajors(MajorsDto majorsDto);
    public MajorsDto updateMajors(MajorsDto majorsDto, int majorsID);
    public void deleteMajors(int majorsID);
    public MajorsDto getMajors(int majorsId);

}
