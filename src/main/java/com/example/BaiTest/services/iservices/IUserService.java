package com.example.BaiTest.services.iservices;

import com.example.BaiTest.dtos.User.UserDTO;
import com.example.BaiTest.dtos.User.UserLoginDTO;
import com.example.BaiTest.model.User;
import com.example.BaiTest.responses.ListUserResponse;
import com.example.BaiTest.responses.LoginResponse;
import com.example.BaiTest.responses.UserResponse;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;

    UserResponse getAllUser(int numberPage, int limit);
    LoginResponse login(UserLoginDTO userLoginDTO) throws Exception;
    long getTotalUserCount();

    long getTotalUserLocked();

    boolean lockUser(int userID) throws Exception;

}
