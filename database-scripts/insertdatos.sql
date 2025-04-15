-- Inserta los 9 departamentos oficiales del Estado Plurinacional de Bolivia en la tabla Departamento
INSERT INTO Departamento (Id_departamento, Nombre_departamento) VALUES
(1, 'Chuquisaca'),
(2, 'La Paz'),
(3, 'Cochabamba'),
(4, 'Oruro'),
(5, 'Potosí'),
(6, 'Tarija'),
(7, 'Santa Cruz'),
(8, 'Beni'),
(9, 'Pando');

-- Inserta 31 partidos políticos de ejemplo en la tabla Partido_Politico, cada uno asociado a un departamento
INSERT INTO Partido_Politico (
    Id_Partido,
    Departamento_Id_departamento,
    Nombre_partido,
    Sigla,
    Lider,
    Ideologia,
    NAfiliados,
    Fecha_fundacion
) VALUES
(1, 2, 'Movimiento Progreso Nacional', 'MPN', 'Luis Andrade', 'Centro Izquierda', 12000, '2001-05-15'),
(12345678, 1, 'Movimiento Aurora', 'MA', 'Sofía Villalobos', 'Progresista', 3200, '1998-03-15'),
(23456789, 2, 'Fuerza Andina', 'FA', 'Carlos Quispe', 'Indigenista', 4500, '2001-07-20'),
(34567890, 3, 'Renovación Ciudadana', 'RC', 'María López', 'Liberal', 5200, '2005-11-10'),
(45678901, 4, 'Unidad Popular', 'UP', 'Jorge Arce', 'Socialista', 6100, '1995-05-25'),
(56789012, 5, 'Alianza Verde', 'AV', 'Elena Rojas', 'Ecologista', 2800, '2010-09-12'),
(67890123, 6, 'Vanguardia Nacional', 'VN', 'Luis Fernández', 'Conservador', 3700, '2003-02-18'),
(78901234, 7, 'Horizonte Democrático', 'HD', 'Ana Morales', 'Democrático', 4900, '2008-06-30'),
(89012345, 8, 'Pueblo Unido', 'PU', 'Miguel Vargas', 'Socialdemócrata', 5300, '2012-01-14'),
(90123456, 9, 'Futuro Solidario', 'FS', 'Claudia Pérez', 'Humanista', 4100, '2015-08-22'),
(12345679, 1, 'Nueva Esperanza', 'NE', 'Ricardo Gutiérrez', 'Progresista', 4600, '1999-04-05'),
(23456780, 2, 'Acción Boliviana', 'AB', 'Gabriela Suárez', 'Liberal', 3400, '2007-10-19'),
(34567891, 3, 'Cambio Radical', 'CR', 'Fernando Aguilar', 'Socialista', 5800, '2004-12-03'),
(45678902, 4, 'Fuerza del Pueblo', 'FP', 'Isabel Mendoza', 'Indigenista', 6200, '2011-03-27'),
(56789013, 5, 'Avance Democrático', 'AD', 'Hugo Salazar', 'Democrático', 3900, '2000-07-09'),
(67890124, 6, 'Movimiento Solidario', 'MS', 'Patricia Ortega', 'Humanista', 4700, '2013-05-16'),
(78901235, 7, 'Progreso Nacional', 'PN', 'Diego Castro', 'Conservador', 5200, '2006-09-01'),
(89012346, 8, 'Alianza por la Justicia', 'AJ', 'Lucía Herrera', 'Socialdemócrata', 4300, '2016-11-11'),
(90123457, 9, 'Futuro Verde', 'FV', 'Andrés Paredes', 'Ecologista', 3100, '2018-02-08'),
(12345680, 1, 'Unidad Nacional', 'UN', 'Verónica Chávez', 'Progresista', 5600, '2002-06-14'),
(23456791, 2, 'Horizonte Popular', 'HP', 'Javier Peña', 'Socialista', 6000, '2014-09-23'),
(34567802, 3, 'Renacer Democrático', 'RD', 'Carmen Silva', 'Democrático', 3800, '2009-12-07'),
(45678913, 4, 'Fuerza Nueva', 'FN', 'Esteban Ríos', 'Conservador', 4500, '2017-04-02'),
(56789024, 5, 'Movimiento por la Igualdad', 'MI', 'Natalia Vargas', 'Humanista', 4900, '2020-01-19'),
(67890135, 6, 'Progreso Andino', 'PA', 'Tomás Guzmán', 'Indigenista', 5300, '2021-08-13'),
(78901246, 7, 'Acción Solidaria', 'AS', 'Rosa Delgado', 'Socialdemócrata', 4100, '2022-03-05'),
(89012357, 8, 'Vanguardia Popular', 'VP', 'Álvaro Espinoza', 'Progresista', 4700, '2023-10-29'),
(90123468, 9, 'Futuro Democrático', 'FD', 'Daniela Romero', 'Democrático', 3600, '2024-07-18'),
(12345681, 1, 'Renovación Nacional', 'RN', 'Felipe Torres', 'Conservador', 5200, '2025-12-25'),
(23456792, 2, 'Horizonte Solidario', 'HS', 'Mónica Estrada', 'Humanista', 4400, '2026-11-03'),
(34567803, 3, 'Unidad Andina', 'UA', 'Gustavo León', 'Indigenista', 5700, '2027-06-21');

