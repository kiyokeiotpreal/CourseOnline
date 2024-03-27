package com.example.BaiTest.services.implement;

import com.example.BaiTest.dtos.NotiDTO;
import com.example.BaiTest.model.Notification;
import com.example.BaiTest.model.User;
import com.example.BaiTest.repository.NotificationRepo;
import com.example.BaiTest.repository.UserRepo;
import com.example.BaiTest.services.iservices.INotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationImpl implements INotification {
    @Autowired
    private NotificationRepo notificationRepo;
    @Autowired
    private UserRepo userRepo;
    @Override
    public List<String> getAllNotification() {
        List<String> content = new ArrayList<>();
        for (Notification noti: notificationRepo.findAll()){
            content.add(noti.getContent());
        }
        return content;
    }

    @Override
    public ResponseEntity<?> addNotificaton(NotiDTO notiDTO) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        User user = userRepo.findById(notiDTO.getUserID()).orElse(null);
        if (user == null){
            return ResponseEntity.badRequest().body("Người dùng không tồn tại");
        }
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setContent(notiDTO.getContent());
        notification.setSeen(false);
        notification.setCreatedAt(timestamp);
        notificationRepo.save(notification);
        return ResponseEntity.ok("Tạo mới thông báo thành công");
    }
}
