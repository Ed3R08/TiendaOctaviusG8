-- =============================================
-- Script Completo para "TiendaOctavius"
-- =============================================

-- Elimina el esquema y los usuarios si existen
DROP SCHEMA IF EXISTS tiendaOctavius;
DROP USER IF EXISTS octavius_user;
DROP USER IF EXISTS octavius_reportes;

-- Crea el esquema
CREATE SCHEMA tiendaOctavius;
USE tiendaOctavius;

-- Crea los usuarios para la base de datos
CREATE USER 'octavius_user'@'%' IDENTIFIED BY 'Clave_123';
CREATE USER 'octavius_reportes'@'%' IDENTIFIED BY 'Reporte_Octavius_123';

-- Asigna los privilegios
GRANT ALL PRIVILEGES ON tiendaOctavius.* TO 'octavius_user'@'%';
GRANT SELECT ON tiendaOctavius.* TO 'octavius_reportes'@'%';
FLUSH PRIVILEGES;

-----------------------------------------------------------
-- 1. Crear la tabla "categoria"
-----------------------------------------------------------
CREATE TABLE categoria (
  id_categoria BIGINT NOT NULL AUTO_INCREMENT,
  descripcion VARCHAR(30) NOT NULL,
  ruta_imagen VARCHAR(1024),
  activo BOOLEAN,
  PRIMARY KEY (id_categoria)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;

-- Inserta las categorías (ejemplo con imágenes relacionadas con piscinas)
INSERT INTO categoria (descripcion, ruta_imagen, activo)
VALUES
  ('Servicios', 'https://images.unsplash.com/photo-1561484937-4c64d4650b0e?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60', true),
  ('Químicos', 'https://images.unsplash.com/photo-1601758003122-50bb1b89f3e4?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60', true),
  ('Accesorios', 'https://images.unsplash.com/photo-1581091012184-3c22b15e3b81?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60', true),
  ('Equipos', 'https://images.unsplash.com/photo-1591012911207-0f22d7205f47?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60', true);

-----------------------------------------------------------
-- 2. Crear la tabla "usuario"
-----------------------------------------------------------
CREATE TABLE usuario (
  id_usuario BIGINT NOT NULL AUTO_INCREMENT,
  username VARCHAR(20) NOT NULL,
  password VARCHAR(512) NOT NULL,
  nombre VARCHAR(20) NOT NULL,
  apellidos VARCHAR(30) NOT NULL,
  correo VARCHAR(75),
  telefono VARCHAR(15),
  ruta_imagen VARCHAR(1024),
  activo BOOLEAN,
  PRIMARY KEY (id_usuario)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;

-- Inserta registros de ejemplo en usuario (sin especificar id_usuario)
INSERT INTO usuario (username, password, nombre, apellidos, correo, telefono, ruta_imagen, activo)
VALUES 
  ('juan', '$2a$10$P1.w58XvnaYQUQgZUCk4aO/RTRl8EValluCqB3S2VMLTbRt.tlre.', 'Juan', 'Castro Mora', 'jcastro@gmail.com', '4556-8978', 'https://img2.rtve.es/i/?w=1600&i=1677587980597.jpg', true),
  ('rebeca', '$2a$10$GkEj.ZzmQa/aEfDmtLIh3udIH5fMphx/35d0EYeqZL5uzgCJ0lQRi', 'Rebeca', 'Contreras Mora', 'acontreras@gmail.com', '5456-8789', 'https://media.licdn.com/dms/image/v2/C5603AQGwjJ5ht4bWXQ/profile-displayphoto-shrink_200_200/profile-displayphoto-shrink_200_200/0/1661476259292?e=2147483647&v=beta&t=9_i5zTdqHRMSXlb9H4TuWkWeRGQXmaZLjxkBlWsg2lg', true),
  ('pedro', '$2a$10$koGR7eS22Pv5KdaVJKDcge04ZB53iMiw76.UjHPY.XyVYlYqXnPbO', 'Pedro', 'Mena Loria', 'lmena@gmail.com', '7898-8936', 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/fd/Eduardo_de_Pedro_2019.jpg/480px-Eduardo_de_Pedro_2019.jpg?20200109230854', true);

-----------------------------------------------------------
-- 3. Crear la tabla "producto"
-----------------------------------------------------------
CREATE TABLE producto (
  id_producto BIGINT NOT NULL AUTO_INCREMENT,
  id_categoria BIGINT NOT NULL,
  descripcion VARCHAR(30) NOT NULL,
  detalle VARCHAR(1600) NOT NULL,
  precio DOUBLE,
  existencias INT,
  ruta_imagen VARCHAR(1024),
  activo BOOLEAN,
  PRIMARY KEY (id_producto),
  FOREIGN KEY fk_producto_categoria (id_categoria) REFERENCES categoria(id_categoria)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;

-- Inserta productos para cada categoría

-- Para "Servicios" (id_categoria = 1)
INSERT INTO producto (id_categoria, descripcion, detalle, precio, existencias, ruta_imagen, activo)
VALUES
  (1, 'Mantenimiento', 'Servicio integral de mantenimiento de piscinas, incluyendo revisión y limpieza profunda de equipos y estructuras.', 150.00, 10, 'https://images.unsplash.com/photo-1561484937-4c64d4650b0e?ixlib=rb-4.0.3&auto=format&fit=crop&w=800&q=60', true),
  (1, 'Limpieza', 'Servicio regular de limpieza para mantener el agua cristalina y libre de impurezas.', 100.00, 15, 'https://via.placeholder.com/150?text=Limpieza', true),
  (1, 'Visita Técnica', 'Visita técnica para diagnóstico y solución de problemas en el sistema de la piscina.', 200.00, 5, 'https://via.placeholder.com/150?text=Visita+T%C3%A9cnica', true);

-- Para "Químicos" (id_categoria = 2)
INSERT INTO producto (id_categoria, descripcion, detalle, precio, existencias, ruta_imagen, activo)
VALUES
  (2, 'Cloro Granulado', 'Cloro granulado para desinfección rápida y efectiva del agua de la piscina.', 50.00, 100, 'https://via.placeholder.com/150?text=Cloro+Granulado', true),
  (2, 'Cloro en Tabletas', 'Tabletas de cloro de liberación controlada para el mantenimiento diario.', 80.00, 80, 'https://via.placeholder.com/150?text=Cloro+Tabletas', true),
  (2, 'Alguicida', 'Producto para eliminar y prevenir el crecimiento de algas en la piscina.', 30.00, 120, 'https://via.placeholder.com/150?text=Alguicida', true),
  (2, 'Clarificador', 'Producto que mejora la claridad del agua eliminando partículas en suspensión.', 25.00, 150, 'https://via.placeholder.com/150?text=Clarificador', true);

-- Para "Accesorios" (id_categoria = 3)
INSERT INTO producto (id_categoria, descripcion, detalle, precio, existencias, ruta_imagen, activo)
VALUES
  (3, 'Boquillas de Retorno', 'Boquillas para un óptimo retorno del agua en piscinas.', 35.00, 50, 'https://via.placeholder.com/150?text=Boquillas', true),
  (3, 'Hidrojets', 'Sistemas de hidrojets para masaje y mejor circulación del agua.', 120.00, 30, 'https://via.placeholder.com/150?text=Hidrojets', true),
  (3, 'Parrillas de Fondo', 'Parrillas que facilitan el drenaje y la limpieza del fondo de la piscina.', 45.00, 40, 'https://via.placeholder.com/150?text=Parrillas', true),
  (3, 'Skimmer', 'Skimmer para recoger hojas y residuos en la superficie del agua.', 60.00, 25, 'https://via.placeholder.com/150?text=Skimmer', true),
  (3, 'Rebalse', 'Sistema de rebalse para evitar el sobrellenado de la piscina.', 55.00, 20, 'https://via.placeholder.com/150?text=Rebalse', true);

-- Para "Equipos" (id_categoria = 4)
INSERT INTO producto (id_categoria, descripcion, detalle, precio, existencias, ruta_imagen, activo)
VALUES
  (4, 'Bomba de Agua', 'Bomba de alta eficiencia para la circulación y recirculación del agua de la piscina.', 300.00, 10, 'https://via.placeholder.com/150?text=Bomba', true),
  (4, 'Filtro de Piscina', 'Filtro de alta capacidad para mantener el agua limpia y libre de impurezas.', 400.00, 8, 'https://via.placeholder.com/150?text=Filtro', true),
  (4, 'Clorinador', 'Sistema automático que dosifica cloro en la piscina para garantizar la desinfección continua.', 250.00, 12, 'https://via.placeholder.com/150?text=Clorinador', true);

-----------------------------------------------------------
-- 4. Crear la tabla "factura"
-----------------------------------------------------------
CREATE TABLE factura (
  id_factura BIGINT NOT NULL AUTO_INCREMENT,
  id_usuario BIGINT NOT NULL,
  fecha DATE,
  total DOUBLE,
  estado INT,  -- 1 = Activa, 2 = Pagada, 3 = Anulada
  PRIMARY KEY (id_factura),
  FOREIGN KEY fk_factura_usuario (id_usuario) REFERENCES usuario(id_usuario)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;

-----------------------------------------------------------
-- 5. Crear la tabla "venta"
-----------------------------------------------------------
CREATE TABLE venta (
  id_venta BIGINT NOT NULL AUTO_INCREMENT,
  id_factura BIGINT NOT NULL,
  id_producto BIGINT NOT NULL,
  precio DOUBLE,
  cantidad INT,
  PRIMARY KEY (id_venta),
  FOREIGN KEY fk_venta_factura (id_factura) REFERENCES factura(id_factura),
  FOREIGN KEY fk_venta_producto (id_producto) REFERENCES producto(id_producto)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;

-----------------------------------------------------------
-- 6. Crear la tabla "rol"
-----------------------------------------------------------
CREATE TABLE rol (
  id_rol BIGINT NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(20) NOT NULL,
  id_usuario BIGINT,
  PRIMARY KEY (id_rol),
  FOREIGN KEY fk_rol_usuario (id_usuario) REFERENCES usuario(id_usuario)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;

-- Inserta roles de ejemplo (suponiendo que los id de usuario sean 1, 2 y 3)
INSERT INTO rol (nombre, id_usuario) VALUES 
  ('ADMIN', 1),
  ('VENDEDOR', 1),
  ('USER', 1),
  ('VENDEDOR', 2),
  ('USER', 2),
  ('USER', 3);

-----------------------------------------------------------
-- 7. Crear la tabla "ruta"
-----------------------------------------------------------
CREATE TABLE ruta (
    id_ruta BIGINT NOT NULL AUTO_INCREMENT,
    patron VARCHAR(255) NOT NULL,
    rol_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_ruta)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;

INSERT INTO ruta (patron, rol_name) VALUES 
  ('/producto/nuevo', 'ADMIN'),
  ('/producto/guardar', 'ADMIN'),
  ('/producto/modificar/**', 'ADMIN'),
  ('/producto/eliminar/**', 'ADMIN'),
  ('/categoria/nuevo', 'ADMIN'),
  ('/categoria/guardar', 'ADMIN'),
  ('/categoria/modificar/**', 'ADMIN'),
  ('/categoria/eliminar/**', 'ADMIN'),
  ('/usuario/**', 'ADMIN'),
  ('/constante/**', 'ADMIN'),
  ('/rol/**', 'ADMIN'),
  ('/ruta/**', 'ADMIN'),
  ('/producto/listado', 'VENDEDOR'),
  ('/categoria/listado', 'VENDEDOR'),
  ('/pruebas/**', 'VENDEDOR'),
  ('/reportes/**', 'VENDEDOR'),
  ('/facturar/carrito', 'USER'),
  ('/payment/**', 'USER');

-----------------------------------------------------------
-- 8. Crear la tabla "ruta_permit"
-----------------------------------------------------------
CREATE TABLE ruta_permit (
    id_ruta BIGINT NOT NULL AUTO_INCREMENT,
    patron VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_ruta)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;

INSERT INTO ruta_permit (patron) VALUES 
  ('/'),
  ('/index'),
  ('/errores/**'),
  ('/carrito/**'),
  ('/registro/**'),
  ('/js/**'),
  ('/webjars/**');
----------------------------------------------------------------------
-- Se debe anadir de mas el de fav para el icono de la parte superior
----------------------------------------------------------------------
INSERT INTO ruta_permit (patron) VALUES 
  ('/fav/**')


-----------------------------------------------------------
-- 9. Crear la tabla "constante"
-----------------------------------------------------------
CREATE TABLE constante (
    id_constante BIGINT NOT NULL AUTO_INCREMENT,
    atributo VARCHAR(25) NOT NULL,
    valor VARCHAR(150) NOT NULL,
    PRIMARY KEY (id_constante)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8mb4;

INSERT INTO constante (atributo, valor) VALUES 
  ('dominio', 'localhost'),
  ('certificado', 'c:/cert'),
  ('dolar', '520.75'),
  ('paypal.client-id', 'AUjOjw5Q1I0QLTYjbvRS0j4Amd8xrUU2yL9UYyb3TOTcrazzd3G3lYRc6o7g9rOyZkfWEj2wxxDi0aRz'),
  ('paypal.client-secret', 'EMdb08VRlo8Vusd_f4aAHRdTE14ujnV9mCYPovSmXCquLjzWd_EbTrRrNdYrF1-C4D4o-57wvua3YD2u'),
  ('paypal.mode', 'sandbox'),
  ('urlPaypalCancel', 'http://localhost/payment/cancel'),
  ('urlPaypalSuccess', 'http://localhost/payment/success');

USE tiendaOctavius;
UPDATE usuario
SET 
  username = 'Eder',
  nombre = 'Eder',
  apellidos = 'Serrano Valerio',
  correo = 'ederseva@gmail.com',
  telefono = '83199289',
  ruta_imagen = 'https://i.imgur.com/5S1OuYL.jpg'
WHERE id_usuario = 1;

insert into ruta_permit (patron) values ('/fav/**');
SET SQL_SAFE_UPDATES = 0;

-- Actualiza la imagen para la categoría 'Servicios'
UPDATE categoria 
SET ruta_imagen = 'https://i.imgur.com/ZFt2HRb.jpg'
WHERE descripcion = 'Servicios';

-- Actualiza la imagen para la categoría 'Químicos'
UPDATE categoria 
SET ruta_imagen = 'https://i.imgur.com/ZFt2HRb.jpg'
WHERE descripcion = 'Químicos';

-- Actualiza la imagen para la categoría 'Accesorios'
UPDATE categoria 
SET ruta_imagen = 'https://i.imgur.com/mj0gYIx.jpg'
WHERE descripcion = 'Accesorios';

-- Actualiza la imagen para la categoría 'Equipos'
UPDATE categoria 
SET ruta_imagen = 'https://i.imgur.com/qV97892.jpg'
WHERE descripcion = 'Equipos';


SET SQL_SAFE_UPDATES = 1;
-- Desactiva el modo seguro para esta sesión (opcional)
SET SQL_SAFE_UPDATES = 0;

-- Para la categoría "Servicios"
UPDATE producto 
SET ruta_imagen = 'https://i.imgur.com/ZFt2HRb.jpg'
WHERE descripcion = 'Mantenimiento'
LIMIT 1;

UPDATE producto 
SET ruta_imagen = 'https://i.imgur.com/CB2H0XY.jpg'
WHERE descripcion = 'Limpieza'
LIMIT 1;

UPDATE producto 
SET ruta_imagen = 'https://i.imgur.com/sisEdjN.jpg'
WHERE descripcion = 'Visita Técnica'
LIMIT 1;

-- Para la categoría "Químicos"
UPDATE producto 
SET ruta_imagen = 'https://i.imgur.com/yUnRO97.jpg'
WHERE descripcion = 'Cloro Granulado'
LIMIT 1;

UPDATE producto 
SET ruta_imagen = 'https://i.imgur.com/A7MS0u3.jpg'
WHERE descripcion = 'Cloro en Tabletas'
LIMIT 1;

UPDATE producto 
SET ruta_imagen = 'https://i.imgur.com/qmNhWJ2.jpg'
WHERE descripcion = 'Alguicida'
LIMIT 1;

UPDATE producto 
SET ruta_imagen = 'https://i.imgur.com/qP0Ew8p.jpg'
WHERE descripcion = 'Clarificador'
LIMIT 1;

-- Para la categoría "Accesorios"
UPDATE producto 
SET ruta_imagen = 'https://i.imgur.com/0AloD4A.jpg'
WHERE descripcion = 'Boquillas de Retorno'
LIMIT 1;

UPDATE producto 
SET ruta_imagen = 'https://i.imgur.com/7jaExhD.jpg'
WHERE descripcion = 'Hidrojets'
LIMIT 1;

UPDATE producto 
SET ruta_imagen = 'https://i.imgur.com/PcKDLFI.jpg'
WHERE descripcion = 'Parrillas de Fondo'
LIMIT 1;

UPDATE producto 
SET ruta_imagen = 'https://i.imgur.com/AnYNpT6.jpg'
WHERE descripcion = 'Skimmer'
LIMIT 1;

UPDATE producto 
SET ruta_imagen = 'https://i.imgur.com/JX0BtQH.jpg'
WHERE descripcion = 'Rebalse'
LIMIT 1;

-- Para la categoría "Equipos"
UPDATE producto 
SET ruta_imagen = 'https://i.imgur.com/dhjw4Cm.jpg'
WHERE descripcion = 'Bomba de Agua'
LIMIT 1;

UPDATE producto 
SET ruta_imagen = 'https://i.imgur.com/0WqE6yU.jpg'
WHERE descripcion = 'Filtro de Piscina'
LIMIT 1;

UPDATE producto 
SET ruta_imagen = 'https://i.imgur.com/y5qAZYw.jpg'
WHERE descripcion = 'Clorinador'
LIMIT 1;

-- Vuelve a activar el modo seguro (opcional)
SET SQL_SAFE_UPDATES = 1;