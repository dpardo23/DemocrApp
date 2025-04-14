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
