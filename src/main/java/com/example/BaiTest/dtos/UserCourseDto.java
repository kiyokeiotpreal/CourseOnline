package com.example.BaiTest.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class UserCourseDto {
    private int Id;

    private boolean isDone;

    private Timestamp registerTime;

    private Timestamp lastTimeStudy;

    private Timestamp doneTime;


    private CourseDto course;

}
