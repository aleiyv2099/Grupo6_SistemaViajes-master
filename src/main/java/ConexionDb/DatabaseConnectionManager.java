package ConexionDb;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/sistemareserva?serverTimezone=UTC";
    private static final String DB_USER = "mi_user";
    private static final String DB_PASSWORD = "admin123";

    public Connection getActiveConnection(){
        Connection con;
        try {
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            return con;
        } catch (SQLException e) {
            System.out.println("Database Connection Error: " + e.toString());
        }
        return null;
    }
}
