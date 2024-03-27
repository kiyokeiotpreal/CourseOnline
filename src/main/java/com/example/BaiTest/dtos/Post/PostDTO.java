package com.example.BaiTest.dtos.Post;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDTO {
    private int postId;

    private String description;

    private String imagePost;

    private Integer userID;

    private List<PostSentencesDTO> postSentences;

}
