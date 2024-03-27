package com.example.BaiTest.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
public class UserLikeCommentLessonDto {
    private int Id;

    private Timestamp createAt;

    private boolean isDeleted;

    private CommentLessonDto commentLesson;

}
