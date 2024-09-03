package com.ibm.training.Content.Microservice.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ibm.training.Content.Microservice.Entity.PostContent;
import com.ibm.training.Content.Microservice.Repository.PostContentRepository;
import com.ibm.training.Content.Microservice.exceptions.FileStorageException;
import com.ibm.training.Content.Microservice.exceptions.PostContentException;

import java.io.IOException;
import java.util.List;

@Service
public class PostContentServiceImpl implements PostContentService {

    private static final Logger logger = LoggerFactory.getLogger(PostContentServiceImpl.class);

    @Autowired
    private PostContentRepository postContentRepository;

    @Override
    public PostContent uploadImagePost(String contentType, MultipartFile imageFile, String caption) {
        byte[] imageData = getImageData(imageFile);
        PostContent postContent = new PostContent();
        postContent.setContentType(contentType);
        postContent.setCaption(caption);
        postContent.setContentData(imageData);
        postContent.setUserId(1L);

        return savePostContent(postContent);
    }

    @Override
    public PostContent uploadTextPost(String contentType, String contentText) {
        PostContent postContent = new PostContent();
        postContent.setContentType(contentType);
        postContent.setCaption(contentText);
        postContent.setUserId(1L);
        return savePostContent(postContent);
    }

    @Override
    public PostContent uploadImageTextPost(String contentType, String contentText, MultipartFile imageFile) {
        byte[] imageData = getImageData(imageFile);
        PostContent postContent = new PostContent();
        postContent.setContentType(contentType);
        postContent.setCaption(contentText);
        postContent.setContentData(imageData);
        postContent.setUserId(1L);
        return savePostContent(postContent);
    }

    private byte[] getImageData(MultipartFile imageFile) {
        try {
            return imageFile.getBytes();
        } catch (IOException e) {
            logger.error("Error reading the image file", e);
            throw new FileStorageException("Error processing the image file", e);
        }
    }

    private PostContent savePostContent(PostContent postContent) {
        try {
            return postContentRepository.save(postContent);
        } catch (DataAccessException e) {
            logger.error("Error saving post content to the database", e);
            throw new PostContentException("Error saving post content to the database", e);
        }
    }

    @Override
    public PostContent getPostById(Long postId) {
        return postContentRepository.findById(postId).orElse(null);
    }

    @Override
    public List<PostContent> getPostsByUserId(Long userId) {
        return postContentRepository.findByUserId(userId);
    }

    @Override
    public List<PostContent> getAllPosts() {
        return postContentRepository.findAll();
    }

}
