package Function;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class DatabaseHandler {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/lirarian";
    private static final String USER = "root";
    private static final String PASS = "yuhang0821";

    public Set<String> getUniqueBookTypes() throws SQLException {
        Set<String> uniqueTypes = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            String query = "SELECT DISTINCT type FROM book";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                uniqueTypes.add(rs.getString("type"));
            }
        }
        return uniqueTypes;
    }

    public void insertBookType(String type) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO booktype (type) VALUES (?)")) {
            pstmt.setString(1, type);
            pstmt.executeUpdate();
        }
    }

    public void updateBookType(String oldType, String newType) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement("UPDATE booktype SET type = ? WHERE type = ?")) {
            pstmt.setString(1, newType);
            pstmt.setString(2, oldType);
            pstmt.executeUpdate();
        }
    }

    public void deleteBookType(String type) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM booktype WHERE type = ?")) {
            pstmt.setString(1, type);
            pstmt.executeUpdate();
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM book WHERE type = ?")) {
            pstmt.setString(1, type);
            pstmt.executeUpdate();
        }
    }
}