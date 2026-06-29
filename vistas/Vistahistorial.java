/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Viewcliente;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Vistahistorial extends JFrame {

    // TABLA
    public JTable tablaHistorial;

    // BOTON
    public JButton volver;

    public Vistahistorial() {

        // Imagen de fondo
        imagenesnexusgo.JPanelimage fondo =
                new imagenesnexusgo.JPanelimage("/imagenesnexusgo/WhatsApp Image 2026-06-15 at 5.41.42 PM.jpeg");

        fondo.setSize(this.getSize());
        this.setContentPane(fondo);
        this.revalidate();
        this.repaint();

      
        setTitle("Historial de Servicios");
        setSize(850, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        
        JLabel titulo =
                new JLabel("HISTORIAL DE SERVICIOS");

        titulo.setBounds(300, 20, 250, 30);

        add(titulo);

        

        JScrollPane panelHistorial =
                new JScrollPane();

        tablaHistorial =
                new JTable();

        tablaHistorial.setModel(

                new javax.swing.table.DefaultTableModel(

                        new Object[][]{},
                        new String[]{
                            "Id",
                            "Nombre",
                            "Telefono",
                            "Servicio",
                            "Fecha",
                            "Hora"
                        }

                )

        );

        panelHistorial.setViewportView(tablaHistorial);

        panelHistorial.setBounds(40, 80, 760, 350);

        add(panelHistorial);

     
        volver =
                new JButton("Volver");

        volver.setBounds(350, 470, 120, 35);

        add(volver);

    }

}
