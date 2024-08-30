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
    @Column(length = 10)
    private String contentType;
    @Column
    private String contentUrl;
    @Column(columnDefinition = "TEXT")
    private String caption;
    // Constructors, Getters, and Setters
	public PostContent() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PostContent(Long postId, String contentType, String contentUrl, String caption) {
		super();
		this.postId = postId;
		this.contentType = contentType;
		this.contentUrl = contentUrl;
		this.caption = caption;
	}
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
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
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
    
        
}