package com.ibm.training.UserAuthAndProfile.Controller;

import com.ibm.training.UserAuthAndProfile.model.PostContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.Principal;
import java.util.List;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;


@RestController
public class HomeController {
    @Autowired
    private WebClient webClient;


    @GetMapping("/api/home/posts")
    public ResponseEntity<List<PostContent>> posts(
            @RegisteredOAuth2AuthorizedClient("api-client-authorization-code")
            OAuth2AuthorizedClient client) {

        List<PostContent> posts = this.webClient
                .get()
                .uri("http://127.0.0.1:8083/api/home/posts")
                .attributes(ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient(client))
                .retrieve()
                .bodyToFlux(PostContent.class)
                .collectList()
                .block();

        return ResponseEntity.ok(posts);
    }
}
