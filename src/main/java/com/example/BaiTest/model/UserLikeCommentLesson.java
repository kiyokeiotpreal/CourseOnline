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
@Table(name = "userLikeCommentLesson")
public class UserLikeCommentLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private Timestamp createAt;

    private boolean isDeleted;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "commentLessonId", foreignKey = @ForeignKey(name = "fk_UserLikeCommentLesson_CommentLesson"), nullable = false)
    @JsonManagedReference
    private CommentLesson commentLesson;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "userLikeCommentId", foreignKey = @ForeignKey(name = "fk_UserLikeCommentLesson_User"), nullable = false)
    @JsonManagedReference
    private User user;


}
