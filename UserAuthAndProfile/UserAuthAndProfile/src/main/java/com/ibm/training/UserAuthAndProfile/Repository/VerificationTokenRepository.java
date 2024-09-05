package com.ibm.training.UserAuthAndProfile.Repository;

import com.ibm.training.UserAuthAndProfile.Entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VerificationTokenRepository extends
        JpaRepository<VerificationToken,Long> {
    VerificationToken findByToken(String token);

    // Delete token by its value
    void deleteByToken(String token);

    VerificationToken findByUserId(Long userId);
}
