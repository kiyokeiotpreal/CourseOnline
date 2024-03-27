package com.example.BaiTest.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentPostResponse {
    private String comment;

    private int likeCount;

    private int dislikeCount;



}
