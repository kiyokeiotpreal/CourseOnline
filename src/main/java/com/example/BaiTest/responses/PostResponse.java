package com.example.BaiTest.responses;

import com.example.BaiTest.model.Post;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
    private String description;

    private String imagePost;

    private Timestamp createAt;

    private Timestamp updateAt;

    private int likeCount;

    private int commentCount;
    private PostUserResponse user;

    private List<PostSentenceResponse> sentences;




}
