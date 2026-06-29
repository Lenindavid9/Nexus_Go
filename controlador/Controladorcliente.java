/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControladorCliente;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import Viewcliente.Vistahistorial;
import Modelcliente.Cita;
import Modelcliente.CitaDao;
import Viewcliente.Vistacliente;
import Viewcliente.Vistahistorial;
import Viewcliente.Vistapromociones;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List; 
import javax.swing.JOptionPane;

public class Controladorcliente implements ActionListener {

    Vistacliente vista;

    Cita cita = new Cita();

    CitaDao dao = new CitaDao();

    public Controladorcliente(Vistacliente vista) {

        this.vista = vista;

        vista.reservar.addActionListener(this);
        vista.promociones.addActionListener(this);
        vista.limpiar.addActionListener(this);
        vista.historial.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // BOTON RESERVAR
        if (e.getSource() == vista.reservar) {

            // VALIDAR CAMPOS VACIOS
            if (vista.txtNombre.getText().isEmpty()
                    || vista.txtTelefono.getText().isEmpty()
                    || vista.txtFecha.getDate() == null
                    || vista.listaHora.getSelectedIndex() == 0
                    || vista.listaServicios.getSelectedIndex() == 0) {

                JOptionPane.showMessageDialog(vista,
                        "Debe completar todos los campos");

                return;
            }

            // VALIDAR NOMBRE
            if (!vista.txtNombre.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {

                JOptionPane.showMessageDialog(vista,
                        "El nombre solo debe contener letras");

                return;
            }

            // VALIDAR TELEFONO
            if (!vista.txtTelefono.getText().matches("[0-9]+")) {

                JOptionPane.showMessageDialog(vista,
                        "El teléfono solo debe contener números");

                return;
            }

            if (vista.txtTelefono.getText().length() != 10) {

                JOptionPane.showMessageDialog(vista,
                        "El teléfono debe tener 10 dígitos");

                return;
            }

            // OBTENER DATOS
            String nombre = vista.txtNombre.getText();

            String telefono = vista.txtTelefono.getText();

            String servicio = vista.listaServicios.getSelectedItem().toString();

            String fecha = new SimpleDateFormat("yyyy-MM-dd")
                    .format(vista.txtFecha.getDate());

            String hora = vista.listaHora.getSelectedItem().toString();

            // GUARDAR EN EL OBJETO
            cita.setNombre(nombre);
            cita.setTelefono(telefono);
            cita.setServicio(servicio);
            cita.setFecha(fecha);
            cita.setHora(hora);

            // ENVIAR AL DAO
            int resultado = dao.guardar(cita);

            if (resultado == 1) {

                JOptionPane.showMessageDialog(vista,
                        "Reserva realizada correctamente");

                limpiarCampos();

            } else {

                JOptionPane.showMessageDialog(vista,
                        "No fue posible guardar la reserva");

            }

        }

        // BOTON HISTORIAL
        if (e.getSource() == vista.historial) {

            Vistahistorial historial = new Vistahistorial();

            cargarHistorial(historial);

            historial.setVisible(true);

        }

        // BOTON PROMOCIONES
        if (e.getSource() == vista.promociones) {

            Vistapromociones promociones = new Vistapromociones();

            promociones.setVisible(true);

        }

        // BOTON LIMPIAR
        if (e.getSource() == vista.limpiar) {

            limpiarCampos();

        }

    }

    // LIMPIAR CAMPOS
    public void limpiarCampos() {

        vista.txtNombre.setText("");

        vista.txtTelefono.setText("");

        vista.txtFecha.setDate(null);

        vista.listaHora.setSelectedIndex(0);

        vista.listaServicios.setSelectedIndex(0);

    }

    // CARGAR HISTORIAL
    public void cargarHistorial(Vistahistorial historial) {

        DefaultTableModel modelo;

        modelo = (DefaultTableModel) historial.tablaHistorial.getModel();

        modelo.setRowCount(0);

        List<Cita> lista = dao.listar();

        Object[] fila = new Object[6];

        for (int i = 0; i < lista.size(); i++) {

            fila[0] = lista.get(i).getId();
            fila[1] = lista.get(i).getNombre();
            fila[2] = lista.get(i).getTelefono();
            fila[3] = lista.get(i).getServicio();
            fila[4] = lista.get(i).getFecha();
            fila[5] = lista.get(i).getHora();

            modelo.addRow(fila);

        }

    }

}

