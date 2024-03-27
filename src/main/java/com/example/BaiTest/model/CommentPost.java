package com.example.BaiTest.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "commentPost")
public class CommentPost {
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
    @JoinColumn(name = "postId", foreignKey = @ForeignKey(name = "fk_CommentPost_Post"), nullable = false)
    @JsonManagedReference
    private Post post;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "userCommentId", foreignKey = @ForeignKey(name = "fk_CommentPost_User"), nullable = false)
    @JsonManagedReference
    private User user;

    @OneToMany(mappedBy = "commentPost", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<UserLikeCommentPost> userLikeCommentPost;

}
