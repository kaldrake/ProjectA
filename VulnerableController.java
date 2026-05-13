// src/main/java/com/example/VulnerableController.java
package com.example;

import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.io.*;

@RestController
public class VulnerableController {
    
    // 1. SQL Injection
    @GetMapping("/sql")
    public String sqlInjection(@RequestParam String id) {
        String query = "SELECT * FROM users WHERE id = " + id;
        return "Executing: " + query;
    }
    
    // 2. Hardcoded password
    private static final String ADMIN_PASSWORD = "admin123";
    
    // 3. Path traversal
    @GetMapping("/file")
    public String readFile(@RequestParam String filename) throws IOException {
        File file = new File("/tmp/" + filename);
        return "Reading: " + file.getAbsolutePath();
    }
}
