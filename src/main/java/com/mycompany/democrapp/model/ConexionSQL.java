package com.mycompany.democrapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que maneja la conexión a la base de datos SQL Server.
 */
public class ConexionSQL {
    // URL de conexión. Cambia "NombreDeTuBaseDeDatos" por el nombre real de tu base de datos,
    // y ajusta el host/puerto según corresponda.
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=NombreDeTuBaseDeDatos";
    private static final String USER = "tuUsuario";        // Usuario de la base de datos
    private static final String PASSWORD = "tuPassword";     // Contraseña de la base de datos
    
    /*private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=DB_PArtidosPoliticos";
    private static final String USER = "Ciudadano";
    private static final String PASSWORD = "UMSS2025"; */ 
    /** //Conexion a la base de datos
     * Retorna una conexión activa a la base de datos.
     * 
     * @return Connection objeto de conexión.
     * @throws SQLException Si ocurre un error al conectar.
     */
    public static Connection getConnection() throws SQLException {
        Connection con = null;
        try {
            // Cargar el driver de SQL Server (puede no ser necesario en versiones modernas de Java)
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("No se encontró el driver de SQL Server: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al conectarse a la base de datos: " + e.getMessage());
            throw e;
        }
        return con;
    }
}