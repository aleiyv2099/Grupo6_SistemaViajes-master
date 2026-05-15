package ConexionDb;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnectionManager {

    private static final Logger LOGGER = Logger.getLogger(DatabaseConnectionManager.class.getName());
    private static final String DB_URL;
    private static final String DB_USER;
    private static final String DB_PASSWORD;

    static {
        Properties props = new Properties();
        try (InputStream in = DatabaseConnectionManager.class
                .getClassLoader().getResourceAsStream("database.properties")) {
            if (in == null) throw new IOException("database.properties no encontrado en resources");
            props.load(in);
        } catch (IOException e) {
            throw new ExceptionInInitializerError("No se pudo cargar database.properties: " + e.getMessage());
        }
        DB_URL      = props.getProperty("db.url");
        DB_USER     = props.getProperty("db.user");
        DB_PASSWORD = props.getProperty("db.password");
    }

    public Connection getActiveConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
