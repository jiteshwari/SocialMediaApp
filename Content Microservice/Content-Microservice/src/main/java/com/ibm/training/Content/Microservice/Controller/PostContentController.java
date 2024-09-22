package com.ibm.training.Content.Microservice.Controller;

import com.ibm.training.Content.Microservice.Entity.PostContent;
import com.ibm.training.Content.Microservice.Service.CloudStorageService;
import com.ibm.training.Content.Microservice.Service.PostContentService;
import com.ibm.training.Content.Microservice.dto.ImagePostForm;
import com.ibm.training.Content.Microservice.exceptions.FileStorageException;
import com.ibm.training.Content.Microservice.exceptions.PostContentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class PostContentController {

    private static final Logger logger = LoggerFactory.getLogger(PostContentController.class);

    @Autowired
    private PostContentService postContentService;

    @Autowired
    private CloudStorageService cloudStorageService;

    @PostMapping("/uploadImagePost")
    public ResponseEntity<?> uploadImagePost(@ModelAttribute ImagePostForm form) {
        logger.info("Uploading image post for userId: {}, contentType: {}, caption: {}", form.getUserId(), form.getContentType(), form.getCaption());

        if (form.getImageFile().isEmpty()) {
            logger.warn("Image upload failed: No file selected.");
            return ResponseEntity.badRequest().body("No file selected for upload.");
        }

        try {
            // Upload image to cloud storage
            String imageUrl = cloudStorageService.uploadImage(form.getImageFile());
            logger.info("Image uploaded successfully to cloud storage. URL: {}", imageUrl);

            // Create post content
            PostContent postContent = new PostContent();
            postContent.setContentType(form.getContentType());
            postContent.setPosturl(imageUrl);
            postContent.setCaption(form.getCaption());
            postContent.setUserId(form.getUserId());

            // Save post content in the service layer
            PostContent savedPost = postContentService.uploadImagePost(postContent);
            logger.info("Post saved successfully with ID: {}", savedPost.getPostId());

            // Return successful response
            return ResponseEntity.status(HttpStatus.CREATED).body("Image post uploaded successfully with ID: " + savedPost.getPostId());

        } catch (IllegalArgumentException e) {
            logger.error("Invalid input during image post upload for userId: {}", form.getUserId(), e);
            return ResponseEntity.badRequest().body("Invalid input: " + e.getMessage());
        } catch (IOException e) {
            logger.error("Image upload failed due to storage error for userId: {}", form.getUserId(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed due to a storage issue.");
        } catch (Exception e) {
            logger.error("Unexpected error during image post upload for userId: {}", form.getUserId(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred during image post upload.");
        }
    }


    @PostMapping("/uploadTextPost")
    public ResponseEntity<String> uploadTextPost(
            @RequestParam("contentType") String contentType,
            @RequestParam("contentText") String contentText,
             @RequestParam("userId") Long userId
    ) {

        logger.info("Uploading text post with contentType: {}, contentText: {}", contentType, contentText);

        try {
            PostContent savedPost = postContentService.uploadTextPost(contentType, contentText,userId);
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
