package com.mycompany.democrapp.controller;

import com.mycompany.democrapp.model.UsuarioDAO;
import javax.swing.JOptionPane;
import com.mycompany.democrapp.view.EdicionDeDatos;

/**
 * Controlador que maneja el proceso de inicio de sesión.
 */
public class LoginController {

    // Instancia del DAO para operaciones con el Usuario
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Método para iniciar sesión del usuario.
     * Se realizan validaciones del formato de entrada y luego se consulta la base de datos.
     * Si las credenciales son correctas, se abre el panel "EdicionDeDatos".
     *
     * @param usuario Nombre de usuario. Se permite solo caracteres alfabéticos.
     * @param contrasena Contraseña que puede contener caracteres alfabéticos, numéricos y especiales.
     */
    public void iniciarSesion(String usuario, String contrasena) {
        // Validar formato del nombre de usuario: solo letras (sin espacios o caracteres especiales)
        if (!usuario.matches("^[a-zA-Z]+$")) {
            JOptionPane.showMessageDialog(null, 
                "El nombre de usuario solo puede contener letras.", 
                "Error de validación", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que la contraseña no esté vacía (se pueden agregar más criterios de validación)
        if (contrasena == null || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "La contraseña no puede estar vacía.", 
                "Error de validación", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Validar credenciales consultando la base de datos mediante el procedimiento almacenado.
            boolean loginValido = usuarioDAO.validarUsuario(usuario, contrasena);

            if (loginValido) {
                // Las credenciales son válidas: abrir el panel "EdicionDeDatos"
                // NOTA: Aquí se debe conectar con la interfaz de usuario, por ahora se instancia simplemente.
                EdicionDeDatos edicionPanel = new EdicionDeDatos();
                edicionPanel.setVisible(true);
            } else {
                // Credenciales incorrectas: mostrar mensaje de error en un modal.
                JOptionPane.showMessageDialog(null, 
                    "Nombre de usuario o contraseña incorrectos.", 
                    "Error de autenticación", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            // Manejo general de excepciones: se informa al usuario mediante un modal.
            JOptionPane.showMessageDialog(null, 
                "Ocurrió un error durante el inicio de sesión: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}