// UserController.java
import java.sql.*;

public class UserController {
    
    public User getUser(String userId) throws SQLException {
        // ❌ BLOCKER: SQL Injection
        String query = "SELECT * FROM users WHERE id = " + userId;
        Statement stmt = DriverManager.getConnection("jdbc:mysql://localhost/db").createStatement();
        ResultSet rs = stmt.executeQuery(query);
        return mapUser(rs);
    }
}
