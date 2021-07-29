package com.szekai.OrderService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class OrderController {
    private final PaymentWebClient webClientController;

    @GetMapping("/greet")
    public Mono<String> greetDemo() {
        return webClientController.getUserFromWebClient("admin");
    }
}
