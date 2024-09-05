//package com.ibm.training.UserAuthAndProfile.Entity;
//
//
//import javax.persistence.*;
//
//import java.time.LocalDateTime;
//@Entity
//@Table(name = "User")
//
//
//public class UserProfile {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long userId;
//    @Column(length = 50, nullable = false)
//    private String username;
//    @Column(length = 100, nullable = false, unique = true)
//    private String email;
//    @Column(length = 255, nullable = false)
//    private String passwordHash;
//    @Column(length = 255)
//    private String profilePicture;
//    @Column(columnDefinition = "TEXT")
//    private String bio;
//    @Column(name = "created_at", nullable = false, updatable = false)
//    @org.hibernate.annotations.CreationTimestamp
//    private LocalDateTime createdAt;
//    @Column(name = "updated_at", nullable = false)
//    @org.hibernate.annotations.UpdateTimestamp
//    private LocalDateTime updatedAt;
//    // Getters and Setters
//    public Long getUserId() {
//        return userId;
//    }
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }
//    public String getUsername() {
//        return username;
//    }
//    public void setUsername(String username) {
//        this.username = username;
//    }
//    public String getEmail() {
//        return email;
//    }
//    public void setEmail(String email) {
//        this.email = email;
//    }
//    public String getPasswordHash() {
//        return passwordHash;
//    }
//    public void setPasswordHash(String passwordHash) {
//        this.passwordHash = passwordHash;
//    }
//    public String getProfilePicture() {
//        return profilePicture;
//    }
//    public void setProfilePicture(String profilePicture) {
//        this.profilePicture = profilePicture;
//    }
//    public String getBio() {
//        return bio;
//    }
//    public void setBio(String bio) {
//        this.bio = bio;
//    }
//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }
//    public LocalDateTime getUpdatedAt() {
//        return updatedAt;
//    }
//    public void setUpdatedAt(LocalDateTime updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//}
//
