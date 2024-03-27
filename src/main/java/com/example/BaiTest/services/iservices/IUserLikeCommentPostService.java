package com.example.BaiTest.services.iservices;

import com.example.BaiTest.dtos.Post.UserLikeCommentPostDTO;
import com.example.BaiTest.model.UserLikeCommentPost;
import org.springframework.http.ResponseEntity;

public interface IUserLikeCommentPostService {
    ResponseEntity<?> addUserLikeCommentPost(UserLikeCommentPostDTO userLikeCommentPostDTO);

    void deleteUserLikeCommentPost ( int id);
}
