package com.example.BaiTest.responses;

import com.example.BaiTest.dtos.CommentLessonDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CommentLessonResponse {
    private List<CommentLessonDto> content;
    private int pageNumber;
    private int pageSize;
    private long totalElement;
    private int totalPage;

    private boolean lastPage;
}
