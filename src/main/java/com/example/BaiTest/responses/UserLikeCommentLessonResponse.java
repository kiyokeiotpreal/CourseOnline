package com.example.BaiTest.responses;

import com.example.BaiTest.dtos.UserLikeCommentLessonDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserLikeCommentLessonResponse {
    private List<UserLikeCommentLessonDto> content;
    private int pageNumber;
    private int pageSize;
    private long totalElement;
    private int totalPage;

    private boolean lastPage;
}
