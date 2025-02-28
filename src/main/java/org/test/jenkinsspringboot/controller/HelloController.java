package org.test.jenkinsspringboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, world";
    }

    @PostMapping("/greet")
    public String greet(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        return "Hello, " + name + "!";
    }
}