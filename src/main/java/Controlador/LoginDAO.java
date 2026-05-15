
package Controlador;

import ConexionDb.DatabaseConnectionManager;
import Modelo.LoginDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {
    
    DatabaseConnectionManager dbManager = new DatabaseConnectionManager();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public LoginDTO log(String correo, String pass){
        LoginDTO l = new LoginDTO();
        String sql = "SELECT * FROM usuario WHERE correo = ? AND pass = ?";
    try{
       con= dbManager.getActiveConnection();
       ps = con.prepareStatement(sql);
       ps.setString(1, correo);
       ps.setString(2, pass);
       rs= ps.executeQuery();
       if(rs.next()){
           l.setUserId(rs.getInt("id"));
           l.setFullName(rs.getString("nombre"));
           l.setEmail(rs.getString("correo"));
           l.setPassword(rs.getString("pass"));
       }
    }catch(SQLException e){
        System.out.println(e.toString());
    }
    return l;
    }
    
}
