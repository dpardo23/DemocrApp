package com.mycompany.democrapp.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.JOptionPane;

/**
 * Clase ValidarDatos: Contiene métodos estáticos para validar campos antes de
 * su inserción en la base de datos.
 */
public class ValidarDatos {

    /**
     * Valida que el identificador exista en la base de
     *
     * @param connection datos.
     * @param identificadorStr
     * @return
     */
    public static boolean validarIdentificador(Connection connection, String identificadorStr) {
        // Validar que el identificador tenga exactamente 5 dígitos numéricos
        if (!identificadorStr.matches("\\d{8}")) {
            JOptionPane.showMessageDialog(null, "Error: El identificador debe ser un número entero de exactamente 8 dígitos.");
            return false;
        }

        try {
            // Convertir a entero
            int identificador = Integer.parseInt(identificadorStr);

            // Consulta para verificar si el identificador ya existe en la base de datos
            String query = "SELECT Id_Partido FROM Partido_Politico WHERE Id_Partido = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, identificador);
                ResultSet resultSet = statement.executeQuery();

                // Si ya existe, mostrar error y devolver false
                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(null, "Error: El identificador ya existe en la base de datos.");
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: El identificador debe ser un número válido.");
            return false;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al validar el identificador: " + e.getMessage());
            return false;
        }

        // Si pasó todas las validaciones y no existe en la base de datos, permitir la inserción
        JOptionPane.showMessageDialog(null, "Identificador válido. Puede proceder con el registro.");
        return true;
    }

    /**
     * Valida que el departamento solo contenga caracteres alfabéticos y no
     * exceda 25 caracter
     *
     * @param departamentoStr
     * @return
     */
    public static int validarDepartamento(String departamentoStr) {
        try {
            // Verificar que la entrada solo contenga números entre 1 y 9
            if (!departamentoStr.matches("[1-9]")) {
                // Mostrar alerta si el número no está entre 1 y 9
                JOptionPane.showMessageDialog(
                        null,
                        "Error: El departamento debe ser un número entre 1 y 9.",
                        "Validación de Departamento",
                        JOptionPane.ERROR_MESSAGE
                );
                return -1; // Valor inválido
            }

            // Convertir el valor a entero
            int departamento = Integer.parseInt(departamentoStr);

            // Retornar el valor si es válido
            return departamento;

        } catch (NumberFormatException e) {
            // Mostrar una alerta si el formato no es válido
            JOptionPane.showMessageDialog(
                    null,
                    "Error: El departamento debe ser un número válido.",
                    "Formato Inválido",
                    JOptionPane.ERROR_MESSAGE
            );
            return -1; // Valor inválido
        }
    }