-- Inserta datos de popularidad para el partido 1 en todas las provincias del departamento de Chuquisaca (ID = 1)
INSERT INTO Popularidad (
  Id_popularidad,
  Partido_Politico_Id_Partido,
  Departamento_Id_departamento,
  Popularidad,
  Provincia,
  Fecha_popularidad
) VALUES
(1, 1, 1, 72, 'Oropeza', '2025-04-01'),
(2, 1, 1, 68, 'Yamparáez', '2025-04-01'),
(3, 1, 1, 70, 'Jaime Zudáñez', '2025-04-01'),
(4, 1, 1, 65, 'Tomina', '2025-04-01'),
(5, 1, 1, 74, 'Hernando Siles', '2025-04-01'),
(6, 1, 1, 66, 'Belisario Boeto', '2025-04-01'),
(7, 1, 1, 69, 'Nor Cinti', '2025-04-01'),
(8, 1, 1, 73, 'Sud Cinti', '2025-04-01'),
(9, 1, 1, 71, 'Luis Calvo', '2025-04-01');

-- Inserta datos de popularidad para el partido 1 en todas las provincias del departamento de La Paz (ID = 2)
INSERT INTO Popularidad (
  Id_popularidad,
  Partido_Politico_Id_Partido,
  Departamento_Id_departamento,
  Popularidad,
  Provincia,
  Fecha_popularidad
) VALUES
(10, 1, 2, 78, 'Abel Iturralde', '2025-04-01'),
(11, 1, 2, 70, 'Aroma', '2025-04-01'),
(12, 1, 2, 73, 'Bautista Saavedra', '2025-04-01'),
(13, 1, 2, 75, 'Caranavi', '2025-04-01'),
(14, 1, 2, 69, 'Franz Tamayo', '2025-04-01'),
(15, 1, 2, 67, 'Gualberto Villarroel', '2025-04-01'),
(16, 1, 2, 76, 'Ingavi', '2025-04-01'),
(17, 1, 2, 71, 'Inquisivi', '2025-04-01'),
(18, 1, 2, 74, 'José Manuel Pando', '2025-04-01'),
(19, 1, 2, 72, 'Larecaja', '2025-04-01'),
(20, 1, 2, 68, 'Loayza', '2025-04-01'),
(21, 1, 2, 70, 'Los Andes', '2025-04-01'),
(22, 1, 2, 77, 'Manco Kapac', '2025-04-01'),
(23, 1, 2, 66, 'Muñecas', '2025-04-01'),
(24, 1, 2, 69, 'Nor Yungas', '2025-04-01'),
(25, 1, 2, 71, 'Omasuyos', '2025-04-01'),
(26, 1, 2, 75, 'Pacajes', '2025-04-01'),
(27, 1, 2, 80, 'Pedro Domingo Murillo', '2025-04-01'),
(28, 1, 2, 73, 'Sud Yungas', '2025-04-01');

