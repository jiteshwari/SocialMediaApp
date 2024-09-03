package com.ibm.training.Homepage.Microservice.repos;




import com.ibm.training.Homepage.Microservice.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepo extends JpaRepository<Like, Long> {

}