    /**
     * Valida que el nombre del partido solo contenga caracteres alfabéticos y
     * no exceda 25 caracteres.
     *
     * @param nombrePartido
     * @return
     */
    public static String validarNombrePartido(String nombrePartido) {
        try {
            // Validar que el nombre solo contenga caracteres alfabéticos
            if (!nombrePartido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error: El nombre del partido debe contener solo caracteres alfabéticos.",
                        "Validación de Nombre",
                        JOptionPane.ERROR_MESSAGE
                );
                return null; // Retornar nulo para indicar que no es válido
            }

            // Validar que el nombre no exceda los 25 caracteres
            if (nombrePartido.length() > 25) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error: El nombre del partido no puede exceder 25 caracteres.",
                        "Validación de Nombre",
                        JOptionPane.ERROR_MESSAGE
                );
                return null; // Retornar nulo para indicar que no es válido
            }
        } catch (Exception e) {
            // Mostrar alerta si ocurre una excepción inesperada
            JOptionPane.showMessageDialog(
                    null,
                    "Excepción inesperada: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return null; // Retornar nulo si ocurre un error
        }

        // Retornar el nombre válido si pasa todas las validaciones
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
            // Validar que la sigla solo contenga caracteres alfabéticos
            if (!sigla.matches("[a-zA-Z]+")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error: La sigla debe contener solo caracteres alfabéticos.",
                        "Validación de Sigla",
                        JOptionPane.ERROR_MESSAGE
                );
                return null; // Indicar que la sigla no es válida
            }

            // Validar que la sigla no exceda 10 caracteres
            if (sigla.length() > 10) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error: La sigla no puede exceder 10 caracteres.",
                        "Validación de Sigla",
                        JOptionPane.ERROR_MESSAGE
                );
                return null; // Indicar que la sigla no es válida
            }
        } catch (Exception e) {
            // Manejo de excepciones inesperadas
            JOptionPane.showMessageDialog(
                    null,
                    "Excepción inesperada: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return null; // Indicar que hubo un error inesperado
        }

        // Retornar la sigla válida si pasa todas las validaciones
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
            // Validar que el nombre solo contenga caracteres alfabéticos y espacios
            if (!nombreLider.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error: El nombre del líder debe contener solo caracteres alfabéticos.",
                        "Validación de Nombre del Líder",
                        JOptionPane.ERROR_MESSAGE
                );
                return null; // Indicar que el nombre no es válido
            }

            // Validar que el nombre no exceda 25 caracteres
            if (nombreLider.length() > 25) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error: El nombre del líder no puede exceder 25 caracteres.",
                        "Validación de Nombre del Líder",
                        JOptionPane.ERROR_MESSAGE
                );
                return null; // Indicar que el nombre no es válido
            }
        } catch (Exception e) {
            // Manejar excepciones inesperadas
            JOptionPane.showMessageDialog(
                    null,
                    "Excepción inesperada: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return null; // Indicar que hubo un error inesperado
        }

        // Retornar el nombre válido si pasa todas las validaciones
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
            // Validar que la ideología solo contenga caracteres alfabéticos
            if (!ideologia.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error: La ideología debe contener solo caracteres alfabéticos.",
                        "Validación de Ideología",
                        JOptionPane.ERROR_MESSAGE
                );
                return null; // Indicar que la ideología no es válida
            }

            // Validar que la ideología no exceda 100 caracteres
            if (ideologia.length() > 100) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error: La ideología no puede exceder 100 caracteres.",
                        "Validación de Ideología",
                        JOptionPane.ERROR_MESSAGE
                );
                return null; // Indicar que la ideología no es válida
            }
        } catch (Exception e) {
            // Manejo de excepciones inesperadas
            JOptionPane.showMessageDialog(
                    null,
                    "Excepción inesperada: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return null; // Indicar que hubo un error inesperado
        }

        // Retornar la ideología válida si pasa todas las validaciones
        return ideologia;
    }

    /**
     * Valida que el número de afiliados sea numérico y no exceda 10 dígitos.
     *
     * @param numAfiliadosStr
     * @return
     */
    public static int validarNumAfiliados(String numAfiliadosStr) {
        try {
            // Verificar que el número contenga solo dígitos
            if (!numAfiliadosStr.matches("\\d+")) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error: El número de afiliados debe ser un valor numérico.",
                        "Validación de Número de Afiliados",
                        JOptionPane.ERROR_MESSAGE
                );
                return -1; // Indicar que el número no es válido
            }

            // Convertir el número a entero
            int numAfiliados = Integer.parseInt(numAfiliadosStr);

            // Validar que el número no exceda 10 dígitos
            if (numAfiliadosStr.length() > 10) {
                JOptionPane.showMessageDialog(
                        null,
                        "Error: El número de afiliados no puede exceder 10 dígitos.",
                        "Validación de Número de Afiliados",
                        JOptionPane.ERROR_MESSAGE
                );
                return -1; // Indicar que el número no es válido
            }

            return numAfiliados; // Si es válido, devolver el número como entero

        } catch (NumberFormatException e) {
            // Mostrar mensaje de error si ocurre una excepción inesperada
            JOptionPane.showMessageDialog(
                    null,
                    "Error: Formato incorrecto del número de afiliados.",
                    "Error de Formato",
                    JOptionPane.ERROR_MESSAGE
            );
            return -1; // Indicar que hubo un error inesperado
        }
    }

    /**
     * Valida que el número de afiliados sea numérico y no exceda 10 dígitos.
     *
     * @param fecha
     * @return
     */
    public static boolean validarFecha(String fecha) {
        try {
            // Definir el formato esperado
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // Intentar parsear la fecha ingresada
            LocalDate parsedDate = LocalDate.parse(fecha, formatter);

            // Si llega aquí, la fecha es válida
            return true;
        } catch (DateTimeParseException e) {
            // Mostrar un mensaje de error si el formato o la fecha es inválida
            JOptionPane.showMessageDialog(
                    null,
                    "Error: La fecha debe ser válida, formato YYYY-MM-DD.",
                    "Validación de Fecha",
                    JOptionPane.ERROR_MESSAGE
            );
            return false; // Retornar false si la fecha es inválida
        }
    }

    /**
     * Método que utiliza el procedimiento almacenado para verificar duplicados
     * en la base de datos.
     *
     * @param nombrePartido Nombre del partido a validar.
     * @param sigla Sigla del partido a validar.
     * @param nombreLider Nombre del líder a validar.
     * @param parseInt
     * @param connection
     * @throws IllegalArgumentException Si se detecta un error o si los datos ya
     * existen.
     */
    public static boolean verificarDuplicados(String nombrePartido, String sigla, String nombreLider, Connection connection) {
        String query = "{CALL VerificarDatosDuplicados(?, ?, ?)}";

        try (CallableStatement cs = connection.prepareCall(query)) {
            // Configurar los parámetros del procedimiento almacenado
            cs.setString(1, nombrePartido);
            cs.setString(2, sigla);
            cs.setString(3, nombreLider);

            // Ejecutar el procedimiento almacenado
            cs.execute();
            return false; // Si no hay excepciones, significa que no hay duplicados

        } catch (SQLException e) {
            // Verificar si el mensaje contiene información sobre duplicados
            if (e.getMessage().contains("Los datos ingresados ya existen")) {
                return true; // Retornar true si hay duplicados
            } else {
                // Manejar otros errores inesperados
                JOptionPane.showMessageDialog(null, "❌ Error inesperado al verificar duplicados: " + e.getMessage());
                return true; // Asumimos que ocurrió un problema, para evitar proceder
            }
        }
    }
}
