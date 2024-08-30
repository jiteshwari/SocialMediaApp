//package com.ibm.training.Content.Microservice.Controller;
//
//import java.io.IOException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.ibm.training.Content.Microservice.Entity.PostContent;
//import com.ibm.training.Content.Microservice.Repository.PostContentRepository;
//
//@RestController
//@RequestMapping("/api/posts")
//public class PostContentController {
//
//@Autowired
//private PostContentRepository postContentRepository;
//
//@PostMapping("/uploadImagePost")
//public ResponseEntity<PostContent> uploadImagePost(
//        @RequestParam("contentType") String contentType,
//        @RequestParam("imageFile") MultipartFile imageFile) {
//
//    PostContent postContent = new PostContent();
//    postContent.setContentType(contentType);
//
//    try {
//        postContent.setContentUrl(new String(imageFile.getBytes())); // Assuming the URL is a base64 string of the image
//    } catch (IOException e) {
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }
//
//    PostContent savedPost = postContentRepository.save(postContent);
//    return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
//}
//
//@PostMapping("/uploadTextPost")
//public ResponseEntity<PostContent> uploadTextPost(
//        @RequestParam("contentType") String contentType,
//        @RequestParam("contentText") String contentText) {
//
//    PostContent postContent = new PostContent();
//    postContent.setContentType(contentType);
//    postContent.setContentText(contentText);
//
//    PostContent savedPost = postContentRepository.save(postContent);
//    return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
//}
//
//@PostMapping("/uploadImageTextPost")
//public ResponseEntity<PostContent> uploadImageTextPost(
//        @RequestParam("contentType") String contentType,
//        @RequestParam("contentText") String contentText,
//        @RequestParam("imageFile") MultipartFile imageFile) {
//
//    PostContent postContent = new PostContent();
//    postContent.setContentType(contentType);
//    postContent.setContentText(contentText);
//
//    try {
//        postContent.setContentUrl(new String(imageFile.getBytes())); // Assuming the URL is a base64 string of the image
//    } catch (IOException e) {
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }
//
//    PostContent savedPost = postContentRepository.save(postContent);
//    return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
//}
//}



package com.ibm.training.Content.Microservice.Controller;

import com.ibm.training.Content.Microservice.Entity.PostContent;
import com.ibm.training.Content.Microservice.Service.PostContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/posts")
public class PostContentController {

    @Autowired
    private PostContentService postContentService;

    @PostMapping("/uploadImagePost")
    public ResponseEntity<PostContent> uploadImagePost(
            @RequestParam("contentType") String contentType,
            @RequestParam("url") String imageFile ,
            @RequestParam("caption") String caption) {

        try {
            PostContent savedPost = postContentService.uploadImagePost(contentType, imageFile, caption);
            return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/uploadTextPost")
    public ResponseEntity<PostContent> uploadTextPost(
            @RequestParam("contentType") String contentType,
            @RequestParam("contentText") String contentText) {

        PostContent savedPost = postContentService.uploadTextPost(contentType, contentText);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @PostMapping("/uploadImageTextPost")
    public ResponseEntity<PostContent> uploadImageTextPost(
            @RequestParam("contentType") String contentType,
            @RequestParam("contentText") String contentText,
            @RequestParam("imageFile") MultipartFile imageFile) {

        try {
            PostContent savedPost = postContentService.uploadImageTextPost(contentType, contentText, imageFile);
            return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

