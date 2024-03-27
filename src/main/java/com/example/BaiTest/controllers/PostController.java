package com.example.BaiTest.controllers;

import com.example.BaiTest.dtos.Post.PostDTO;

import com.example.BaiTest.services.implement.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/post")
@RequiredArgsConstructor
public class PostController {
    @Autowired
    private  PostService postService;


    @PostMapping("/addPost")
    public ResponseEntity<?> addPost(@Valid @RequestBody PostDTO post) {
        try {
            //service chi tra ve du lieu entity model, dong goi response entity lam o controller
            return postService.addPost(post);
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @PutMapping("revisePost")
    public ResponseEntity<?> revisePost(@Valid @RequestBody PostDTO post) {
        try {
            //service chi tra ve du lieu entity model, dong goi response entity lam o controller
            return postService.revisePost(post);
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());

        }
    }

    @DeleteMapping("/deletePost")
    public ResponseEntity<?> deletePost(@Valid @RequestParam int id){
            return postService.deletePost(id);
    }

    @GetMapping("/total-post-count")
    public ResponseEntity<?> getTotalPostCount(){
        try {
            var result = postService.getTotalPostCount();
            return ResponseEntity.ok().body(result);
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error serve");
        }
    }
}
