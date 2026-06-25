/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nexusgo.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import nexusgo.model.Usuario;
import nexusgo.model.UsuarioDao;
import nexusgo.view.VistaInicioSesion;
import nexusgo.view.VistaPrincipalOperario;

/**
 *
 * @author USUARIO
 */
public class ControladorInicioSesion implements ActionListener{
  // Atributos de la Vista y el Modelo (MVC)
    private final VistaInicioSesion vistaLogin;
    private final UsuarioDao usuarioDao;

    /**
     * Constructor del controlador. Acopla la vista y activa el escucha del botón.
     */
    public ControladorInicioSesion(VistaInicioSesion vistaLogin) {
        this.vistaLogin = vistaLogin;
        this.usuarioDao = new UsuarioDao();
        
        // Enlazar el botón "Entrar" de la vista con este controlador
        this.vistaLogin.getBtnEntrar().addActionListener(this);
    }

    /**
     * Captura los clics realizados en la interfaz gráfica del Login.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaLogin.getBtnEntrar()) {
            ejecutarLogin();
        }
    }

    /**
     * Proceso lógico de validación, consulta a Laragon/MySQL y enrutamiento por Rol.
     */
    private void ejecutarLogin() {
        // 1. Obtener y limpiar los textos ingresados por el usuario
        String identificacion = vistaLogin.tNroIdentidad.getText().trim();
        String contrasena = vistaLogin.tContrasena.getText().trim();

        // 2. Validación de seguridad básica en la interfaz
        if (identificacion.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(vistaLogin, 
                    "Por favor, complete todos los campos de ingreso.", 
                    "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 3. Consultar a la base de datos mediante el DAO (Devuelve un objeto Usuario o null)
        Usuario usuarioLogueado = usuarioDao.autenticarUsuario(identificacion, contrasena);

        // 4. Evaluar el resultado de la autenticación
        if (usuarioLogueado != null) {
            
            // Extraemos los datos reales guardados en la base de datos
            String nombreReal = usuarioLogueado.getNombre();
            String rolReal = usuarioLogueado.getRol();

            JOptionPane.showMessageDialog(vistaLogin, "¡Bienvenido " + nombreReal + " al Sistema NEXUS!");

            // 5. ENRUTAMIENTO DINÁMICO SEGÚN EL ROL
            if (rolReal.equalsIgnoreCase("Operario")) {
                
                // Instanciamos la ventana principal del módulo de inventario
                VistaPrincipalOperario vistaMenu = new VistaPrincipalOperario();
                
                // Inicializamos su controlador pasándole el Frame y el Usuario logueado
                ControladorInventarioOperario controladorInventario = new ControladorInventarioOperario(vistaMenu, usuarioLogueado);
                
                // Configuración y despliegue de la ventana principal
                vistaMenu.getsidebar().setVisible(true);
                vistaMenu.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH); // Pantalla completa
                vistaMenu.setVisible(true);
                
                // Cerramos la ventana de Login para no consumir memoria RAM innecesaria
                vistaLogin.dispose();
                
            } else if (rolReal.equalsIgnoreCase("Supervisor")) {
                // Si el Supervisor comparte la misma vista del Operario pero con más privilegios:
                VistaPrincipalOperario vistaMenu = new VistaPrincipalOperario();
                ControladorInventarioOperario controladorInventario = new ControladorInventarioOperario(vistaMenu, usuarioLogueado);
                
                vistaMenu.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
                vistaMenu.setVisible(true);
                vistaLogin.dispose();
                
            } else if (rolReal.equalsIgnoreCase("Administrador")) {
                // Espacio reservado para cuando desarrolles el panel del dueño de la peluquería (Caja, reportes, etc.)
                JOptionPane.showMessageDialog(vistaLogin, "El panel de Administrador está en desarrollo.", "Módulo Pendiente", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(vistaLogin, "El rol '" + rolReal + "' no tiene un panel asignado en el sistema.", "Error de Permisos", JOptionPane.ERROR_MESSAGE);
            }
            
        } else {
            // Si el objeto regresó nulo es porque el usuario o la clave no existen en la BD
            JOptionPane.showMessageDialog(vistaLogin, 
                    "Número de identificación o contraseña incorrectos.\nInténtelo nuevamente.", 
                    "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
        }
    }
    
   
    
}
