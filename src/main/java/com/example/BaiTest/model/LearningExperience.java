package com.example.BaiTest.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "learningExperience")
public class LearningExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private Timestamp fromDate;

    private Timestamp toDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "majorsId", foreignKey = @ForeignKey(name = "fk_LearningExperience_Majors"), nullable = false)
    @JsonManagedReference
    private Majors majors;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", foreignKey = @ForeignKey(name = "fk_LearningExperience_User"), nullable = false)
    @JsonManagedReference
    private User user;

}
