/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.democrapp.main;

import com.mycompany.democrapp.controller.GraficasController;
import com.mycompany.democrapp.view.VistaGraficas;
import com.mycompany.democrapp.model.ModeloGraficas;

/**
 *
 * @author ASUS
 */
public class Graficas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            VistaGraficas Vista=new VistaGraficas();
            ModeloGraficas modelo=new ModeloGraficas();
            //Vista.setVisible(true);
            GraficasController GraficasController=new GraficasController(modelo,Vista);
            Vista.setVisible(true);
    } 
    
}
