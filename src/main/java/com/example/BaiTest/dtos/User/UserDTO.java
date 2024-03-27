package com.example.BaiTest.dtos.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {


    private String email;


    private String userName;


    private String password;


    private String retypePassword;

    private String dateOfBirth;

    private String gender;

    private String address;


    private String firstName;

    private String lastName;




}
