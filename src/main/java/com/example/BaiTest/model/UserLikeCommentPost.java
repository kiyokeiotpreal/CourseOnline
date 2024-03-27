package com.example.BaiTest.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "userLikeCommentPost")
public class UserLikeCommentPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private Timestamp createAt;

    private boolean isDeleted;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "commentPostId", foreignKey = @ForeignKey(name = "fk_userLikeCommentPost_CommentPost"), nullable = false)
    @JsonManagedReference
    private CommentPost commentPost;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "userLikeCommentId", foreignKey = @ForeignKey(name = "fk_userLikeCommentPost_User"), nullable = false)
    @JsonManagedReference
    private User user;

}
