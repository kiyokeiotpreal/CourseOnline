package com.example.BaiTest.services.iservices;

import com.example.BaiTest.dtos.UserLessonCheckPointDto;
import com.example.BaiTest.responses.UserLessonCheckPointResponse;


public interface UserLessonCheckPointService {
    UserLessonCheckPointDto createUserLessonCheckpoint(UserLessonCheckPointDto userLessonCheckPointDto, int userId, int commentLessonId);
    UserLessonCheckPointDto updateUserLessonCheckPoint(UserLessonCheckPointDto userLessonCheckPointDto, int userLessonCheckPointId);
    void deleteUserLessonCheckPoint(int userLessonCheckPointId);
    UserLessonCheckPointResponse getAllUserLessonCheckPoint(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);


}
