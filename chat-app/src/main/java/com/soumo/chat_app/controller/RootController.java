package com.soumo.chat_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {
    public RootController(){}
    @GetMapping("/")
    public String root(){
        return "redirect:/swagger-ui.html";
    }
}
