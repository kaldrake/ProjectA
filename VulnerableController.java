package com.example;

import org.springframework.web.bind.annotation.*;
import java.sql.*;
import java.io.*;
import java.security.MessageDigest;
import java.nio.file.*;

@RestController
public class VulnerableController {
    
    // 1. BLOCKER: SQL Injection with actual execution
    @GetMapping("/sql")
    public String sqlInjection(@RequestParam String userId) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:test");
        String query = "SELECT * FROM users WHERE id = " + userId;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        return "SQL executed";
    }
    
    // 2. BLOCKER: Command Injection
    @GetMapping("/ping")
    public String commandInjection(@RequestParam String host) throws IOException {
        Runtime.getRuntime().exec("ping -c 4 " + host);
        return "Command executed";
    }
    
    // 3. CRITICAL: Hardcoded password (used)
    private static final String ADMIN_PASSWORD = "admin123";
    
    @PostMapping("/login")
    public String login(@RequestParam String password) {
        if (ADMIN_PASSWORD.equals(password)) {
            return "Login success";
        }
        return "Login failed";
    }
    
    // 4. CRITICAL: Hardcoded API key (used)
    private static final String API_KEY = "sk-12345-abcde";
    
    @GetMapping("/data")
    public String getData(@RequestHeader("X-API-Key") String apiKey) {
        if (API_KEY.equals(apiKey)) {
            return "Sensitive data";
        }
        return "Unauthorized";
    }
    
    // 5. CRITICAL: MD5 hashing
    @GetMapping("/hash")
    public String weakHash(@RequestParam String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(input.getBytes());
        return bytesToHex(hash);
    }
    
    // 6. MAJOR: Path traversal
    @GetMapping("/file")
    public String readFile(@RequestParam String filename) throws IOException {
        Path path = Paths.get("/tmp/" + filename);
        return Files.readString(path);
    }
    
    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
