package com.ibm.training.UserAuthAndProfile.controller;

import com.ibm.training.UserAuthAndProfile.dto.JwtRequestDTO;
import com.ibm.training.UserAuthAndProfile.dto.JwtResponse;
import com.ibm.training.UserAuthAndProfile.entity.User;
import com.ibm.training.UserAuthAndProfile.service.TokenBlacklistService;
import com.ibm.training.UserAuthAndProfile.service.UserService;
import com.ibm.training.UserAuthAndProfile.util.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

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
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid credentials");
        }

        final UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getEmail());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        User loggedInUser = userService.findByEmail(jwtRequest.getEmail()).get();

        return ResponseEntity.ok(new JwtResponse(loggedInUser, jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        User registeredUser = userService.register(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenBlacklistService.blacklistToken(token);
            return ResponseEntity.ok("Logged out successfully");
        }

        return ResponseEntity.badRequest().body("No token provided");
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            if (tokenBlacklistService.isTokenBlacklisted(token)) {
                return ResponseEntity.status(401).body("Token is blacklisted");
            }

            try {
                String username = jwtTokenUtil.extractUsername(token);
                UserDetails userDetails = userService.loadUserByUsername(username);

                if (jwtTokenUtil.validateToken(token, userDetails)) {
                    return ResponseEntity.ok("Token is valid");
                } else {
                    return ResponseEntity.status(401).body("Token is invalid");
                }
            } catch (Exception e) {
                return ResponseEntity.status(401).body("Token verification failed");
            }
        }

        return ResponseEntity.status(400).body("No token provided");
    }

    @PutMapping("/edit-profile/{id}")
    public ResponseEntity<?> editProfile(@PathVariable Long id, @RequestBody User updatedUser) {
        User editedUser = userService.editUserProfile(id, updatedUser);
        return ResponseEntity.ok(editedUser);
    }
}
