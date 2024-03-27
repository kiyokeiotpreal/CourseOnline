package com.example.BaiTest.dtos.Post;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentPostDTO {
    private Integer postID;
    private Integer userID;
    private String comment;
}
