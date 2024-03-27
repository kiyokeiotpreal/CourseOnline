package com.example.BaiTest.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostUserResponse {
    private  String avatar;
    private String nameUser;
}
