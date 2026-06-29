/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelcliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CitaDao {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    
   
    public int guardar(Cita cita) {
        String sql = "INSERT INTO citas_prueba(nombre,telefono,servicio,fecha,hora) VALUES(?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cita.getNombre());
            ps.setString(2, cita.getTelefono());
            ps.setString(3, cita.getServicio());
            ps.setString(4, cita.getFecha());
            ps.setString(5, cita.getHora());
            ps.executeUpdate();
            return 1;
        } catch (Exception e) {
            System.out.println("Error en guardar: " + e);
            return 0;
        }
    } 

    
    public List<Cita> listar() {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT * FROM citas_prueba";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cita cita = new Cita();
                cita.setId(rs.getInt("id"));
                cita.setNombre(rs.getString("nombre"));
                cita.setTelefono(rs.getString("telefono"));
                cita.setServicio(rs.getString("servicio"));
                cita.setFecha(rs.getString("fecha"));
                cita.setHora(rs.getString("hora"));
                lista.add(cita);
            }
        } catch (Exception e) {
            System.out.println("Error en listar: " + e);
        }
        return lista;
    }
    
   
    public int modificar(Cita cita) {
        String sql = "UPDATE citas_prueba SET servicio=?, fecha=? WHERE hora=?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, cita.getServicio());
            ps.setString(2, cita.getFecha());
            ps.setString(3, cita.getHora()); // Usa la hora para buscar cuál fila cambiar
            
            ps.executeUpdate();
            return 1; 
        } catch (Exception e) {
            System.out.println("Error en modificar: " + e);
            return 0; 
        }
    }
}
    
