package com.ibm.training.UserAuthAndProfile.controller;

import com.ibm.training.UserAuthAndProfile.dto.JwtRequestDTO;
import com.ibm.training.UserAuthAndProfile.dto.JwtResponse;
import com.ibm.training.UserAuthAndProfile.dto.UserRegistrationForm;
import com.ibm.training.UserAuthAndProfile.entity.User;
import com.ibm.training.UserAuthAndProfile.exception.TokenInvalidException;
import com.ibm.training.UserAuthAndProfile.service.CloudStorageService;
import com.ibm.training.UserAuthAndProfile.service.TokenBlacklistService;
import com.ibm.training.UserAuthAndProfile.service.UserService;
import com.ibm.training.UserAuthAndProfile.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private CloudStorageService cloudStorageService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequestDTO jwtRequest) {
        logger.info("Login attempt for user with email: {}", jwtRequest.getEmail());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword())
            );
            logger.info("Authentication successful for user: {}", jwtRequest.getEmail());
        } catch (BadCredentialsException e) {
            logger.error("Authentication failed for user: {}", jwtRequest.getEmail(), e);
            throw new BadCredentialsException("Invalid credentials");
        }

        final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getEmail());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        User loggedInUser = userService.findByEmail(jwtRequest.getEmail())
                .orElseThrow(() -> {
                    logger.error("User not found for email: {}", jwtRequest.getEmail());
                    return new BadCredentialsException("User not found");
                });

        logger.info("User {} logged in successfully", jwtRequest.getEmail());
        return ResponseEntity.ok(new JwtResponse(loggedInUser, jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@ModelAttribute UserRegistrationForm form) {
        logger.info("Registering new user with email: {}", form.getEmail());

        try {
            User user = new User();
            user.setEmail(form.getEmail());
            user.setPassword(form.getPassword());
            user.setFirstName(form.getFirstName());
            user.setLastName(form.getLastName());
            user.setBio(form.getBio());
            String profilePicUrl = cloudStorageService.uploadImage(form.getProfilepic());
            user.setProfilepic(profilePicUrl);

            User registeredUser = userService.register(user);

            logger.info("User registered successfully with email: {}", form.getEmail());
            return ResponseEntity.ok(registeredUser);
        } catch (IllegalArgumentException e) {
            logger.error("Registration failed for user: {}", form.getEmail(), e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            logger.error("File upload failed for user: {}", form.getEmail(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed");
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenBlacklistService.blacklistToken(token);
            logger.info("Token blacklisted successfully: {}", token);
            return ResponseEntity.ok("Logged out successfully");
        }

        logger.warn("Logout attempt without a valid token.");
        return ResponseEntity.badRequest().body("No token provided");
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (tokenBlacklistService.isTokenBlacklisted(token)) {
                logger.warn("Token verification failed: Token is blacklisted");
                throw new TokenInvalidException("Token is blacklisted");
            }

            try {
                String username = jwtTokenUtil.extractUsername(token);
                UserDetails userDetails = userService.loadUserByUsername(username);

                if (jwtTokenUtil.validateToken(token, userDetails)) {
                    logger.info("Token verification successful for token: {}", token);
                    return ResponseEntity.ok("Token is valid");
                } else {
                    logger.warn("Token is invalid: {}", token);
                    throw new TokenInvalidException("Token is invalid");
                }
            } catch (Exception e) {
                logger.error("Token verification failed for token: {}", token, e);
                throw new TokenInvalidException("Token verification failed");
            }
        }

        logger.warn("Token verification attempt without a valid Authorization header.");
        return ResponseEntity.status(400).body("No token provided");
    }

    @PutMapping("/edit-profile/{id}")
    public ResponseEntity<?> editProfile(@PathVariable Long id, @RequestBody User updatedUser) {
        logger.info("Editing profile for user with ID: {}", id);
        try {
            User editedUser = userService.editUserProfile(id, updatedUser);
            logger.info("Profile updated successfully for user with ID: {}", id);
            return ResponseEntity.ok(editedUser);
        } catch (Exception e) {
            logger.error("Profile update failed for user with ID: {}", id, e);
            return ResponseEntity.status(500).body("Profile update failed");
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
