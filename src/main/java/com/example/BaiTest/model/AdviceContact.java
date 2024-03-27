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
@Table(name = "adviceContact")
public class AdviceContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String name;

    private int phoneNumber;

    private boolean isContact;

    private Timestamp timeSendRequest;

    private int contactTimeBegin;

    private int contactTimeEnd;

    private int rate;

    private String evaluate;

    private String note;

    private int status;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "userContactId", foreignKey = @ForeignKey(name = "fk_AdviceContact_User"), nullable = false)
    @JsonManagedReference
    private User user;


}
