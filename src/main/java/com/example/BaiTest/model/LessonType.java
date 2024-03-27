package com.example.BaiTest.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lessonType")
public class LessonType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String name;

    @OneToMany(mappedBy = "lessonType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Lesson> lesson;
}
