// Paquete principal que contiene la clase para manejar la conexión a la base de datos
package com.mycompany.democrapp.model;

// Importamos las clases necesarias para trabajar con la base de datos
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Clase que se encarga de conectar con la base de datos y guardar información
public class ConexionSQL {

    // URL para conectarse a la base de datos, junto con usuario y contraseña
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=FormularioDB;encrypt=false";
    private static final String USER = "Ciudadano";
    private static final String PASSWORD = "UMSS2025";

    // Método privado que establece y devuelve la conexión a la base de datos
    Connection conectar() {
        Connection conexion = null; // Inicializamos la conexión como null
        try {
            // Tratamos de conectarnos usando los datos especificados (URL, usuario y contraseña)
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            // Si hay un error al conectarse, lo mostramos en la consola
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return conexion; // Retornamos la conexión (puede ser null si falló)
    }
    
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
    
    
    // Método público que guarda un equipo en la tabla "EquipoDeFutbol" de la base de datos
    public void guardarPartido(int idPartido, String nombrePartido, String sigla, String lider, String ideologia, int nAfiliados, int departamento) {
        // Query para insertar datos en la tabla
        String query = "INSERT INTO Partido_Politico (Id_Partido, Departamento_Id_departamento, Nombre_partido, Sigla, Lider, Ideologia, NAfiliados) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        // Usamos un bloque try-with-resources para asegurarnos de cerrar la conexión y el PreparedStatement automáticamente
        try (Connection conexion = conectar(); // Obtenemos la conexión a la base de datos
             PreparedStatement ps = conexion.prepareStatement(query)) { // Preparamos la consulta
            
            // Asignamos los valores a los placeholders (?) en el query
           ps.setInt(1, idPartido);
            ps.setInt(2, departamento);
            ps.setString(3, nombrePartido);
            ps.setString(4, sigla);
            ps.setString(5, lider);
            ps.setString(6, ideologia);
            ps.setInt(7, nAfiliados);

            // Ejecutamos la consulta para guardar los datos en la base de datos
            ps.executeUpdate();
            System.out.println("Datos guardados correctamente.");
        
        } catch (SQLException e) {
            // Si hay un error al guardar los datos, lo mostramos en la consola
            System.err.println("Error al guardar en la base de datos: " + e.getMessage());
        }
    }

}
