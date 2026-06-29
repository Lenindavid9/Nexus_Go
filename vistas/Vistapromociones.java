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

/**
 *
 * @author HOME
 */
public class Vistapromociones extends JFrame{
   
 
    public JTable tablaPromociones;
    public JButton volver;

    public Vistapromociones() {
        
        imagenesnexusgo.JPanelimage fondo = new imagenesnexusgo.JPanelimage("/imagenesnexusgo/WhatsApp Image 2026-06-15 at 5.41.42 PM.jpeg");
        fondo.setSize(this.getSize());
        this.setContentPane(fondo);
        this.revalidate();
        this.repaint();

        setTitle("Promociones");
        setSize(700,500);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        
        JLabel titulo =
                new JLabel("PROMOCIONES DISPONIBLES");

        titulo.setBounds(250,20,250,30);
        add(titulo);

        JScrollPane panel =
                new JScrollPane();

        tablaPromociones =
                new JTable();

        tablaPromociones.setModel(
                new javax.swing.table.DefaultTableModel(
                        new Object[][]{
                            {"Corte + Peinado","20%"},
                            {"Tinte","15%"},
                            {"Manicure","10%"}
                        },
                        new String[]{
                            "Servicio",
                            "Descuento"
                        }
                )
        );

        panel.setViewportView(tablaPromociones);

        panel.setBounds(100,80,450,200);
        add(panel);

        volver =
                new JButton("Volver");

        volver.setBounds(280,330,120,30);
        add(volver);
    }
}


