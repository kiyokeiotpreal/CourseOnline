package com.example.BaiTest.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class UserLessonCheckPointDto {
    private int Id;

    private Timestamp openLessonTime;

    private boolean isDone;


    private LessonDto lesson;



}
