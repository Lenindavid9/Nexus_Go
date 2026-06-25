/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nexusgo.controller;

import nexusgo.model.Herramientas;
import nexusgo.model.HerramientaDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import nexusgo.model.Producto;
import nexusgo.model.ProductoDao;
import nexusgo.view.PanelBienvenida;
import nexusgo.view.VistaAgregarProducto;
import nexusgo.view.VistaOperarioInventario;
import nexusgo.view.VistaPrincipalOperario;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import nexusgo.model.Usuario;
import nexusgo.view.VistaAgregarHerramienta;
import nexusgo.view.VistaInicioSesion;
import nexusgo.view.VistaRegistrarSalida;

/**
 *
 * @author USUARIO
 */
public class ControladorInventarioOperario implements ActionListener {

    private final VistaPrincipalOperario vistaPrincipal;
    private VistaOperarioInventario panelInventario;
    private VistaAgregarProducto panelFormulario;
    private VistaAgregarHerramienta panelFormularioHerramienta;
    private VistaRegistrarSalida panelSalidaInsumo;

    // ------------------------------------------------------------------
    //   COMPONENTES DEL MODELO / DATOS (M) Y ESTADO
    // ------------------------------------------------------------------
    private final ProductoDao productoDao = new ProductoDao();
    private final HerramientaDao herramientaDao = new HerramientaDao();
    private final Usuario usuarioLogueado; // <--- GUARDAR EL USUARIO LOGUEADO GLOBAlMENTE
    private int idSeleccionado = -1;

