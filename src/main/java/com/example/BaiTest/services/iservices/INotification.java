package com.example.BaiTest.services.iservices;

import com.example.BaiTest.dtos.NotiDTO;
import com.example.BaiTest.model.Notification;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface INotification {
    List<String> getAllNotification();
    ResponseEntity<?> addNotificaton(NotiDTO notiDTO);
}
