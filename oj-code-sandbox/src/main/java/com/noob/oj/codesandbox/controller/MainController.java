package com.noob.oj.codesandbox.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName MainController
 * @Description TODO
 * @Author holic-x
 * @Date 2024/5/3 17:51
 */
@RestController("/")
public class MainController {

    @GetMapping("/health")
    public String healthCheck() {
        return "ok";
    }
}




