-- Script de creacion de la base de datos speedfast_db
-- Nota: el script original del enunciado creaba la base "speedfast" pero
-- luego hacia USE speedfast_db (nombre distinto). Aqui se corrige usando
-- un unico nombre consistente en todo el script: speedfast_db.
-- Tambien se completa la tabla "entrega", que en el PDF quedaba cortada
-- sin la FK hacia repartidor ni el cierre del CREATE TABLE.

CREATE DATABASE IF NOT EXISTS speedfast_db;
USE speedfast_db;

CREATE TABLE repartidor (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL
);

CREATE TABLE pedido (
  id INT AUTO_INCREMENT PRIMARY KEY,
  direccion VARCHAR(150) NOT NULL,
  tipo VARCHAR(30) NOT NULL,      -- COMIDA | ENCOMIENDA | EXPRESS
  estado VARCHAR(20) NOT NULL     -- PENDIENTE | EN_REPARTO | ENTREGADO
);

CREATE TABLE entrega (
  id INT AUTO_INCREMENT PRIMARY KEY,
  id_pedido INT NOT NULL,
  id_repartidor INT NOT NULL,
  fecha DATE NOT NULL,
  hora TIME NOT NULL,
  FOREIGN KEY (id_pedido) REFERENCES pedido(id),
  FOREIGN KEY (id_repartidor) REFERENCES repartidor(id)
);
