package com.example.BaiTest.controllers;

import com.example.BaiTest.dtos.ApiResponse;
import com.example.BaiTest.dtos.MajorsDto;
import com.example.BaiTest.services.iservices.MajorsService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/majors")
public class MajorsController {
    @Autowired
    private MajorsService majorsService;

    @PostMapping("/add")
    public ResponseEntity<MajorsDto> createMajors(@Valid @RequestBody MajorsDto majorsDto){
        MajorsDto createMajors = this.majorsService.createMajors(majorsDto);
        return new ResponseEntity<MajorsDto>(createMajors, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<MajorsDto> updateMajor(@Valid @RequestBody MajorsDto majorsDto, @RequestParam int majorsId){
        MajorsDto updateMajors = this.majorsService.updateMajors(majorsDto,majorsId);
        return new ResponseEntity<MajorsDto>(updateMajors, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteMajor(@Valid @RequestParam int majorId){
        this.majorsService.deleteMajors(majorId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Majors is deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/getId")
    public ResponseEntity<MajorsDto> getMajors(@RequestParam int majorsId){
        MajorsDto majorsDto = this.majorsService.getMajors(majorsId);
        return new ResponseEntity<MajorsDto>(majorsDto,HttpStatus.OK);
    }


}
