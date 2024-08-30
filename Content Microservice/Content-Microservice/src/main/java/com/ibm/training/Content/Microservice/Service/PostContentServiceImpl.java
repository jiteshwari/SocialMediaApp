package com.ibm.training.Content.Microservice.Service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ibm.training.Content.Microservice.Entity.PostContent;
import com.ibm.training.Content.Microservice.Repository.PostContentRepository;


@Service
public class PostContentServiceImpl implements PostContentService{

	@Autowired
    private PostContentRepository postContentRepository;

    @Override
    public PostContent uploadImagePost(String contentType, String imageFile, String caption) throws Exception {
        PostContent postContent = new PostContent();
        postContent.setContentType(contentType);
        
        postContent.setContentUrl(new String(imageFile.getBytes())); // Replace with actual URL storage logic
        postContent.setCaption(caption);
        
        return postContentRepository.save(postContent);
    }

    @Override
    public PostContent uploadTextPost(String contentType, String contentText) {
        PostContent postContent = new PostContent();
        postContent.setContentType(contentType);
        postContent.setContentText(contentText);

        return postContentRepository.save(postContent);
    }

    @Override
    public PostContent uploadImageTextPost(String contentType, String contentText, MultipartFile imageFile) throws Exception {
        PostContent postContent = new PostContent();
        postContent.setContentType(contentType);
        postContent.setContentText(contentText);

        try {
            postContent.setContentUrl(new String(imageFile.getBytes())); // Replace with actual URL storage logic
        } catch (IOException e) {
            throw new Exception("Error processing the image file", e);
        }

        return postContentRepository.save(postContent);
    }
}