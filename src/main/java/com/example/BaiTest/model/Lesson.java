package com.example.BaiTest.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "lesson")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String name;

    private String description;

    private Timestamp crateAt;

    private Timestamp updateAt;

    private Timestamp removeAt;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "lessonTypeId", foreignKey = @ForeignKey(name = "fk_Lesson_LessonType"), nullable = false)
    @JsonManagedReference
    private LessonType lessonType;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<CommentLesson> commentLesson;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<UserLessonCheckpoint> userLessonCheckpoint;



}
