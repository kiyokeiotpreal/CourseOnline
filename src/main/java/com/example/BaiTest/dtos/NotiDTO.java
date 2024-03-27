package com.example.BaiTest.dtos;

import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotiDTO {
    private Integer userID;
    private String content;
}
