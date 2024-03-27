package com.example.BaiTest.responses;

import com.example.BaiTest.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("user")
    private User user;
}
