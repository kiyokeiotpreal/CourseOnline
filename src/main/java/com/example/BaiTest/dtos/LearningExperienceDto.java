package com.example.BaiTest.dtos;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class LearningExperienceDto {
//    private int Id;

    private Timestamp fromDate;

    private Timestamp toDate;

    private MajorsDto majors;


}
