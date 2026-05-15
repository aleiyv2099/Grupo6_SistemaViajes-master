package ConexionDb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnectionManager {

    private static final Logger LOGGER = Logger.getLogger(DatabaseConnectionManager.class.getName());
    private static final String DB_URL;
    private static final String DB_USER;
    private static final String DB_PASSWORD;
    private static final String BASE_URL;

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
        // URL base sin nombre de BD: jdbc:mysql://host:port/db?params → jdbc:mysql://host:port/?params
        BASE_URL    = DB_URL.replaceFirst("/[^/?]+(?=\\?|$)", "/");

        initializeDatabase();
    }

    private static void initializeDatabase() {
        InputStream schemaStream = DatabaseConnectionManager.class
                .getClassLoader().getResourceAsStream("schema.sql");
        if (schemaStream == null) {
            LOGGER.warning("schema.sql no encontrado en resources — saltando inicialización");
            return;
        }

        try (Connection conn = DriverManager.getConnection(BASE_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             BufferedReader reader = new BufferedReader(new InputStreamReader(schemaStream, "UTF-8"))) {

            StringBuilder sqlBuffer = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.startsWith("--") || trimmed.isEmpty()) continue;
                sqlBuffer.append(line).append("\n");
                if (trimmed.endsWith(";")) {
                    String statement = sqlBuffer.toString().trim();
                    if (!statement.isEmpty()) {
                        try {
                            stmt.execute(statement);
                        } catch (SQLException e) {
                            LOGGER.log(Level.FINE, "SQL omitido: {0}", e.getMessage());
                        }
                    }
                    sqlBuffer.setLength(0);
                }
            }
            LOGGER.info("Base de datos lista");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "No se pudo inicializar la base de datos: " + e.getMessage(), e);
        }
    }

    public Connection getActiveConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
