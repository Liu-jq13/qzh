package com.kama.notes.controller;

import com.kama.notes.scope.RequestScopeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @Autowired
    private RequestScopeData requestScopeData;

    @GetMapping("/hello")
    public String hello() {

        System.out.println("get data in /test/hello");
        System.out.println(requestScopeData.getUserId());
        System.out.println(requestScopeData.getToken());
        return "Hello World!";
    }

    @GetMapping("/exception")
    public String exception() {
        throw new RuntimeException("test exception");
    }
}
