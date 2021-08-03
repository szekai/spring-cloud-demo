package com.szekai.OrderService.controller;

import com.szekai.OrderService.vo.Order;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final PaymentWebClient webClientController;
    @Autowired
    private KieSession session;

    @GetMapping("/greet")
    public Mono<String> greetDemo() {
        return webClientController.getUserFromWebClient("admin");
    }



    @PostMapping("/order")
    public Order orderNow(@RequestBody Order order) {
        session.insert(order);
        session.fireAllRules();
        return order;
    }
}
