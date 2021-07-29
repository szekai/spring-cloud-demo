package com.szekai.OrderService.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class PaymentWebClient {
    @Autowired
    private WebClient.Builder webClientBuilder;

    private final String baseUrl = "http://payment-service";

    public Mono<String> getUserFromWebClient(String clientId) {
        Mono<String> result;
        if (clientId.equalsIgnoreCase("admin")) {
            result = webClientBuilder.baseUrl(baseUrl)
                    .build()
                    .get()
                    .uri("/admin")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class);
        } else {
            log.error("Unable to get the response, passing default.");
            result = Mono.fromCallable(() -> "Error in call.");
        }

        return result;
    }
}
