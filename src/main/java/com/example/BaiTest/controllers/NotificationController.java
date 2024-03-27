package com.example.BaiTest.controllers;

import com.example.BaiTest.dtos.NotiDTO;
import com.example.BaiTest.services.implement.NotificationImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/notification")
@RequiredArgsConstructor
public class NotificationController {
    @Autowired
    private NotificationImpl notification;

    @GetMapping("/getAllNoti")
    public List<String> getAllNotification(){
        return notification.getAllNotification();
    }
    @PostMapping("/addNoti")
    public ResponseEntity<?> addNoti(@RequestBody NotiDTO notiDTO){
        return notification.addNotificaton(notiDTO);
    }
}
