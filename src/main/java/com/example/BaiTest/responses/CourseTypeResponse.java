package com.example.BaiTest.responses;

import com.example.BaiTest.model.Course;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseTypeResponse {
    private String image_course;
    private String name_of_course;
    private String course_type_name;
    private Integer lesson_count;
    private String time_lesson_total;
}
