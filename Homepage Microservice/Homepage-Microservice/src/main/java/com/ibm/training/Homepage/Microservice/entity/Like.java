package com.ibm.training.Homepage.Microservice.entity;



import jakarta.persistence.*;

@Entity
@Table(name = "Likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @Column(nullable = false)
    private Long userId;  // ID of the user who liked the post

    @Column(nullable = false)
    private Long postId;  // ID of the liked post

    // Default constructor
    public Like() {}

    // Parameterized constructor
    public Like(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
    }

    // Getters and setters
    public Long getLikeId() {
        return likeId;
    }

    public void setLikeId(Long likeId) {
        this.likeId = likeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
