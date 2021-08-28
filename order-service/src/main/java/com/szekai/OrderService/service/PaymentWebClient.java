package com.szekai.OrderService.service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeoutException;

@Slf4j
@Service
public class PaymentWebClient {
    @Autowired
    private WebClient.Builder webClientBuilder;

    private final String baseUrl = "http://payment-service";

    @CircuitBreaker(name = "orderService", fallbackMethod = "buildFallbackPaymentList" )
    @RateLimiter(name = "orderService", fallbackMethod = "buildFallbackPayment2List")
    @Retry(name = "retryOrderService", fallbackMethod = "buildFallbackPayment3List")
    @Bulkhead(name = "bulkheadOrderService", type= Bulkhead.Type.THREADPOOL, fallbackMethod = "buildFallbackPayment4List")
    public Mono<String> getPaymentFromWebClient(String clientId) throws TimeoutException {
        randomlyRunLong();
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
    @SuppressWarnings("unused")
    private Mono<String> buildFallbackPaymentList(String organizationId, Throwable t){
        return Mono.fromCallable(() -> "Service no available, circuit breaker");
    }

    private Mono<String> buildFallbackPayment2List(String organizationId, Throwable t){
        return Mono.fromCallable(() -> "Service no available, rate limiter");
    }

    private Mono<String> buildFallbackPayment3List(String organizationId, Throwable t){
        return Mono.fromCallable(() -> "Service no available, retry");
    }

    private Mono<String> buildFallbackPayment4List(String organizationId, Throwable t){
        return Mono.fromCallable(() -> "Service no available, bulkhead");
    }
    private void randomlyRunLong() throws TimeoutException {
        Random rand = new Random();
        int randomNum = rand.nextInt((3 - 1) + 1) + 1;
        if (randomNum==3) sleep();
    }

    private void sleep() throws TimeoutException{
        try {
            System.out.println("Sleep");
            Thread.sleep(5000);
            throw new java.util.concurrent.TimeoutException();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }
}
