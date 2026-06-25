/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nexusgo.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author USUARIO
 */
public class UsuarioDao {
    
   private final Conexion conexion = new Conexion();

    public Usuario autenticarUsuario(String identificacion, String contrasena) {
        // Traemos el nombre y el rol de la base de datos
        String sql = "SELECT nombre, rol FROM usuarios WHERE identificacion = ? AND contrasena = ?";
        
        try (Connection con = conexion.getConection(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, identificacion);
            ps.setString(2, contrasena);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario user = new Usuario();
                    user.setIdentificacion(identificacion);
                    user.setNombre(rs.getString("nombre"));
                    user.setRol(rs.getString("rol"));
                    return user; // Retorna el usuario con sus datos reales de la BD
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en autenticación: " + e.getMessage());
        }
        return null; // Si las credenciales no coinciden
    }
    
}
