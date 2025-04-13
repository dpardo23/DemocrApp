package com.mycompany.democrapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionSQL {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=Partidos_Politicos;encrypt=false;trustServerCertificate=true";
    private static final String USER = "Ciudadano";
    private static final String PASSWORD = "UMSS2025";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el controlador JDBC.", e);
        }
    }
}