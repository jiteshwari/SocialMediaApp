package com.ibm.training.UserAuthAndProfile.dto;

import org.springframework.web.multipart.MultipartFile;

public class UserRegistrationForm {
    private String email;
    private String username;
    private String password;
    private MultipartFile profilepic;
    private String firstName;
    private String lastName;
    private String bio;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MultipartFile getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(MultipartFile profilepic) {
        this.profilepic = profilepic;
    }
}
