package com.ibm.training.Content.Microservice.Service;

import org.springframework.stereotype.Service;

import com.ibm.training.Content.Microservice.Entity.PostContent;

import java.util.List;

@Service
public interface PostContentService {

    PostContent uploadImagePost(String contentType, String url, String caption, Long userId) throws Exception;

    PostContent uploadTextPost(String contentType, String contentText);

    PostContent uploadImageTextPost(String contentType, String contentText, String url) throws Exception;

    PostContent getPostById(Long postId);

    List<PostContent> getPostsByUserId(Long userId);

    List<PostContent> getAllPosts();
}
