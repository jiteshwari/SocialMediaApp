package com.ibm.training.Homepage.Microservice.service;



import com.ibm.training.Homepage.Microservice.dto.PostContent;
import com.ibm.training.Homepage.Microservice.entity.Like;
import com.ibm.training.Homepage.Microservice.exceptions.ExternalServiceException;
import com.ibm.training.Homepage.Microservice.exceptions.LikeOperationException;
import com.ibm.training.Homepage.Microservice.repos.LikeRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.util.Arrays;
import java.util.List;

@Service
public class HomeService {

    private static final Logger logger = LoggerFactory.getLogger(HomeService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LikeRepo likeRepository;

//    // Utility method to create headers with JWT token
//    private HttpHeaders createHeadersWithToken() {
//        //HttpHeaders headers = new HttpHeaders();
//        //String token = JwtAuthenticationFilter.getCurrentToken();
//        if (token != null) {
//            headers.set("Authorization", "Bearer " + token);
//            logger.info("JWT token added to headers.");
//        } else {
//            logger.warn("No JWT token available for the request.");
//        }
//        return headers;
//    }

    public PostContent getPostContent(Long postId) {
        String url = "http://content-microservice/api/posts/" + postId;
       // HttpHeaders headers = createHeadersWithToken();
        //HttpEntity<Void> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<PostContent> response = restTemplate.exchange(url, HttpMethod.GET, null, PostContent.class);
            logger.info("Successfully fetched post content for postId {}.", postId);
            return response.getBody();
        } catch (RestClientException e) {
            logger.error("Error fetching post content for postId {}: {}", postId, e.getMessage(), e);
            throw new ExternalServiceException("Failed to fetch post content.", e);
        }
    }

    public List<PostContent> getAllPosts() {
        String url = "http://localhost:8082/api/posts/all";
      //  HttpHeaders headers = createHeadersWithToken();
        //HttpEntity<Void> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<PostContent[]> response = restTemplate.exchange(url, HttpMethod.GET, null, PostContent[].class);
            logger.info("Successfully fetched all posts.");
            return Arrays.asList(response.getBody());
        } catch (RestClientException e) {
            logger.error("Error fetching all posts: {}", e.getMessage(), e);
            throw new ExternalServiceException("Failed to fetch all posts.", e);
        }
    }

    public Like likePost(Long userId, Long postId) {
        try {
            Like like = new Like(userId, postId);
            Like savedLike = likeRepository.save(like);
            logger.info("User {} liked post {}.", userId, postId);
            return savedLike;
        } catch (Exception e) {
            logger.error("Error liking post {}: {}", postId, e.getMessage(), e);
            throw new LikeOperationException("Failed to like post.");
        }
    }
}
