package com.example.BaiTest.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private Integer id;
    private String userName;
    @JsonProperty("token")
    private String token;
    private String refreshToken;
    private List<String> roles;
}
