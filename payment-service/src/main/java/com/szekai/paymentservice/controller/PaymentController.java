package com.szekai.paymentservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    @Autowired
    Environment environment;

    /**
     * Returns a string response containing the port information, so that later we can distinguish that from which
     * service instance the request was satisfied.
     */
    @GetMapping("/admin")
    public ResponseEntity<String> getAdmin() {
        return ResponseEntity.ok().body("Payment from port = " +
                environment.getProperty("local.server.port"));
    }
}
