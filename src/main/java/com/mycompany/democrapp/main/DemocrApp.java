package com.mycompany.democrapp.main;

import com.mycompany.democrapp.controller.RegistroEdicionController;
import com.mycompany.democrapp.model.ConexionSQL;
import com.mycompany.democrapp.view.RegistroEdicionPartidos;

public class DemocrApp {
    public static void main(String[] args) {
        ConexionSQL modelo = new ConexionSQL();
        RegistroEdicionPartidos  vista = new RegistroEdicionPartidos();
        RegistroEdicionController controlador = new RegistroEdicionController(modelo, vista);
        controlador.iniciar();
    }
}