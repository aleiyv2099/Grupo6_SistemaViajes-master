package Controlador;

import ConexionDb.DatabaseConnectionManager;
import Modelo.LoginDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginDAO {

    private static final Logger LOGGER = Logger.getLogger(LoginDAO.class.getName());
    private final DatabaseConnectionManager dbManager = new DatabaseConnectionManager();

    public LoginDTO log(String correo, String pass) {
        LoginDTO l = new LoginDTO();
        String sql = "SELECT * FROM usuario WHERE correo = ? AND pass = ?";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            ps.setString(2, pass);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    l.setUserId(rs.getInt("id"));
                    l.setFullName(rs.getString("nombre"));
                    l.setEmail(rs.getString("correo"));
                    l.setPassword(rs.getString("pass"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al autenticar usuario", e);
        }
        return l;
    }
}
