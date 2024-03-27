package com.example.BaiTest.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class CommentLessonDto {
    private int Id;

    private String comment;

    private int likeCount;

    private int dislikeCount;

    private Timestamp createAt;

    private Timestamp updateAt;

    private Timestamp removeAt;

    private boolean isActive;


    private LessonDto lesson;
}
