/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Viewcliente;

import com.toedter.calendar.JDateChooser;
import javax.swing.ImageIcon;
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
public class Vistacliente extends JFrame {
    
    public DefaultTableModel modeloHistorial = new DefaultTableModel();
    
    public JTextField txtNombre;//variables que declare.
    public JTextField txtTelefono;
    public JDateChooser txtFecha;
    public JComboBox<String> listaHora;
    
    //LISTA DE SERVICIOS
    public JComboBox<String> listaServicios;
    
    //BOTONES
    public JButton reservar;
    public JButton promociones;
    public JButton limpiar;
    public JButton historial;
    
    // mis TABLAS para la lista del historial
    
    public JTable tablaServicios;
    public JTable tablaHistorial;
    
    //constructor
    public Vistacliente(){
        
        //imagen de fondo para el jframe.
        imagenesnexusgo.JPanelimage fondo = new imagenesnexusgo.JPanelimage("/imagenesnexusgo/WhatsApp Image 2026-06-15 at 5.41.42 PM.jpeg");
        fondo.setSize(this.getSize());
        this.setContentPane(fondo);
        this.revalidate();
        this.repaint();
    
        setTitle("Salon de belleza");
        setSize(900,600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JLabel titulo=
                new JLabel("Bienvenido a NEXUS GO ");
        titulo.setBounds(350,10,200,30);
        add(titulo);
        
        JLabel nombre=
                new JLabel("NOMBRE");
        nombre.setBounds(30,60,100,20);
        add (nombre);
        
        txtNombre = new JTextField();
        txtNombre.setBounds(120,60,150,25);
        add(txtNombre);

        JLabel telefono =
                new JLabel("Telefono");

        telefono.setBounds(30,100,100,20);
        add(telefono);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(120,100,150,25);
        add(txtTelefono);

        JLabel servicio =
                new JLabel("Servicio");

        servicio.setBounds(30,140,100,20);
        add(servicio);
        
        historial =
        new JButton("Historial");

        historial.setBounds(450,280,120,30);

        add(historial);
        
          listaServicios =
                new JComboBox<>();
        listaServicios.addItem("");
        listaServicios.addItem("Corte");
        listaServicios.addItem("Peinado");
        listaServicios.addItem("Tinte");
        listaServicios.addItem("Manicure");
        

        listaServicios.setBounds(120,140,150,25);
        add(listaServicios);


        txtFecha = new JDateChooser();

        txtFecha.setBounds(120,180,150,25);

        add(txtFecha);

        JLabel hora =
                new JLabel("Hora");

        hora.setBounds(30,220,100,20);
        add(hora);

        listaHora =
        new JComboBox<>();

    listaHora.addItem("");

    listaHora.addItem("08:00 AM");
    listaHora.addItem("09:00 AM");
    listaHora.addItem("10:00 AM");
    listaHora.addItem("11:00 AM");
    listaHora.addItem("12:00 PM");
    listaHora.addItem("01:00 PM");
    listaHora.addItem("02:00 PM");
    listaHora.addItem("03:00 PM");
    listaHora.addItem("04:00 PM");
    listaHora.addItem("05:00 PM");
    listaHora.addItem("06:00 PM");

    listaHora.setBounds(120,220,150,25);

    add(listaHora);
        reservar =
                new JButton("Reservar CITA");

        reservar.setBounds(30,280,120,30);
        add(reservar);

        promociones =
                new JButton("Promociones");

        promociones.setBounds(170,280,120,30);
        add(promociones);

        limpiar =
                new JButton("Limpiar");

        limpiar.setBounds(310,280,120,30);
        add(limpiar);

        JScrollPane panelServicios =
                new JScrollPane();

        tablaServicios =
                new JTable();

        tablaServicios.setModel(
                new javax.swing.table.DefaultTableModel(
                        new Object[][]{},
                        new String[]{
                            "Servicio",
                            "Duracion",
                            "Precio"
                        }
                )
        );

        panelServicios.setViewportView(tablaServicios);
        panelServicios.setBounds(450,50,380,180);

        add(panelServicios);

        JScrollPane panelHistorial =
                          new JScrollPane();

        tablaHistorial =
                new JTable();

        tablaHistorial.setModel(
                new javax.swing.table.DefaultTableModel(
                      new Object[][]{},
                      new String[]{
                           "Fecha",
                           "Servicio",
                            "Estado"
                        }
                )
        );

        panelHistorial.setViewportView(tablaHistorial);
        panelHistorial.setBounds(450,280,380,180);

        add(panelHistorial);
    }
}

        
    
    
    

