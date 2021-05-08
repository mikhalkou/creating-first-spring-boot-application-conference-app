package com.mikhalkou.learn.springboot1.conferencespringboot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {
    @Value("${app.version}")
    private String appVersion;

    @GetMapping("/")
    public Map getStatus() {
        Map<String, String> map = new HashMap<>();
        map.put("version", appVersion);
        return map;
    }
}