-- Inserta datos de popularidad para el partido 1 en todas las provincias de Cochabamba (ID = 3)
INSERT INTO Popularidad (
  Id_popularidad,
  Partido_Politico_Id_Partido,
  Departamento_Id_departamento,
  Popularidad,
  Provincia,
  Fecha_popularidad
) VALUES
(29, 1, 3, 72, 'Arani', '2025-04-01'),
(30, 1, 3, 68, 'Arque', '2025-04-01'),
(31, 1, 3, 75, 'Ayopaya', '2025-04-01'),
(32, 1, 3, 70, 'Capinota', '2025-04-01'),
(33, 1, 3, 67, 'Carrasco', '2025-04-01'),
(34, 1, 3, 80, 'Cercado', '2025-04-01'),
(35, 1, 3, 74, 'Chapare', '2025-04-01'),
(36, 1, 3, 69, 'Esteban Arce', '2025-04-01'),
(37, 1, 3, 73, 'Germán Jordán', '2025-04-01'),
(38, 1, 3, 71, 'Mizque', '2025-04-01'),
(39, 1, 3, 76, 'Punata', '2025-04-01'),
(40, 1, 3, 70, 'Quillacollo', '2025-04-01'),
(41, 1, 3, 66, 'Tapacarí', '2025-04-01'),
(42, 1, 3, 75, 'Tiraque', '2025-04-01');

-- Inserta datos de popularidad para el partido 1 en todas las provincias de Oruro (ID = 4)
INSERT INTO Popularidad (
  Id_popularidad,
  Partido_Politico_Id_Partido,
  Departamento_Id_departamento,
  Popularidad,
  Provincia,
  Fecha_popularidad
) VALUES
(43, 1, 4, 74, 'Cercado', '2025-04-01'),
(44, 1, 4, 69, 'Eduardo Abaroa', '2025-04-01'),
(45, 1, 4, 72, 'Ladislao Cabrera', '2025-04-01'),
(46, 1, 4, 70, 'Litoral', '2025-04-01'),
(47, 1, 4, 67, 'Mejillones', '2025-04-01'),
(48, 1, 4, 76, 'Nor Carangas', '2025-04-01'),
(49, 1, 4, 71, 'Pantaleón Dalence', '2025-04-01'),
(50, 1, 4, 73, 'Poopó', '2025-04-01'),
(51, 1, 4, 68, 'Sabaya', '2025-04-01'),
(52, 1, 4, 65, 'Sajama', '2025-04-01'),
(53, 1, 4, 70, 'San Pedro de Totora', '2025-04-01'),
(54, 1, 4, 66, 'Saucarí', '2025-04-01'),
(55, 1, 4, 74, 'Sebastián Pagador', '2025-04-01'),
(56, 1, 4, 69, 'Sud Carangas', '2025-04-01'),
(57, 1, 4, 75, 'Tomás Barrón', '2025-04-01');

-- Inserta datos de popularidad para el partido 1 en todas las provincias de Potosí (ID = 5)
INSERT INTO Popularidad (
  Id_popularidad,
  Partido_Politico_Id_Partido,
  Departamento_Id_departamento,
  Popularidad,
  Provincia,
  Fecha_popularidad
) VALUES
(58, 1, 5, 74, 'Alonso de Ibáñez', '2025-04-01'),
(59, 1, 5, 70, 'Antonio Quijarro', '2025-04-01'),
(60, 1, 5, 73, 'Bernardino Bilbao', '2025-04-01'),
(61, 1, 5, 69, 'Charcas', '2025-04-01'),
(62, 1, 5, 75, 'Chayanta', '2025-04-01'),
(63, 1, 5, 68, 'Cornelio Saavedra', '2025-04-01'),
(64, 1, 5, 72, 'Daniel Campos', '2025-04-01'),
(65, 1, 5, 67, 'Enrique Baldivieso', '2025-04-01'),
(66, 1, 5, 70, 'José María Linares', '2025-04-01'),
(67, 1, 5, 76, 'Modesto Omiste', '2025-04-01'),
(68, 1, 5, 74, 'Nor Chichas', '2025-04-01'),
(69, 1, 5, 71, 'Nor Lípez', '2025-04-01'),
(70, 1, 5, 69, 'Rafael Bustillo', '2025-04-01'),
(71, 1, 5, 73, 'Sud Chichas', '2025-04-01'),
(72, 1, 5, 66, 'Sud Lípez', '2025-04-01'),
(73, 1, 5, 77, 'Tomás Frías', '2025-04-01');

