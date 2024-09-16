package com.ibm.training.Homepage.Microservice.dto;

import java.io.Serializable;
import java.time.LocalDateTime; // Import LocalDateTime

public class PostContent implements Serializable {

    private Long postId;
    private Long userId;
    private String contentType;
    private String posturl;
    private String caption;
    private LocalDateTime createdDate; // Field for creation date and time
    private LocalDateTime updatedDate; // Field for last update date and time

    // Default Constructor
    public PostContent() {}

    // Parameterized Constructor
    public PostContent(Long postId, Long userId, String contentType, String posturl, String caption, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.postId = postId;
        this.userId = userId;
        this.contentType = contentType;
        this.posturl = posturl;
        this.caption = caption;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}
