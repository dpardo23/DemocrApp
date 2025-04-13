package com.mycompany.democrapp.main;

import com.mycompany.democrapp.controller.EdicionPartidoController;
import com.mycompany.democrapp.model.ConexionSQL;
import com.mycompany.democrapp.view.EdicionDeDatos;

public class DemocrApp {
    public static void main(String[] args) {
        
        // Crear una instancia del modelo (se conecta con la base de datos)
        ConexionSQL modelo = new ConexionSQL();

        // Crear una instancia de la vista (formulario que verá el usuario)
        EdicionDeDatos vista = new EdicionDeDatos();

        // Crear una instancia del controlador (conecta la vista y el modelo)
        EdicionPartidoController controlador = new EdicionPartidoController(modelo, vista);

        // Llamar al método que inicia la interfaz gráfica
        controlador.iniciar();
    }
}