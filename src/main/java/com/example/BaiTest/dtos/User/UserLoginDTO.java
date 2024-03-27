package com.example.BaiTest.dtos.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UserLoginDTO {
    private String email;
    private String password;
}
