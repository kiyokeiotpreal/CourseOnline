package com.example.BaiTest.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "userLessonCheckpoint")
public class UserLessonCheckpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private Timestamp openLessonTime;

    private boolean isDone;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "lessonId", foreignKey = @ForeignKey(name = "fk_UserLessonCheckpoint_Lesson"), nullable = false)
    @JsonManagedReference
    private Lesson lesson;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", foreignKey = @ForeignKey(name = "fk_UserLessonCheckpoint_User"), nullable = false)
    @JsonManagedReference
    private User user;


}
