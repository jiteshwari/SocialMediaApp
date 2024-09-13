package com.ibm.training.Homepage.Microservice.config;




import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter implements Filter {

    @Autowired
    private RestTemplate restTemplate;

    private static final String AUTH_SERVICE_URL = "http://localhost:8084/api/auth/verify"; // Update with actual UserAuth service URL



    private boolean validateTokenWithAuthService(String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<String> request = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    AUTH_SERVICE_URL,
                    HttpMethod.POST,
                    request,
                    String.class
            );

            return response.getStatusCode() == HttpStatus.OK; // Return true if token is valid
        } catch (Exception e) {
            return false; // Return false if token validation fails
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Cast the general ServletRequest and ServletResponse to Http-specific ones
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Extract the Authorization header
        String authorizationHeader = httpRequest.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            // If no token or invalid format, return 401 Unauthorized
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Missing or invalid Authorization header");
            return;
        }

        // Extract the token from the Authorization header
        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix

        // Validate the token with the UserAuth microservice
        if (!validateTokenWithAuthService(token)) {
            // If the token is invalid, return 401 Unauthorized
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Invalid or expired token");
            return;
        }

        // If token is valid, continue the request
        chain.doFilter(request, response);
    }


    @Override
    public void destroy() {}
}
