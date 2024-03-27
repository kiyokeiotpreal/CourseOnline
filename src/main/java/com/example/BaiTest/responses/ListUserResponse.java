package com.example.BaiTest.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListUserResponse {
    private Integer id;
    private String avatar;

    private String email;

    private String userName;

    private String dateOfBirth;

    private String gender;

    private String address;

    private String firstName;

    private String lastName;

    private String  role;
    private int isActive;
}
