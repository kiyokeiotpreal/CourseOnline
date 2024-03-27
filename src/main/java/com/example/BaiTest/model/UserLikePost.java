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
@Table(name = "userLikePost")
public class UserLikePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private Timestamp likeTime;

    private boolean isDeleted;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "postId", foreignKey = @ForeignKey(name = "fk_UserLikePost_Post"), nullable = false)
    @JsonManagedReference
    private Post post;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", foreignKey = @ForeignKey(name = "fk_UserLikePost_User"), nullable = false)
    @JsonManagedReference
    private User user;

}
