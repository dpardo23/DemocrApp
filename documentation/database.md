# **Documentación de Procedimientos Almacenados**

Esta sección describe los procedimientos almacenados utilizados en la base de datos del proyecto. Cada procedimiento tiene un propósito específico y está diseñado para garantizar la integridad y funcionalidad de los datos.

---

## 📝 **Procedimiento 1: VerificarDatosDuplicados**

### **Propósito**
Valida si los datos ingresados (`Nombre_partido`, `Sigla`, `Lider`) ya existen en la tabla `Partido_Politico`. Si encuentra duplicados, genera un error utilizando `RAISERROR`.

### **Uso**
- Validar la unicidad de los datos antes de insertar o actualizar registros en la tabla `Partido_Politico`.
- Garantizar la integridad de los datos evitando duplicados en los campos clave.
- Usado en la clase `ValidarDatos.java` dentro del paquete `com.mycompany.democrapp.model`.

### **Definición del Procedimiento**
```sql
CREATE PROCEDURE VerificarDatosDuplicados(
    @Nombre_partido VARCHAR(255),
    @Sigla VARCHAR(255),
    @Lider VARCHAR(255)
)
AS
BEGIN
    IF EXISTS (
        SELECT 1
        FROM Partido_Politico
        WHERE Nombre_partido = @Nombre_partido
           OR Sigla = @Sigla
           OR Lider = @Lider
    )
    BEGIN
        RAISERROR ('Los datos ingresados ya existen en la tabla Partido_Politico.', 16, 1);
    END
END;
```
### **Ejemplo de Llamado**
```sql
EXEC VerificarDatosDuplicados 'Partido XYZ', 'XYZ', 'Juan Perez';
```

## 📝 **Procedimiento 2: GetPartidoPoliticos**

### **Propósito**
Recupera una lista básica de partidos políticos registrados en la base de datos. Devuelve dos columnas clave: el identificador único del partido (`Id_Partido`) y su nombre (`Nombre_partido`).

### **Uso**
- Generar listados básicos de partidos políticos.
- Rellenar menús desplegables o tablas.
- Crear reportes simples que solo requieran el ID y el nombre del partido.

### **Definición del Procedimiento**
```sql
CREATE PROCEDURE GetPartidoPoliticos
AS
BEGIN
    SELECT Id_Partido, Nombre_partido FROM Partido_Politico;
END;
```

### **Ejemplo de Llamado**
```sql
EXEC GetPartidoPoliticos;
```

## 📝 **Procedimiento 3: usp_validarUsuario**

### **Propósito**
Valida las credenciales de un usuario (nombre de usuario y contraseña) verificándolas contra los registros de la tabla `Usuario`. Si las credenciales son correctas, devuelve el registro correspondiente.

### **Uso**
- Sistemas de inicio de sesión o autenticación de usuarios.
- Verificar la identidad antes de permitir el acceso a ciertas áreas o funciones de la aplicación.

### **Definición del Procedimiento**
```sql
CREATE PROCEDURE usp_validarUsuario(
    @usuario VARCHAR(50),
    @contrasena VARCHAR(50)
)
AS
BEGIN
    SELECT usuario, contrasena
    FROM Usuario
    WHERE usuario = @usuario AND contrasena = @contrasena;
END;
```

### **Ejemplo de Llamado**
```sql
EXEC usp_validarUsuario 'admin', '12345';
```

## 📝 **Procedimiento 4: EditarPartido**

### **Propósito**
Permite actualizar la información de un partido político en la base de datos. Utiliza el `Id_Partido` para localizar el registro que se debe modificar y actualiza los campos con los nuevos valores proporcionados.

### **Uso**
- Modificar datos de partidos políticos en sistemas de gestión.
- Actualización de información como nombre del partido, sigla, líder, ideología, número de afiliados y departamento.

### **Definición del Procedimiento**
```sql
CREATE PROCEDURE EditarPartido(
    @Id_Partido INT,
    @NombrePartido VARCHAR(255),
    @Sigla VARCHAR(255),
    @NombreLider VARCHAR(255),
    @Ideologia VARCHAR(255),
    @NumAfiliados INT,
    @Departamento INT
)
AS
BEGIN
    UPDATE Partido_Politico
    SET 
        Nombre_partido = @NombrePartido,
        Sigla = @Sigla,
        Lider = @NombreLider,
        Ideologia = @Ideologia,
        NAfiliados = @NumAfiliados,
        Departamento_Id_departamento = @Departamento
    WHERE Id_Partido = @Id_Partido;
END;
```

### **Ejemplo de Llamado**
```sql
EXEC EditarPartido 
    @Id_Partido = 1,
    @NombrePartido = 'Partido Actualizado',
    @Sigla = 'PA',
    @NombreLider = 'Líder Actualizado',
    @Ideologia = 'Progresista',
    @NumAfiliados = 1500,
    @Departamento = 3;
```

## 📝 **Procedimiento 5: VerificarPartidoPolitico**

### **Propósito**
Verifica si un partido político existe en la base de datos utilizando el `Id_Partido`. Devuelve un valor numérico indicando si el partido existe o no.

### **Uso**
- Validar la existencia de un partido político antes de realizar otras operaciones como edición o eliminación.
- Asegurar consistencia de datos en sistemas administrativos.

### **Definición del Procedimiento**
```sql
CREATE PROCEDURE VerificarPartidoPolitico(
    @idComparar NVARCHAR(50)
)
AS
BEGIN
    SELECT COUNT(*) AS Existe 
    FROM Partido_Politico
    WHERE Id_Partido = @idComparar;
END;
```

### **Ejemplo de Llamado**
```sql
EXEC VerificarPartidoPolitico @idComparar = '1';
```
