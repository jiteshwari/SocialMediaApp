package com.ibm.training.Homepage.Microservice.service;



import com.ibm.training.Homepage.Microservice.entity.Like;
import com.ibm.training.Homepage.Microservice.entity.PostContent;
import com.ibm.training.Homepage.Microservice.exceptions.LikeOperationException;
import com.ibm.training.Homepage.Microservice.repos.LikeRepo;

import com.ibm.training.Homepage.Microservice.repos.PostContentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class HomeService {

    private static final Logger logger = LoggerFactory.getLogger(HomeService.class);



    @Autowired
    private PostContentRepository postContentRepository;


    @Autowired
    private LikeRepo likeRepository;


    public List<PostContent> getAllPosts() {
        return postContentRepository.findAll();
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
