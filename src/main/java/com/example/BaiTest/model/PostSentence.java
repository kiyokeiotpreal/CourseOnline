package com.example.BaiTest.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "postSentence")
public class PostSentence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String imagePost;

    private String imageTile;

    private String content;

    private int sortNumber;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "postId", foreignKey = @ForeignKey(name = "fk_PostSentence_Post"), nullable = false)
    @JsonManagedReference
    private Post post;

}
