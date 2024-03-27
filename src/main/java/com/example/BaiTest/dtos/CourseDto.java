package com.example.BaiTest.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseDto {
    private int Id;

    private String nameOfCourse;

    private String courseSubTile;

    private String imageCourse;

    private int lessonCount;

    private int chapterCount;

    private String timeLessonTotal;

    private int registerCount;

    private int doneCount;

    private CourseTypeDto courseType;

    private CourseLevelDto courseLevel;

}
