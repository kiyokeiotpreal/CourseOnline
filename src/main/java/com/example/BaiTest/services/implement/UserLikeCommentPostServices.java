package com.example.BaiTest.services.implement;

import com.example.BaiTest.dtos.Post.UserLikeCommentPostDTO;
import com.example.BaiTest.model.CommentPost;
import com.example.BaiTest.model.User;
import com.example.BaiTest.model.UserLikeCommentPost;
import com.example.BaiTest.repository.CommentPostRepo;
import com.example.BaiTest.repository.UserLikeCommentPostRepo;
import com.example.BaiTest.repository.UserRepo;
import com.example.BaiTest.services.iservices.IUserLikeCommentPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class UserLikeCommentPostServices implements IUserLikeCommentPostService {
    @Autowired
    private UserLikeCommentPostRepo userLikeCommentPostRepoo;
    @Autowired
    private CommentPostRepo commentPostRepo;
    @Autowired
    private UserRepo userRepo;

    private int updateLikeCountUserLikeCommentPost (int commentPostID){
        int likeCount= 0;
        for (UserLikeCommentPost cmt: userLikeCommentPostRepoo.findAll()){
            if(cmt.getCommentPost().getId() == commentPostID){
                likeCount ++;
            }
        }
        return likeCount;
    }

    @Override
    public ResponseEntity<?> addUserLikeCommentPost(UserLikeCommentPostDTO userLikeCommentPostDTO) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        CommentPost commentPost = commentPostRepo.findById(userLikeCommentPostDTO.getCommentPostID()).orElse(null);
        User user = userRepo.findById(userLikeCommentPostDTO.getUserID()).orElse(null);
        if(commentPost !=  null){
            UserLikeCommentPost userlikeCommentpost = UserLikeCommentPost.builder()
                    .user(user)
                    .commentPost(commentPost)
                    .createAt(timestamp)
                    .build();
            userLikeCommentPostRepoo.save(userlikeCommentpost);
            commentPost.setLikeCount(updateLikeCountUserLikeCommentPost(userLikeCommentPostDTO.getCommentPostID()));
            commentPostRepo.save(commentPost);
            return new ResponseEntity<>("liked", HttpStatus.OK);
        }
        return new ResponseEntity<>("Cann't like", HttpStatus.BAD_REQUEST);
    }

    @Override
    public void deleteUserLikeCommentPost(int id) {
        UserLikeCommentPost userLikeCommentPost = userLikeCommentPostRepoo.findById(id).orElse(null);
        if(userLikeCommentPost != null){
            int commentPostID = userLikeCommentPost.getCommentPost().getId();
            CommentPost commentPost = commentPostRepo.findById(commentPostID).orElse(null);
            userLikeCommentPostRepoo.deleteById(id);

            assert commentPost != null;
            commentPost.setLikeCount(updateLikeCountUserLikeCommentPost(commentPostID));
            commentPostRepo.save(commentPost);
        }
    }
}
