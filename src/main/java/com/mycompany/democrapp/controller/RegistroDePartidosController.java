// Paquete principal que organiza el proyecto
package com.mycompany.democrapp.controller;

// Importaciones necesarias para conectar con la base de datos, validar datos y manejar la vista
import com.mycompany.democrapp.model.ConexionSQL;
import com.mycompany.democrapp.model.InsercionDatos;
import com.mycompany.democrapp.model.ValidarDatos;
import com.mycompany.democrapp.view.RegistroDePartidos;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class RegistroDePartidosController {

    // Aquí se guarda una referencia al formulario que el usuario ve
    private final RegistroDePartidos vista;

    // Constructor que recibe el modelo y la vista
    public RegistroDePartidosController(ConexionSQL modelo, RegistroDePartidos vista) {
        this.vista = vista;

        // Acción para el botón "Guardar"
        this.vista.btnGuardarRegistro.addActionListener((ActionEvent e) -> {
            try (Connection connection = ConexionSQL.getConnection()) {
                // 1. Capturar datos desde los campos del formulario
                String idPartido = vista.txtIdentificador.getText();
                String departamento = vista.txtDepartamento.getText();
                String nombrePartido = vista.txtNombrePartido.getText();
                String sigla = vista.txtSigla.getText();
                String nombreLider = vista.txtNombreLider.getText();
                String ideologia = vista.txtIdeologia.getText();
                String numAfiliados = vista.txtNumAfiliados.getText();
                String fechaFundacion = vista.txtFechaFundacion.getText();

                // 2. Validar los datos
                if (!ValidarDatos.validarIdentificador(ConexionSQL.getConnection(), idPartido)) {
                    return; // Detener si el identificador es inválido
                }
                if (ValidarDatos.validarDepartamento(departamento) == -1) {
                    return; // Detener si el departamento es inválido
                }
                if (ValidarDatos.validarNombrePartido(nombrePartido) == null) {
                    return; // Detener si el nombre del partido es inválido
                }
                if (ValidarDatos.validarSigla(sigla) == null) {
                    return; // Detener si la sigla es inválida
                }
                if (ValidarDatos.validarNombreLider(nombreLider) == null) {
                    return; // Detener si el nombre del líder es inválido
                }
                if (ValidarDatos.validarIdeologia(ideologia) == null) {
                    return; // Detener si la ideología es inválida
                }
                if (ValidarDatos.validarNumAfiliados(numAfiliados) == -1) {
                    return; // Detener si el número de afiliados es inválido
                }
                if (!ValidarDatos.validarFecha(fechaFundacion)) {
                    return; // Detener si la fecha es inválida
                }
              
                // Verificar duplicados
                if (ValidarDatos.verificarDuplicados(nombrePartido, sigla, nombreLider, connection)) {
                    JOptionPane.showMessageDialog(null, "Error: Los datos ingresados ya existen. Por favor, verifica y corrige.");
                    return; // Detener el flujo si hay duplicados
                }

                // 3. Insertar los datos en la base de datos
                InsercionDatos insercionDatos = new InsercionDatos();
                boolean exito = insercionDatos.insertarPartido(
                        Integer.parseInt(idPartido),
                        Integer.parseInt(departamento),
                        nombrePartido,
                        sigla,
                        nombreLider,
                        ideologia,
                        Integer.parseInt(numAfiliados),
                        fechaFundacion
                );

                // 4. Mostrar mensajes de éxito o fallo
                if (exito) {
                    JOptionPane.showMessageDialog(null, "Partido Politico registrado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo registrar el partido.");
                }
            } catch (IllegalArgumentException ex) {
                // Mostrar un mensaje si hubo un error al validar los datos
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            } catch (SQLException ex) {
                // Mostrar un mensaje si hubo un error en la base de datos
                JOptionPane.showMessageDialog(null, "Error de base de datos: " + ex.getMessage());
                // Para depuración
            } catch (Exception ex) {
                // Mostrar un mensaje para errores inesperados
                JOptionPane.showMessageDialog(null, "Error inesperado: " + ex.getMessage());
            }
        });

        // Acción para el botón "Atras"
        this.vista.btnAtrasRegistro.addActionListener((ActionEvent e) -> {

        });
    }

    // Método para iniciar la interfaz gráfica
    public void iniciar() {
        // Mostrar directamente el JFrame si vista es un JFrame
        vista.setVisible(true);
    }
}
