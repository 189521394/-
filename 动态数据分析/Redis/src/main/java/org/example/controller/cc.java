package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class cc {
    @RequestMapping("/")
    public String getView() {
        return "index";
    }
}
