package com.example.BaiTest.repository;

import com.example.BaiTest.model.UserLikeCommentPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLikeCommentPostRepo extends JpaRepository<UserLikeCommentPost, Integer> {
    void deleteAllByCommentPost_Id(int id);
}
