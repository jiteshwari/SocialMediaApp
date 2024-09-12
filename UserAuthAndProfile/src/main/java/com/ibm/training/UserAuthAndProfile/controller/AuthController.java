package com.ibm.training.UserAuthAndProfile.controller;


import com.ibm.training.UserAuthAndProfile.dto.JwtRequestDTO;
import com.ibm.training.UserAuthAndProfile.dto.JwtResponse;
import com.ibm.training.UserAuthAndProfile.entity.User;
import com.ibm.training.UserAuthAndProfile.service.UserService;
import com.ibm.training.UserAuthAndProfile.util.JwtTokenUtil;
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
}
