-- Crear la base de datos
CREATE DATABASE Partidos_Politicos;
GO

-- Usar la base de datos recién creada
USE Partidos_Politicos;
GO

-- Crear la tabla UserN
CREATE TABLE UserN (
  Id_userN INTEGER NOT NULL,
  UserN VARCHAR(255) NULL,
  PasswordN VARCHAR(255) NULL,
  PRIMARY KEY(Id_userN)
);

-- Crear la tabla Cargo
CREATE TABLE Cargo (
  Id_Cargo INTEGER NOT NULL,
  Nombre_cargo INTEGER,
  PRIMARY KEY(Id_Cargo)
);

-- Crear la tabla Departamento
CREATE TABLE Departamento (
  Id_departamento INTEGER NOT NULL,
  Nombre_departamento VARCHAR(255),
  PRIMARY KEY(Id_departamento)
);

-- Crear la tabla Sesion
CREATE TABLE Sesion (
  Id_sesion INTEGER NOT NULL,
  UserN_Id_userN INTEGER NOT NULL,
  PID INTEGER,
  Active BIT,
  PRIMARY KEY(Id_sesion, UserN_Id_userN),
  FOREIGN KEY(UserN_Id_userN) REFERENCES UserN(Id_userN)
);

CREATE INDEX Sesion_FKIndex1 ON Sesion (UserN_Id_userN);
CREATE INDEX IFK_tiene ON Sesion (UserN_Id_userN);

-- Crear la tabla Partido_Politico
CREATE TABLE Partido_Politico (
  Id_Partido INTEGER NOT NULL,
  Departamento_Id_departamento INTEGER NOT NULL,
  Nombre_partido VARCHAR(255),
  Sigla VARCHAR(255),
  Lider VARCHAR(255),
  Ideologia VARCHAR(255),
  NAfiliados INTEGER,
  Fecha_fundacion DATE,
  PRIMARY KEY(Id_Partido),
  FOREIGN KEY(Departamento_Id_departamento) REFERENCES Departamento(Id_departamento)
);

CREATE INDEX Partido_Politico_FKIndex1 ON Partido_Politico (Departamento_Id_departamento);
CREATE INDEX IFK_Clasifica ON Partido_Politico (Departamento_Id_departamento);

-- Crear la tabla Donacion
CREATE TABLE Donacion (
  Id_donaciones INTEGER NOT NULL,
  Partido_Politico_Id_Partido INTEGER NOT NULL,
  Monto INTEGER,
  Fecha DATE,
  PRIMARY KEY(Id_donaciones, Partido_Politico_Id_Partido),
  FOREIGN KEY(Partido_Politico_Id_Partido) REFERENCES Partido_Politico(Id_Partido)
);

CREATE INDEX Donaciones_FKIndex1 ON Donacion (Partido_Politico_Id_Partido);
CREATE INDEX IFK_tiene ON Donacion (Partido_Politico_Id_Partido);

-- Crear la tabla Donante
CREATE TABLE Donante (
  Id_donante INTEGER NOT NULL,
  Donacion_Partido_Politico_Id_Partido INTEGER NOT NULL,
  Donacion_Id_donaciones INTEGER NOT NULL,
  Nombre VARCHAR(255),
  Apellido VARCHAR(255),
  CI VARCHAR(255),
  PRIMARY KEY(Id_donante, Donacion_Partido_Politico_Id_Partido, Donacion_Id_donaciones),
  FOREIGN KEY(Donacion_Id_donaciones, Donacion_Partido_Politico_Id_Partido) REFERENCES Donacion(Id_donaciones, Partido_Politico_Id_Partido)
);

CREATE INDEX Donante_FKIndex1 ON Donante (Donacion_Id_donaciones, Donacion_Partido_Politico_Id_Partido);
CREATE INDEX IFK_tiene ON Donante (Donacion_Id_donaciones, Donacion_Partido_Politico_Id_Partido);

-- Crear la tabla Estatuto
CREATE TABLE Estatuto (
  id_Estatuto INTEGER NOT NULL,
  Partido_Politico_Id_Partido INTEGER NOT NULL,
  Nombre_Estatuto VARCHAR(255),
  ContenidoPDF VARCHAR(255),
  PRIMARY KEY(id_Estatuto, Partido_Politico_Id_Partido),
  FOREIGN KEY(Partido_Politico_Id_Partido) REFERENCES Partido_Politico(Id_Partido)
);

CREATE INDEX Estatuto_FKIndex1 ON Estatuto (Partido_Politico_Id_Partido);
CREATE INDEX IFK_Tiene ON Estatuto (Partido_Politico_Id_Partido);

-- Crear la tabla Integrante
CREATE TABLE Integrante (
  Id_Integrante INTEGER NOT NULL,
  Partido_Politico_Id_Partido INTEGER NOT NULL,
  Cargo_Id_Cargo INTEGER NOT NULL,
  Nombres VARCHAR(255),
  Apellidos VARCHAR(255),
  CI INTEGER,
  Profesion VARCHAR(255),
  Fecha_Nacimiento DATE,
  Dirrecion VARCHAR(255),
  Correro VARCHAR(255),
  PRIMARY KEY(Id_Integrante, Partido_Politico_Id_Partido),
  FOREIGN KEY(Cargo_Id_Cargo) REFERENCES Cargo(Id_Cargo),
  FOREIGN KEY(Partido_Politico_Id_Partido) REFERENCES Partido_Politico(Id_Partido)
);

CREATE INDEX Integrante_FKIndex1 ON Integrante (Cargo_Id_Cargo);
CREATE INDEX Integrante_FKIndex2 ON Integrante (Partido_Politico_Id_Partido);
CREATE INDEX IFK_Clasifica ON Integrante (Cargo_Id_Cargo);
CREATE INDEX IFK_tiene ON Integrante (Partido_Politico_Id_Partido);

-- Crear la tabla Popularidad
CREATE TABLE Popularidad (
  Id_popularidad INTEGER NOT NULL,
  Partido_Politico_Id_Partido INTEGER NOT NULL,
  Departamento_Id_departamento INTEGER NOT NULL,
  Popularidad INTEGER,
  Provincia VARCHAR,
  Fecha_popularidad DATE,
  PRIMARY KEY(Id_popularidad, Partido_Politico_Id_Partido),
  FOREIGN KEY(Departamento_Id_departamento) REFERENCES Departamento(Id_departamento),
  FOREIGN KEY(Partido_Politico_Id_Partido) REFERENCES Partido_Politico(Id_Partido)
);

CREATE INDEX Popularidad_FKIndex1 ON Popularidad (Departamento_Id_departamento);
CREATE INDEX Popularidad_FKIndex2 ON Popularidad (Partido_Politico_Id_Partido);
CREATE INDEX IFK_clasifica ON Popularidad (Departamento_Id_departamento);
CREATE INDEX IFK_tiene ON Popularidad (Partido_Politico_Id_Partido);
