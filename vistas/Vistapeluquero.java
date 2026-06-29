/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Viewpeluquero;

import javax.swing.ImageIcon;
import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import Viewpeluquero.Vistapeluquero;
import Viewpeluquero.Vistamodificarcitas;

/**
 *
 * @author HOME
 */
public class Vistapeluquero extends JFrame {

    
    private Container contenedor;
    
    private JLabel ltitulo, lcliente, lfecha, lhora;
    
    public JTextField txtCliente;
    public JTextField txtFecha;
    public JTextField txtHora;
    
    public JComboBox<String> listaCorte;
    
    public JButton asignar;
    public JButton modificar;

   
    public Vistapeluquero() {
        
        imagenesnexusgo.JPanelimage fondo = new imagenesnexusgo.JPanelimage("/imagenesnexusgo/WhatsApp Image 2026-06-15 at 5.41.42 PM.jpeg");
        
       
        super("PANEL PELUQUERO");

        
        contenedor = getContentPane();
        contenedor.setLayout(null); 
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ltitulo = new JLabel("Bienvenido Peluquero a NEXUS GO");
        ltitulo.setBounds(350, 30, 250, 30);
        contenedor.add(ltitulo);

        lcliente = new JLabel("Cliente");
        lcliente.setBounds(50, 100, 100, 20);
        contenedor.add(lcliente);

        lfecha = new JLabel("Fecha");
        lfecha.setBounds(50, 160, 100, 20);
        contenedor.add(lfecha);

        lhora = new JLabel("Hora");
        lhora.setBounds(50, 220, 100, 20);
        contenedor.add(lhora);

       
        txtCliente = new JTextField();
        txtCliente.setBounds(150, 100, 180, 25);
        contenedor.add(txtCliente);

        txtFecha = new JTextField();
        txtFecha.setBounds(150, 160, 180, 25);
        contenedor.add(txtFecha);

        txtHora = new JTextField();
        txtHora.setBounds(150, 220, 180, 25);
        contenedor.add(txtHora);

       
        listaCorte = new JComboBox<>();
        listaCorte.addItem("Corte");
        listaCorte.addItem("Peinado");
        listaCorte.addItem("Tinte");
        listaCorte.addItem("Manicure");
        listaCorte.setBounds(150, 270, 180, 25);
        contenedor.add(listaCorte);

       
        asignar = new JButton("Asignar");
        asignar.setBounds(50, 350, 130, 35);
        contenedor.add(asignar);

        modificar = new JButton("Modificar");
        modificar.setBounds(200, 350, 130, 35);
        contenedor.add(modificar);
    }
}