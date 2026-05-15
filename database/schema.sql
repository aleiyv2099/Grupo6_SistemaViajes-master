-- Sistema de Agencia de Viajes — Grupo 6
-- Esquema completo con datos de prueba

CREATE DATABASE IF NOT EXISTS sistemareserva CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE sistemareserva;

-- Usuario de la aplicación
CREATE USER IF NOT EXISTS 'mi_user'@'localhost' IDENTIFIED BY 'admin123';
GRANT ALL PRIVILEGES ON sistemareserva.* TO 'mi_user'@'localhost';
FLUSH PRIVILEGES;

-- -------------------------------------------------------

DROP TABLE IF EXISTS `detalle_factura`;
DROP TABLE IF EXISTS `facturas`;
DROP TABLE IF EXISTS `mascotas`;
DROP TABLE IF EXISTS `reservas`;
DROP TABLE IF EXISTS `clientes`;
DROP TABLE IF EXISTS `usuario`;

CREATE TABLE `clientes` (
  `CodigoCliente` int NOT NULL AUTO_INCREMENT,
  `DNI` varchar(20) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Telefono` varchar(20) DEFAULT NULL,
  `Direccion` varchar(200) DEFAULT NULL,
  `Estado` tinyint DEFAULT '1',
  PRIMARY KEY (`CodigoCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `reservas` (
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

CREATE TABLE `facturas` (
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

CREATE TABLE `detalle_factura` (
  `id` int NOT NULL AUTO_INCREMENT,
  `CodigoFactura` int NOT NULL,
  `CodigoReserva` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `CodigoFactura` (`CodigoFactura`),
  KEY `CodigoReserva` (`CodigoReserva`),
  CONSTRAINT `detalle_factura_ibfk_1` FOREIGN KEY (`CodigoFactura`) REFERENCES `facturas` (`CodigoFactura`),
  CONSTRAINT `detalle_factura_ibfk_2` FOREIGN KEY (`CodigoReserva`) REFERENCES `reservas` (`CodigoReserva`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `mascotas` (
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

CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `correo` varchar(100) NOT NULL,
  `pass` varchar(100) NOT NULL,
  `rol` varchar(50) DEFAULT 'vendedor',
  PRIMARY KEY (`id`),
  UNIQUE KEY `correo` (`correo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- -------------------------------------------------------
-- Datos de prueba
-- -------------------------------------------------------

INSERT INTO `clientes` VALUES
  (54,'1234567890','Carlos Pérez','0991111111','Av. Principal 123',1),
  (55,'0987654321','Ana López','0992222222','Calle 5 de Junio',1);

INSERT INTO `reservas` VALUES
  (57,54,'Quito','Guayaquil','2026-05-01','08:00','A1',150.00,0),
  (60,54,'Guayaquil','Cuenca','2026-05-10','10:00','B3',80.00,0),
  (69,55,'Cuenca','Quito','2026-05-15','14:00','C5',120.00,0);

INSERT INTO `facturas` VALUES
  (26,'2026-04-27',231.20,'Efectivo','Pagado',54,1),
  (28,'2026-04-27',120.00,'Efectivo','Pendiente',55,1);

INSERT INTO `detalle_factura` VALUES
  (1,26,57),(2,26,60);

INSERT INTO `mascotas` VALUES
  (1,54,'Rocky','Labrador',25.50,'Vacunas al día',1),
  (2,55,'Luna','Siamés',3.80,'Gato doméstico tranquilo',1);

INSERT INTO `usuario` VALUES
  (1,'Vendedor Demo','vendedor@gmail.com','12345','vendedor'),
  (2,'Admin','admin@gmail.com','12345','admin');
