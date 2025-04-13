/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.democrapp.model;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author ASUS
 */
public class ModeloGraficas {
    public ArrayList<String> MostrarDepartamentos() {
        ArrayList<String> departamento = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            // Obtener la conexión a la base de datos
            con = ConexionSQL.getConnection();
            // Preparar la llamada al procedimiento almacenado.
            cs = con.prepareCall("{call dbo.ObtenerDepartamento}");

            // Ejecutar el procedimiento almacenado
            rs = cs.executeQuery();

            // Si se retorna un registro, las credenciales son válidas
            while(rs.next()){
                departamento.add(rs.getString("Nombre_departamento"));
            }
        } catch (SQLException ex) {
            System.err.println("Error al validar usuario: " + ex.getMessage());
        } finally {
            // Cerrar recursos en un bloque finally para evitar fugas de recursos
            try {
                if(rs != null) rs.close();
                if(cs != null) cs.close();
                if(con != null) con.close();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar recursos: " + ex.getMessage());
            }
        }
        return departamento;
    }  
    public ArrayList<String> MostrarPartidos() {
        ArrayList<String> Partido = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            // Obtener la conexión a la base de datos
            con = ConexionSQL.getConnection();
            // Preparar la llamada al procedimiento almacenado.
            cs = con.prepareCall("{call dbo.ObtenerNombresPartidos}");

            // Ejecutar el procedimiento almacenado
            rs = cs.executeQuery();

            // Si se retorna un registro, las credenciales son válidas
            while(rs.next()){
                Partido.add(rs.getString("Nombre_partido"));
            }
        } catch (SQLException ex) {
            System.err.println("Error al validar usuario: " + ex.getMessage());
        } finally {
            // Cerrar recursos en un bloque finally para evitar fugas de recursos
            try {
                if(rs != null) rs.close();
                if(cs != null) cs.close();
                if(con != null) con.close();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar recursos: " + ex.getMessage());
            }
        }
        return Partido;
    }
    
    public class prueba{
        private String provincia;
        private int popularidad;
        
        public prueba(String provincia, int popularidad){
            this.provincia = provincia;
            this.popularidad = popularidad;
        }
        
        public int getPopularidad() {
            return popularidad;
        }
        public void setPopularidad(int popularidad) {
            this.popularidad = popularidad;
        }    

        public String getProvincia() {
            return provincia;
        }
        public void setProvincia(String provincia) {
            this.provincia = provincia;
        }
    }
    
    
    public ArrayList<prueba> Obtenervalores(String partido,String departamento) {
        ArrayList<prueba> valores = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            // Obtener la conexión a la base de datos
            con = ConexionSQL.getConnection();
            // Preparar la llamada al procedimiento almacenado.
            cs = con.prepareCall("{call dbo.ObtenerValores(?, ?)}");
            cs.setString(1, partido);
            cs.setString(2, departamento);

            // Ejecutar el procedimiento almacenado
            rs = cs.executeQuery();

            // Si se retorna un registro, las credenciales son válidas
            while(rs.next()){
                valores.add(new prueba(rs.getString("provincia"),rs.getInt("popularidad")));
            }
        } catch (SQLException ex) {
            System.err.println("Error al validar usuario: " + ex.getMessage());
        } finally {
            // Cerrar recursos en un bloque finally para evitar fugas de recursos
            try {
                if(rs != null) rs.close();
                if(cs != null) cs.close();
                if(con != null) con.close();
            } catch (SQLException ex) {
                System.err.println("Error al cerrar recursos: " + ex.getMessage());
            }
        }
        return valores;
    }
    
        /*ArrayList<String> test = new ArrayList<>();
        ArrayList<String> test2 = new ArrayList<>();
        ArrayList<com.mycompany.democrapp.main.prueba.prueba2> test3 = new ArrayList<>();
        /*test=MostrarPartidos();
        test2=MostrarDepartamentos();
        System.out.println(test);
        System.out.println(test2);
        test3=Obtenervalores();
        DefaultPieDataset datos=new DefaultPieDataset();
        for(com.mycompany.democrapp.main.prueba.prueba2 prueba2:test3){
            datos.setValue(prueba2.getProvincia(),prueba2.getPopularidad());
        }
        
        JFreeChart grafico_circular=ChartFactory.createPieChart("Popularidad por Provincia", datos,false,true,false);
        ChartPanel panel=new ChartPanel(grafico_circular);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(400,200));
        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(panel,BorderLayout.NORTH);
        pack();
        repaint();*/
}
