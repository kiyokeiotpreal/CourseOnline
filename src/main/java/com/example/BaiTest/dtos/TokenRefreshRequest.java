package com.example.BaiTest.dtos;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TokenRefreshRequest {
    private String refreshToken;
    private String accessToken;

}
