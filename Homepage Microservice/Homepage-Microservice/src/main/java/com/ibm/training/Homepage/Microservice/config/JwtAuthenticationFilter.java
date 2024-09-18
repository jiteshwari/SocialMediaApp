//package com.ibm.training.Homepage.Microservice.config;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.*;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.IOException;
//
//@Component
//public class JwtAuthenticationFilter implements Filter {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    private static final String AUTH_SERVICE_URL = "http://localhost:8084/api/auth/verify"; // Update with actual UserAuth service URL
//
//    private static final ThreadLocal<String> currentToken = new ThreadLocal<>();
//
//    private boolean validateTokenWithAuthService(String token) {
//        try {
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("Authorization", "Bearer " + token);
//
//            HttpEntity<String> request = new HttpEntity<>(headers);
//
//            ResponseEntity<String> response = restTemplate.exchange(
//                    AUTH_SERVICE_URL,
//                    HttpMethod.POST,
//                    request,
//                    String.class
//            );
//
//            return response.getStatusCode() == HttpStatus.OK; // Return true if token is valid
//        } catch (Exception e) {
//            return false; // Return false if token validation fails
//        }
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {}
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        // Set CORS headers
//        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
//        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
//        httpResponse.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
//        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
//
//        // Handle pre-flight requests
//        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
//            httpResponse.setStatus(HttpServletResponse.SC_OK);
//            return;
//        }
//
//        String path = httpRequest.getRequestURI();
//
//        // Bypass the JWT filter for Swagger UI and API docs
//        if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        // Extract the Authorization header
//        String authorizationHeader = httpRequest.getHeader("Authorization");
//
//        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            httpResponse.getWriter().write("Missing or invalid Authorization header");
//            return;
//        }
//
//        // Extract the token
//        String token = authorizationHeader.substring(7);
//        currentToken.set(token); // Store the token in ThreadLocal
//
//        // Validate the token with the UserAuth microservice
//        if (!validateTokenWithAuthService(token)) {
//            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            httpResponse.getWriter().write("Invalid or expired token");
//            return;
//        }
//
//        chain.doFilter(request, response);
//    }
//
//    @Override
//    public void destroy() {}
//
//    public static String getCurrentToken() {
//        return currentToken.get();
//    }
//}
