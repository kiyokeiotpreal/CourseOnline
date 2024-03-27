package com.example.BaiTest.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String nameOfCourse;

    private String courseSubTile;

    private String imageCourse;

    private int lessonCount;

    private int chapterCount;

    private String timeLessonTotal;

    private int registerCount;

    private int doneCount;
    private LocalDate createdDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "courseLevelId", foreignKey = @ForeignKey(name = "fk_Course_CourseLevel"), nullable = false)
    @JsonManagedReference
    private CourseLevel courseLevel;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "courseTypeId", foreignKey = @ForeignKey(name = "fk_Course_CourseType"), nullable = false)
    @JsonManagedReference
    private CourseType courseType;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<UserCourse> userCourse;


}
