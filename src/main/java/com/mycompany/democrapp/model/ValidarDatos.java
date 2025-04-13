package com.mycompany.democrapp.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase ValidarDatos: Contiene métodos estáticos para validar campos antes de
 * su inserción en la base de datos.
 */
public class ValidarDatos {

    /**
     * Valida que el nombre del partido solo contenga caracteres alfabéticos y
     * no exceda 25 caracteres.
     *
     * @param nombrePartido
     * @return
     */
    public static String validarNombrePartido(String nombrePartido) {
        try {
            if (!nombrePartido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                return "Error: El nombre del partido debe contener solo caracteres alfabéticos.";
            }
            if (nombrePartido.length() > 25) {
                return "Error: El nombre del partido no puede exceder 25 caracteres.";
            }
        } catch (Exception e) {
            return "Excepción inesperada: " + e.getMessage();
        }
        return nombrePartido;
    }

    /**
     * Valida que la sigla solo contenga caracteres alfabéticos y no exceda 10
     * caracteres.
     *
     * @param sigla
     * @return
     */
    public static String validarSigla(String sigla) {
        try {
            if (!sigla.matches("[a-zA-Z]+")) {
                return "Error: La sigla debe contener solo caracteres alfabéticos.";
            }
            if (sigla.length() > 10) {
                return "Error: La sigla no puede exceder 10 caracteres.";
            }
        } catch (Exception e) {
            return "Excepción inesperada: " + e.getMessage();
        }
        return sigla;
    }

    /**
     * Valida que el nombre del líder solo contenga caracteres alfabéticos y no
     * exceda 25 caracteres.
     *
     * @param nombreLider
     * @return
     */
    public static String validarNombreLider(String nombreLider) {
        try {
            if (!nombreLider.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                return "Error: El nombre del líder debe contener solo caracteres alfabéticos.";
            }
            if (nombreLider.length() > 25) {
                return "Error: El nombre del líder no puede exceder 25 caracteres.";
            }
        } catch (Exception e) {
            return "Excepción inesperada: " + e.getMessage();
        }
        return nombreLider;
    }

    /**
     * Valida que la ideología solo contenga caracteres alfabéticos y no exceda
     * 100 caracteres.
     *
     * @param ideologia
     * @return
     */
    public static String validarIdeologia(String ideologia) {
        try {
            if (!ideologia.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                return "Error: La ideología debe contener solo caracteres alfabéticos.";
            }
            if (ideologia.length() > 100) {
                return "Error: La ideología no puede exceder 100 caracteres.";
            }
        } catch (Exception e) {
            return "Excepción inesperada: " + e.getMessage();
        }
        return ideologia;
    }

    /**
     * Valida que el número de afiliados sea numérico y no exceda 10 dígitos.
     *
     * @param numAfiliadosStr
     * @return
     */
    public static int validarNumAfiliados(String numAfiliadosStr) throws IllegalArgumentException {
    try {
        // Verificar que el número contenga solo dígitos
        if (!numAfiliadosStr.matches("\\d+")) {
            throw new IllegalArgumentException("El número de afiliados debe ser un valor numérico.");
        }
        
        // Convertir el número a entero
        int numAfiliados = Integer.parseInt(numAfiliadosStr);

        // Verificar que el número no exceda 10 dígitos
        if (numAfiliadosStr.length() > 10) {
            throw new IllegalArgumentException("El número de afiliados no puede exceder 10 dígitos.");
        }

        return numAfiliados; // Si es válido, devolver el número como entero
    } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Formato incorrecto del número de afiliados.", e);
    }
}

    /**
     * Valida que el departamento solo contenga caracteres alfabéticos y no
     * exceda 25 caracter
     *
     * @param departamentoStr
     * @return
     */
    public static int validarDepartamento(String departamentoStr) throws IllegalArgumentException {
    try {
        // Verificar que la entrada solo contenga números entre 1 y 9
        if (!departamentoStr.matches("[1-9]")) {
            throw new IllegalArgumentException("Error: El departamento debe ser un número entre 1 y 9.");
        }

        // Convertir el valor a entero
        int departamento = Integer.parseInt(departamentoStr);

        // Retornar el valor si es válido
        return departamento;
    } catch (NumberFormatException e) {
        // Lanzar una excepción si el formato no es un número válido
        throw new IllegalArgumentException("Error: El departamento debe ser un número entre 1 y 9.", e);
    }
}

    /**
     * Valida que el identificador exista en la base de
     *
     * @param connection datos.
     * @param identificador
     * @return
     */
    public static String validarIdentificador(Connection connection, int identificador) {
        String query = "SELECT Id_Partido FROM Partido_Politico WHERE Id_Partido = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, identificador);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return "Error: El identificador no coincide con ningún registro en la base de datos.";
            }
        } catch (Exception e) {
            return "Error al validar el identificador: " + e.getMessage();
        }
        return "Identificador válido.";
    }

    /**
     * Método que utiliza el procedimiento almacenado para verificar duplicados
     * en la base de datos.
     *
     * @param nombrePartido Nombre del partido a validar.
     * @param sigla Sigla del partido a validar.
     * @param nombreLider Nombre del líder a validar.
     * @param idPartido Identificador del partido a validar.
     * @param conexionDB Clase de conexión a la base de datos.
     * @throws IllegalArgumentException Si se detecta un error o si los datos ya
     * existen.
     */
    public static void verificarDuplicados(String nombrePartido, String sigla, String nombreLider, int idPartido, ConexionSQL conexionDB) throws IllegalArgumentException {
        Connection conn = null;
        CallableStatement cs = null;

        try {
            // Obtenemos la conexión desde la clase ConexionDB
            conn = conexionDB.conectar();

            // Preparar la llamada al procedimiento almacenado
            cs = conn.prepareCall("{CALL VerificarDuplicados(?, ?, ?, ?)}");
            cs.setString(1, nombrePartido); // Asignamos el nombre del partido como parámetro
            cs.setString(2, sigla);         // Asignamos la sigla como parámetro
            cs.setString(3, nombreLider);  // Asignamos el nombre del líder como parámetro
            cs.setInt(4, idPartido);       // Asignamos el identificador como parámetro

            // Ejecutamos el procedimiento almacenado
            cs.execute();
        } catch (SQLException e) {
            // Si el procedimiento almacenado devuelve un error, lanzamos una excepción con un mensaje claro
            throw new IllegalArgumentException("Error al validar duplicados en la base de datos: " + e.getMessage());
        } finally {
            // Cerramos los recursos para evitar fugas de memoria
            try {
                if (cs != null) {
                    cs.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar CallableStatement: " + e.getMessage());
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
