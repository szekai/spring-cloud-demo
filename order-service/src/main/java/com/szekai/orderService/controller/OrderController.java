package com.szekai.orderService.controller;

import com.szekai.orderService.service.PaymentWebClient;
import com.szekai.orderService.vo.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final PaymentWebClient webClientController;
    @Autowired
    private KieSession session;

    @GetMapping("/greet")
    public Mono<String> greetDemo() throws TimeoutException {
        return webClientController.getPaymentFromWebClient("admin")
                .map(greeting -> {
                    log.info(greeting);
                    return String.format("%s: %s", "Order", greeting);});
    }



    @PostMapping("/order")
    public Order orderNow(@RequestBody Order order) {
        session.insert(order);
        session.fireAllRules();
        return order;
    }
}
