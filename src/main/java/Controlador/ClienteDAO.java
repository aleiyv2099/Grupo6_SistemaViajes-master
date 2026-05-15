package Controlador;

import Modelo.ClienteDTO;
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

public class ClienteDAO {

    private static final Logger LOGGER = Logger.getLogger(ClienteDAO.class.getName());
    private final DatabaseConnectionManager dbManager = new DatabaseConnectionManager();

    public boolean registerClient(ClienteDTO cl) {
        String sql = "INSERT INTO clientes (dni,nombre,telefono,direccion) VALUES (?,?,?,?)";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cl.getIdentificationNumber());
            ps.setString(2, cl.getFullName());
            ps.setString(3, cl.getPhoneNumber());
            ps.setString(4, cl.getAddress());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }

    public List<ClienteDTO> listActiveClients() {
        List<ClienteDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE Estado = 1";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ClienteDTO cl = new ClienteDTO();
                cl.setClientCode(rs.getInt("codigoCliente"));
                cl.setIdentificationNumber(rs.getString("DNI"));
                cl.setFullName(rs.getString("Nombre"));
                cl.setPhoneNumber(rs.getString("Telefono"));
                cl.setAddress(rs.getString("Direccion"));
                lista.add(cl);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar clientes", e);
        }
        return lista;
    }

    public boolean softDeleteClient(int idCliente) {
        String sqlCliente = "UPDATE clientes SET Estado = 0 WHERE CodigoCliente = ?";
        String sqlReservas = "UPDATE reservas SET Estado = 0 WHERE CodigoCliente = ?";
        try (Connection con = dbManager.getActiveConnection()) {
            try (PreparedStatement ps = con.prepareStatement(sqlCliente)) {
                ps.setInt(1, idCliente);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = con.prepareStatement(sqlReservas)) {
                ps.setInt(1, idCliente);
                ps.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar cliente", e);
            return false;
        }
    }

    public boolean updateClient(ClienteDTO cl) {
        String sql = "UPDATE clientes SET dni=?, nombre=?, telefono=?, direccion=? WHERE CodigoCliente=?";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cl.getIdentificationNumber());
            ps.setString(2, cl.getFullName());
            ps.setString(3, cl.getPhoneNumber());
            ps.setString(4, cl.getAddress());
            ps.setInt(5, cl.getClientCode());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar cliente", e);
            return false;
        }
    }

    public List<ClienteDTO> searchClientsByCodeIdName(String filtro) {
        List<ClienteDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE Estado = 1 AND ("
                + "CAST(CodigoCliente AS CHAR) LIKE ? OR DNI LIKE ? OR Nombre LIKE ?)";
        String like = "%" + filtro + "%";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ClienteDTO c = new ClienteDTO();
                    c.setClientCode(rs.getInt("CodigoCliente"));
                    c.setIdentificationNumber(rs.getString("DNI"));
                    c.setFullName(rs.getString("Nombre"));
                    c.setPhoneNumber(rs.getString("Telefono"));
                    c.setAddress(rs.getString("Direccion"));
                    lista.add(c);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar clientes", e);
        }
        return lista;
    }

    public ClienteDTO getClientByCode(int codigoCliente) {
        ClienteDTO cliente = null;
        String sql = "SELECT * FROM clientes WHERE CodigoCliente = ?";
        try (Connection con = dbManager.getActiveConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, codigoCliente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cliente = new ClienteDTO();
                    cliente.setClientCode(rs.getInt("CodigoCliente"));
                    cliente.setIdentificationNumber(rs.getString("Dni"));
                    cliente.setFullName(rs.getString("Nombre"));
                    cliente.setPhoneNumber(rs.getString("Telefono"));
                    cliente.setAddress(rs.getString("Direccion"));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al obtener cliente por código", e);
        }
        return cliente;
    }
}
