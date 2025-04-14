package com.mycompany.democrapp.main;

import com.mycompany.democrapp.controller.PrincipalController;
import com.mycompany.democrapp.view.Principal;

public class DemocrApp {
    public static void main(String[] args) {
        // Crear la instancia de la vista principal
        Principal framePrincipal = new Principal();

        // Crear el controlador para gestionar las acciones de los botones
        PrincipalController principalController = new PrincipalController(framePrincipal);

        // Mostrar la ventana principal
        framePrincipal.setVisible(true);
    }
}