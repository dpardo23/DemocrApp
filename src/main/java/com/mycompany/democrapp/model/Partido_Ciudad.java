/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.democrapp.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class Partido_Ciudad {
    public ArrayList<String> MostrarPartidos() {
        ArrayList<String> Partido = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            // Obtener la conexión a la base de datos
            con = ConexionSQL.getConnection();
            // Preparar la llamada al procedimiento almacenado.
            cs = con.prepareCall("{call Partido_ciudad}");

            // Ejecutar el procedimiento almacenado
            rs = cs.executeQuery();

            // Si se retorna un registro, las credenciales son válidas
            if(rs.next()){
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
}
