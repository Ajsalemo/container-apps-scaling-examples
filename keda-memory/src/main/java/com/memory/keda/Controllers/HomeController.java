package com.memory.keda.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    String message = "container-apps-scaling-keda-memory";
    
    @GetMapping("/")
    public String index() {
        return message;
    }
}
