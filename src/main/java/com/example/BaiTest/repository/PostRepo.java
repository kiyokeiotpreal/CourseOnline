package com.example.BaiTest.repository;

import com.example.BaiTest.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {


}
