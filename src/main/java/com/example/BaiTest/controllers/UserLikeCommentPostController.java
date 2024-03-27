package com.example.BaiTest.controllers;

import com.example.BaiTest.dtos.ApiResponse;
import com.example.BaiTest.dtos.Post.UserLikeCommentPostDTO;
import com.example.BaiTest.services.implement.UserLikeCommentPostServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/post/UserLikeCommentPost")
@RequiredArgsConstructor
public class UserLikeCommentPostController {

    @Autowired
    private UserLikeCommentPostServices userLikeCommentPostServices;

    @PostMapping("/addUserLikeCommentPost")
    public ResponseEntity<?> addUserLikeCommentPost (@RequestBody UserLikeCommentPostDTO userLikeCommentPostDTO){
        var result =   userLikeCommentPostServices.addUserLikeCommentPost(userLikeCommentPostDTO);
        try{
            if(result == null){
                throw  new IllegalArgumentException("Loi");
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return result;
    }

    @DeleteMapping("/deleteUserLikeCommentPost")
    public ResponseEntity<ApiResponse> deleteUserLikeCommentPost( @RequestParam int id){
        this.userLikeCommentPostServices.deleteUserLikeCommentPost(id);
        return new ResponseEntity<ApiResponse>(
                new  ApiResponse("Delete like sucessfully", true ), HttpStatus.OK);
    }


}
