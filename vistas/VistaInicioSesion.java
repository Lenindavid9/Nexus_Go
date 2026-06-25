/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nexusgo.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 *
 * @author USUARIO
 */
public class VistaInicioSesion extends JFrame{
    
       private Container contenedor;
    private JLabel fondo;
    private JPanel inicio;
    private JLabel jNroIdentificacion, jContrasena;
    private JButton entrar;
    public JTextField tNroIdentidad, tContrasena;
    private TitledBorder titulo;
    private GridLayout migrid;
    private FlowLayout miflow;

    public VistaInicioSesion()  {

        super("Inicio de Sesión");
        contenedor = getContentPane();
        
        // Fondo con FlowLayout
        this.fondo = new JLabel(new ImageIcon ("C:\\Users\\USUARIO\\Documents\\NetBeansProjects\\nexusGo/fondito.jpg"));
        this.fondo.setOpaque(true);
        this.fondo.setLayout(new FlowLayout());
        this.setContentPane(fondo);
        //inicio.setLayout(new GridBagLayout());
       
        // Inicializamos el panel y le ponemos fondos blancos
        titulo = new TitledBorder("Inicio_de_sesión");
        inicio = new JPanel();
        //inicio.setPreferredSize(new Dimension(400,400));
        inicio.setBorder(titulo);
        migrid = new GridLayout(8, 4, 8, 12);
        inicio.setLayout(migrid);

        // Componentes del Panel
        jNroIdentificacion = new JLabel("Ingrese su número de identificación");
        tNroIdentidad = new JTextField();
        jContrasena = new JLabel("Ingrese su contraseña");
        tContrasena = new JTextField();
        
        //boton
        entrar = new JButton("Entrar");
        //colores del boton
        entrar.setForeground(Color.WHITE);
        entrar.setBackground(Color.decode("#EFB810"));

        //agregar al panel
        inicio.add(jNroIdentificacion);
        inicio.add(tNroIdentidad);
        inicio.add(jContrasena);
        inicio.add(tContrasena);
        inicio.add(new JLabel());
        inicio.add(entrar);

        fondo.add(inicio);
    }
    
    public JButton getBtnEntrar() {
        return entrar;
    }
    
    
    
}
