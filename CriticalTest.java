// CriticalTest.java
import java.sql.*;
import java.io.*;

public class CriticalTest {
    
    public static void main(String[] args) throws Exception {
        // 1. BLOCKER: SQL Injection
        String userId = "1";
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:test");
        String query = "SELECT * FROM users WHERE id = " + userId;
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);  // ← BLOCKER
        
        // 2. BLOCKER: Command Injection
        Runtime.getRuntime().exec("ping " + userId);  // ← BLOCKER
        
        // 3. CRITICAL: Hardcoded password
        String password = "admin123";  // ← CRITICAL
        
        // 4. CRITICAL: MD5
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
        md.digest(password.getBytes());  // ← CRITICAL
    }
}
