package Controlador;

import Modelo.MascotaDTO;
import ConexionDb.DatabaseConnectionManager;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class MascotaDAO {

    private static final Logger LOGGER = Logger.getLogger(MascotaDAO.class.getName());
    private final DatabaseConnectionManager dbManager = new DatabaseConnectionManager();

    public boolean registerMascota(MascotaDTO m) {
        String sql = "INSERT INTO mascotas (CodigoCliente, nombre, raza, peso, observaciones, Estado) VALUES (?,?,?,?,?,1)";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, m.getClientCode());
            ps.setString(2, m.getNombre());
            ps.setString(3, m.getRaza());
            ps.setDouble(4, m.getPeso());
            ps.setString(5, m.getObservaciones());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }

    public List<MascotaDTO> listActiveMascotas() {
        List<MascotaDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM mascotas WHERE Estado = 1";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                MascotaDTO m = new MascotaDTO();
                m.setIdMascota(rs.getInt("id_mascota"));
                m.setClientCode(rs.getInt("CodigoCliente"));
                m.setNombre(rs.getString("nombre"));
                m.setRaza(rs.getString("raza"));
                m.setPeso(rs.getDouble("peso"));
                m.setObservaciones(rs.getString("observaciones"));
                lista.add(m);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar mascotas", e);
        }
        return lista;
    }

    public boolean updateMascota(MascotaDTO m) {
        String sql = "UPDATE mascotas SET nombre=?, raza=?, peso=?, observaciones=? WHERE id_mascota=?";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, m.getNombre());
            ps.setString(2, m.getRaza());
            ps.setDouble(3, m.getPeso());
            ps.setString(4, m.getObservaciones());
            ps.setInt(5, m.getIdMascota());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar mascota", e);
            return false;
        }
    }

    public boolean softDeleteMascota(int idMascota) {
        String sql = "UPDATE mascotas SET Estado = 0 WHERE id_mascota = ?";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idMascota);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar mascota", e);
            return false;
        }
    }

    public List<MascotaDTO> searchMascotasByClientOrName(String filtro) {
        List<MascotaDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM mascotas WHERE Estado=1 AND (CodigoCliente LIKE ? OR nombre LIKE ?)";
        String like = "%" + filtro + "%";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, like);
            ps.setString(2, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MascotaDTO m = new MascotaDTO();
                    m.setIdMascota(rs.getInt("id_mascota"));
                    m.setClientCode(rs.getInt("CodigoCliente"));
                    m.setNombre(rs.getString("nombre"));
                    m.setRaza(rs.getString("raza"));
                    m.setPeso(rs.getDouble("peso"));
                    m.setObservaciones(rs.getString("observaciones"));
                    lista.add(m);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar mascotas", e);
        }
        return lista;
    }

    public boolean existsMascota(String nombre, int clientCode) {
        String sql = "SELECT COUNT(*) FROM mascotas WHERE nombre = ? AND CodigoCliente = ? AND Estado = 1";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setInt(2, clientCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al verificar existencia de mascota", e);
        }
        return false;
    }
}
