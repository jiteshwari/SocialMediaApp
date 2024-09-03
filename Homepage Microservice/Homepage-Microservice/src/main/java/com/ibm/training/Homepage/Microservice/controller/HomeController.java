package com.ibm.training.Homepage.Microservice.controller;

import com.ibm.training.Homepage.Microservice.entity.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ibm.training.Homepage.Microservice.dto.PostContent;
import com.ibm.training.Homepage.Microservice.service.HomeService;

import java.util.List;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/posts")
    public ResponseEntity<List<PostContent>> getAllPosts() {
        try {
            List<PostContent> posts = homeService.getAllPosts();
            if (posts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/like")
    public ResponseEntity<Like> likePost(@RequestParam Long userId, @RequestParam Long postId) {
        try {
            Like like = homeService.likePost(userId, postId);
            return new ResponseEntity<>(like, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
