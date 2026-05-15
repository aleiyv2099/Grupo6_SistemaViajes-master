/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ClienteDTO;
import ConexionDb.DatabaseConnectionManager;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Mini Wernaso
 */
public class ClienteDAO {

    DatabaseConnectionManager dbManager = new DatabaseConnectionManager();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean registerClient(ClienteDTO cl) {
        String sql = "INSERT INTO clientes (dni,nombre,telefono,direccion) VALUES (?,?,?,?)";

        try {
            con = dbManager.getActiveConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cl.getIdentificationNumber());
            ps.setString(2, cl.getFullName());
            ps.setString(3, cl.getPhoneNumber());
            ps.setString(4, cl.getAddress());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    public List listActiveClients() {
        List<ClienteDTO> ListaCl = new ArrayList();
        String sql = "SELECT * FROM clientes WHERE Estado = 1";
        try {
            con = dbManager.getActiveConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ClienteDTO cl = new ClienteDTO();
                cl.setClientCode(rs.getInt("codigoCliente"));
                cl.setIdentificationNumber(rs.getString("DNI"));
                cl.setFullName(rs.getString("Nombre"));
                cl.setPhoneNumber(rs.getString("Telefono"));
                cl.setAddress(rs.getString("Direccion"));
                ListaCl.add(cl);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return ListaCl;
    }

    public boolean softDeleteClient(int idCliente) {
        String sqlCliente = "UPDATE clientes SET Estado = 0 WHERE CodigoCliente = ?";
        String sqlReservas = "UPDATE reservas SET Estado = 0 WHERE CodigoCliente = ?";

        try {
            con = dbManager.getActiveConnection();
            ps = con.prepareStatement(sqlCliente);
            ps.setInt(1, idCliente);
            ps.executeUpdate();

            ps = con.prepareStatement(sqlReservas);
            ps.setInt(1, idCliente);
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

   public boolean updateClient(ClienteDTO cl) {
    String sql = "UPDATE clientes SET dni=?, nombre=?, telefono=?, direccion=? WHERE CodigoCliente=?";
    try {
        con = dbManager.getActiveConnection();   // abrir conexión aquí
        ps = con.prepareStatement(sql);
        ps.setString(1, cl.getIdentificationNumber());
        ps.setString(2, cl.getFullName());
        ps.setString(3, cl.getPhoneNumber());
        ps.setString(4, cl.getAddress());
        ps.setInt(5, cl.getClientCode());
        ps.executeUpdate();
        return true;
    } catch (SQLException e) {
        System.out.println(e.toString());
        return false;
    } finally {
        try {
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}


    public List<ClienteDTO> searchClientsByCodeIdName(String filtro) {
        List<ClienteDTO> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE Estado = 1 AND ("
        + "CAST(CodigoCliente AS CHAR) LIKE ? OR DNI LIKE ? OR Nombre LIKE ?)";

        try {
            con = dbManager.getActiveConnection();
            ps = con.prepareStatement(sql);
            String likeFiltro = "%" + filtro + "%";
            ps.setString(1, likeFiltro);
            ps.setString(2, likeFiltro);
            ps.setString(3, likeFiltro);
            rs = ps.executeQuery();

            while (rs.next()) {
                ClienteDTO c = new ClienteDTO();
                c.setClientCode(rs.getInt("CodigoCliente"));
                c.setIdentificationNumber(rs.getString("DNI"));
                c.setFullName(rs.getString("Nombre"));
                c.setPhoneNumber(rs.getString("Telefono"));
                c.setAddress(rs.getString("Direccion"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }

    
    public ClienteDTO getClientByCode(int codigoCliente) {
    ClienteDTO cliente = null;
    String sql = "SELECT * FROM clientes WHERE CodigoCliente = ?";
    try (Connection con = dbManager.getActiveConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, codigoCliente);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            cliente = new ClienteDTO();
            cliente.setClientCode(rs.getInt("CodigoCliente"));
            cliente.setIdentificationNumber(rs.getString("Dni"));
            cliente.setFullName(rs.getString("Nombre"));
            cliente.setPhoneNumber(rs.getString("Telefono"));
            cliente.setAddress(rs.getString("Direccion"));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return cliente;
}
}
