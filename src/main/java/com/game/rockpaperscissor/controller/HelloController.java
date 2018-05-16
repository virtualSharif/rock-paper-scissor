package com.game.rockpaperscissor.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Hello")
@RestController
@RequestMapping("/api/v1/hello")
public class HelloController {

    @GetMapping
    public String sayHello() {
        return "Hey there, I am your game partner. My name is Kelvin(COMPUTER)";
    }

}
