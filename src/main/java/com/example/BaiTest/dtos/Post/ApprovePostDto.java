package com.example.BaiTest.dtos.Post;

import com.example.BaiTest.dtos.User.UserDTO;
import lombok.*;


import java.sql.Timestamp;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApprovePostDto {

    private String statusPost;

    private Timestamp createAt;

    private Timestamp updateAt;

    private PostDTO postDTO;

}
