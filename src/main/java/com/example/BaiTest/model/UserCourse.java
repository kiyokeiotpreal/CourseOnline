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
@Table(name = "userCourse")
public class UserCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private boolean isDone;

    private Timestamp registerTime;

    private Timestamp lastTimeStudy;

    private Timestamp doneTime;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "courseId", foreignKey = @ForeignKey(name = "fk_UserCourse_Course"), nullable = false)
    @JsonManagedReference
    private Course course;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", foreignKey = @ForeignKey(name = "fk_UserCourse_User"), nullable = false)
    @JsonManagedReference
    private User user;
}
