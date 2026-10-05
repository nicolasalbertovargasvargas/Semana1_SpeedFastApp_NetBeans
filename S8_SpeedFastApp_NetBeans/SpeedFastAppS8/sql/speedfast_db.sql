-- Script de creacion de la base de datos speedfast_db (Semana 8)
-- IMPORTANTE: este esquema usa nombres de tabla en PLURAL
-- (repartidores, pedidos, entregas) y columnas ENUM, a diferencia
-- del esquema de la Semana 7 que usaba nombres en singular. Si ya
-- tenias la base de datos de la semana pasada, debes recrearla con
-- este script (o migrar las tablas) antes de continuar.

CREATE DATABASE IF NOT EXISTS speedfast_db;
USE speedfast_db;

DROP TABLE IF EXISTS entregas;
DROP TABLE IF EXISTS pedidos;
DROP TABLE IF EXISTS repartidores;

CREATE TABLE repartidores (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL
);

CREATE TABLE pedidos (
  id INT AUTO_INCREMENT PRIMARY KEY,
  direccion VARCHAR(100) NOT NULL,
  tipo ENUM('COMIDA','ENCOMIENDA','EXPRESS'),
  estado ENUM('PENDIENTE','EN_REPARTO','ENTREGADO')
);

CREATE TABLE entregas (
  id INT AUTO_INCREMENT PRIMARY KEY,
  id_pedido INT,
  id_repartidor INT,
  fecha DATE,
  hora TIME,
  FOREIGN KEY (id_pedido) REFERENCES pedidos(id),
  FOREIGN KEY (id_repartidor) REFERENCES repartidores(id)
);
