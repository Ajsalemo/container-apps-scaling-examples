package com.cpu.keda.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    String message = "container-apps-scaling-examples-keda-cpu";

    @GetMapping("/")
    public String index() {
        return message;
    }
}
