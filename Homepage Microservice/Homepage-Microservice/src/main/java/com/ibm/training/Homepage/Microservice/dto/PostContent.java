package com.ibm.training.Homepage.Microservice.dto;


import jakarta.persistence.*;
import java.io.Serializable;


public class PostContent {

  
    private Long postId;

  
    private Long userId;

    private String contentType;


    private String posturl;

    private String caption;

    // Default Constructor
    public PostContent() {}

    // Parameterized Constructor


    public PostContent(Long postId, Long userId, String contentType, String posturl, String caption) {
        this.postId = postId;
        this.userId = userId;
        this.contentType = contentType;
        this.posturl = posturl;
        this.caption = caption;
    }

    // Getters and Setters
    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getPosturl() {
        return posturl;
    }

    public void setPosturl(String posturl) {
        this.posturl = posturl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
