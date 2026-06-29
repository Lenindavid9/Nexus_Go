/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladorpeluquero;

import Modelcliente.Cita;
import Modelcliente.CitaDao;
import Viewpeluquero.Vistamodificarcitas;
import Viewpeluquero.Vistapeluquero;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author HOME
 */


public class Controladorpeluquero implements ActionListener {

   
    Vistapeluquero vista;
    Vistamodificarcitas vistaModificar;
    Cita cita = new Cita();
    CitaDao dao = new CitaDao();

    
    public Controladorpeluquero(Vistapeluquero vista) {
        this.vista = vista;
        
        
        this.vista.asignar.addActionListener(this);
        this.vista.modificar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

       
        if (e.getSource() == vista.asignar) {
            if (vista.txtCliente.getText().isEmpty() || vista.txtFecha.getText().isEmpty() || vista.txtHora.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Debe completar todos los campos");
            } else {
                cita.setNombre(vista.txtCliente.getText());
                cita.setFecha(vista.txtFecha.getText());
                cita.setHora(vista.txtHora.getText());
                cita.setServicio(vista.listaCorte.getSelectedItem().toString());

                int r = dao.guardar(cita);
                if (r == 1) {
                    JOptionPane.showMessageDialog(vista, "Cita asignada con éxito");
                    vista.txtCliente.setText("");
                    vista.txtFecha.setText("");
                    vista.txtHora.setText("");
                }
            }
        }

        
        if (e.getSource() == vista.modificar) {
            vistaModificar = new Vistamodificarcitas();
            
            
            vistaModificar.guardar.addActionListener(this);
            
            
            vistaModificar.setVisible(true);
            listarAgenda();
        }

        
        try {
            if (e.getSource() == vistaModificar.guardar) {
                if (vistaModificar.txtFecha.getText().isEmpty() || vistaModificar.txtHora.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(vistaModificar, "Complete Fecha y Hora");
                } else {
                    cita.setServicio(vistaModificar.listaServicios.getSelectedItem().toString());
                    cita.setFecha(vistaModificar.txtFecha.getText());
                    cita.setHora(vistaModificar.txtHora.getText());

                    int r = dao.modificar(cita);
                    if (r == 1) {
                        JOptionPane.showMessageDialog(vistaModificar, "Cita modificada con éxito");
                        listarAgenda(); 
                    }
                }
            }
        } catch (Exception ex) {
            
        }
    }

    public void listarAgenda() {
        List<Cita> lista = dao.listar();
        
        vistaModificar.modeloAgenda.setColumnCount(0);
        vistaModificar.modeloAgenda.setRowCount(0);

       
        vistaModificar.modeloAgenda.addColumn("Hora");
        vistaModificar.modeloAgenda.addColumn("Cliente");
        vistaModificar.modeloAgenda.addColumn("Servicio");

        Object[] fila = new Object[3];
        for (int i = 0; i < lista.size(); i++) {
            fila[0] = lista.get(i).getHora();
            fila[1] = lista.get(i).getNombre();
            fila[2] = lista.get(i).getServicio();
            vistaModificar.modeloAgenda.addRow(fila);
        }
    }
}