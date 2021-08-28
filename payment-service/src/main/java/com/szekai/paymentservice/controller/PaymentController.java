package com.szekai.paymentservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PaymentController {
    @Value("${successful.call.divisor}")
    private int divisor;
    private int nrOfCalls = 0;

    @Autowired
    Environment environment;

    /**
     * Returns a string response containing the port information, so that later we can distinguish that from which
     * service instance the request was satisfied.
     */
    @GetMapping("/admin")
    public ResponseEntity<String> getAdmin() {
        log.info("Service unavaiable");
        if (isServiceUnavailable()) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        log.info("Service available");
        return ResponseEntity.ok().body("Payment from port = " +
                environment.getProperty("local.server.port"));
    }

    private boolean isServiceUnavailable() {
        return ++nrOfCalls % divisor != 0;
    }
}
