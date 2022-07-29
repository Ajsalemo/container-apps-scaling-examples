package com.cpu.keda.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CpuController {
    @GetMapping("/api/cpu")
    public ResponseEntity<String> loopForCpu() {
        for (int i = 0; i < 5; i++) {
            System.out.println(i);
        }

        return new ResponseEntity<String>("Executing loop, check console..", HttpStatus.OK);
    }
}
