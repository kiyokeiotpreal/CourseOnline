package com.example.BaiTest.responses;

import com.example.BaiTest.dtos.LessonDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class LessonResponse {
    private List<LessonDto> content;
    private int pageNumber;
    private int pageSize;
    private long totalElement;
    private int totalPage;

    private boolean lastPage;
}
