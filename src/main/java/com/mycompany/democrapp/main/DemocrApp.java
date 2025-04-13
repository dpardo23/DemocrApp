package com.mycompany.democrapp.main;

import com.mycompany.democrapp.controller.RegistroDePartidosController;
import com.mycompany.democrapp.model.ConexionSQL;
import com.mycompany.democrapp.view.RegistroDePartidos;

public class DemocrApp {
    public static void main(String[] args) {
        
        // Crear una instancia del modelo (se conecta con la base de datos)
        ConexionSQL modelo = new ConexionSQL();

        // Crear una instancia de la vista (formulario que verá el usuario)
        RegistroDePartidos vista = new RegistroDePartidos();

        // Crear una instancia del controlador (conecta la vista y el modelo)
        RegistroDePartidosController controlador = new RegistroDePartidosController(modelo, vista);

        // Llamar al método que inicia la interfaz gráfica
        controlador.iniciar();
    }
}