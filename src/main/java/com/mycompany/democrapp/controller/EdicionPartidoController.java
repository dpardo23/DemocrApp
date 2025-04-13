package com.mycompany.democrapp.controller;

import com.mycompany.democrapp.model.ConexionSQL;
import com.mycompany.democrapp.model.EdicionPartido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EdicionPartidoController {
    public List<EdicionPartido> getPartidos() {
        List<EdicionPartido> partidos = new ArrayList<>();
        String query = "EXEC GetPartidoPoliticos"; // SQL Server usa EXEC para procedimientos almacenados

        try (Connection connection = ConexionSQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("Id_Partido");
                String nombre = resultSet.getString("Nombre_partido");
                partidos.add(new EdicionPartido(id, nombre));
            }
        } catch (Exception e) {
            System.err.println("Error al obtener partidos: " + e.getMessage());
        }
        return partidos;
    }
}