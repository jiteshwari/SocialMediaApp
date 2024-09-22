package com.ibm.training.Homepage.Microservice.repos;

import com.ibm.training.Homepage.Microservice.entity.PostContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



import java.util.List;

@Repository
public interface  PostContentRepository extends JpaRepository<PostContent, Long>{


    List<PostContent> findByUserId(Long userId);
}



