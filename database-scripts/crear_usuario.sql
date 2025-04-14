-- Cambiar al contexto de la base de datos master
USE master;
GO

-- Crear el usuario de SQL Server
CREATE LOGIN Ciudadano WITH PASSWORD = 'UMSS2025';
GO

-- Cambiar al contexto de la base de datos Partidos_Politicos
USE Partidos_Politicos;
GO

-- Crear el usuario asociado dentro de la base de datos
CREATE USER Ciudadano FOR LOGIN Ciudadano;
GO

-- Asignar todos los permisos al usuario
EXEC sp_addrolemember 'db_owner', 'Ciudadano';
GO

-- Confirmar que los permisos han sido aplicados
SELECT dp.name AS DatabaseRoleName, 
       dppr.name AS MemberName
FROM sys.database_role_members drm
JOIN sys.database_principals dp ON drm.role_principal_id = dp.principal_id
JOIN sys.database_principals dppr ON drm.member_principal_id = dppr.principal_id
WHERE dppr.name = 'Ciudadano';
GO

-- Mensaje opcional para confirmar la ejecución
PRINT 'El usuario Ciudadano ha sido creado, asignado a la base de datos Partidos_Politicos y tiene todos los permisos necesarios.';
GO
