import java.sql.*;
import java.io.*;

public class RealVulnerabilities {
    
    // =============================================
    // BLOCKER: SQL Injection with REAL database
    // =============================================
    public void realSQLInjection(String userId) throws SQLException {
        // MUST have actual connection and execution
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        Statement stmt = conn.createStatement();
        
        // Create table first
        stmt.execute("CREATE TABLE IF NOT EXISTS users (id VARCHAR(10), name VARCHAR(100))");
        
        // ❌ BLOCKER - actual SQL injection with execution
        String query = "SELECT * FROM users WHERE id = '" + userId + "'";
        ResultSet rs = stmt.executeQuery(query);  // ← REAL execution!
        System.out.println("Query executed: " + query);
        conn.close();
    }
    
    // =============================================
    // BLOCKER: Command Injection with REAL execution
    // =============================================
    public void realCommandInjection(String userInput) throws IOException {
        // ❌ BLOCKER - actual command execution
        Runtime.getRuntime().exec("cmd /c " + userInput);  // ← REAL execution!
        System.out.println("Command executed: " + userInput);
    }
    
    // =============================================
    // CRITICAL: Hardcoded password used in AUTH
    // =============================================
    private static final String ADMIN_PASSWORD = "admin123";
    
    public boolean realAuthentication(String inputPassword) {
        // ❌ CRITICAL - actual authentication check
        return ADMIN_PASSWORD.equals(inputPassword);  // ← REAL comparison!
    }
    
    // =============================================
    // CRITICAL: Hardcoded API key used in validation
    // =============================================
    private static final String API_KEY = "sk-12345-abcde";
    
    public boolean validateApiKey(String key) {
        // ❌ CRITICAL - actual API key validation
        return API_KEY.equals(key);  // ← REAL comparison!
    }
    
    // =============================================
    // CRITICAL: Weak cryptography (MD5)
    // =============================================
    public void realWeakHash(String input) throws Exception {
        // ❌ CRITICAL - actual MD5 usage
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(input.getBytes());  // ← REAL hashing!
        System.out.println("MD5 hash: " + bytesToHex(hash));
    }
    
    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    
    // =============================================
    // Main method to trigger everything
    // =============================================
    public static void main(String[] args) throws Exception {
        RealVulnerabilities test = new RealVulnerabilities();
        
        test.realSQLInjection("1' OR '1'='1");
        test.realCommandInjection("calc.exe");
        test.realAuthentication("admin123");
        test.validateApiKey("sk-12345-abcde");
        test.realWeakHash("password");
        
        System.out.println("All vulnerabilities executed!");
    }
}
