package com.example.BaiTest.repository;

import com.example.BaiTest.model.CommentPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentPostRepo extends JpaRepository<CommentPost, Integer> {
    List<CommentPost> findAllByPostId(int id);
    void deleteAllByPost_Id(int id);
}
