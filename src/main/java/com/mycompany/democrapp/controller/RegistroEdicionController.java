// Paquete principal que organiza el proyecto
package com.mycompany.democrapp.controller;

// Importaciones necesarias para conectar con la base de datos, validar datos y manejar la vista
import com.mycompany.democrapp.model.ConexionSQL;
import com.mycompany.democrapp.model.InsercionDatos;
import com.mycompany.democrapp.model.ValidarDatos;
import com.mycompany.democrapp.view.RegistroEdicionPartidos;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class RegistroEdicionController {

    // Aquí se guarda una referencia al formulario que el usuario ve
    private final RegistroEdicionPartidos vista;
    private final ValidarDatos validacion;
    private String idComparar;
    private int vpopularidad=0;
    
    private DefaultTableModel dtmp=new DefaultTableModel();
    private DefaultTableModel dtmd=new DefaultTableModel();
    private DefaultTableModel dtmpr=new DefaultTableModel();
    //private String partido="";
    //private String Departamento="";

    // Constructor que recibe el modelo y la vista
    public RegistroEdicionController(ConexionSQL modelo, RegistroEdicionPartidos vista) {

        this.vista = vista;
        this.validacion = new ValidarDatos();

        /**
         * Acccion para el boton "Guardar" en la pantalla de registro. Cuando se
         * presiona guarda todos los campos TextField del registro y mediante un
         * query los manda a la base de datos
         */
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
                    return;
                }
                if (ValidarDatos.validarDepartamento(departamento) == -1) {
                    return; 
                }
                if (ValidarDatos.validarNombrePartido(nombrePartido) == null) {
                    return; 
                }
                if (ValidarDatos.validarSigla(sigla) == null) {
                    return; 
                }
                if (ValidarDatos.validarNombreLider(nombreLider) == null) {
                    return; 
                }
                if (ValidarDatos.validarIdeologia(ideologia) == null) {
                    return; 
                }
                if (ValidarDatos.validarNumAfiliados(numAfiliados) == -1) {
                    return; 
                }
                if (!ValidarDatos.validarFecha(fechaFundacion)) {
                    return; 
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
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error de base de datos: " + ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error inesperado: " + ex.getMessage());
            }

            // Limpiar los campos de texto del formulario de registro
            vista.txtIdentificador.setText("");
            vista.txtDepartamento.setText("");
            vista.txtNombrePartido.setText("");
            vista.txtSigla.setText("");
            vista.txtIdeologia.setText("");
            vista.txtNumAfiliados.setText("");
            vista.txtFechaFundacion.setText("");
            vista.txtNombreLider.setText("");
        });

        /**
         * Acción para el botón "Atrás" en la pantalla de registro de datos.
         * Cuando se acciona, se ocultan todos los paneles excepto el panel
         * principal. También limpia los TextField relacionados con el registro
         * de partidos.
         */
        this.vista.btnAtrasRegistro.addActionListener((ActionEvent e) -> {
            // Ocultar los demas paneles
            vista.panelRegistroDeDatos.setVisible(false);
            vista.panelTablaEdicion.setVisible(false);
            vista.panelEdicionDeDatos.setVisible(false);
            vista.panelgraficas.setVisible(false);
            vista.panelpopularidad.setVisible(false);

            // Mostrar el panel principal
            vista.panelPrincipal.setVisible(true);

            // Limpiar los campos de texto del formulario de registro
            vista.txtIdentificador.setText("");
            vista.txtDepartamento.setText("");
            vista.txtNombrePartido.setText("");
            vista.txtSigla.setText("");
            vista.txtIdeologia.setText("");
            vista.txtNumAfiliados.setText("");
            vista.txtFechaFundacion.setText("");
            vista.txtNombreLider.setText("");
        });

        /**
         * Acción para el botón "Atrás" en la pantalla de edición de datos.
         * Cuando se acciona, se ocultan todos los paneles excepto el principal.
         * Además, se limpia el campo de identificación relacionado con la
         * edición de registros.
         */
        this.vista.btnAtrasTabla.addActionListener((ActionEvent e) -> {
            // Ocultar los demas paneles
            vista.panelRegistroDeDatos.setVisible(false);
            vista.panelTablaEdicion.setVisible(false);
            vista.panelEdicionDeDatos.setVisible(false);
            vista.panelgraficas.setVisible(false);
            vista.panelpopularidad.setVisible(false);

            // Mostrar el panel principal
            vista.panelPrincipal.setVisible(true);

            // Limpiar el campo de identificacion en la edicion de datos
            vista.txtIdentificadorTabla.setText("");
        });

        
        this.vista.btnpopularidad.addActionListener((ActionEvent e) -> {
            // Ocultar los demas paneles
            vista.panelRegistroDeDatos.setVisible(false);
            vista.panelTablaEdicion.setVisible(false);
            vista.panelEdicionDeDatos.setVisible(false);
            vista.panelgraficas.setVisible(false);
            vista.panelpopularidad.setVisible(true);

            // Mostrar el panel principal
            vista.panelPrincipal.setVisible(false);
            
            llenadop();

            // Limpiar el campo de identificacion en la edicion de datos
            //vista.txtIdentificadorTabla.setText("");
        });
        /**
         * Accion para el boton "Validar" en la pantalla de edicion de datos.
         * Cuando se acciona, toma el valor ingresado en el TextField y lo
         * compara con el Identificador registrado en la base de datos, despues
         * muestra la pantalla con el formulario de edcion.
         */
        this.vista.btnValidarTabla.addActionListener((ActionEvent e) -> {
            try {
                // Capturar el valor ingresado en txtidentificadorTabla
                idComparar = vista.txtIdentificadorTabla.getText();

                // Verificar que no este vacio
                if (idComparar == null || idComparar.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(vista, "Por favor ingrese un identificador.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // LLamar al metodo que verifica el identificador en la base de datos.
                boolean existe = validacion.verificarIdPartido(idComparar);
                if (existe) {
                    // Ocultar los demas paneles
                    vista.panelPrincipal.setVisible(false);
                    vista.panelTablaEdicion.setVisible(false);
                    vista.panelRegistroDeDatos.setVisible(false);
                    vista.panelgraficas.setVisible(false);
                    vista.panelpopularidad.setVisible(false);

                    // Mostrar el panel de edicion de datos
                    vista.panelEdicionDeDatos.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(vista, "El identificador no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, "Ocurrio un error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        /**
         * Acción para el botón "Registro de datos" en la pantalla principal.
         * Cuando se presiona, oculta otros paneles y muestra el panel de
         * registro de datos.
         */
        this.vista.btnRegistroDeDatos.addActionListener((ActionEvent e) -> {
            // Ocultar los demas paneles
            vista.panelTablaEdicion.setVisible(false);
            vista.panelPrincipal.setVisible(false);
            vista.panelEdicionDeDatos.setVisible(false);
            vista.panelgraficas.setVisible(false);
            vista.panelpopularidad.setVisible(false);

            // Mostrar el panel de registro de datos
            vista.panelRegistroDeDatos.setVisible(true);
        });

        
        
        this.vista.btnvergraficas.addActionListener((ActionEvent e) -> {
            // Ocultar los demas paneles
            if(vista.partido==""){
            JOptionPane.showMessageDialog(null, "Seleccione el partido del cual desea ver las graficas");
        }else{
            if(vista.Departamento==""){
                JOptionPane.showMessageDialog(null, "Seleccione el departamento del cual desea ver las graficas");
            }else{
                ArrayList<datos> grafica = new ArrayList<>();
                grafica=Obtenervalores(vista.partido,vista.Departamento);
                if(grafica.isEmpty()){
                    JOptionPane.showMessageDialog(null, "En este momento aun no existen datos de popularidad del partido del partido");
                }else{
                    DefaultPieDataset datosgrafica=new DefaultPieDataset();
                    for(datos a:grafica){
                        datosgrafica.setValue(a.getProvincia(),a.getPopularidad());
                    }
                    JFreeChart grafico_circular=ChartFactory.createPieChart("Popularidad por Provincia", datosgrafica,false,true,false);
                    ChartPanel panel=new ChartPanel(grafico_circular);
                    panel.setMouseWheelEnabled(true);
                    panel.setPreferredSize(new Dimension(280,384));
                    vista.panelvistagrafica.setLayout(new BorderLayout());
                    vista.panelvistagrafica.add(panel,BorderLayout.NORTH);
                    vista.pack();
                    vista.repaint();
                }
            }
        }
        });
        
        this.vista.btnGraficas.addActionListener((ActionEvent e) -> {
            // Ocultar los demas paneles
            vista.panelTablaEdicion.setVisible(false);
            vista.panelPrincipal.setVisible(false);
            vista.panelEdicionDeDatos.setVisible(false);
            vista.panelpopularidad.setVisible(false);

            // Mostrar el panel de registro de datos
            vista.panelRegistroDeDatos.setVisible(false);
            vista.panelgraficas.setVisible(true);
            llenado();
        });
        
        
        this.vista.btnAtraspopularidad.addActionListener((ActionEvent e) -> {
            // Ocultar los demas paneles
            vista.panelTablaEdicion.setVisible(false);
            vista.panelPrincipal.setVisible(true);
            vista.panelEdicionDeDatos.setVisible(false);
            vista.panelpopularidad.setVisible(false);

            // Mostrar el panel de registro de datos
            vista.panelRegistroDeDatos.setVisible(false);
            vista.panelgraficas.setVisible(false);
            vista.partido="";
            vista.Departamento="";
            vista.provincia="";
        });
        /**
         * Acción para el botón "Edición de datos" en la pantalla principal.
         * Cuando se presiona, se limpia la tabla y se muestra el panel de con
         * la tabla que tiene los identificadores y nombres de los partidos.
         * politcos.
         */
        this.vista.btnEdicionDeDatos.addActionListener((ActionEvent e) -> {
            // Obtener el modelo de la tabla y limpiar sus datos
            DefaultTableModel model = (DefaultTableModel) this.vista.tablaIdentificador.getModel();
            model.setRowCount(0); // Limpiar tabla

            // Ocultar los demas paneles
            vista.panelPrincipal.setVisible(false);
            vista.panelRegistroDeDatos.setVisible(false);
            vista.panelEdicionDeDatos.setVisible(false);
            vista.panelgraficas.setVisible(false);
            vista.panelpopularidad.setVisible(false);

            // Mostrar el panel que contiene la tabla
            vista.panelTablaEdicion.setVisible(true);

            try {
                var datos = validacion.obtenerPartidosPoliticos();

                //Agregar los datos a la tabla
                for (String[] fila : datos) {
                    model.addRow(new Object[]{fila[0], fila[1]});
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, "Error al cargar los datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Refrescar la vista
            vista.panelTablaEdicion.repaint();
            vista.panelTablaEdicion.revalidate();
        });

        /**
         * Acción para el botón "Atras" en la pantalla edicion de datos. Cuando
         * se presiona, oculta otros paneles y muestra el panel donde se
         * encuentra la tabla y limpia los datos existentes en los TextField.
         */
        this.vista.btnAtrasEdit.addActionListener((ActionEvent e) -> {
            // Ocultar los demas paneles
            vista.panelRegistroDeDatos.setVisible(false);
            vista.panelPrincipal.setVisible(false);
            vista.panelEdicionDeDatos.setVisible(false);
            vista.panelgraficas.setVisible(false);
            vista.panelpopularidad.setVisible(false);

            // Mostrar el panel de registro de datos
            vista.panelTablaEdicion.setVisible(true);

            // Limpiar los campos de texto del formulario de edicion
            vista.txtNombrePartidoEdit.setText("");
            vista.txtSiglaEdit.setText("");
            vista.txtNombreLiderEdit.setText("");
            vista.txtIdeologiaEdit.setText("");
            vista.txtNumeroAfiliadosEdit.setText("");
            vista.txtDepartamentoEdit.setText("");
        });

        this.vista.btnatrasgraficas.addActionListener((ActionEvent e) -> {
            // Ocultar los demas paneles
            vista.panelRegistroDeDatos.setVisible(false);
            vista.panelPrincipal.setVisible(true);
            vista.panelEdicionDeDatos.setVisible(false);
            vista.panelgraficas.setVisible(false);
            vista.panelpopularidad.setVisible(false);

            // Mostrar el panel de registro de datos
            vista.panelTablaEdicion.setVisible(false);

            // Limpiar los campos de texto del formulario de edicion
            vista.partido="";
            vista.Departamento="";
            vista.provincia="";
        });
        
        
        this.vista.btnInsertarpopularidad.addActionListener((ActionEvent e) -> {
            // Ocultar los demas paneles
            vpopularidad=Integer.parseInt(vista.popularidad.getText());
        if(vista.partido==""){
            JOptionPane.showMessageDialog(null, "Seleccione el partido del cual desea ver las graficas");
        }else{
            if(vista.provincia==""){
                JOptionPane.showMessageDialog(null, "Seleccione la provincia del cual desea ver las graficas");
            }else{
                insertarDatos(vista.partido,vista.Departamento,vista.provincia,vpopularidad);
            }
        }
        });
        /**
         * Acción para el botón "Guardar" en la pantalla edicion de datos.
         * Cuando se presiona, oculta otros paneles y muestra el panel donde se
         * encuentra el formulario y limpia los datos existentes enlos
         * TextField, ademas manda los nuevos a la BD.
         */
        this.vista.btnGuardarEdit.addActionListener((ActionEvent e) -> {
            try (Connection connection = ConexionSQL.getConnection()) {
                // 1. Capturar los datos de los campos del formulario
                String nombrePartidoEdit = vista.txtNombrePartidoEdit.getText();
                String departamentoEdit = vista.txtDepartamentoEdit.getText();
                String siglaEdit = vista.txtSiglaEdit.getText();
                String nombreLiderEdit = vista.txtNombreLiderEdit.getText();
                String ideologiaEdit = vista.txtIdeologiaEdit.getText();
                String numAfiliadosEdit = vista.txtNumeroAfiliadosEdit.getText();

                // 2. Validar los datos
                if (ValidarDatos.validarNombrePartido(nombrePartidoEdit) == null) {
                    return;
                }
                if (ValidarDatos.validarDepartamento(departamentoEdit) == -1) {
                    return;
                }
                if (ValidarDatos.validarSigla(siglaEdit) == null) {
                    return;
                }
                if (ValidarDatos.validarNombreLider(nombreLiderEdit) == null) {
                    return;
                }
                if (ValidarDatos.validarIdeologia(ideologiaEdit) == null) {
                    return;
                }
                if (ValidarDatos.validarNumAfiliados(numAfiliadosEdit) == -1) {
                    return;
                }

                // 3. Verificar duplicados
                if (ValidarDatos.verificarDuplicados(nombrePartidoEdit, siglaEdit, nombreLiderEdit, connection)) {
                    JOptionPane.showMessageDialog(null, "Error: Los datos ingresados ya existen. Por favor, verifica y corrije");
                    return;
                }

                // 4. Insertar los datos en la base de datos
                try {
                    // Llamar al método actualizarPartido en la clase ValidarDatos
                    ValidarDatos.actualizarPartido(
                            Integer.parseInt(idComparar), // Usamos idComparar como el ID del partido, convirtiéndolo a entero
                            nombrePartidoEdit,
                            siglaEdit,
                            nombreLiderEdit,
                            ideologiaEdit,
                            Integer.parseInt(numAfiliadosEdit),
                            Integer.parseInt(departamentoEdit)
                    );

                    JOptionPane.showMessageDialog(vista, "Los datos se han actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    // Limpiar los campos del formulario después de la actualización
                    vista.txtNombrePartidoEdit.setText("");
                    vista.txtDepartamentoEdit.setText("");
                    vista.txtSiglaEdit.setText("");
                    vista.txtNombreLiderEdit.setText("");
                    vista.txtIdeologiaEdit.setText("");
                    vista.txtNumeroAfiliadosEdit.setText("");

                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(vista, "Error: asegúrate de ingresar datos numéricos válidos en los campos correspondientes.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(vista, "Error inesperado al actualizar los datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegistroEdicionController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Método para iniciar la interfaz gráfica "Principal"
    public void iniciar() {
        vista.setVisible(true);
        vista.panelTablaEdicion.setVisible(false);
        vista.panelEdicionDeDatos.setVisible(false);
        vista.panelPrincipal.setVisible(false);
        vista.panelgraficas.setVisible(false);
        vista.panelpopularidad.setVisible(false);
    }
    
    public ArrayList<String> MostrarDepartamentos() {
        ArrayList<String> departamento = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            // Obtener la conexión a la base de datos
            con = ConexionSQL.getConnection();
            // Preparar la llamada al procedimiento almacenado.
            cs = con.prepareCall("{call dbo.ObtenerDepartamentos}");

            // Ejecutar el procedimiento almacenado
            rs = cs.executeQuery();

            // Si se retorna un registro, las credenciales son válidas
            while(rs.next()){
                departamento.add(rs.getString("Nombre_departamento"));
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
        return departamento;
    }  
    public ArrayList<String> MostrarPartidos() {
        ArrayList<String> Partido = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            // Obtener la conexión a la base de datos
            con = ConexionSQL.getConnection();
            // Preparar la llamada al procedimiento almacenado.
            cs = con.prepareCall("{call dbo.ObtenerNombresPartidos}");

            // Ejecutar el procedimiento almacenado
            rs = cs.executeQuery();

            // Si se retorna un registro, las credenciales son válidas
            while(rs.next()){
                Partido.add(rs.getString("Nombre_partido"));
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
        return Partido;
    }
    
    public class datos{
        private String provincia;
        private int popularidad;
        
        public datos(String provincia, int popularidad){
            this.provincia = provincia;
            this.popularidad = popularidad;
        }
        
        public int getPopularidad() {
            return popularidad;
        }
        public void setPopularidad(int popularidad) {
            this.popularidad = popularidad;
        }    

        public String getProvincia() {
            return provincia;
        }
        public void setProvincia(String provincia) {
            this.provincia = provincia;
        }
    }
    
    
    public ArrayList<datos> Obtenervalores(String partido,String departamento) {
        ArrayList<datos> valores = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            // Obtener la conexión a la base de datos
            con = ConexionSQL.getConnection();
            // Preparar la llamada al procedimiento almacenado.
            cs = con.prepareCall("{call dbo.ObtenerValores(?, ?)}");
            cs.setString(1, partido);
            cs.setString(2, departamento);

            // Ejecutar el procedimiento almacenado
            rs = cs.executeQuery();

            // Si se retorna un registro, las credenciales son válidas
            while(rs.next()){
                valores.add(new datos(rs.getString("provincia"),rs.getInt("popularidad")));
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
        return valores;
    }
    public void llenartablapartido(){
        /*DefaultTableModel dtm=new DefaultTableModel();
        tablaPartido.setModel(dtm);*/
        ArrayList<String> partido = new ArrayList<String>();
        partido=MostrarPartidos();
        for(String dato:partido){
            dtmp.addRow(new Object[]{
                dato.toString()
            });
        }
    }
    public void llenartabladepartamento(){
        /*DefaultTableModel dtm=new DefaultTableModel();
        tablaPartido.setModel(dtm);*/
        ArrayList<String> departamento = new ArrayList<String>();
        departamento=MostrarDepartamentos();
        for(String dato:departamento){
            dtmd.addRow(new Object[]{
                dato.toString()
            });
        }
    }
    
    public void llenado(){
        String[] titulop=new String[]{"Partido"};
        dtmp.setColumnIdentifiers(titulop);
        vista.tablaPartido.setModel(dtmp);
        String[] titulod=new String[]{"Departamento"};
        dtmd.setColumnIdentifiers(titulod);
        vista.tablaDepartamento.setModel(dtmd);
        llenartablapartido();
        llenartabladepartamento();
    }
    
    public void llenadop(){
        String[] titulop=new String[]{"Partido"};
        dtmp.setColumnIdentifiers(titulop);
        vista.tablaPartido1.setModel(dtmp);
        String[] titulod=new String[]{"Departamento"};
        dtmd.setColumnIdentifiers(titulod);
        vista.tablaDepartamento1.setModel(dtmd);
        llenartablapartido();
        llenartabladepartamento();
    }
    
    
    public void insertarDatos(String partido,String departamento,String provincia,int popularidad) {
        //boolean alerta;
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            // Obtener la conexión a la base de datos
            con = ConexionSQL.getConnection();
            // Preparar la llamada al procedimiento almacenado.
            cs = con.prepareCall("{call InsertarPopularidad(?, ?, ?, ?, ?)}");

            cs.setString(1, partido);      // nombrePartido
            cs.setString(2, departamento);                // nombreDepartamento
            cs.setString(3, provincia);               // nombreProvincia
            cs.setInt(4, popularidad); 
            // Ejecutar el procedimiento almacenado
            
            cs.registerOutParameter(5, Types.BIT);
            
            //rs = cs.execute();
            cs.execute();

            boolean creado = cs.getBoolean(5);
            if (creado) {
                JOptionPane.showMessageDialog(null, "✅ Registro creado correctamente.");
                //System.out.println("✅ Registro creado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "⚠️ El registro ya existía. No se insertó.");
                //System.out.println("⚠️ El registro ya existía. No se insertó.");
            }
            // Si se retorna un registro, las credenciales son válidas
            //alerta= cs.getBoolean(5);
            //System.out.println("Mensaje del procedimiento se hizo correctamente ");
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
    }
    
    /*public void llenadoprovincia(){
        //DefaultTableModel dtmpr=new DefaultTableModel();
        String[] titulopr=new String[]{"Provincias"};
        dtmpr.setColumnIdentifiers(titulopr);
        vista.tablaprovincia.setModel(dtmpr);
        //llenartablaprovincias(vista.Departamento);
    }*/
    /*public void llenartablaprovincias(String provincia){
        DefaultTableModel dtmpr=new DefaultTableModel();
        vista.tablaprovincia.setModel(dtmpr);
        ArrayList<String> provincias = new ArrayList<>();
        provincias=MostrarProvincias(provincia);
        for(String dato:provincias){
            dtmpr.addRow(new Object[]{
                dato.toString()
            });
        }
    }*/
    
    /*public ArrayList<String> MostrarProvincias(String provincia) {
        ArrayList<String> Provincia = new ArrayList<>();
        Connection con = null;
        CallableStatement cs = null;
        ResultSet rs = null;

        try {
            // Obtener la conexión a la base de datos
            con = ConexionSQL.getConnection();
            // Preparar la llamada al procedimiento almacenado.
            cs = con.prepareCall("{call dbo.ObtenerProvincias(?)}");

            cs.setString(1, provincia);
            // Ejecutar el procedimiento almacenado
            rs = cs.executeQuery();

            // Si se retorna un registro, las credenciales son válidas
            while(rs.next()){
                Provincia.add(rs.getString("Provincia"));
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
        return Provincia;
    }*/
    
    
}
