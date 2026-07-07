package Controlador;

import ConexionDb.DatabaseConnectionManager;
import Modelo.SoporteDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class SoporteDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    DatabaseConnectionManager cn = new DatabaseConnectionManager();

    public boolean registrarTicket(SoporteDTO soporte) {

        String sql = "INSERT INTO soporte(nombreCliente, correo, telefono, tipoProblema, descripcion, prioridad, estado, responsable) VALUES(?,?,?,?,?,?,?,?)";

        try {

            con = cn.getActiveConnection();

            ps = con.prepareStatement(sql);

            ps.setString(1, soporte.getNombreCliente());
            ps.setString(2, soporte.getCorreo());
            ps.setString(3, soporte.getTelefono());
            ps.setString(4, soporte.getTipoProblema());
            ps.setString(5, soporte.getDescripcion());
            ps.setString(6, soporte.getPrioridad());
            ps.setString(7, soporte.getEstado());
            ps.setString(8, soporte.getResponsable());

            ps.execute();

            return true;

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, e.toString());

            return false;
        }
    }
    
    public boolean updateSupportTicket(SoporteDTO soporte) {

    String sql = "UPDATE soporte SET "
        + "nombreCliente=?, "
        + "correo=?, "
        + "telefono=?, "
        + "tipoProblema=?, "
        + "descripcion=?, "
        + "prioridad=?, "
        + "estado=?, "
        + "responsable=? "
        + "WHERE codigoTicket=?";
    try {

        con = cn.getActiveConnection();

        ps = con.prepareStatement(sql);

        ps.setString(1, soporte.getNombreCliente());
        ps.setString(2, soporte.getCorreo());
        ps.setString(3, soporte.getTelefono());
        ps.setString(4, soporte.getTipoProblema());
        ps.setString(5, soporte.getDescripcion());
        ps.setString(6, soporte.getPrioridad());
        ps.setString(7, soporte.getEstado());
        ps.setString(8, soporte.getResponsable());
        ps.setInt(9, soporte.getCodigoTicket());

        ps.executeUpdate();

        return true;

    } catch (SQLException e) {

        System.out.println(e.toString());

        return false;
    }
}
    
    public List<SoporteDTO> listSupportTickets() {

    List<SoporteDTO> lista = new ArrayList<>();

    String sql = "SELECT * FROM soporte";

    try {

        con = cn.getActiveConnection();

        ps = con.prepareStatement(sql);

        rs = ps.executeQuery();

        while (rs.next()) {

            SoporteDTO soporte = new SoporteDTO();

            soporte.setCodigoTicket(
                    rs.getInt("codigoTicket")
            );

            soporte.setNombreCliente(
                    rs.getString("nombreCliente")
            );

            soporte.setCorreo(
                    rs.getString("correo")
            );

            soporte.setTelefono(
                    rs.getString("telefono")
            );

            soporte.setTipoProblema(
                    rs.getString("tipoProblema")
            );

            soporte.setDescripcion(
                    rs.getString("descripcion")
            );

            soporte.setEstado(
                    rs.getString("estado")
            );
            
            soporte.setPrioridad(
                    rs.getString("prioridad")
            );

            soporte.setResponsable(
                    rs.getString("responsable")
            );

            lista.add(soporte);
        }

    } catch (SQLException e) {

        System.out.println(e.toString());
    }

    return lista;
}
    
    public boolean deleteSupportTicket(int codigoTicket) {

    String sql = "DELETE FROM soporte WHERE codigoTicket=?";

    try {

        con = cn.getActiveConnection();

        ps = con.prepareStatement(sql);

        ps.setInt(1, codigoTicket);

        ps.executeUpdate();

        return true;

    } catch (SQLException e) {

        JOptionPane.showMessageDialog(null, e.toString());

        return false;
    }
}
    public List<SoporteDTO> searchSupportTickets(String filtro) {

    List<SoporteDTO> lista = new ArrayList<>();

    String sql = "SELECT * FROM soporte "
            + "WHERE codigoTicket LIKE ? "
            + "OR nombreCliente LIKE ? "
            + "OR tipoProblema LIKE ? "
            + "OR estado LIKE ?";

    try {

        con = cn.getActiveConnection();

        ps = con.prepareStatement(sql);

        ps.setString(1, "%" + filtro + "%");
        ps.setString(2, "%" + filtro + "%");
        ps.setString(3, "%" + filtro + "%");
        ps.setString(4, "%" + filtro + "%");

        rs = ps.executeQuery();

        while (rs.next()) {

            SoporteDTO soporte = new SoporteDTO();

            soporte.setCodigoTicket(rs.getInt("codigoTicket"));
            soporte.setNombreCliente(rs.getString("nombreCliente"));
            soporte.setCorreo(rs.getString("correo"));
            soporte.setTelefono(rs.getString("telefono"));
            soporte.setTipoProblema(rs.getString("tipoProblema"));
            soporte.setDescripcion(rs.getString("descripcion"));
            soporte.setPrioridad(rs.getString("prioridad"));
            soporte.setEstado(rs.getString("estado"));
            soporte.setResponsable(rs.getString("responsable"));

            lista.add(soporte);
        }

    } catch (SQLException e) {

        System.out.println(e.toString());
    }

    return lista;
}
    
}
