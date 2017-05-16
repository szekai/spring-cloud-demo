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

    @Value("${info.foo}")
    private String info;

    @Value("${info.test1}")
    private String test1;

    @Value("${info.test2}")
    private String test2;
    @RequestMapping(
            value = "/whoami/{username}",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String whoami(@PathVariable("username") String username){
        return String.format("Hello! You're %s and you'll become a(n) %s...\n" +
                        " Info from application.yml %s\n" +
                        "Info Test 1 from %s\n" +
                        "Info Test 2 from %s",
                username, role, info, test1, test2);
    }


    @Value("${db.config.driver}")
    private String driver;
    @Value("${db.config.url}")
    private String url;
    @Value("${db.config.user}")
    private String user;
    @Value("${db.config.password}")
    private String password;

    @RequestMapping(
            value = "/dbinfo",
            method = RequestMethod.GET
    )
    public DBinfo dbinfo(){
        return new DBinfo(driver, url, user, password);
    }

}
