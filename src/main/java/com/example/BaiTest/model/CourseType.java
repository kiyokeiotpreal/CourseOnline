package com.example.BaiTest.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "courseType")
public class CourseType {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String name;

    @OneToMany(mappedBy = "courseType", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Course> course;

}
