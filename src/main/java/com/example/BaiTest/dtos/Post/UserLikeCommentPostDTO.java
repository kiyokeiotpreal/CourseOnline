package com.example.BaiTest.dtos.Post;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserLikeCommentPostDTO {
    private Integer userID;
    private Integer commentPostID;
}
