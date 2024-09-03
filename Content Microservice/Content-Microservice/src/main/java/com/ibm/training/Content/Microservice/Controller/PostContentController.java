package com.ibm.training.Content.Microservice.Controller;

import com.ibm.training.Content.Microservice.Entity.PostContent;
import com.ibm.training.Content.Microservice.Service.PostContentService;
import com.ibm.training.Content.Microservice.exceptions.FileStorageException;
import com.ibm.training.Content.Microservice.exceptions.PostContentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostContentController {

    @Autowired
    private PostContentService postContentService;

    @PostMapping("/uploadImagePost")
    public ResponseEntity<String> uploadImagePost(
            @RequestParam("contentType") String contentType,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("caption") String caption) {

        if (imageFile.isEmpty()) {
            return ResponseEntity.badRequest().body("No file selected for upload.");
        }

        try {
            PostContent savedPost = postContentService.uploadImagePost(contentType, imageFile, caption);
            return new ResponseEntity<>("File uploaded successfully with ID: " + savedPost.getPostId(), HttpStatus.CREATED);
        } catch (FileStorageException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to store file. Please try again!");
        } catch (PostContentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving post content. Please check your input.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @PostMapping("/uploadTextPost")
    public ResponseEntity<String> uploadTextPost(
            @RequestParam("contentType") String contentType,
            @RequestParam("contentText") String contentText) {

        try {
            PostContent savedPost = postContentService.uploadTextPost(contentType, contentText);
            return new ResponseEntity<>("Text post created successfully with ID: " + savedPost.getPostId(), HttpStatus.CREATED);
        } catch (PostContentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving post content. Please check your input.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @PostMapping("/uploadImageTextPost")
    public ResponseEntity<String> uploadImageTextPost(
            @RequestParam("contentType") String contentType,
            @RequestParam("contentText") String contentText,
            @RequestParam("imageFile") MultipartFile imageFile) {

        if (imageFile.isEmpty()) {
            return ResponseEntity.badRequest().body("No file selected for upload.");
        }

        try {
            PostContent savedPost = postContentService.uploadImageTextPost(contentType, contentText, imageFile);
            return new ResponseEntity<>("Post created successfully with ID: " + savedPost.getPostId(), HttpStatus.CREATED);
        } catch (FileStorageException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to store file. Please try again!");
        } catch (PostContentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving post content. Please check your input.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    // Get a single post by ID
    @GetMapping("/{postId}")
    public ResponseEntity<PostContent> getPostById(@PathVariable Long postId) {
        PostContent postContent = postContentService.getPostById(postId);
        if (postContent != null) {
            return ResponseEntity.ok(postContent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get all posts by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostContent>> getPostsByUserId(@PathVariable Long userId) {
        List<PostContent> posts = postContentService.getPostsByUserId(userId);
        return ResponseEntity.ok(posts);
    }

    // Get all posts from all users
    @GetMapping("/all")
    public ResponseEntity<List<PostContent>> getAllPosts() {
        List<PostContent> posts = postContentService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

}
