package com.mycompany.democrapp.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase DAO que contiene las operaciones de la base de datos para el Usuario.
 */
public class UsuarioDAO {

    /**
     * Valida el usuario haciendo uso del procedimiento almacenado "usp_validarUsuario".
     * Se asume que dicho procedimiento recibe dos parámetros: 
     *   @usuario (VARCHAR) y @contrasena (VARCHAR), y retorna un registro si las credenciales son correctas.
     * 
     * @param usuario Nombre de usuario.
     * @param contrasena Contraseña del usuario.
     * @return true si las credenciales son correctas; false en caso contrario.
     */
    public boolean validarUsuario(String usuario, String contrasena) {
        boolean valido = false;
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            // Obtener la conexión a la base de datos
            con = ConexionSQL.getConnection();
            // Preparar la llamada al procedimiento almacenado.
            cs = con.prepareCall("{call usp_validarUsuario(?, ?)}");
            cs.setString(1, usuario);
            cs.setString(2, contrasena);

            // Ejecutar el procedimiento almacenado
            rs = cs.executeQuery();

            // Si se retorna un registro, las credenciales son válidas
            if(rs.next()){
                valido = true;
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
        return valido;
    }    
}