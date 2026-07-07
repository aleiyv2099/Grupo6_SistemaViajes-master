-- Sistema de Agencia de Viajes -- Grupo 6
-- Ejecuta este archivo en MySQL para crear la base de datos y cargar los datos de ejemplo

CREATE DATABASE IF NOT EXISTS sistemareserva CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE sistemareserva;

-- Usuario de la aplicación (debe coincidir con src/main/resources/database.properties)
CREATE USER IF NOT EXISTS 'viajes'@'localhost' IDENTIFIED BY 'Viajes123#';
GRANT ALL PRIVILEGES ON sistemareserva.* TO 'viajes'@'localhost';
FLUSH PRIVILEGES;

CREATE TABLE IF NOT EXISTS `clientes` (
  `CodigoCliente` int NOT NULL AUTO_INCREMENT,
  `DNI` varchar(20) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Telefono` varchar(20) DEFAULT NULL,
  `Direccion` varchar(200) DEFAULT NULL,
  `Estado` tinyint DEFAULT '1',
  PRIMARY KEY (`CodigoCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `reservas` (
  `CodigoReserva` int NOT NULL AUTO_INCREMENT,
  `CodigoCliente` int NOT NULL,
  `Origen` varchar(100) NOT NULL,
  `Destino` varchar(100) NOT NULL,
  `FechaViaje` varchar(20) NOT NULL,
  `HoraSalida` varchar(10) NOT NULL,
  `AsientoAsignado` varchar(10) NOT NULL,
  `PrecioPasaje` decimal(10,2) NOT NULL,
  `Estado` tinyint DEFAULT '1',
  PRIMARY KEY (`CodigoReserva`),
  KEY `CodigoCliente` (`CodigoCliente`),
  CONSTRAINT `reservas_ibfk_1` FOREIGN KEY (`CodigoCliente`) REFERENCES `clientes` (`CodigoCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `facturas` (
  `CodigoFactura` int NOT NULL AUTO_INCREMENT,
  `FechaEmision` date NOT NULL,
  `MontoTotal` decimal(10,2) NOT NULL,
  `MetodoPago` varchar(50) NOT NULL,
  `EstadoFactura` varchar(50) NOT NULL,
  `CodigoCliente` int NOT NULL,
  `Estado` tinyint DEFAULT '1',
  PRIMARY KEY (`CodigoFactura`),
  KEY `CodigoCliente` (`CodigoCliente`),
  CONSTRAINT `facturas_ibfk_1` FOREIGN KEY (`CodigoCliente`) REFERENCES `clientes` (`CodigoCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `detalle_factura` (
  `id` int NOT NULL AUTO_INCREMENT,
  `CodigoFactura` int NOT NULL,
  `CodigoReserva` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `CodigoFactura` (`CodigoFactura`),
  KEY `CodigoReserva` (`CodigoReserva`),
  CONSTRAINT `detalle_factura_ibfk_1` FOREIGN KEY (`CodigoFactura`) REFERENCES `facturas` (`CodigoFactura`),
  CONSTRAINT `detalle_factura_ibfk_2` FOREIGN KEY (`CodigoReserva`) REFERENCES `reservas` (`CodigoReserva`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `mascotas` (
  `id_mascota` int NOT NULL AUTO_INCREMENT,
  `CodigoCliente` int NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `raza` varchar(50) NOT NULL,
  `peso` decimal(5,2) NOT NULL,
  `observaciones` text,
  `Estado` tinyint DEFAULT '1',
  PRIMARY KEY (`id_mascota`),
  KEY `fk_mascotas_clientes` (`CodigoCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `pass` varchar(100) NOT NULL,
  `rol` varchar(50) DEFAULT 'vendedor',
  PRIMARY KEY (`id`),
  UNIQUE KEY `correo` (`correo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT IGNORE INTO `clientes` VALUES
  (54,'1234567890','Carlos Perez','0991111111','Av. Principal 123',1),
  (55,'0987654321','Ana Lopez','0992222222','Calle 5 de Junio',1);

INSERT IGNORE INTO `reservas` VALUES
  (57,54,'Quito','Guayaquil','2026-05-01','08:00','A1',150.00,0),
  (60,54,'Guayaquil','Cuenca','2026-05-10','10:00','B3',80.00,0),
  (69,55,'Cuenca','Quito','2026-05-15','14:00','C5',120.00,0);

INSERT IGNORE INTO `facturas` VALUES
  (26,'2026-04-27',231.20,'Efectivo','Pagado',54,1),
  (28,'2026-04-27',120.00,'Efectivo','Pendiente',55,1);

INSERT IGNORE INTO `detalle_factura` VALUES
  (1,26,57),(2,26,60);

INSERT IGNORE INTO `mascotas` VALUES
  (1,54,'Rocky','Labrador',25.50,'Vacunas al dia',1),
  (2,55,'Luna','Siames',3.80,'Gato domestico tranquilo',1);

INSERT IGNORE INTO `usuario` VALUES
  (1,'Vendedor Demo','vendedor@gmail.com','12345','vendedor'),
  (2,'Admin','admin@gmail.com','12345','admin');

-- Módulo 2 — Gestión de Equipaje
CREATE TABLE IF NOT EXISTS `equipaje` (
  `id_equipaje` int NOT NULL AUTO_INCREMENT,
  `CodigoCliente` int NOT NULL,
  `tipo_equipaje` varchar(50) NOT NULL,
  `peso` decimal(6,2) NOT NULL,
  `cantidad` int NOT NULL,
  PRIMARY KEY (`id_equipaje`),
  KEY `CodigoCliente` (`CodigoCliente`),
  CONSTRAINT `fk_equipaje_cliente` FOREIGN KEY (`CodigoCliente`) REFERENCES `clientes` (`CodigoCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT IGNORE INTO `equipaje` VALUES
  (1,54,'Maleta grande',23.00,1),
  (2,54,'Equipaje de mano',8.00,1),
  (3,55,'Maleta mediana',15.50,2);

-- Módulo 5 — Soporte al Cliente (tickets) — Autor: Nicole Malavé
CREATE TABLE IF NOT EXISTS `soporte` (
  `codigoTicket` int NOT NULL AUTO_INCREMENT,
  `nombreCliente` varchar(100) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `telefono` varchar(10) DEFAULT NULL,
  `tipoProblema` varchar(40) NOT NULL,
  `descripcion` varchar(300) DEFAULT NULL,
  `prioridad` varchar(10) NOT NULL DEFAULT 'Media',
  `estado` varchar(20) NOT NULL DEFAULT 'Pendiente',
  `responsable` varchar(100) DEFAULT 'Sin asignar',
  PRIMARY KEY (`codigoTicket`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT IGNORE INTO `soporte`
  (`codigoTicket`,`nombreCliente`,`correo`,`telefono`,`tipoProblema`,`descripcion`,`prioridad`,`estado`,`responsable`) VALUES
  (1,'Carlos Perez','carlos@gmail.com','0991112223','Error Reserva','No puede cambiar el asiento de su vuelo','Alta','Pendiente','Sin asignar'),
  (2,'Ana Lopez','ana@gmail.com','0987654321','Facturación','Cobro duplicado en su tarjeta','Media','En Proceso','Nicole Malave');
