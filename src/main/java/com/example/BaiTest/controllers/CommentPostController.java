package com.example.BaiTest.controllers;

import com.example.BaiTest.dtos.CourseDto;
import com.example.BaiTest.dtos.Post.CommentPostDTO;
import com.example.BaiTest.responses.CommentPostResponse;
import com.example.BaiTest.services.implement.CommentPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/post/commentPost")
@RequiredArgsConstructor
public class CommentPostController {
    @Autowired
    private CommentPostService commentPostService;

    @PostMapping("/addCommentPost")
    public ResponseEntity<?> addCommentPost (@RequestBody CommentPostDTO commentPostDTO){
        return commentPostService.addCommentPost(commentPostDTO);
    }

    @PutMapping("/updateCommentPost")
    public ResponseEntity<CommentPostResponse> updateCommentPost(
            @Valid @RequestBody CommentPostDTO commentPostDTO, @RequestParam int commentPostID){
        CommentPostResponse updateCommentPost = this.commentPostService.updateCommentPost(commentPostDTO, commentPostID);
        return new ResponseEntity<CommentPostResponse>(updateCommentPost, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCommentPost")
    public ResponseEntity<?> deleteCommentPost (@RequestParam int id){
        var result =  commentPostService.deleteCommentPost(id);
        try{
            if(result == null){
                throw  new IllegalArgumentException("Loi");
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return result;

    }
}
