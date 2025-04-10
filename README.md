# DemocrApp
Una aplicación diseñada para facilitar la gestión de partidos políticos, permitiendo el registro de miembros, la visualización de datos y la administración de formularios de manera eficiente y accesible.



@Tomas, implementa este procedimiento almacenado en la base de datos para la consulta de las credenciales de usuario:
CREATE PROCEDURE usp_validarUsuario
    @usuario VARCHAR(50),
    @contrasena VARCHAR(50)
AS
BEGIN
    -- Retorna el registro si las credenciales coinciden
    SELECT usuario, contrasena
    FROM Usuario
    WHERE usuario = @usuario AND contrasena = @contrasena;
END;
