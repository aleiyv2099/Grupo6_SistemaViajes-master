package Controlador;

import Modelo.MascotaDTO;
import ConexionDb.DatabaseConnectionManager;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class MascotaDAO {

    DatabaseConnectionManager dbManager = new DatabaseConnectionManager();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // =========================
    // REGISTRAR MASCOTA
    // =========================
    public boolean registerMascota(MascotaDTO m) {

        String sql = "INSERT INTO mascotas (CodigoCliente, nombre, raza, peso, observaciones, Estado) VALUES (?,?,?,?,?,1)";

        try {
            con = dbManager.getActiveConnection();
            ps = con.prepareStatement(sql);

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

        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    // =========================
    // LISTAR MASCOTAS ACTIVAS
    // =========================
    public List listActiveMascotas() {

    List<MascotaDTO> ListaMs = new ArrayList<>();

    String sql = "SELECT * FROM mascotas WHERE Estado = 1";

    try {

        con = dbManager.getActiveConnection();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {

            MascotaDTO msdto = new MascotaDTO();

            msdto.setIdMascota(rs.getInt("id_mascota"));
            msdto.setClientCode(rs.getInt("CodigoCliente"));
            msdto.setNombre(rs.getString("nombre"));
            msdto.setRaza(rs.getString("raza"));
            msdto.setPeso(rs.getDouble("peso"));
            msdto.setObservaciones(rs.getString("observaciones"));

            ListaMs.add(msdto);
        }

    } catch (SQLException e) {
        System.out.println(e.toString());
    }

    return ListaMs;
}

    // =========================
    // ACTUALIZAR MASCOTA
    // =========================
    public boolean updateMascota(MascotaDTO m) {

        String sql = "UPDATE mascotas SET nombre=?, raza=?, peso=?, observaciones=? WHERE id_mascota=?";

        try {
            con = dbManager.getActiveConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, m.getNombre());
            ps.setString(2, m.getRaza());
            ps.setDouble(3, m.getPeso());
            ps.setString(4, m.getObservaciones());
            ps.setInt(5, m.getIdMascota());

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

    // =========================
    // ELIMINACIÓN LÓGICA
    // =========================
    public boolean softDeleteMascota(int idMascota) {

        String sql = "UPDATE mascotas SET Estado = 0 WHERE id_mascota = ?";

        try {
            con = dbManager.getActiveConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, idMascota);
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

    // =========================
    // BUSCAR MASCOTAS
    // =========================
   public List<MascotaDTO> searchMascotasByClientOrName(String filtro) {

    List<MascotaDTO> lista = new ArrayList<>();

    String sql = "SELECT * FROM mascotas "
               + "WHERE Estado=1 AND "
               + "(CodigoCliente LIKE ? OR nombre LIKE ?)";

    try {

        con = dbManager.getActiveConnection();
        ps = con.prepareStatement(sql);

        ps.setString(1, "%" + filtro + "%");
        ps.setString(2, "%" + filtro + "%");

        rs = ps.executeQuery();

        while(rs.next()) {

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
        System.out.println(e.toString());
    }

    return lista;
}

    // =========================
    // VALIDAR DUPLICADO
    // =========================
    public boolean existsMascota(String nombre, int clientCode) {

        String sql = "SELECT COUNT(*) FROM mascotas WHERE nombre = ? AND CodigoCliente = ? AND Estado = 1";

        try {
            con = dbManager.getActiveConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, nombre);
            ps.setInt(2, clientCode);

            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}