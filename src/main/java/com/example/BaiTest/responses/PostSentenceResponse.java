package com.example.BaiTest.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostSentenceResponse {
    private String imagePost;

    private String imageTile;

    private String content;

    private int sortNumber;
}
