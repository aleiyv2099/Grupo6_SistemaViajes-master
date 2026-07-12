package Controlador;

import ConexionDb.DatabaseConnectionManager;
import Modelo.FacturaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class FacturaDAO {

    private static final Logger LOGGER = Logger.getLogger(FacturaDAO.class.getName());
    private final DatabaseConnectionManager dbManager = new DatabaseConnectionManager();

    public boolean registerInvoiceWithTransaction(FacturaDTO factura) {
        String sqlFactura  = "INSERT INTO facturas (FechaEmision, MontoTotal, MetodoPago, EstadoFactura, CodigoCliente) VALUES (?, ?, ?, ?, ?)";
        String sqlReservas = "UPDATE reservas SET Estado = 0 WHERE CodigoCliente = ?";
        String sqlDetalle  = "INSERT INTO detalle_factura (CodigoFactura, CodigoReserva) VALUES (?, ?)";

        try (Connection con = dbManager.getActiveConnection()) {
            con.setAutoCommit(false);
            try {
                int codigoFactura;
                try (PreparedStatement ps = con.prepareStatement(sqlFactura, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setDate(1, new java.sql.Date(factura.getIssueDate().getTime()));
                    ps.setDouble(2, factura.getTotalAmount());
                    ps.setString(3, factura.getPaymentMethod());
                    ps.setString(4, factura.getInvoiceStatus());
                    ps.setInt(5, factura.getClientCode());
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (!keys.next()) throw new SQLException("No se generó CodigoFactura");
                        codigoFactura = keys.getInt(1);
                    }
                }

                try (PreparedStatement ps = con.prepareStatement(sqlReservas)) {
                    ps.setInt(1, factura.getClientCode());
                    ps.executeUpdate();
                }

                List<Integer> reservas = factura.getReservationList();
                if (reservas == null) reservas = new ArrayList<>();
                try (PreparedStatement ps = con.prepareStatement(sqlDetalle)) {
                    for (Integer codigoReserva : reservas) {
                        ps.setInt(1, codigoFactura);
                        ps.setInt(2, codigoReserva);
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }

                con.commit();
                return true;

            } catch (SQLException e) {
                con.rollback();
                LOGGER.log(Level.WARNING, "Factura revertida (rollback)", e);
                JOptionPane.showMessageDialog(null,
                        "No se pudo registrar la factura. Verifique que las reservas seleccionadas existan y pertenezcan al cliente.");
                return false;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error de conexión en registerInvoiceWithTransaction", e);
            return false;
        }
    }

    public boolean updateInvoice(FacturaDTO factura) {
        String sql = "UPDATE facturas SET MetodoPago = ?, EstadoFactura = ? WHERE CodigoFactura = ? AND Estado = 1";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, factura.getPaymentMethod());
            ps.setString(2, factura.getInvoiceStatus());
            ps.setInt(3, factura.getInvoiceCode());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar factura", e);
            return false;
        }
    }

    public boolean softDeleteInvoice(int codigoFactura) {
        String sql = "UPDATE facturas SET Estado = 0 WHERE CodigoFactura = ? AND Estado = 1";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codigoFactura);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar factura", e);
            return false;
        }
    }

    public List<FacturaDTO> buscarFacturas(String filtro) {
        List<FacturaDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM facturas WHERE Estado = 1 AND ("
                + "CAST(CodigoFactura AS CHAR) LIKE ? OR MetodoPago LIKE ? OR EstadoFactura LIKE ?)";
        String like = "%" + filtro + "%";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FacturaDTO factura = new FacturaDTO();
                    factura.setInvoiceCode(rs.getInt("CodigoFactura"));
                    factura.setIssueDate(rs.getDate("FechaEmision"));
                    factura.setTotalAmount(rs.getDouble("MontoTotal"));
                    factura.setPaymentMethod(rs.getString("MetodoPago"));
                    factura.setInvoiceStatus(rs.getString("EstadoFactura"));
                    factura.setClientCode(rs.getInt("CodigoCliente"));
                    factura.setReservationList(obtenerReservasPorFactura(factura.getInvoiceCode()));
                    lista.add(factura);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar facturas", e);
        }
        return lista;
    }

    private List<Integer> obtenerReservasPorFactura(int codigoFactura) {
        List<Integer> reservas = new ArrayList<>();
        String sql = "SELECT CodigoReserva FROM detalle_factura WHERE CodigoFactura = ?";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codigoFactura);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) reservas.add(rs.getInt("CodigoReserva"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener reservas de factura", e);
        }
        return reservas;
    }

    public List<FacturaDTO> listActiveInvoices() {
        List<FacturaDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM facturas WHERE Estado = 1";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                FacturaDTO factura = new FacturaDTO();
                int codigo = rs.getInt("CodigoFactura");
                factura.setInvoiceCode(codigo);
                factura.setIssueDate(rs.getDate("FechaEmision"));
                factura.setTotalAmount(rs.getDouble("MontoTotal"));
                factura.setPaymentMethod(rs.getString("MetodoPago"));
                factura.setInvoiceStatus(rs.getString("EstadoFactura"));
                factura.setClientCode(rs.getInt("CodigoCliente"));
                factura.setReservationList(obtenerReservasPorFactura(codigo));
                lista.add(factura);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar facturas", e);
        }
        return lista;
    }

    public double calculateTotalAmount(List<Integer> codigosReserva) {
        if (codigosReserva == null || codigosReserva.isEmpty()) return 0.0;
        double total = 0.0;
        String sql = "SELECT PrecioPasaje FROM reservas WHERE CodigoReserva = ?";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (Integer codigo : codigosReserva) {
                ps.setInt(1, codigo);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) total += rs.getDouble("PrecioPasaje");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al calcular monto total", e);
        }
        return total;
    }

    public List<Integer> getUnbilledReservationsForClient(int codigoCliente) {
        List<Integer> lista = new ArrayList<>();
        String sql = "SELECT CodigoReserva FROM reservas WHERE CodigoCliente = ? AND Estado = 1";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codigoCliente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(rs.getInt("CodigoReserva"));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener reservas no facturadas", e);
        }
        return lista;
    }

    public String getReservationCodesForInvoice(int codigoFactura) {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT CodigoReserva FROM detalle_factura WHERE CodigoFactura = ?";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codigoFactura);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (sb.length() > 0) sb.append(", ");
                    sb.append(rs.getInt("CodigoReserva"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener códigos de reserva de factura", e);
        }
        return sb.toString();
    }
}
