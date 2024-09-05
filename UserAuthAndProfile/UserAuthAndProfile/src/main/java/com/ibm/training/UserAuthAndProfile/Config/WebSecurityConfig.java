package com.ibm.training.UserAuthAndProfile.Config;



import com.ibm.training.UserAuthAndProfile.Repository.UserRepository;
import com.ibm.training.UserAuthAndProfile.Repository.VerificationTokenRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class WebSecurityConfig {

    private final VerificationTokenRepository tokenRepository;

    private final UserRepository userRepository;


    public WebSecurityConfig(VerificationTokenRepository tokenRepository, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    private static final String[] WHITE_LIST_URLS = {
            "/hello",
            "/customlogout",
            "/register",
            "/logout",
            "/verifyRegistration*",
            "/resendVerifyToken*"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .antMatchers(WHITE_LIST_URLS).permitAll()
                .antMatchers("/api/**").authenticated()
                .and()
                .oauth2Login(oauth2login ->
                        oauth2login.loginPage("/oauth2/authorization/api-client-oidc"))
                .oauth2Client()
                .and()
                .logout(logout -> logout
                        .logoutUrl("/logout")  // Specify the logout URL
                        .addLogoutHandler(customLogoutHandler())
                        .logoutSuccessUrl("/oauth2/authorization/api-client-oidc")  // Redirect after successful logout
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"));

        return http.build();
    }

    @Bean
    public CustomLogoutHandler customLogoutHandler() {
        return new CustomLogoutHandler(tokenRepository,userRepository);
    }
}
