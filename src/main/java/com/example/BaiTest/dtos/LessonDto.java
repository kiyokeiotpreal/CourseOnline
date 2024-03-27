package com.example.BaiTest.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class LessonDto {
    private int Id;

    private String name;

    private String description;

    private Timestamp crateAt;

    private Timestamp updateAt;

    private Timestamp removeAt;

    private LessonTypeDto lessonType;


}
