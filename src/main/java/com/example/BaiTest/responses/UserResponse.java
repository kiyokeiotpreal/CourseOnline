package com.example.BaiTest.responses;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private List<ListUserResponse> content;
    private int totalPages;
    private long totalElements;
    private int number;
    private int size;
}
