package com.ibm.training.Content.Microservice.Entity;


import jakarta.persistence.*;

import java.io.Serializable;
@Entity
@Table(name = "Post")
public class PostContent implements Serializable {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
    @Column(length = 10, nullable = false)
    private String contentType;
    @Column(length = 225)
    private String contentUrl;
    @Column(columnDefinition = "TEXT")
    private String contentText;
    // Constructors, Getters, and Setters
    public void Post() {}
    public Long getPostId() {
        return postId;
    }
    public void setPostId(Long postId) {
        this.postId = postId;
    }
//    public User getUser() {
//        return user;
//    }
//    public void setUser(User user) {
//        this.user = user;
//    }
    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public String getContentUrl() {
        return contentUrl;
    }
    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }
    public String getContentText() {
        return contentText;
    }
    public void setContentText(String contentText) {
        this.contentText = contentText;
    }
}