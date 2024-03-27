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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "commentLesson")
public class CommentLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String comment;

    private int likeCount;

    private int dislikeCount;

    private Timestamp createAt;

    private Timestamp updateAt;

    private Timestamp removeAt;

    private boolean isActive;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "lessonId", foreignKey = @ForeignKey(name = "fk_CommentLesson_Lesson"), nullable = false)
    @JsonManagedReference
    private Lesson lesson;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "userCommentId", foreignKey = @ForeignKey(name = "fk_CommentLesson_User"), nullable = false)
    @JsonManagedReference
    private User user;

    @OneToMany(mappedBy = "commentLesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<UserLikeCommentLesson> userLikeCommentLesson;

}
