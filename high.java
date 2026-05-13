import java.sql.*;
import java.io.*;

public class HighSeverityTest {
    
    // CRITICAL: Hardcoded password used in authentication
    private static final String ADMIN_PASSWORD = "admin123";
    private static final String API_SECRET = "sk-12345-abcde";
    
    public boolean login(String password) {
        // ✅ CRITICAL - password used in authentication
        return ADMIN_PASSWORD.equals(password);
    }
    
    public boolean validateApiKey(String key) {
        // ✅ CRITICAL - API key used in validation
        return API_SECRET.equals(key);
    }
    
    // BLOCKER: SQL Injection with actual execution
    public void sqlInjection(String userId) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:test");
        // ✅ BLOCKER - string concatenation with actual execution
        String query = "SELECT * FROM users WHERE id = '" + userId + "'";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
    }
    
    // BLOCKER: Command Injection with actual execution
    public void commandInjection(String cmd) throws IOException {
        // ✅ BLOCKER - user input in command
        Runtime.getRuntime().exec("cmd /c " + cmd);
    }
    
    // CRITICAL: Weak cryptography (MD5)
    public void weakHash(String input) throws Exception {
        // ✅ CRITICAL - MD5 used for hashing
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
        md.digest(input.getBytes());
    }
}
