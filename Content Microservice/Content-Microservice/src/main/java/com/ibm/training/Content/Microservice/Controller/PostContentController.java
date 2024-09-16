package com.ibm.training.Content.Microservice.Controller;

import com.ibm.training.Content.Microservice.Entity.PostContent;
import com.ibm.training.Content.Microservice.Service.CloudStorageService;
import com.ibm.training.Content.Microservice.Service.PostContentService;
import com.ibm.training.Content.Microservice.exceptions.FileStorageException;
import com.ibm.training.Content.Microservice.exceptions.PostContentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:3000")
public class PostContentController {

    private static final Logger logger = LoggerFactory.getLogger(PostContentController.class);

    @Autowired
    private PostContentService postContentService;

    @Autowired
    private CloudStorageService cloudStorageService;

    @PostMapping("/uploadImagePost")
    public ResponseEntity<String> uploadImagePost(
            @RequestParam("contentType") String contentType,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("caption") String caption,
            @RequestParam("userId") Long userId) {


        logger.info("Uploading image post with contentType: {}, caption: {}", contentType, caption);

        if (imageFile.isEmpty()) {
            logger.warn("Upload failed: No file selected.");
            return ResponseEntity.badRequest().body("No file selected for upload.");
        }

        try {
            String url = cloudStorageService.uploadImage(imageFile);
            PostContent savedPost = postContentService.uploadImagePost(contentType, url, caption,userId);
            logger.info("File uploaded successfully with ID: {}", savedPost.getPostId());
            return new ResponseEntity<>("File uploaded successfully with ID: " + savedPost.getPostId(), HttpStatus.CREATED);
        } catch (FileStorageException e) {
            logger.error("File storage failed for image upload", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to store file. Please try again!");
        } catch (PostContentException e) {
            logger.error("Error saving post content", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving post content. Please check your input.");
        } catch (Exception e) {
            logger.error("Unexpected error during image upload", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @PostMapping("/uploadTextPost")
    public ResponseEntity<String> uploadTextPost(
            @RequestParam("contentType") String contentType,
            @RequestParam("contentText") String contentText) {

        logger.info("Uploading text post with contentType: {}, contentText: {}", contentType, contentText);

        try {
            PostContent savedPost = postContentService.uploadTextPost(contentType, contentText);
            logger.info("Text post created successfully with ID: {}", savedPost.getPostId());
            return new ResponseEntity<>("Text post created successfully with ID: " + savedPost.getPostId(), HttpStatus.CREATED);
        } catch (PostContentException e) {
            logger.error("Error saving text post content", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving post content. Please check your input.");
        } catch (Exception e) {
            logger.error("Unexpected error during text post creation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    @PostMapping("/uploadImageTextPost")
    public ResponseEntity<String> uploadImageTextPost(
            @RequestParam("contentType") String contentType,
            @RequestParam("contentText") String contentText,
            @RequestParam("imageFile") MultipartFile imageFile) {

        logger.info("Uploading image and text post with contentType: {}, contentText: {}", contentType, contentText);

        if (imageFile.isEmpty()) {
            logger.warn("Upload failed: No file selected.");
            return ResponseEntity.badRequest().body("No file selected for upload.");
        }

        try {
            String url = cloudStorageService.uploadImage(imageFile);
            PostContent savedPost = postContentService.uploadImageTextPost(contentType, contentText, url);
            logger.info("Image and text post created successfully with ID: {}", savedPost.getPostId());
            return new ResponseEntity<>("Post created successfully with ID: " + savedPost.getPostId(), HttpStatus.CREATED);
        } catch (FileStorageException e) {
            logger.error("File storage failed for image upload", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to store file. Please try again!");
        } catch (PostContentException e) {
            logger.error("Error saving image and text post content", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving post content. Please check your input.");
        } catch (Exception e) {
            logger.error("Unexpected error during image and text post creation", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
        }
    }

    // Get a single post by ID
    @GetMapping("/{postId}")
    public ResponseEntity<PostContent> getPostById(@PathVariable Long postId) {
        logger.info("Fetching post with ID: {}", postId);
        PostContent postContent = postContentService.getPostById(postId);
        if (postContent != null) {
            logger.info("Post with ID: {} found", postId);
            return ResponseEntity.ok(postContent);
        } else {
            logger.warn("Post with ID: {} not found", postId);
            return ResponseEntity.notFound().build();
        }
    }

    // Get all posts by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostContent>> getPostsByUserId(@PathVariable Long userId) {
        logger.info("Fetching posts for user with ID: {}", userId);
        List<PostContent> posts = postContentService.getPostsByUserId(userId);
        logger.info("Returning {} posts for user with ID: {}", posts.size(), userId);
        return ResponseEntity.ok(posts);
    }

    // Get all posts from all users
    @GetMapping("/all")
    public ResponseEntity<List<PostContent>> getAllPosts() {
        logger.info("Fetching all posts from all users.");
        List<PostContent> posts = postContentService.getAllPosts();
        logger.info("Returning {} posts", posts.size());
        return ResponseEntity.ok(posts);
    }
}
