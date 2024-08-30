package com.ibm.training.UserAuthAndProfile.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ibm.training.UserAuthAndProfile.Entity.UserProfile;

interface UserRepository extends JpaRepository<UserProfile, Long > {
}
