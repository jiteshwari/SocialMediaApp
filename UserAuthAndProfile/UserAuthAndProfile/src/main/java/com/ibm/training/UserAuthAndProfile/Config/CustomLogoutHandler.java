package com.ibm.training.UserAuthAndProfile.Config;

import com.ibm.training.UserAuthAndProfile.Entity.User;
import com.ibm.training.UserAuthAndProfile.Repository.UserRepository;
import com.ibm.training.UserAuthAndProfile.Repository.VerificationTokenRepository;
import com.ibm.training.UserAuthAndProfile.Entity.VerificationToken; // Adjust import based on your actual entity package
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

public class CustomLogoutHandler implements LogoutHandler {

    private final VerificationTokenRepository tokenRepository;
    private final UserRepository userRepository;

    public CustomLogoutHandler(VerificationTokenRepository tokenRepository, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public String customlogout(Principal p) {
        // Log all request headers for debugging

        System.out.println(p.getName());
        if (!p.getName().isEmpty()) {

            String username = p.getName(); // Get username or user ID based on your implementation
            User user=userRepository.findByEmail(username);
            // Find tokens associated with the user
            VerificationToken token = tokenRepository.findByUserId(user.getId()); // Adjust method based on actual repository method

            if (token != null ) {
                // Log token deletion attempt
                System.out.println("Attempting to delete tokens for user: " + user.getId()+token);


                // Delete each token
                    tokenRepository.deleteByToken(token.getToken());
                    System.out.println("Token deleted successfully: " + token.getToken());

            } else {
                System.out.println("No tokens found for user: " + user.getId());
            }
        } else {
            System.out.println("No authentication information found");
        }

        // Invalidate the security context and session
      //  new SecurityContextLogoutHandler().logout(request, response, authentication);
        return "logged out successfully";
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // Log all request headers for debugging
//        request.getHeaderNames().asIterator().forEachRemaining(headerName -> {
//            System.out.println(headerName + ": " + request.getHeader(headerName));
//        });
//        System.out.println(authentication);
//        if (authentication != null && authentication.getPrincipal() instanceof User) {
//            User userDetails = (User) authentication.getPrincipal();
//            Long userId = userDetails.getId(); // Get username or user ID based on your implementation
//
//            // Find tokens associated with the user
//            List<VerificationToken> tokens = tokenRepository.findByUserId(userId); // Adjust method based on actual repository method
//
//            if (tokens != null && !tokens.isEmpty()) {
//                // Log token deletion attempt
//                System.out.println("Attempting to delete tokens for user: " + userId);
//
//                // Delete each token
//                for (VerificationToken token : tokens) {
//                    tokenRepository.deleteByToken(token.getToken());
//                    System.out.println("Token deleted successfully: " + token.getToken());
//                }
//            } else {
//                System.out.println("No tokens found for user: " + userId);
//            }
//        } else {
//            System.out.println("No authentication information found");
//        }
//
//        // Invalidate the security context and session
//        new SecurityContextLogoutHandler().logout(request, response, authentication);
    }
}
