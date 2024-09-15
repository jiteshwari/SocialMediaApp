package com.ibm.training.Homepage.Microservice.controller;

import com.ibm.training.Homepage.Microservice.entity.Like;
import com.ibm.training.Homepage.Microservice.dto.PostContent;
import com.ibm.training.Homepage.Microservice.exceptions.LikeOperationException;
import com.ibm.training.Homepage.Microservice.exceptions.PostNotFoundException;
import com.ibm.training.Homepage.Microservice.service.HomeService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private HomeService homeService;

    @GetMapping("/posts")
    public ResponseEntity<List<PostContent>> getAllPosts() {
        try {
            List<PostContent> posts = homeService.getAllPosts();
            if (posts.isEmpty()) {
                logger.warn("No posts found.");
                throw new PostNotFoundException("No posts available.");
            }
            logger.info("Successfully retrieved all posts.");
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (PostNotFoundException e) {
            logger.error("Error fetching posts: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error fetching all posts: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/like")
    public ResponseEntity<Like> likePost(@RequestParam Long userId, @RequestParam Long postId) {
        try {
            Like like = homeService.likePost(userId, postId);
            logger.info("Post {} liked by user {}.", postId, userId);
            return new ResponseEntity<>(like, HttpStatus.OK);
        } catch (LikeOperationException e) {
            logger.error("Error liking post {}: {}", postId, e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logger.error("Error liking post {}: {}", postId, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
