package com.example.userservice.controller;


import com.example.userservice.vo.Greeting;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UserController {

    private final Environment env;
    private final Greeting greeting;

    @RequestMapping("/health-check")
    public String hello() {
        return "It`s working in user-service";
    }

    @RequestMapping("/welcome")
    public String greeting() {
        //return env.getProperty("greeting.message");
        return greeting.getMessage();
    }

}
