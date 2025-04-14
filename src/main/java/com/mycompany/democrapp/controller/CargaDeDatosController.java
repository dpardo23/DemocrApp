package com.mycompany.democrapp.controller;

import com.mycompany.democrapp.model.ValidarDatos;
import com.mycompany.democrapp.view.EdicionDeDatos;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public final class CargaDeDatosController {

    private final EdicionDeDatos vista;
    private final DefaultTableModel modeloPartidos;

    public CargaDeDatosController(EdicionDeDatos vista) {
        this.vista = vista;

        modeloPartidos = new DefaultTableModel(new String[]{"ID", "Partido"}, 0);
        vista.jTable1.setModel(modeloPartidos);

        cargarPartidos();
    }

    public void cargarPartidos() {
        modeloPartidos.setRowCount(0);
        ArrayList<String[]> partidos = ValidarDatos.obtenerPartidoPoliticos();
        for (String[] partido : partidos) {
            modeloPartidos.addRow(partido);
        }
    }
    
    // Método para iniciar la interfaz gráfica
    public void iniciar() {
        // Mostrar directamente el JFrame si vista es un JFrame
        vista.setVisible(true);
    }
}