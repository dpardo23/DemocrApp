package com.mycompany.democrapp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsercionDatos {

    public boolean insertarPartido(int idPartido, int departamento, String nombrePartido, String sigla, String nombreLider, String ideologia, int numAfiliados,String fechaFundacion) {
        String query = "INSERT INTO Partido_Politico (Id_Partido, Departamento_Id_departamento, Nombre_partido, Sigla, Lider, Ideologia, NAfiliados, Fecha_fundacion) VALUES (?, ?, ?, ?, ?, ?, ?,?)";

        try (Connection connection = ConexionSQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, idPartido);
            statement.setInt(2, departamento);
            statement.setString(3, nombrePartido);
            statement.setString(4, sigla);
            statement.setString(5, nombreLider);
            statement.setString(6, ideologia);
            statement.setInt(7, numAfiliados);
            statement.setString(8, fechaFundacion);

            int filasInsertadas = statement.executeUpdate();
            return filasInsertadas > 0; // Retorna true si la inserción fue exitosa

        } catch (SQLException e) {
            System.err.println("Error al insertar el partido: " + e.getMessage());
            return false;
        }
    }
}