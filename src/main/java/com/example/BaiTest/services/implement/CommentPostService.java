package com.example.BaiTest.services.implement;


import com.example.BaiTest.dtos.CourseDto;
import com.example.BaiTest.dtos.Post.CommentPostDTO;
import com.example.BaiTest.exceptions.ResourceNotFoundException;
import com.example.BaiTest.model.*;
import com.example.BaiTest.repository.CommentPostRepo;
import com.example.BaiTest.repository.PostRepo;
import com.example.BaiTest.repository.UserLikeCommentPostRepo;
import com.example.BaiTest.repository.UserRepo;
import com.example.BaiTest.responses.CommentPostResponse;
import com.example.BaiTest.services.iservices.ICommentPostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class CommentPostService implements ICommentPostService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentPostRepo commentPostRepo;
    @Autowired
    private UserLikeCommentPostRepo userLikeCommentPostRepo;
    @Autowired
    private ModelMapper modelMapper;

    public int updateCommentCount (int postID){
        int countCommentofPost = 0 ;
        for (CommentPost cm: commentPostRepo.findAll()){
            if(cm.getPost().getId() == postID){
                countCommentofPost++;
            }
        }
        return countCommentofPost;
    }

    @Override
    public ResponseEntity<?> addCommentPost(CommentPostDTO commentPostDTO) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Post post = postRepo.findById(commentPostDTO.getPostID()).orElse(null);
        User user = userRepo.findById(commentPostDTO.getUserID()).orElse(null);
        if(post != null && user !=null) {
            CommentPost newCommentPost = CommentPost.builder()
                    .comment(commentPostDTO.getComment())
                    .dislikeCount(0)
                    .isActive(true)
                    .createAt(timestamp)
                    .post(post)
                    .user(user)
                    .updateAt(timestamp)
                    .likeCount(0)
                    .removeAt(null)
                    .build();
            commentPostRepo.save(newCommentPost);

            post.setCommentCount(updateCommentCount(post.getId()));
            postRepo.save(post);

            CommentPostResponse commentPostResponse = CommentPostResponse.builder()
                    .comment(newCommentPost.getComment())
                    .dislikeCount(newCommentPost.getDislikeCount())
                    .likeCount(newCommentPost.getLikeCount())
                    .build();
            return  new ResponseEntity<>(commentPostResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>("Cann't add comment", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> deleteCommentPost(int commentPostID) {
         Optional<CommentPost> commentPost = commentPostRepo.findById(commentPostID);
         CommentPost commentPost1 = commentPostRepo.findById(commentPostID).orElse(null);
         int postID = commentPost1.getPost().getId();
         Post post = postRepo.findById(postID).orElse(null);
         if(commentPost.isPresent() && post != null){
                for (UserLikeCommentPost x: userLikeCommentPostRepo.findAll()) {
                    if(x.getCommentPost().getId() == commentPostID){
                        userLikeCommentPostRepo.deleteById(x.getId());
                    }
                }
                commentPostRepo.delete(commentPost1);
                post.setCommentCount(updateCommentCount(postID));
                postRepo.save(post);
                return  new ResponseEntity<>("Deleted Comment", HttpStatus.OK);
         }
        return new ResponseEntity<>("Can't delete", HttpStatus.BAD_REQUEST);
    }

    @Override
    public CommentPostResponse updateCommentPost(CommentPostDTO commentPostDTO, int commentPostID) {
        CommentPost commentPost = this.commentPostRepo.findById(commentPostID).orElseThrow(()->
                new ResourceNotFoundException("CommentPost", "CommentPostId", (long)commentPostID));

        commentPost.setComment(commentPostDTO.getComment());

        CommentPost updateCommentPost = this.commentPostRepo.save(commentPost);

        return this.commentPostToResponse(updateCommentPost);
    }

    public CommentPostResponse commentPostToResponse(CommentPost commentPost){
        return modelMapper.map(commentPost, CommentPostResponse.class);
    }
}