    /**
     * CONSTRUCTOR CORREGIDO: Ahora recibe la vista y el usuario autenticado
     */
    public ControladorInventarioOperario(VistaPrincipalOperario vistaPrincipal, Usuario usuarioLogueado) {
        this.vistaPrincipal = vistaPrincipal;
        this.usuarioLogueado = usuarioLogueado; // <--- Sincronizamos el usuario

        try {
            this.panelInventario = new VistaOperarioInventario();
            this.panelFormulario = new VistaAgregarProducto();
            this.panelFormularioHerramienta = new VistaAgregarHerramienta();
            this.panelSalidaInsumo = new VistaRegistrarSalida();

            inicializarListeners();

            // Carga inicial de datos
            listarProductosEnTabla();
            listarHerramientasEnTabla();

            // Muestra la bienvenida dinámica con los datos reales del usuario
            cambiarPanelCentral(new PanelBienvenida(usuarioLogueado.getNombre(), usuarioLogueado.getRol()));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error crítico al inicializar los módulos del sistema: " + e.getMessage(),
                    "Error Crítico de Arranque", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void inicializarListeners() {
        try {
            this.vistaPrincipal.getsidebar().bCasa.addActionListener(this);
            this.vistaPrincipal.getsidebar().misCitas.addActionListener(this);

            this.panelInventario.btnAgregarProducto.addActionListener(this);
            this.panelInventario.btnAgregarHerramienta.addActionListener(this);
            this.panelInventario.cerrarSesion.addActionListener(this);

            this.panelInventario.tablaProductos.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int fila = panelInventario.tablaProductos.getSelectedRow();
                    if (fila >= 0) {
                        lanzarMenuDecision("Producto", fila);
                    }
                }
            });

            this.panelInventario.tablaHerramientas.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int fila = panelInventario.tablaHerramientas.getSelectedRow();
                    if (fila >= 0) {
                        lanzarMenuDecision("Herramienta", fila);
                    }
                }
            });

            this.panelFormulario.btnVolver.addActionListener(this);
            this.panelFormulario.btnEditar.addActionListener(this);
            this.panelFormulario.btnImagen.addActionListener(this);

            this.panelFormularioHerramienta.btnVolver.addActionListener(this);
            this.panelFormularioHerramienta.btnEditar.addActionListener(this);
            this.panelFormularioHerramienta.btnImagen.addActionListener(this);

            this.panelSalidaInsumo.btnRegistrarSalida.addActionListener(this);
            this.panelSalidaInsumo.btnVolver.addActionListener(this);

        } catch (NullPointerException npe) {
            System.err.println("Error al enlazar listeners. Verifique los getters de las vistas: " + npe.getMessage());
        }
    }

    private void lanzarMenuDecision(String tipo, int fila) {
        String[] opciones = {"Registrar Salida", "Editar", "Eliminar"};

        int seleccion = JOptionPane.showOptionDialog(panelInventario,
                "¿Qué acción desea realizar con el registro seleccionado?",
                "NEXUS - Panel Operario",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (seleccion == 0) {
            if (tipo.equals("Producto")) {
                idSeleccionado = (int) panelInventario.tablaProductos.getValueAt(fila, 0);
                panelSalidaInsumo.txtCantidadSalida.setText("");
                cambiarPanelCentral(this.panelSalidaInsumo);
            } else {
                JOptionPane.showMessageDialog(panelInventario, "Las herramientas cambian por estado físico, no por salida numérica.");
            }
        } else if (seleccion == 1) {
            if (tipo.equals("Producto")) {
                idSeleccionado = (int) panelInventario.tablaProductos.getValueAt(fila, 0);

                panelFormulario.txtNombre.setText(panelInventario.tablaProductos.getValueAt(fila, 1).toString());
                panelFormulario.txtCantidad.setText(panelInventario.tablaProductos.getValueAt(fila, 3).toString());
                panelFormulario.txtPrecio.setText(panelInventario.tablaProductos.getValueAt(fila, 2).toString());
                panelFormulario.txtDescripcion.setText("");
                panelFormulario.txtStockMinimo.setText("");

                panelFormulario.btnEditar.setText("Editar");
                cambiarPanelCentral(this.panelFormulario);
            } else {
                idSeleccionado = (int) panelInventario.tablaHerramientas.getValueAt(fila, 0);

                panelFormularioHerramienta.txtIdHerramienta.setText(String.valueOf(idSeleccionado));
                panelFormularioHerramienta.txtIdHerramienta.setEditable(false);
                panelFormularioHerramienta.txtNombre.setText(panelInventario.tablaHerramientas.getValueAt(fila, 1).toString());

                panelFormularioHerramienta.btnEditar.setText("Editar");
                cambiarPanelCentral(this.panelFormularioHerramienta);
            }
        } else if (seleccion == 2) {
            if (tipo.equals("Producto")) {
                idSeleccionado = (int) panelInventario.tablaProductos.getValueAt(fila, 0);
                eliminarProducto(idSeleccionado);
            } else {
                idSeleccionado = (int) panelInventario.tablaHerramientas.getValueAt(fila, 0);
                eliminarHerramienta(idSeleccionado);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == vistaPrincipal.getsidebar().bCasa) {
                // CORREGIDO: Usa el usuario dinámico en vez de datos fijos
                cambiarPanelCentral(new PanelBienvenida(usuarioLogueado.getNombre(), usuarioLogueado.getRol()));
            }

            if (e.getSource() == vistaPrincipal.getsidebar().misCitas) {
                cambiarPanelCentral(this.panelInventario);
                listarProductosEnTabla();
                listarHerramientasEnTabla();
            }

            if (e.getSource() == panelInventario.cerrarSesion) {
                ejecutarCerrarSesion();
            }

            if (e.getSource() == panelInventario.btnAgregarProducto) {
                limpiarCamposFormularioProducto();
                panelFormulario.btnEditar.setText("Guardar");
                cambiarPanelCentral(this.panelFormulario);
            }

            if (e.getSource() == panelFormulario.btnImagen) {
                buscarYCopiarImagen("producto");
            }

            if (e.getSource() == panelFormulario.btnEditar) {
                if (panelFormulario.btnEditar.getText().equals("Guardar")) {
                    registrarNuevoProducto();
                } else {
                    actualizarProducto();
                }
            }

            if (e.getSource() == panelFormulario.btnVolver) {
                cambiarPanelCentral(this.panelInventario);
                listarProductosEnTabla();
            }

            if (e.getSource() == panelInventario.btnAgregarHerramienta) {
                limpiarCamposFormularioHerramienta();
                panelFormularioHerramienta.btnEditar.setText("Guardar");
                cambiarPanelCentral(this.panelFormularioHerramienta);
            }

            if (e.getSource() == panelFormularioHerramienta.btnImagen) {
                buscarYCopiarImagen("herramienta");
            }

            if (e.getSource() == panelFormularioHerramienta.btnEditar) {
                if (panelFormularioHerramienta.btnEditar.getText().equals("Guardar")) {
                    registrarNuevaHerramienta();
                } else {
                    actualizarHerramienta();
                }
            }

            if (e.getSource() == panelFormularioHerramienta.btnVolver) {
                cambiarPanelCentral(this.panelInventario);
                listarHerramientasEnTabla();
            }

            if (e.getSource() == panelSalidaInsumo.btnRegistrarSalida) {
                ejecutarRestaDeStock();
            }

            if (e.getSource() == panelSalidaInsumo.btnVolver) {
                cambiarPanelCentral(this.panelInventario);
                listarProductosEnTabla();
            }

        } catch (NullPointerException npe) {
            JOptionPane.showMessageDialog(vistaPrincipal,
                    "Error de referencia: Un componente visual o dato requerido no se cargó correctamente.",
                    "Error de Software", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vistaPrincipal,
                    "Ocurrió un comportamiento inesperado en la interfaz: " + ex.getMessage(),
                    "Error General", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ejecutarRestaDeStock() {
        try {
            int cantidadARestar = Integer.parseInt(panelSalidaInsumo.txtCantidadSalida.getText().trim());
            if (cantidadARestar <= 0) {
                JOptionPane.showMessageDialog(panelSalidaInsumo, "La cantidad debe ser mayor a cero.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (productoDao.registrarSalidaStock(idSeleccionado, cantidadARestar)) {
                JOptionPane.showMessageDialog(panelSalidaInsumo, "¡Transacción exitosa! El inventario se actualizó.");
                cambiarPanelCentral(this.panelInventario);
                listarProductosEnTabla();
            } else {
                JOptionPane.showMessageDialog(panelSalidaInsumo, "Error: No se pudo modificar el stock. Verifique cantidad disponible.", "Error SQL", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(panelSalidaInsumo, "Ingrese un número entero válido.", "Formato Inválido", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarNuevoProducto() {
        try {
            Producto nuevoProducto = new Producto();
            nuevoProducto.setNombreProducto(panelFormulario.txtNombre.getText().trim());
            nuevoProducto.setDescripcion(panelFormulario.txtDescripcion.getText().trim());
            nuevoProducto.setStockActual(Integer.parseInt(panelFormulario.txtCantidad.getText().trim()));
            nuevoProducto.setStockMinimo(panelFormulario.txtStockMinimo.getText().trim().isEmpty() ? 0 : Integer.parseInt(panelFormulario.txtStockMinimo.getText().trim()));

            String precioLimpio = panelFormulario.txtPrecio.getText().replace("$", "").replace(".", "").trim();
            nuevoProducto.setPrecioCompra(Double.parseDouble(precioLimpio));
            nuevoProducto.setUrlImagen(panelFormulario.lblNombreImagen.getText());

            if (productoDao.agregar(nuevoProducto) > 0) {
                JOptionPane.showMessageDialog(panelFormulario, "¡Producto/Insumo registrado con éxito!");
                cambiarPanelCentral(this.panelInventario);
                listarProductosEnTabla();
            } else {
                JOptionPane.showMessageDialog(panelFormulario, "No se pudo insertar el producto en la base de datos.", "Error de Guardado", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panelFormulario, "Error en los campos ingresados: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarProducto() {
        try {
            Producto p = new Producto();
            p.setIdProducto(idSeleccionado);
            p.setNombreProducto(panelFormulario.txtNombre.getText().trim());
            p.setDescripcion(panelFormulario.txtDescripcion.getText().trim());
            p.setStockActual(Integer.parseInt(panelFormulario.txtCantidad.getText().trim()));
            p.setStockMinimo(panelFormulario.txtStockMinimo.getText().trim().isEmpty() ? 0 : Integer.parseInt(panelFormulario.txtStockMinimo.getText().trim()));

            String precioLimpio = panelFormulario.txtPrecio.getText().replace("$", "").replace(".", "").trim();
            p.setPrecioCompra(Double.parseDouble(precioLimpio));
            p.setUrlImagen(panelFormulario.lblNombreImagen.getText());

            if (productoDao.editar(p) > 0) {
                JOptionPane.showMessageDialog(panelFormulario, "¡Producto modificado correctamente!");
                cambiarPanelCentral(this.panelInventario);
                listarProductosEnTabla();
            } else {
                JOptionPane.showMessageDialog(panelFormulario, "No se pudo actualizar el producto en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panelFormulario, "Error en los datos ingresados: " + ex.getMessage(), "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarNuevaHerramienta() {
        try {
            Herramientas nuevaHerramienta = new Herramientas();
            nuevaHerramienta.setIdHerramienta(Integer.parseInt(panelFormularioHerramienta.txtIdHerramienta.getText().trim()));
            nuevaHerramienta.setNombreHerramienta(panelFormularioHerramienta.txtNombre.getText().trim());
            nuevaHerramienta.setEstadoActual("Excelente");

            if (herramientaDao.agregar(nuevaHerramienta) > 0) {
                JOptionPane.showMessageDialog(panelFormularioHerramienta, "¡Herramienta registrada exitosamente!");
                cambiarPanelCentral(this.panelInventario);
                listarHerramientasEnTabla();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panelFormularioHerramienta, "Error al registrar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarHerramienta() {
        try {
            Herramientas h = new Herramientas();
            h.setIdHerramienta(idSeleccionado);
            h.setNombreHerramienta(panelFormularioHerramienta.txtNombre.getText().trim());
            h.setEstadoActual("Excelente");

            if (herramientaDao.editar(h) > 0) {
                JOptionPane.showMessageDialog(panelFormularioHerramienta, "¡Herramienta modificada correctamente!");
                cambiarPanelCentral(this.panelInventario);
                listarHerramientasEnTabla();
            } else {
                JOptionPane.showMessageDialog(panelFormularioHerramienta, "No se pudo actualizar la herramienta.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panelFormularioHerramienta, "Error al actualizar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProducto(int id) {
        int confirmar = JOptionPane.showConfirmDialog(panelInventario,
                "¿Está seguro de que desea eliminar este producto/insumo de forma permanente?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirmar == JOptionPane.YES_OPTION) {
            if (productoDao.eliminar(id) > 0) {
                JOptionPane.showMessageDialog(panelInventario, "Producto eliminado correctamente.");
                listarProductosEnTabla();
            } else {
                JOptionPane.showMessageDialog(panelInventario, "No se pudo eliminar el producto de la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarHerramienta(int id) {
        int confirmar = JOptionPane.showConfirmDialog(panelInventario,
                "¿Está seguro de que desea eliminar esta herramienta de forma permanente?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirmar == JOptionPane.YES_OPTION) {
            if (herramientaDao.eliminar(id) > 0) {
                JOptionPane.showMessageDialog(panelInventario, "Herramienta eliminado correctamente.");
                listarHerramientasEnTabla();
            } else {
                JOptionPane.showMessageDialog(panelInventario, "No se pudo eliminar la herramienta de la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void buscarYCopiarImagen(String tipoModulo) {
        JFileChooser selector = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imágenes (JPG, PNG)", "jpg", "jpeg", "png");
        selector.setFileFilter(filtro);

        JPanel panelPadre = tipoModulo.equals("producto") ? panelFormulario : panelFormularioHerramienta;
        int resultado = selector.showOpenDialog(panelPadre);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                File archivoSeleccionado = selector.getSelectedFile();
                String nombreOriginal = archivoSeleccionado.getName();
                String prefijo = tipoModulo.equals("producto") ? "prod_" : "herr_";
                String nombreLimpio = System.currentTimeMillis() + "_" + prefijo + nombreOriginal.replaceAll("\\s+", "_");

                Path destino = Paths.get("src/nexusgo/img/" + nombreLimpio);
                Files.createDirectories(destino.getParent());
                Files.copy(archivoSeleccionado.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);

                if (tipoModulo.equals("producto")) {
                    // CORRECCIÓN: Se cambia nombreLinter por nombreLimpio
                    panelFormulario.lblNombreImagen.setText(nombreLimpio);
                } else {
                    panelFormularioHerramienta.lblNombreImagen.setText(nombreLimpio);
                }
                JOptionPane.showMessageDialog(panelPadre, "Imagen cargada y vinculada correctamente.");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panelPadre, "Fallo en la transferencia de archivos: " + ex.getMessage(), "Error de Archivo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void listarProductosEnTabla() {
        try {
            DefaultTableModel modeloBlindado = new DefaultTableModel(new Object[]{"ID", "Nombre", "Precio", "Stock", "Tipo"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            panelInventario.tablaProductos.setModel(modeloBlindado);

            List<Producto> lista = productoDao.listar();
            if (lista != null) {
                for (Producto p : lista) {
                    modeloBlindado.addRow(new Object[]{p.getIdProducto(), p.getNombreProducto(), p.getPrecioCompra(), p.getStockActual(), "Insumo Interno"});
                }
            }
        } catch (Exception e) {
            System.err.println("Error al listar productos en tabla visual: " + e.getMessage());
        }
    }

    public void listarHerramientasEnTabla() {
        try {
            DefaultTableModel modeloBlindado = new DefaultTableModel(new Object[]{"ID", "Nombre", "Estado", "Tipo"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            panelInventario.tablaHerramientas.setModel(modeloBlindado);

            List<Herramientas> lista = herramientaDao.listar();
            if (lista != null) {
                for (Herramientas h : lista) {
                    modeloBlindado.addRow(new Object[]{h.getIdHerramienta(), h.getNombreHerramienta(), h.getEstadoActual(), "Activo"});
                }
            }
        } catch (Exception e) {
            System.err.println("Error al listar herramientas en tabla visual: " + e.getMessage());
        }
    }

    private void limpiarCamposFormularioProducto() {
        panelFormulario.txtNombre.setText("");
        panelFormulario.txtDescripcion.setText("");
        panelFormulario.txtCantidad.setText("");
        panelFormulario.txtPrecio.setText("");
        panelFormulario.txtStockMinimo.setText("");
        panelFormulario.lblNombreImagen.setText("ningún archivo seleccionado");
    }

    private void limpiarCamposFormularioHerramienta() {
        panelFormularioHerramienta.txtIdHerramienta.setText("");
        panelFormularioHerramienta.txtIdHerramienta.setEditable(true);
        panelFormularioHerramienta.txtNombre.setText("");
        panelFormularioHerramienta.lblNombreImagen.setText("ningún archivo seleccionado");
    }

    /**
     * LOGOUT CORREGIDO: Cierra la ventana actual y vuelve a desplegar de forma
     * limpia el Login
     */
    private void ejecutarCerrarSesion() {
        int confirmar = JOptionPane.showConfirmDialog(vistaPrincipal,
                "¿Desea cerrar sesión en el Sistema NEXUS?",
                "Cerrar Sesión", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirmar == JOptionPane.YES_OPTION) {
            vistaPrincipal.dispose(); // Cierra el Frame principal actual

            // Reabre la ventana de Login y su respectivo controlador
            VistaInicioSesion loginVista = new VistaInicioSesion();
            new ControladorInicioSesion(loginVista);
            loginVista.setLocationRelativeTo(null);
            loginVista.setVisible(true);
        }
    }

    /**
     * ENRUTADOR DE NAVEGACIÓN: Asegúrate de que "getContenido()" coincida con
     * tu VistaPrincipalOperario
     */
    private void cambiarPanelCentral(JPanel panelNuevo) {
        try {
            vistaPrincipal.getContenido().removeAll();
            vistaPrincipal.getContenido().add(panelNuevo, java.awt.BorderLayout.CENTER);
            vistaPrincipal.revalidate();
            vistaPrincipal.repaint();
        } catch (Exception e) {
            System.err.println("Error en navegación de paneles: " + e.getMessage());
        }
    }
}
