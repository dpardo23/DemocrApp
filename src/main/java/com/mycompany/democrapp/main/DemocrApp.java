package com.mycompany.democrapp.main;

import com.mycompany.democrapp.controller.LoginController;
import com.mycompany.democrapp.model.EdicionPartido;

public class DemocrApp {

    public static void main(String[] args) {
        new EdicionPartido().setVisible(true);
        // Crear una instancia del controlador de login
        LoginController loginController = new LoginController();

        // Simulación de entrada (temporal, para pruebas)
        String usuario = "admin"; // Reemplaza por datos reales para pruebas
        String contrasena = "12345"; // Reemplaza por datos reales para pruebas

        // Intentar iniciar sesión con los datos proporcionados
        loginController.iniciarSesion(usuario, contrasena);
        //objetoconexion.establecerconexion()
    }
}