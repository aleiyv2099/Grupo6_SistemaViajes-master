package Controlador;

import Modelo.SoporteDTO;
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

/**
 * DAO del módulo Soporte al Cliente.
 * Implementa RF-1 (registro), RF-2 (consulta/búsqueda), RF-3 (actualización) y RF-4 (eliminación).
 */
public class SoporteDAO {

    private static final Logger LOGGER = Logger.getLogger(SoporteDAO.class.getName());
    private final DatabaseConnectionManager dbManager = new DatabaseConnectionManager();

    // RF-1: Registro de tickets de soporte
    public boolean registerTicket(SoporteDTO t) {
        String sql = "INSERT INTO tickets "
                + "(NombreCliente, Correo, NumeroContacto, TipoProblema, Descripcion, FechaCreacion, EstadoTicket, Observaciones, Responsable, Estado) "
                + "VALUES (?,?,?,?,?,CURDATE(),?,?,?,1)";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getNombreCliente());
            ps.setString(2, t.getCorreo());
            ps.setString(3, t.getNumeroContacto());
            ps.setString(4, t.getTipoProblema());
            ps.setString(5, t.getDescripcion());
            ps.setString(6, t.getEstadoTicket());   // Estado inicial: Pendiente
            ps.setString(7, t.getObservaciones());
            ps.setString(8, t.getResponsable());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }

    // RF-2: Consulta de tickets registrados
    public List<SoporteDTO> listActiveTickets() {
        List<SoporteDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE Estado = 1 ORDER BY CodigoTicket DESC";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar tickets", e);
        }
        return lista;
    }

    // RF-2: Búsquedas por Código de ticket, Nombre del cliente o Estado
    public List<SoporteDTO> searchTickets(String filtro) {
        List<SoporteDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE Estado=1 AND "
                + "(CAST(CodigoTicket AS CHAR) LIKE ? OR NombreCliente LIKE ? OR EstadoTicket LIKE ?) "
                + "ORDER BY CodigoTicket DESC";
        String like = "%" + filtro + "%";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar tickets", e);
        }
        return lista;
    }

    // RF-3: Actualización de estado, observaciones y responsable
    public boolean updateTicket(SoporteDTO t) {
        String sql = "UPDATE tickets SET EstadoTicket=?, Observaciones=?, Responsable=? WHERE CodigoTicket=?";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, t.getEstadoTicket());
            ps.setString(2, t.getObservaciones());
            ps.setString(3, t.getResponsable());
            ps.setInt(4, t.getCodigoTicket());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar ticket", e);
            return false;
        }
    }

    // RF-4: Eliminación (lógica) de tickets
    public boolean softDeleteTicket(int codigoTicket) {
        String sql = "UPDATE tickets SET Estado = 0 WHERE CodigoTicket = ?";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codigoTicket);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar ticket", e);
            return false;
        }
    }

    private SoporteDTO mapRow(ResultSet rs) throws SQLException {
        SoporteDTO t = new SoporteDTO();
        t.setCodigoTicket(rs.getInt("CodigoTicket"));
        t.setNombreCliente(rs.getString("NombreCliente"));
        t.setCorreo(rs.getString("Correo"));
        t.setNumeroContacto(rs.getString("NumeroContacto"));
        t.setTipoProblema(rs.getString("TipoProblema"));
        t.setDescripcion(rs.getString("Descripcion"));
        t.setFechaCreacion(rs.getString("FechaCreacion"));
        t.setEstadoTicket(rs.getString("EstadoTicket"));
        t.setObservaciones(rs.getString("Observaciones"));
        t.setResponsable(rs.getString("Responsable"));
        return t;
    }
}
