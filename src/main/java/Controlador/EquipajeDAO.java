package Controlador;

import Modelo.EquipajeDTO;
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

public class EquipajeDAO {

    private static final Logger LOGGER = Logger.getLogger(EquipajeDAO.class.getName());
    private final DatabaseConnectionManager dbManager = new DatabaseConnectionManager();

    public boolean registerEquipaje(EquipajeDTO e) {
        String sql = "INSERT INTO equipaje (CodigoCliente, tipo_equipaje, peso, cantidad) VALUES (?,?,?,?)";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, e.getCodigoCliente());
            ps.setString(2, e.getTipoEquipaje());
            ps.setDouble(3, e.getPeso());
            ps.setInt(4, e.getCantidad());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
            return false;
        }
    }

    public List<EquipajeDTO> listEquipaje() {
        List<EquipajeDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipaje";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                EquipajeDTO e = new EquipajeDTO();
                e.setIdEquipaje(rs.getInt("id_equipaje"));
                e.setCodigoCliente(rs.getInt("CodigoCliente"));
                e.setTipoEquipaje(rs.getString("tipo_equipaje"));
                e.setPeso(rs.getDouble("peso"));
                e.setCantidad(rs.getInt("cantidad"));
                lista.add(e);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al listar equipajes", ex);
        }
        return lista;
    }

    public boolean updateEquipaje(EquipajeDTO e) {
        String sql = "UPDATE equipaje SET tipo_equipaje=?, peso=?, cantidad=? WHERE id_equipaje=?";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getTipoEquipaje());
            ps.setDouble(2, e.getPeso());
            ps.setInt(3, e.getCantidad());
            ps.setInt(4, e.getIdEquipaje());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al actualizar equipaje", ex);
            return false;
        }
    }

    public boolean deleteEquipaje(int idEquipaje) {
        // Borrado físico directo, ya que la tabla no tiene columna 'Estado'
        String sql = "DELETE FROM equipaje WHERE id_equipaje = ?";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEquipaje);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al eliminar equipaje", ex);
            return false;
        }
    }

    public List<EquipajeDTO> searchEquipajeByClientOrType(String filtro) {
        List<EquipajeDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipaje WHERE CodigoCliente LIKE ? OR tipo_equipaje LIKE ?";
        String like = "%" + filtro + "%";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, like);
            ps.setString(2, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EquipajeDTO e = new EquipajeDTO();
                    e.setIdEquipaje(rs.getInt("id_equipaje"));
                    e.setCodigoCliente(rs.getInt("CodigoCliente"));
                    e.setTipoEquipaje(rs.getString("tipo_equipaje"));
                    e.setPeso(rs.getDouble("peso"));
                    e.setCantidad(rs.getInt("cantidad"));
                    lista.add(e);
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al buscar equipaje", ex);
        }
        return lista;
    }

    public boolean existsEquipaje(String tipoEquipaje, int clientCode) {
        String sql = "SELECT COUNT(*) FROM equipaje WHERE tipo_equipaje = ? AND CodigoCliente = ?";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tipoEquipaje);
            ps.setInt(2, clientCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al verificar existencia de equipaje", ex);
        }
        return false;
    }
}