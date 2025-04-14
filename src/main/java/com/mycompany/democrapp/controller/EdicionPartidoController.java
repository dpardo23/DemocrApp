package com.mycompany.democrapp.controller;

import com.mycompany.democrapp.model.ValidarDatos;
import com.mycompany.democrapp.view.EdicionDeDatos;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class EdicionPartidoController {

    private final EdicionDeDatos vista;

    // Constructor que inicializa la vista
    public EdicionPartidoController(EdicionDeDatos vista) {
        this.vista = vista;

        // Acción para el botón "Guardar"
        this.vista.btnGuardar.addActionListener((ActionEvent e) -> {
            try {
                // Capturar datos desde los campos del formulario
                //int idPartido = Integer.parseInt(vista.txtIdPartido.getText());
                String nombrePartido = ValidarDatos.validarNombrePartido(vista.txtNombrePartido.getText());
                String sigla = ValidarDatos.validarSigla(vista.txtSigla.getText());
                String nombreLider = ValidarDatos.validarNombreLider(vista.txtNombreLider.getText());
                int departamento = ValidarDatos.validarDepartamento(vista.txtDepartamento.getText());
                int numAfiliados = ValidarDatos.validarNumAfiliados(vista.txtAfiliados.getText());
                String ideologia = ValidarDatos.validarIdeologia(vista.txtIdeologia.getText());

                // Actualizar los datos en la base de datos
                //ValidarDatos.actualizarPartido(idPartido, nombrePartido, sigla, nombreLider, ideologia, numAfiliados, departamento);

                // Mostrar mensaje de éxito
                JOptionPane.showMessageDialog(null, "Datos actualizados correctamente.");
            } catch (IllegalArgumentException ex) {
                // Manejar errores de validación
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
            // Manejar errores de conversión de ID

        });

        // Acción para el botón "Atrás"
        this.vista.btnAtras.addActionListener((ActionEvent e) -> {
            // Confirmación para regresar al panel de tabla
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que deseas regresar al panel principal?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) {
                // Cambiar al panel principal
                vista.panelTabla.setVisible(true);
                vista.panelEdicion.setVisible(false);

                // Limpiar los campos del formulario
                //vista.txtIdPartido.setText("");
                vista.txtNombrePartido.setText("");
                vista.txtSigla.setText("");
                vista.txtNombreLider.setText("");
                vista.txtDepartamento.setText("");
                vista.txtAfiliados.setText("");
                vista.txtIdeologia.setText("");
            }
        });

        // Acción para el botón "Validar"
        this.vista.btnValidar.addActionListener((ActionEvent e) -> {
            // Mostrar el panel de edición
            vista.panelEdicion.setVisible(true);
            vista.panelTabla.setVisible(false);
        });
    }
}
