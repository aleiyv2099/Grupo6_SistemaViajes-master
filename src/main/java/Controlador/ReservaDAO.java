package Controlador;

import ConexionDb.DatabaseConnectionManager;
import Modelo.ReservaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ReservaDAO {

    private static final Logger LOGGER = Logger.getLogger(ReservaDAO.class.getName());
    private final DatabaseConnectionManager dbManager = new DatabaseConnectionManager();

    public boolean registerReservation(ReservaDTO re) {
        String sql = "INSERT INTO reservas (codigocliente,origen,destino,fechaviaje,horasalida,asientoasignado,preciopasaje) VALUES (?,?,?,?,?,?,?)";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, re.getClientCode());
            ps.setString(2, re.getOriginCity());
            ps.setString(3, re.getDestinationCity());
            ps.setString(4, re.getTravelDate());
            ps.setString(5, re.getDepartureTime());
            ps.setString(6, re.getAssignedSeat());
            ps.setDouble(7, re.getTicketPrice());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }

    public boolean isSeatAlreadyBooked(String origen, String destino, String fecha, String hora, String asiento) {
        String sql = "SELECT COUNT(*) FROM reservas WHERE Origen = ? AND Destino = ? AND FechaViaje = ? AND HoraSalida = ? AND AsientoAsignado = ? AND Estado = 1";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, origen);
            ps.setString(2, destino);
            ps.setString(3, fecha);
            ps.setString(4, hora);
            ps.setString(5, asiento);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al verificar asiento", ex);
        }
        return false;
    }

    public boolean isSeatAlreadyBooked(String origen, String destino, String fecha, String hora, String asiento, int idReservaActual) {
        String sql = "SELECT COUNT(*) FROM reservas WHERE Origen = ? AND Destino = ? AND FechaViaje = ? AND HoraSalida = ? AND AsientoAsignado = ? AND Estado = 1 AND CodigoReserva <> ?";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, origen);
            ps.setString(2, destino);
            ps.setString(3, fecha);
            ps.setString(4, hora);
            ps.setString(5, asiento);
            ps.setInt(6, idReservaActual);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error al verificar asiento (edición)", ex);
        }
        return false;
    }

    public List<ReservaDTO> listActiveReservations() {
        List<ReservaDTO> lista = new ArrayList<>();
        String sql = "SELECT r.* FROM reservas r "
                + "JOIN clientes c ON r.CodigoCliente = c.CodigoCliente "
                + "WHERE r.Estado = 1 AND c.Estado = 1";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ReservaDTO r = new ReservaDTO();
                r.setReservationCode(rs.getInt("CodigoReserva"));
                r.setClientCode(rs.getInt("CodigoCliente"));
                r.setOriginCity(rs.getString("Origen"));
                r.setDestinationCity(rs.getString("Destino"));
                r.setTravelDate(rs.getString("FechaViaje"));
                r.setDepartureTime(rs.getString("HoraSalida"));
                r.setAssignedSeat(rs.getString("AsientoAsignado"));
                r.setTicketPrice(rs.getDouble("PrecioPasaje"));
                lista.add(r);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar reservas", e);
        }
        return lista;
    }

    public boolean softDeleteReservation(int codigoReserva) {
        String sql = "UPDATE reservas SET Estado = 0 WHERE CodigoReserva = ?";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codigoReserva);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }

    public boolean updateReservation(ReservaDTO re) {
        String sql = "UPDATE reservas SET CodigoCliente = ?, Origen = ?, Destino = ?, FechaViaje = ?, HoraSalida = ?, AsientoAsignado = ?, PrecioPasaje = ? WHERE CodigoReserva = ?";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, re.getClientCode());
            ps.setString(2, re.getOriginCity());
            ps.setString(3, re.getDestinationCity());
            ps.setString(4, re.getTravelDate());
            ps.setString(5, re.getDepartureTime());
            ps.setString(6, re.getAssignedSeat());
            ps.setDouble(7, re.getTicketPrice());
            ps.setInt(8, re.getReservationCode());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }

    public List<ReservaDTO> searchReservationsByClientOrCode(String filtro) {
        List<ReservaDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM reservas WHERE Estado = 1 AND ("
                + "CAST(CodigoReserva AS CHAR) LIKE ? OR CAST(CodigoCliente AS CHAR) LIKE ?)";
        String like = "%" + filtro + "%";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, like);
            ps.setString(2, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ReservaDTO r = new ReservaDTO();
                    r.setReservationCode(rs.getInt("CodigoReserva"));
                    r.setClientCode(rs.getInt("CodigoCliente"));
                    r.setOriginCity(rs.getString("Origen"));
                    r.setDestinationCity(rs.getString("Destino"));
                    r.setTravelDate(rs.getString("FechaViaje"));
                    r.setDepartureTime(rs.getString("HoraSalida"));
                    r.setAssignedSeat(rs.getString("AsientoAsignado"));
                    r.setTicketPrice(rs.getDouble("PrecioPasaje"));
                    lista.add(r);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar reservas", e);
        }
        return lista;
    }

    public List<ReservaDTO> getReservationsByCodes(List<Integer> codigosReservas) {
        List<ReservaDTO> lista = new ArrayList<>();
        if (codigosReservas == null || codigosReservas.isEmpty()) return lista;

        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < codigosReservas.size(); i++) {
            if (i > 0) placeholders.append(",");
            placeholders.append("?");
        }

        String sql = "SELECT * FROM reservas WHERE CodigoReserva IN (" + placeholders + ")";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (int i = 0; i < codigosReservas.size(); i++) {
                ps.setInt(i + 1, codigosReservas.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ReservaDTO r = new ReservaDTO();
                    r.setReservationCode(rs.getInt("CodigoReserva"));
                    r.setOriginCity(rs.getString("Origen"));
                    r.setDestinationCity(rs.getString("Destino"));
                    r.setTravelDate(rs.getString("FechaViaje"));
                    r.setDepartureTime(rs.getString("HoraSalida"));
                    r.setAssignedSeat(rs.getString("AsientoAsignado"));
                    r.setTicketPrice(rs.getDouble("PrecioPasaje"));
                    lista.add(r);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener reservas por códigos", e);
        }
        return lista;
    }
}
