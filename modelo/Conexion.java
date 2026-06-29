/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelcliente;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author HOME
 */

    public class Conexion {
    Connection con;
    String url = "jdbc:mysql://localhost:3306/nexus_go_db";
    String user = "root";
    String pass="";
    
    public Connection getConnection(){
        try{
               Class.forName("com.mysql.cj.jdbc.Driver");
               con=DriverManager.getConnection(url,user,pass);
               
               JOptionPane.showMessageDialog(null, "conexion exitosa");
    }catch (Exception e){
        JOptionPane.showMessageDialog(null, e.toString(),"base de datos apagada" + e.getMessage (),JOptionPane.ERROR_MESSAGE);
               
                
    }
    return con;

    }
    
}

