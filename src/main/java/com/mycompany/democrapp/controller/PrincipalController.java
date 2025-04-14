package com.mycompany.democrapp.controller;

import com.mycompany.democrapp.view.Principal;
import com.mycompany.democrapp.view.RegistroDePartidos;
import com.mycompany.democrapp.view.EdicionDeDatos;

public class PrincipalController {

    private final Principal vista;

    // Constructor
    public PrincipalController(Principal vista) {
        this.vista = vista;
        initController();
    }

    // Método para inicializar acciones de los botones
    private void initController() {
        // Acción para el botón "Registro de Datos"
        vista.btnRegistroDeDatos.addActionListener(e -> abrirRegistroDePartidos());

        // Acción para el botón "Edición de Datos"
        vista.btnEdicionDeDatos.addActionListener(e -> abrirEdicionDeDatos());
    }

    // Método para abrir el frame "Registro de Partidos"
    private void abrirRegistroDePartidos() {
        RegistroDePartidos registroFrame = new RegistroDePartidos();
        registroFrame.setVisible(true);
    }

    // Método para abrir el frame "Edición de Datos"
    private void abrirEdicionDeDatos() {
        EdicionDeDatos edicionFrame = new EdicionDeDatos();
        edicionFrame.setVisible(true);
    }
}