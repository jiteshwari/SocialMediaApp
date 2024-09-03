package com.ibm.training.Content.Microservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ibm.training.Content.Microservice.Entity.PostContent;

import java.util.List;

@Repository
public interface  PostContentRepository extends JpaRepository<PostContent, Long>{


    List<PostContent> findByUserId(Long userId);
}



