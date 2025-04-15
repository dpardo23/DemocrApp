// Paquete principal que organiza el proyecto
package com.mycompany.democrapp.controller;

// Importaciones necesarias para conectar con la base de datos, validar datos y manejar la vista
import com.mycompany.democrapp.model.ConexionSQL;
import com.mycompany.democrapp.model.InsercionDatos;
import com.mycompany.democrapp.model.ValidarDatos;
import com.mycompany.democrapp.view.RegistroEdicionPartidos;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class RegistroEdicionController {

    // Aquí se guarda una referencia al formulario que el usuario ve
    private final RegistroEdicionPartidos vista;
    private final ValidarDatos validacion;
    private String idComparar;

    // Constructor que recibe el modelo y la vista
    public RegistroEdicionController(ConexionSQL modelo, RegistroEdicionPartidos vista) {

        this.vista = vista;
        this.validacion = new ValidarDatos();

        /**
         * Acccion para el boton "Guardar" en la pantalla de registro. Cuando se
         * presiona guarda todos los campos TextField del registro y mediante un
         * query los manda a la base de datos
         */
        this.vista.btnGuardarRegistro.addActionListener((ActionEvent e) -> {
            try (Connection connection = ConexionSQL.getConnection()) {
                // 1. Capturar datos desde los campos del formulario
                String idPartido = vista.txtIdentificador.getText();
                String departamento = vista.txtDepartamento.getText();
                String nombrePartido = vista.txtNombrePartido.getText();
                String sigla = vista.txtSigla.getText();
                String nombreLider = vista.txtNombreLider.getText();
                String ideologia = vista.txtIdeologia.getText();
                String numAfiliados = vista.txtNumAfiliados.getText();
                String fechaFundacion = vista.txtFechaFundacion.getText();

                // 2. Validar los datos
                if (!ValidarDatos.validarIdentificador(ConexionSQL.getConnection(), idPartido)) {
                    return;
                }
                if (ValidarDatos.validarDepartamento(departamento) == -1) {
                    return; 
                }
                if (ValidarDatos.validarNombrePartido(nombrePartido) == null) {
                    return; 
                }
                if (ValidarDatos.validarSigla(sigla) == null) {
                    return; 
                }
                if (ValidarDatos.validarNombreLider(nombreLider) == null) {
                    return; 
                }
                if (ValidarDatos.validarIdeologia(ideologia) == null) {
                    return; 
                }
                if (ValidarDatos.validarNumAfiliados(numAfiliados) == -1) {
                    return; 
                }
                if (!ValidarDatos.validarFecha(fechaFundacion)) {
                    return; 
                }

                // Verificar duplicados
                if (ValidarDatos.verificarDuplicados(nombrePartido, sigla, nombreLider, connection)) {
                    JOptionPane.showMessageDialog(null, "Error: Los datos ingresados ya existen. Por favor, verifica y corrige.");
                    return; // Detener el flujo si hay duplicados
                }

                // 3. Insertar los datos en la base de datos
                InsercionDatos insercionDatos = new InsercionDatos();
                boolean exito = insercionDatos.insertarPartido(
                        Integer.parseInt(idPartido),
                        Integer.parseInt(departamento),
                        nombrePartido,
                        sigla,
                        nombreLider,
                        ideologia,
                        Integer.parseInt(numAfiliados),
                        fechaFundacion
                );

                // 4. Mostrar mensajes de éxito o fallo
                if (exito) {
                    JOptionPane.showMessageDialog(null, "Partido Politico registrado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo registrar el partido.");
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error de base de datos: " + ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error inesperado: " + ex.getMessage());
            }

            // Limpiar los campos de texto del formulario de registro
            vista.txtIdentificador.setText("");
            vista.txtDepartamento.setText("");
            vista.txtNombrePartido.setText("");
            vista.txtSigla.setText("");
            vista.txtIdeologia.setText("");
            vista.txtNumAfiliados.setText("");
            vista.txtFechaFundacion.setText("");
            vista.txtNombreLider.setText("");
        });

        /**
         * Acción para el botón "Atrás" en la pantalla de registro de datos.
         * Cuando se acciona, se ocultan todos los paneles excepto el panel
         * principal. También limpia los TextField relacionados con el registro
         * de partidos.
         */
        this.vista.btnAtrasRegistro.addActionListener((ActionEvent e) -> {
            // Ocultar los demas paneles
            vista.panelRegistroDeDatos.setVisible(false);
            vista.panelTablaEdicion.setVisible(false);
            vista.panelEdicionDeDatos.setVisible(false);

            // Mostrar el panel principal
            vista.panelPrincipal.setVisible(true);

            // Limpiar los campos de texto del formulario de registro
            vista.txtIdentificador.setText("");
            vista.txtDepartamento.setText("");
            vista.txtNombrePartido.setText("");
            vista.txtSigla.setText("");
            vista.txtIdeologia.setText("");
            vista.txtNumAfiliados.setText("");
            vista.txtFechaFundacion.setText("");
            vista.txtNombreLider.setText("");
        });

        /**
         * Acción para el botón "Atrás" en la pantalla de edición de datos.
         * Cuando se acciona, se ocultan todos los paneles excepto el principal.
         * Además, se limpia el campo de identificación relacionado con la
         * edición de registros.
         */
        this.vista.btnAtrasTabla.addActionListener((ActionEvent e) -> {
            // Ocultar los demas paneles
            vista.panelRegistroDeDatos.setVisible(false);
            vista.panelTablaEdicion.setVisible(false);
            vista.panelEdicionDeDatos.setVisible(false);

            // Mostrar el panel principal
            vista.panelPrincipal.setVisible(true);

            // Limpiar el campo de identificacion en la edicion de datos
            vista.txtIdentificadorTabla.setText("");
        });

        /**
         * Accion para el boton "Validar" en la pantalla de edicion de datos.
         * Cuando se acciona, toma el valor ingresado en el TextField y lo
         * compara con el Identificador registrado en la base de datos, despues
         * muestra la pantalla con el formulario de edcion.
         */
        this.vista.btnValidarTabla.addActionListener((ActionEvent e) -> {
            try {
                // Capturar el valor ingresado en txtidentificadorTabla
                idComparar = vista.txtIdentificadorTabla.getText();

                // Verificar que no este vacio
                if (idComparar == null || idComparar.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(vista, "Por favor ingrese un identificador.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // LLamar al metodo que verifica el identificador en la base de datos.
                boolean existe = validacion.verificarIdPartido(idComparar);
                if (existe) {
                    // Ocultar los demas paneles
                    vista.panelPrincipal.setVisible(false);
                    vista.panelTablaEdicion.setVisible(false);
                    vista.panelRegistroDeDatos.setVisible(false);

                    // Mostrar el panel de edicion de datos
                    vista.panelEdicionDeDatos.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(vista, "El identificador no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, "Ocurrio un error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        /**
         * Acción para el botón "Registro de datos" en la pantalla principal.
         * Cuando se presiona, oculta otros paneles y muestra el panel de
         * registro de datos.
         */
        this.vista.btnRegistroDeDatos.addActionListener((ActionEvent e) -> {
            // Ocultar los demas paneles
            vista.panelTablaEdicion.setVisible(false);
            vista.panelPrincipal.setVisible(false);
            vista.panelEdicionDeDatos.setVisible(false);

            // Mostrar el panel de registro de datos
            vista.panelRegistroDeDatos.setVisible(true);
        });

        /**
         * Acción para el botón "Edición de datos" en la pantalla principal.
         * Cuando se presiona, se limpia la tabla y se muestra el panel de con
         * la tabla que tiene los identificadores y nombres de los partidos.
         * politcos.
         */
        this.vista.btnEdicionDeDatos.addActionListener((ActionEvent e) -> {
            // Obtener el modelo de la tabla y limpiar sus datos
            DefaultTableModel model = (DefaultTableModel) this.vista.tablaIdentificador.getModel();
            model.setRowCount(0); // Limpiar tabla

            // Ocultar los demas paneles
            vista.panelPrincipal.setVisible(false);
            vista.panelRegistroDeDatos.setVisible(false);
            vista.panelEdicionDeDatos.setVisible(false);

            // Mostrar el panel que contiene la tabla
            vista.panelTablaEdicion.setVisible(true);

            try {
                var datos = validacion.obtenerPartidosPoliticos();

                //Agregar los datos a la tabla
                for (String[] fila : datos) {
                    model.addRow(new Object[]{fila[0], fila[1]});
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, "Error al cargar los datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Refrescar la vista
            vista.panelTablaEdicion.repaint();
            vista.panelTablaEdicion.revalidate();
        });

        /**
         * Acción para el botón "Atras" en la pantalla edicion de datos. Cuando
         * se presiona, oculta otros paneles y muestra el panel donde se
         * encuentra la tabla y limpia los datos existentes en los TextField.
         */
        this.vista.btnAtrasEdit.addActionListener((ActionEvent e) -> {
            // Ocultar los demas paneles
            vista.panelRegistroDeDatos.setVisible(false);
            vista.panelPrincipal.setVisible(false);
            vista.panelEdicionDeDatos.setVisible(false);

            // Mostrar el panel de registro de datos
            vista.panelTablaEdicion.setVisible(true);

            // Limpiar los campos de texto del formulario de edicion
            vista.txtNombrePartidoEdit.setText("");
            vista.txtSiglaEdit.setText("");
            vista.txtNombreLiderEdit.setText("");
            vista.txtIdeologiaEdit.setText("");
            vista.txtNumeroAfiliadosEdit.setText("");
            vista.txtDepartamentoEdit.setText("");
        });

        /**
         * Acción para el botón "Guardar" en la pantalla edicion de datos.
         * Cuando se presiona, oculta otros paneles y muestra el panel donde se
         * encuentra el formulario y limpia los datos existentes enlos
         * TextField, ademas manda los nuevos a la BD.
         */
        this.vista.btnGuardarEdit.addActionListener((ActionEvent e) -> {
            try (Connection connection = ConexionSQL.getConnection()) {
                // 1. Capturar los datos de los campos del formulario
                String nombrePartidoEdit = vista.txtNombrePartidoEdit.getText();
                String departamentoEdit = vista.txtDepartamentoEdit.getText();
                String siglaEdit = vista.txtSiglaEdit.getText();
                String nombreLiderEdit = vista.txtNombreLiderEdit.getText();
                String ideologiaEdit = vista.txtIdeologiaEdit.getText();
                String numAfiliadosEdit = vista.txtNumeroAfiliadosEdit.getText();

                // 2. Validar los datos
                if (ValidarDatos.validarNombrePartido(nombrePartidoEdit) == null) {
                    return;
                }
                if (ValidarDatos.validarDepartamento(departamentoEdit) == -1) {
                    return;
                }
                if (ValidarDatos.validarSigla(siglaEdit) == null) {
                    return;
                }
                if (ValidarDatos.validarNombreLider(nombreLiderEdit) == null) {
                    return;
                }
                if (ValidarDatos.validarIdeologia(ideologiaEdit) == null) {
                    return;
                }
                if (ValidarDatos.validarNumAfiliados(numAfiliadosEdit) == -1) {
                    return;
                }

                // 3. Verificar duplicados
                if (ValidarDatos.verificarDuplicados(nombrePartidoEdit, siglaEdit, nombreLiderEdit, connection)) {
                    JOptionPane.showMessageDialog(null, "Error: Los datos ingresados ya existen. Por favor, verifica y corrije");
                    return;
                }

                // 4. Insertar los datos en la base de datos
                try {
                    // Llamar al método actualizarPartido en la clase ValidarDatos
                    ValidarDatos.actualizarPartido(
                            Integer.parseInt(idComparar), // Usamos idComparar como el ID del partido, convirtiéndolo a entero
                            nombrePartidoEdit,
                            siglaEdit,
                            nombreLiderEdit,
                            ideologiaEdit,
                            Integer.parseInt(numAfiliadosEdit),
                            Integer.parseInt(departamentoEdit)
                    );

                    JOptionPane.showMessageDialog(vista, "Los datos se han actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    // Limpiar los campos del formulario después de la actualización
                    vista.txtNombrePartidoEdit.setText("");
                    vista.txtDepartamentoEdit.setText("");
                    vista.txtSiglaEdit.setText("");
                    vista.txtNombreLiderEdit.setText("");
                    vista.txtIdeologiaEdit.setText("");
                    vista.txtNumeroAfiliadosEdit.setText("");

                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(vista, "Error: asegúrate de ingresar datos numéricos válidos en los campos correspondientes.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(vista, "Error inesperado al actualizar los datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegistroEdicionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Método para iniciar la interfaz gráfica "Principal"
    public void iniciar() {
        vista.setVisible(true);
        vista.panelTablaEdicion.setVisible(false);
        vista.panelEdicionDeDatos.setVisible(false);
        vista.panelPrincipal.setVisible(false);
    }
}
