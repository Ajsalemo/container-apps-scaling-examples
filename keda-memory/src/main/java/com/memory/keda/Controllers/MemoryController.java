package com.memory.keda.Controllers;

import java.util.Vector;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemoryController {
    @GetMapping("/api/memory")
    public ResponseEntity<String> allocateForMemory() {
        Vector<byte[]> v = new Vector<byte[]>();
        while (true) {
            byte[] b = new byte[1048576];
            v.add(b);
            Runtime rt = Runtime.getRuntime();
            System.out.println("free memory: " + rt.freeMemory());
        }
    }
}
