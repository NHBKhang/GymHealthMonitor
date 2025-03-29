package com.healthmonitor.controllers;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@ControllerAdvice
public class IndexController {
    @RequestMapping("/")
    public String index(Model model, @RequestParam Map<String, String> params) {
        return "index";
    }
}
