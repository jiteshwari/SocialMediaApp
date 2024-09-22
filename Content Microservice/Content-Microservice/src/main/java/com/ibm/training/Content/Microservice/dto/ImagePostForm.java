package com.ibm.training.Content.Microservice.dto;



import org.springframework.web.multipart.MultipartFile;

public class ImagePostForm {

    private String contentType;
    private MultipartFile imageFile;
    private String caption;
    private Long userId;

    // Getters and setters
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
