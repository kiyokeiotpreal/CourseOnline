package com.example.BaiTest.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "approvePost")
public class ApprovePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String statusPost;

    private Timestamp createAt;

    private Timestamp updateAt;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "postId", foreignKey = @ForeignKey(name = "fk_ApprovePost_Post"), nullable = false)
    @JsonManagedReference
    private Post post;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "adminApprovePostId", foreignKey = @ForeignKey(name = "fk_ApprovePost_User"), nullable = false)
    @JsonManagedReference
    private User user;


}
