package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LookUpController {

    @Value("${user.role}")
    private String role;

    @RequestMapping(
            value = "/whoami/{username}",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String whoami(@PathVariable("username") String username){
        return String.format("Hello! You're %s and you'll become a(n) %s...\n", username, role);
    }

}
