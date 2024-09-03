package com.ibm.training.Content.Microservice.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ibm.training.Content.Microservice.Entity.PostContent;

import java.util.List;

@Service
public interface PostContentService {

    PostContent uploadImagePost(String contentType, MultipartFile imageFile, String caption) throws Exception;

    PostContent uploadTextPost(String contentType, String contentText);

    PostContent uploadImageTextPost(String contentType, String contentText, MultipartFile imageFile) throws Exception;

    PostContent getPostById(Long postId);

    List<PostContent> getPostsByUserId(Long userId);

    List<PostContent> getAllPosts();
}
