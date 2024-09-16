package com.ibm.training.Content.Microservice.Entity;

import jakarta.persistence.*;
import java.io.Serializable;


import java.time.LocalDateTime; // Import LocalDateTime

@Entity
@Table(name = "Post")
public class PostContent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false)
    private Long userId;

    @Column(length = 50)  // Increased length for contentType
    private String contentType;

    @Column(name = "url")  // Removed columnDefinition
    private String posturl;

    @Column(columnDefinition = "TEXT")
    private String caption;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate; // Field for creation date and time

    @Column(name = "updated_date")
    private LocalDateTime updatedDate; // Field for last update date and time

    // Default Constructor
    public PostContent() {
        this.createdDate = LocalDateTime.now(); // Initialize createdDate with current time
    }

    // Parameterized Constructor
    public PostContent(Long postId, Long userId, String contentType, String posturl, String caption) {
        this.postId = postId;
        this.userId = userId;
        this.contentType = contentType;
        this.posturl = posturl;
        this.caption = caption;
        this.createdDate = LocalDateTime.now(); // Initialize createdDate with current time
    }

    // PrePersist to set the createdDate before inserting
    @PrePersist
    protected void onCreate() {
        if (this.createdDate == null) {
            this.createdDate = LocalDateTime.now();
        }
    }

    // PreUpdate to set the updatedDate before updating
    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = LocalDateTime.now();
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
