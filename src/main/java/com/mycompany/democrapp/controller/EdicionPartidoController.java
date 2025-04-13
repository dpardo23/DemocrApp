// Paquete principal que organiza el proyecto
package com.mycompany.democrapp.controller;

// Importaciones necesarias para conectar con la base de datos, validar datos y manejar la vista
import com.mycompany.democrapp.model.ConexionSQL;
import com.mycompany.democrapp.model.ValidarDatos;
import com.mycompany.democrapp.view.EdicionDeDatos;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

// Esta clase controla la lógica del formulario de equipos deportivos
public class EdicionPartidoController {

    // Aquí se guarda una referencia al formulario que el usuario ve
    private final EdicionDeDatos vista;
    
    // Constructor que recibe el modelo (base de datos) y la vista (interfaz gráfica)
    public EdicionPartidoController(ConexionSQL modelo, EdicionDeDatos vista) {
        this.vista = vista;

        // Acción para el botón "Guardar"
        this.vista.btnGuardar.addActionListener((ActionEvent e) -> {
            try {
                // 1. Capturar datos desde los campos del formulario
                // 2. Validarlos para asegurarnos de que están correctos
                String editNombrePartido = ValidarDatos.validarNombrePartido(vista.txtNombrePartido.getText());
                String editSigla = ValidarDatos.validarSigla(vista.txtSigla.getText());
                String editNombreLider = ValidarDatos.validarNombreLider(vista.txtNombreLider.getText());
                ValidarDatos.verificarDuplicados(editNombrePartido, editSigla, editNombreLider, 0, modelo); //verificar que no haya repetidos
                int editDepartamento = ValidarDatos.validarDepartamento(vista.txtDepartamento.getText());
                int editNumAfiliados = ValidarDatos.validarNumAfiliados(vista.txtAfiliados.getText());
                String editIdeologia = ValidarDatos.validarIdeologia(vista.txtIdeologia.getText());
                
                // Guardar los datos del equipo en la base de datos
                modelo.guardarPartido(0, editNombrePartido, editSigla, editNombreLider, editIdeologia, editNumAfiliados, editDepartamento);
                
                // Mostrar un mensaje que diga que todo salió bien
                JOptionPane.showMessageDialog(null, "Datos guardados correctamente.");
            } catch (IllegalArgumentException ex) {
                // Mostrar un mensaje si hubo un error al validar los datos
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        // Acción para el botón "Cancelar"
        this.vista.btnAtras.addActionListener((ActionEvent e) -> {
            
          
        });
    }

    // Método para iniciar la interfaz gráfica
    public void iniciar() {
        // Crear una ventana con el título del formulario
        JFrame frame = new JFrame("Formulario Equipos Deportivos");

        // Configurar lo básico de la ventana: tamaño, comportamiento al cerrarla, etc.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 450);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        // Agregar el formulario (vista) a la ventana y hacerla visible
        frame.add(vista);
        frame.setVisible(true);
    }
}
