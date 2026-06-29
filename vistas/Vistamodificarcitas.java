/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Viewpeluquero;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HOME
 */
public class Vistamodificarcitas extends JFrame {

    // El modelo de datos público para que lo use el controlador
    public DefaultTableModel modeloAgenda = new DefaultTableModel();

    public JComboBox<String> listaServicios;
    public JTextField txtFecha;
    public JTextField txtHora;
    public JTextField txtPrecio;
    public JButton guardar;
    public JButton imagen;
    public JTable tablaAgenda;

    public Vistamodificarcitas() {
        
        imagenesnexusgo.JPanelimage fondo = new imagenesnexusgo.JPanelimage("/imagenesnexusgo/WhatsApp Image 2026-06-15 at 5.41.42 PM.jpeg");
        fondo.setSize(this.getSize());
        this.setContentPane(fondo);
        this.revalidate();
        this.repaint();
        
        setTitle("Modificar Citas - Historial");
        setSize(900, 600);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel titulo = new JLabel("MODIFICAR CITAS REGISTRADAS");
        titulo.setBounds(350, 20, 250, 30);
        add(titulo);

        JLabel servicio = new JLabel("Servicio");
        servicio.setBounds(50, 80, 100, 20);
        add(servicio);

        listaServicios = new JComboBox<>();
        listaServicios.addItem("Corte");
        listaServicios.addItem("Peinado");
        listaServicios.addItem("Tinte");
        listaServicios.addItem("Manicure");
        listaServicios.setBounds(150, 80, 180, 25);
        add(listaServicios);

        JLabel fecha = new JLabel("Fecha");
        fecha.setBounds(50, 130, 100, 20);
        add(fecha);

        txtFecha = new JTextField();
        txtFecha.setBounds(150, 130, 180, 25);
        add(txtFecha);

        JLabel hora = new JLabel("Hora");
        hora.setBounds(50, 180, 100, 20);
        add(hora);

        txtHora = new JTextField();
        txtHora.setBounds(150, 180, 180, 25);
        add(txtHora);

        guardar = new JButton("Guardar Cambios");
        guardar.setBounds(150, 250, 180, 35);
        add(guardar);

       
        JScrollPane panelAgenda = new JScrollPane();
        
        
        tablaAgenda = new JTable(modeloAgenda); 

        panelAgenda.setViewportView(tablaAgenda);
        panelAgenda.setBounds(400, 80, 430, 400);
        add(panelAgenda);
    }
}