package com.mycompany.democrapp.model;

import java.util.List;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Clase ValidarDatos: Contiene métodos estáticos para validar campos antes de
 * su inserción en la base de datos.
 */
public class ValidarDatos {

    /**
     * Método para validar el identificador de un partido político.
     *
     * Este método realiza las siguientes acciones: 1. Verifica si el
     * identificador tiene exactamente 8 caracteres numéricos, lo que garantiza
     * el formato esperado. 2. Convierte el identificador proporcionado como
     * cadena (`String`) a un tipo entero (`int`) para asegurar compatibilidad
     * con los datos de la base de datos. 3. Consulta en la tabla
     * `Partido_Politico` de la base de datos si el identificador ya existe. 4.
     * Si el identificador ya existe: - Muestra un mensaje de error al usuario
     * indicando que el identificador no puede ser utilizado. - Devuelve `false`
     * para indicar que la validación ha fallado. 5. Si el identificador no
     * existe: - Muestra un mensaje de éxito indicando que el identificador es
     * válido. - Devuelve `true` para confirmar que la validación fue
     * satisfactoria.
     *
     * Este método permite garantizar que los identificadores sean únicos en el
     * sistema, evitando problemas de duplicación y manteniendo la integridad de
     * los datos en la base de datos.
     *
     * @param connection La conexión activa a la base de datos, utilizada para
     * ejecutar la consulta SQL.
     * @param identificadorStr El identificador del partido político en formato
     * de cadena, capturado desde la interfaz de usuario.
     * @return `true` si el identificador es válido y no existe en la base de
     * datos, `false` en caso contrario.
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
     * Método para validar el número de un departamento.
     *
     * Este método realiza las siguientes verificaciones: 1. Comprueba que el
     * valor ingresado en `departamentoStr` solo contenga números entre 1 y 9. -
     * Se usa `matches("[1-9]")` para validar que el valor esté dentro del rango
     * permitido. - Si el valor ingresado no está entre 1 y 9, se muestra una
     * alerta con `JOptionPane` notificando al usuario del error. - En caso de
     * error, el método devuelve `-1` indicando que la validación ha fallado. 2.
     * Convierte `departamentoStr` a un número entero (`int`) para garantizar
     * compatibilidad con futuras operaciones. - Si la conversión es exitosa, se
     * retorna el número de departamento validado. 3. Manejo de excepciones: -
     * Si ocurre un `NumberFormatException` (por ejemplo, si el usuario ingresa
     * texto en lugar de un número), se muestra un mensaje de error indicando
     * que el formato no es válido. - En este caso, también se retorna `-1` para
     * indicar que la validación falló.
     *
     * Este método es útil para garantizar que los números de departamento sean
     * correctos antes de realizar operaciones en la base de datos o en otros
     * procesos dentro de la aplicación.
     *
     * @param departamentoStr Número de departamento ingresado como cadena de
     * texto.
     * @return Un número entero válido si la validación es correcta, `-1` si el
     * valor no cumple los requisitos.
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
     * Método para validar el nombre de un partido político.
     *
     * Este método realiza las siguientes verificaciones: 1. Comprueba que el
     * nombre proporcionado solo contenga caracteres alfabéticos, incluyendo
     * letras con acentos y espacios. - Utiliza la expresión regular
     * `[a-zA-ZáéíóúÁÉÍÓÚÑ ]+` para validar que el nombre solo tenga letras
     * válidas. - Si el nombre contiene caracteres inválidos, muestra un mensaje
     * de error al usuario mediante `JOptionPane`. - Retorna `null` para indicar
     * que el nombre no cumple con los requisitos. 2. Verifica que el nombre no
     * exceda los 25 caracteres. - Si el nombre es demasiado largo, muestra un
     * mensaje de error mediante `JOptionPane`. - Retorna `null` para indicar
     * que el nombre no es válido. 3. Manejo de excepciones: - Si ocurre una
     * excepción inesperada, muestra un mensaje de error con los detalles de la
     * excepción. - Retorna `null` para indicar que hubo un fallo en la
     * validación. 4. Si todas las validaciones son exitosas, retorna el nombre
     * válido como una cadena (`String`).
     *
     * Este método garantiza que los nombres de los partidos políticos cumplan
     * con los estándares establecidos, mejorando la calidad y consistencia de
     * los datos en el sistema.
     *
     * @param nombrePartido Nombre del partido político en formato de cadena de
     * texto.
     * @return El nombre validado si cumple los requisitos, o `null` si no es
     * válido.
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
     * Método para validar la sigla de un partido político.
     *
     * Este método realiza las siguientes validaciones: 1. Comprueba que la
     * sigla solo contenga caracteres alfabéticos. - Utiliza la expresión
     * regular `[a-zA-Z]+` para asegurarse de que no haya números, caracteres
     * especiales ni espacios. - Si la sigla contiene caracteres inválidos,
     * muestra un mensaje de error al usuario mediante `JOptionPane`. - Retorna
     * `null` para indicar que la sigla no es válida. 2. Verifica que la
     * longitud de la sigla no exceda los 10 caracteres. - Si la sigla es
     * demasiado larga, muestra un mensaje de error al usuario mediante
     * `JOptionPane`. - Retorna `null` para indicar que la sigla no es válida.
     * 3. Manejo de excepciones: - Si ocurre una excepción inesperada durante la
     * validación, muestra un mensaje de error con los detalles de la excepción.
     * - Retorna `null` para indicar que hubo un error inesperado. 4. Si todas
     * las validaciones son exitosas, retorna la sigla válida como una cadena
     * (`String`).
     *
     * Este método garantiza que las siglas de los partidos políticos cumplan
     * con los estándares establecidos, mejorando la calidad y consistencia de
     * los datos en el sistema.
     *
     * @param sigla La sigla del partido político en formato de cadena de texto.
     * @return La sigla validada si cumple los requisitos, o `null` si no es
     * válida.
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
     * Método para validar el nombre del líder de un partido político.
     *
     * Este método realiza las siguientes validaciones: 1. Comprueba que el
     * nombre del líder solo contenga caracteres alfabéticos, incluyendo letras
     * con acentos y espacios. - Utiliza la expresión regular
     * `[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+` para validar que el nombre no contenga números
     * ni caracteres especiales. - Si el nombre contiene caracteres inválidos,
     * muestra un mensaje de error al usuario mediante `JOptionPane`. - Retorna
     * `null` para indicar que el nombre no es válido. 2. Verifica que la
     * longitud del nombre no exceda los 25 caracteres. - Si el nombre es
     * demasiado largo, muestra un mensaje de error mediante `JOptionPane`. -
     * Retorna `null` para indicar que el nombre no es válido. 3. Manejo de
     * excepciones: - Si ocurre una excepción inesperada durante la validación,
     * muestra un mensaje de error con los detalles de la excepción. - Retorna
     * `null` para indicar que hubo un error inesperado. 4. Si todas las
     * validaciones son exitosas, retorna el nombre válido como una cadena
     * (`String`).
     *
     * Este método garantiza que los nombres de los líderes cumplan con los
     * estándares establecidos, mejorando la calidad y consistencia de los datos
     * en el sistema.
     *
     * @param nombreLider Nombre del líder del partido político en formato de
     * cadena de texto.
     * @return El nombre validado si cumple los requisitos, o `null` si no es
     * válido.
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
     * Método para validar la ideología de un partido político.
     *
     * Este método realiza las siguientes validaciones: 1. Comprueba que la
     * ideología solo contenga caracteres alfabéticos, incluyendo letras con
     * acentos y espacios. - Utiliza la expresión regular `[a-zA-ZáéíóúÁÉÍÓÚñÑ
     * ]+` para garantizar que no existan números ni caracteres especiales. - Si
     * la ideología contiene caracteres inválidos, muestra un mensaje de error
     * al usuario mediante `JOptionPane`. - Retorna `null` para indicar que la
     * ideología no es válida. 2. Verifica que la longitud de la ideología no
     * exceda los 100 caracteres. - Si la ideología es demasiado larga, muestra
     * un mensaje de error al usuario mediante `JOptionPane`. - Retorna `null`
     * para indicar que la ideología no es válida. 3. Manejo de excepciones: -
     * Si ocurre una excepción inesperada durante la validación, muestra un
     * mensaje de error con los detalles de la excepción. - Retorna `null` para
     * indicar que hubo un fallo inesperado. 4. Si todas las validaciones son
     * exitosas, retorna la ideología válida como una cadena (`String`).
     *
     * Este método asegura que las ideologías ingresadas cumplan con los
     * estándares establecidos, lo que mejora la calidad y consistencia de los
     * datos en el sistema.
     *
     * @param ideologia La ideología del partido político en formato de cadena
     * de texto.
     * @return La ideología validada si cumple los requisitos, o `null` si no es
     * válida.
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
     * Método para validar el número de afiliados de un partido político.
     *
     * Este método realiza las siguientes validaciones: 1. Verifica que el valor
     * proporcionado (`numAfiliadosStr`) contenga solo dígitos. - Utiliza la
     * expresión regular `\\d+` para asegurarse de que no haya caracteres no
     * numéricos. - Si contiene caracteres inválidos, muestra un mensaje de
     * error al usuario mediante `JOptionPane`. - Retorna `-1` para indicar que
     * el número no es válido. 2. Convierte la cadena `numAfiliadosStr` a un
     * número entero (`int`). - Valida también que el número no exceda los 10
     * dígitos. - Si supera la longitud permitida, muestra un mensaje de error
     * mediante `JOptionPane`. - Retorna `-1` para indicar que el número no es
     * válido. 3. Manejo de excepciones: - Si ocurre una excepción inesperada,
     * como un `NumberFormatException` durante la conversión a entero, muestra
     * un mensaje de error con los detalles de la excepción al usuario mediante
     * `JOptionPane`. - Retorna `-1` para indicar que ocurrió un error
     * inesperado. 4. Si todas las validaciones son exitosas, devuelve el número
     * de afiliados como un valor entero (`int`).
     *
     * Este método garantiza que el número de afiliados sea un valor numérico
     * válido dentro de los límites establecidos, lo que mejora la calidad y
     * consistencia de los datos almacenados en el sistema.
     *
     * @param numAfiliadosStr Número de afiliados ingresado como cadena de
     * texto.
     * @return El número validado como entero si cumple con los requisitos, o
     * `-1` si no es válido.
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
     * Método para validar una fecha en formato `YYYY-MM-DD`.
     *
     * Este método realiza las siguientes acciones: 1. Define el formato
     * esperado de la fecha como `YYYY-MM-DD`. - Utiliza la clase
     * `DateTimeFormatter` para especificar el patrón de formato de la fecha. 2.
     * Intenta analizar (parsear) la fecha proporcionada (`fecha`) utilizando el
     * formato definido. - Si la fecha es válida, el método retorna `true`. 3.
     * Manejo de errores: - Si la fecha no cumple con el formato o es inválida,
     * se lanza una excepción `DateTimeParseException`. - En caso de excepción,
     * se muestra un mensaje de error al usuario mediante `JOptionPane`,
     * indicando que la fecha debe cumplir el formato `YYYY-MM-DD`. - Retorna
     * `false` para indicar que la fecha no es válida.
     *
     * Este método asegura que las fechas ingresadas por el usuario cumplan con
     * el formato estándar, lo que es útil para garantizar la compatibilidad con
     * operaciones de base de datos, cálculos temporales y otros procesos
     * relacionados.
     *
     * @param fecha La fecha ingresada como cadena de texto.
     * @return `true` si la fecha es válida y cumple con el formato, o `false`
     * si no lo es.
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
     * Método para verificar si los datos ingresados (nombre del partido, sigla
     * y nombre del líder) ya existen en la base de datos.
     *
     * Este método realiza las siguientes acciones: 1. Define una consulta para
     * llamar al procedimiento almacenado `VerificarDatosDuplicados` en la base
     * de datos. - El procedimiento almacenado recibe tres parámetros: nombre
     * del partido, sigla y nombre del líder. 2. Configura los parámetros
     * requeridos por el procedimiento almacenado. - Utiliza un objeto
     * `CallableStatement` para establecer los valores de los parámetros. 3.
     * Ejecuta el procedimiento almacenado utilizando la conexión proporcionada.
     * - Si el procedimiento se ejecuta correctamente (sin lanzar excepciones),
     * se asume que no hay datos duplicados en la base de datos. - Retorna
     * `false` para indicar que no se encontraron duplicados. 4. Manejo de
     * excepciones: - Si ocurre una excepción `SQLException`, verifica el
     * mensaje de la excepción. - Si el mensaje indica que los datos ya existen
     * en la base de datos, retorna `true`, indicando que los datos son
     * duplicados. - Si el mensaje no está relacionado con datos duplicados,
     * muestra un mensaje de error al usuario mediante `JOptionPane` indicando
     * un error inesperado. - Retorna `true` para evitar continuar el proceso en
     * caso de problemas inesperados.
     *
     * Este método permite garantizar que los datos ingresados sean únicos en el
     * sistema antes de proceder con operaciones adicionales, ayudando a
     * mantener la integridad y consistencia de la base de datos.
     *
     * @param nombrePartido Nombre del partido político a verificar.
     * @param sigla Sigla del partido político a verificar.
     * @param nombreLider Nombre del líder del partido político a verificar.
     * @param connection La conexión activa a la base de datos, utilizada para
     * ejecutar el procedimiento almacenado.
     * @return `true` si los datos son duplicados, o `false` si no hay
     * duplicados.
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
                return true;
            } else {
                // Manejar otros errores inesperados
                JOptionPane.showMessageDialog(null, "Error inesperado al verificar duplicados: " + e.getMessage());
                return true; // Asumimos que ocurrió un problema, para evitar proceder
            }
        }
    }

    /**
     * Método para obtener una lista de partidos políticos desde la base de
     * datos.
     *
     * Este método realiza las siguientes acciones: 1. Establece una conexión
     * con la base de datos utilizando la clase `ConexionSQL`. 2. Ejecuta el
     * procedimiento almacenado `GetPartidoPoliticos` para recuperar los datos
     * de los partidos políticos. - El procedimiento devuelve las columnas
     * `Id_Partido` y `Nombre_partido`. 3. Procesa el resultado devuelto por la
     * base de datos: - Recorre el conjunto de resultados (`ResultSet`) y extrae
     * los valores de `Id_Partido` y `Nombre_partido`. - Añade cada par de
     * valores (ID y nombre) como un arreglo de cadenas (`String[]`) a una
     * lista. 4. Manejo de excepciones: - Si ocurre una excepción durante la
     * conexión o ejecución de la consulta, imprime la traza del error
     * utilizando `e.printStackTrace()` para depuración. 5. Devuelve una lista
     * de arreglos de cadenas, donde cada arreglo contiene el identificador y el
     * nombre de un partido político.
     *
     * Este método facilita la extracción de datos relacionados con los partidos
     * políticos, permitiendo su uso en componentes como tablas o listas dentro
     * de la aplicación.
     *
     * @return Una lista de arreglos de cadenas (`List<String[]>`) que
     * representa a los partidos políticos en la base de datos.
     */
    public List<String[]> obtenerPartidosPoliticos() {
        List<String[]> partidosPoliticos = new ArrayList<>();
        try (Connection connection = ConexionSQL.getConnection()) {
            String query = "{CALL GetPartidoPoliticos}";
            try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    String idPartido = resultSet.getString("Id_Partido");
                    String nombrePartido = resultSet.getString("Nombre_partido");
                    partidosPoliticos.add(new String[]{idPartido, nombrePartido});
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return partidosPoliticos;
    }

    /**
     * Método para verificar si un identificador existe en la base de datos.
     *
     * Este método realiza las siguientes acciones: 1. Establece una conexión
     * con la base de datos utilizando la clase `ConexionSQL`. 2. Ejecuta el
     * procedimiento almacenado `VerificarPartidoPolitico`, que recibe como
     * parámetro el identificador (`idComparar`) a verificar. 3. Procesa el
     * resultado devuelto por la base de datos: - Recorre el conjunto de
     * resultados (`ResultSet`) y verifica si el identificador existe en la
     * tabla `Partido_Politico`. - Si el identificador existe (columna `Existe`
     * tiene valor mayor a 0), el método retorna `true`. - Si el identificador
     * no existe, el método retorna `false`. 4. Manejo de excepciones: - Si
     * ocurre una excepción durante la conexión o la ejecución de la consulta,
     * imprime la traza del error mediante `e.printStackTrace()` para
     * depuración. - El método captura cualquier error inesperado para evitar
     * que la aplicación falle. 5. Devuelve un valor booleano que indica si el
     * identificador existe en la base de datos o no.
     *
     * Este método es útil para validar la existencia de identificadores únicos
     * antes de realizar operaciones adicionales, asegurando la integridad y
     * consistencia de los datos en el sistema.
     *
     * @param idComparar El identificador a verificar en la base de datos.
     * @return `true` si el identificador existe en la base de datos, o `false`
     * si no existe.
     */
    public boolean verificarIdPartido(String idComparar) {
        boolean existe = false;

        try (Connection connection = ConexionSQL.getConnection()) {
            // Llamar al procedimiento almacenado
            String query = "{CALL VerificarPartidoPolitico(?)}";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, idComparar);

            ResultSet resultSet = statement.executeQuery();

            // Verificar el resultado
            if (resultSet.next()) {
                int count = resultSet.getInt("Existe");
                existe = (count > 0);
            }

            // Cerrar recursos
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return existe;
    }

    /**
     * Método para actualizar la información de un partido político en la base
     * de datos.
     *
     * Este método realiza las siguientes acciones: 1. Establece una conexión
     * con la base de datos utilizando la clase `ConexionSQL`. 2. Define una
     * consulta para llamar al procedimiento almacenado `EditarPartido` en la
     * base de datos. - El procedimiento almacenado permite actualizar los datos
     * de un partido político en función de su ID. 3. Configura los parámetros
     * requeridos por el procedimiento almacenado: - Recibe los valores
     * `idPartido`, `nombrePartido`, `sigla`, `nombreLider`, `ideologia`,
     * `numAfiliados` y `departamento`. - Establece estos valores en el objeto
     * `CallableStatement` (`cs`) para que sean enviados a la base de datos. 4.
     * Ejecuta el procedimiento almacenado utilizando el método
     * `executeUpdate()`. 5. Manejo de excepciones: - Si ocurre una excepción de
     * tipo `SQLException`, imprime el mensaje de error en la consola para
     * facilitar la depuración. - El mensaje indica que hubo un problema al
     * actualizar los datos. 6. Cierra automáticamente la conexión y el objeto
     * `CallableStatement` al finalizar, gracias al uso de `try-with-resources`.
     *
     * Este método permite realizar actualizaciones en los registros de partidos
     * políticos, asegurando que la base de datos refleje los cambios
     * solicitados por el usuario.
     *
     * @param idPartido ID único del partido político a actualizar.
     * @param nombrePartido Nuevo nombre del partido político.
     * @param sigla Nueva sigla del partido político.
     * @param nombreLider Nuevo nombre del líder del partido político.
     * @param ideologia Nueva ideología del partido político.
     * @param numAfiliados Nuevo número de afiliados del partido político.
     * @param departamento Nuevo número de departamento asociado al partido
     * político.
     */
    public static void actualizarPartido(
            int idPartido,
            String nombrePartido,
            String sigla,
            String nombreLider,
            String ideologia,
            int numAfiliados,
            int departamento
    ) {
        try (Connection connection = ConexionSQL.getConnection()) {
            String query = "{CALL EditarPartido(?, ?, ?, ?, ?, ?, ?)}";
            CallableStatement cs = connection.prepareCall(query);
            cs.setInt(1, idPartido);
            cs.setString(2, nombrePartido);
            cs.setString(3, sigla);
            cs.setString(4, nombreLider);
            cs.setString(5, ideologia);
            cs.setInt(6, numAfiliados);
            cs.setInt(7, departamento);
            cs.executeUpdate();
            cs.close();
        } catch (SQLException e) {
            System.out.println("Error al actualizar los datos: " + e.getMessage());
        }
    }
}
