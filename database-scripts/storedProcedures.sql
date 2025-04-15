-- Cambiar al contexto de la base de datos Partidos_Politicos
USE Partidos_Politicos;
GO

-- Procedimiento almacenado 1: VerificarDatosDuplicados
-- Este procedimiento verifica si los datos ingresados (nombre del partido, sigla o líder) ya existen en la tabla Partido_Politico.
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
GO

-- Procedimiento almacenado 2: GetPartidoPoliticos
-- Este procedimiento devuelve una lista de partidos políticos con sus identificadores y nombres.
CREATE PROCEDURE GetPartidoPoliticos
AS
BEGIN
    SELECT Id_Partido, Nombre_partido FROM Partido_Politico;
END;
GO

-- Procedimiento almacenado 3: usp_validarUsuario
-- Este procedimiento valida las credenciales de un usuario en la tabla Usuario.
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
GO

-- Procedimiento almacenado 4: EditarPartido
-- Este procedimiento actualiza los datos de un partido político en la tabla Partido_Politico.
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
GO

-- Procedimiento almacenado 5: VerificarPartidoPolitico
-- Este procedimiento verifica si un partido político existe en la base de datos utilizando su identificador.
CREATE PROCEDURE VerificarPartidoPolitico(
    @idComparar NVARCHAR(50)
)
AS
BEGIN
    SELECT COUNT(*) AS Existe 
    FROM Partido_Politico
    WHERE Id_Partido = @idComparar;
END;
GO
    
-- Procedimiento almacenado 6: ObtenerValores
-- Este procedimiento nos devuelve los valores de popularidad de la base de datos para poder generar la grafica.
CREATE PROCEDURE ObtenerValores
    @nombrePartido VARCHAR(255),
    @nombreDepartamento VARCHAR(255)
AS
BEGIN
    SELECT 
        p.Popularidad,
        p.Provincia,
        p.Fecha_popularidad
    FROM 
        Popularidad p
    INNER JOIN Partido_Politico pp 
        ON p.Partido_Politico_Id_Partido = pp.Id_Partido
    INNER JOIN Departamento d 
        ON p.Departamento_Id_departamento = d.Id_departamento
    WHERE 
        pp.Nombre_partido = @nombrePartido AND
        d.Nombre_departamento = @nombreDepartamento AND
       	p.Fecha_popularidad >= DATEFROMPARTS(YEAR(GETDATE()), MONTH(GETDATE()), 1)
AND
p.Fecha_popularidad < DATEADD(MONTH, 1, DATEFROMPARTS(YEAR(GETDATE()), MONTH(GETDATE()), 1))

-- Procedimiento almacenado 7: ObtenerNombresPartidos
-- Este procedimiento nos devuelve todos los partidos de la base de datos.
CREATE PROCEDURE ObtenerNombresPartidos
AS
BEGIN
    SELECT Nombre_partido FROM Partido_Politico;
END;

-- Procedimiento almacenado 8: ObtenerDepartamentos
-- Este procedimiento nos devuelve todos los departamentos de la base de datos.
CREATE PROCEDURE ObtenerDepartamentos
AS
BEGIN
    SELECT * FROM Departamento;
END;


-- Procedimiento almacenado 9: InsertarPopularidad
-- Este procedimiento inserta a la base de datos los valores de popularidad solo si estos no existen previamente.
CREATE PROCEDURE InsertarPopularidad
    @nombrePartido VARCHAR(255),
    @nombreDepartamento VARCHAR(255),
    @nombreProvincia VARCHAR(255),
    @popularidad INT,
    @registroCreado BIT OUTPUT
AS
BEGIN
    DECLARE @idPartido INT;
    DECLARE @idDepartamento INT;
    DECLARE @fechaActual DATE = GETDATE();

    -- Inicializar el valor de salida
    SET @registroCreado = 0;

    -- Obtener el ID del partido
    SELECT @idPartido = Id_Partido
    FROM Partido_Politico
    WHERE Nombre_partido = @nombrePartido;

    IF @idPartido IS NULL
    BEGIN
        THROW 50001, '❌ Partido político no encontrado.', 1;
    END

    -- Obtener el ID del departamento
    SELECT @idDepartamento = Id_departamento
    FROM Departamento
    WHERE Nombre_departamento = @nombreDepartamento;

    IF @idDepartamento IS NULL
    BEGIN
        THROW 50002, '❌ Departamento no encontrado.', 1;
    END

    -- Verificar si ya existe un registro similar este mes
    IF EXISTS (
        SELECT 1
        FROM Popularidad
        WHERE Partido_Politico_Id_Partido = @idPartido
          AND Departamento_Id_departamento = @idDepartamento
          AND Provincia = @nombreProvincia
          AND MONTH(Fecha_popularidad) = MONTH(@fechaActual)
          AND YEAR(Fecha_popularidad) = YEAR(@fechaActual)
    )
    BEGIN
        -- Registro ya existente, no se inserta
        SET @registroCreado = 0;
        RETURN;
    END

    -- Insertar el nuevo registro
    INSERT INTO Popularidad (
        Id_popularidad,
        Partido_Politico_Id_Partido,
        Departamento_Id_departamento,
        Popularidad,
        Provincia,
        Fecha_popularidad
    )
    VALUES (
        (SELECT ISNULL(MAX(Id_popularidad), 0) + 1 FROM Popularidad),
        @idPartido,
        @idDepartamento,
        @popularidad,
        @nombreProvincia,
        @fechaActual
    );

    -- Confirmar éxito
    SET @registroCreado = 1;
END;