-- Inserta datos de popularidad para el partido 1 en todas las provincias de Tarija (ID = 6)
INSERT INTO Popularidad (
  Id_popularidad,
  Partido_Politico_Id_Partido,
  Departamento_Id_departamento,
  Popularidad,
  Provincia,
  Fecha_popularidad
) VALUES
(74, 1, 6, 72, 'Aniceto Arce', '2025-04-01'),
(75, 1, 6, 69, 'Burdett O''Connor', '2025-04-01'),
(76, 1, 6, 75, 'Cercado', '2025-04-01'),
(77, 1, 6, 70, 'Eustaquio Méndez', '2025-04-01'),
(78, 1, 6, 74, 'Gran Chaco', '2025-04-01'),
(79, 1, 6, 68, 'José María Avilés', '2025-04-01');

-- Popularidad del partido 1 en todas las provincias de Santa Cruz (ID = 7)
INSERT INTO Popularidad (
  Id_popularidad,
  Partido_Politico_Id_Partido,
  Departamento_Id_departamento,
  Popularidad,
  Provincia,
  Fecha_popularidad
) VALUES
(80, 1, 7, 75, 'Andrés Ibáñez', '2025-04-01'),
(81, 1, 7, 69, 'Ángel Sandoval', '2025-04-01'),
(82, 1, 7, 70, 'Chiquitos', '2025-04-01'),
(83, 1, 7, 73, 'Cordillera', '2025-04-01'),
(84, 1, 7, 68, 'Florida', '2025-04-01'),
(85, 1, 7, 74, 'Germán Busch', '2025-04-01'),
(86, 1, 7, 71, 'Guarayos', '2025-04-01'),
(87, 1, 7, 77, 'Ichilo', '2025-04-01'),
(88, 1, 7, 70, 'Manuel María Caballero', '2025-04-01'),
(89, 1, 7, 72, 'Ñuflo de Chávez', '2025-04-01'),
(90, 1, 7, 76, 'Obispo Santistevan', '2025-04-01'),
(91, 1, 7, 67, 'Sara', '2025-04-01'),
(92, 1, 7, 69, 'Vallegrande', '2025-04-01'),
(93, 1, 7, 73, 'Velasco', '2025-04-01'),
(94, 1, 7, 78, 'Warnes', '2025-04-01');

-- Popularidad del partido 1 en todas las provincias de Beni (ID = 8)
INSERT INTO Popularidad (
  Id_popularidad,
  Partido_Politico_Id_Partido,
  Departamento_Id_departamento,
  Popularidad,
  Provincia,
  Fecha_popularidad
) VALUES
(95, 1, 8, 72, 'Cercado', '2025-04-01'),
(96, 1, 8, 70, 'Iténez', '2025-04-01'),
(97, 1, 8, 74, 'José Ballivián', '2025-04-01'),
(98, 1, 8, 68, 'Mamoré', '2025-04-01'),
(99, 1, 8, 73, 'Marbán', '2025-04-01'),
(100, 1, 8, 71, 'Moxos', '2025-04-01'),
(101, 1, 8, 69, 'Vaca Díez', '2025-04-01'),
(102, 1, 8, 75, 'Yacuma', '2025-04-01');

-- Popularidad del partido 1 en todas las provincias de Pando (ID = 9)
INSERT INTO Popularidad (
  Id_popularidad,
  Partido_Politico_Id_Partido,
  Departamento_Id_departamento,
  Popularidad,
  Provincia,
  Fecha_popularidad
) VALUES
(103, 1, 9, 70, 'Abuná', '2025-04-01'),
(104, 1, 9, 72, 'Federico Román', '2025-04-01'),
(105, 1, 9, 68, 'Madre de Dios', '2025-04-01'),
(106, 1, 9, 75, 'Manuripi', '2025-04-01'),
(107, 1, 9, 74, 'Nicolás Suárez', '2025-04-01');
