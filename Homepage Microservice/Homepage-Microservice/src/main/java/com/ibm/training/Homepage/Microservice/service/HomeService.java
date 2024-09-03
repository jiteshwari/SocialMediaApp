package com.ibm.training.Homepage.Microservice.service;

import java.util.Arrays;
import java.util.List;

import com.ibm.training.Homepage.Microservice.entity.Like;
import com.ibm.training.Homepage.Microservice.repos.LikeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ibm.training.Homepage.Microservice.dto.PostContent;

@Service
public class HomeService {

    @Autowired
    private RestTemplate restTemplate;

    public PostContent getPostContent(Long postId) {
        String url = "http://content-microservice/api/posts/" + postId;
        System.out.println(restTemplate.getForObject(url, PostContent.class));
        return restTemplate.getForObject(url, PostContent.class);
    }
    
    public List<PostContent> getAllPosts() {
        String url = "http://localhost:8082/api/posts/all";
        ResponseEntity<PostContent[]> response = restTemplate.getForEntity(url, PostContent[].class);
        System.out.println(response);
        return Arrays.asList(response.getBody());
    }


    @Autowired
    private LikeRepo likeRepository;

    public Like likePost(Long userId, Long postId) {
        Like like = new Like(userId, postId);
        return likeRepository.save(like);
    }
}