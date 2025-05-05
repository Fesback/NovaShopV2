# 🛒 NovaShop Backend

> **NovaShop, un sistema de e-commerce construido con Java + Spring Boot**, utilizando una arquitectura por **capas** (Layered Architecture). Este backend está diseñado para trabajar de forma desacoplada con un frontend desarrollado en **Angular**.

---

## 🚀 Tecnologías utilizadas

- ☕ Java 17
- 🧩 Spring Boot
- 🔄 Spring Data JPA
- 🔐 Spring Security + JWT
- 🐬 MySQL
- 💼 Maven
- 🧠 Lombok
- 🔬 Postman (pruebas de endpoints)
- 🏗️ Arquitectura por capas (Layered Architecture)

## 🌟 Novedades en la última versión
- ✅ **Módulo de Carrito** completo con gestión de ítems 🛍️  
- 🧾 **Generación de boletas PDF** integrada 📄✨  
- 🚚 **Sistema de pedidos** con seguimiento de estados (Pendiente/Enviado/Entregado) 📦  
- 👨💻 **Gestión avanzada de usuarios** con roles (ADMIN/USER) y permisos 🔐  
- 🛡️ **Seguridad reforzada** con JWT y refresh token automático 🔄  

---

## 🚀 Tecnologías utilizadas  
![Java](https://img.shields.io/badge/Java-17-%23ED8B00) 
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.0-%236DB33F) 
![Spring Security](https://img.shields.io/badge/Spring_Security-6.1.0-%236DB33F) 
![JWT](https://img.shields.io/badge/JWT-0.11.5-%23000000)  
![MySQL](https://img.shields.io/badge/MySQL-8.0-%234479A1) 
![Hibernate](https://img.shields.io/badge/Hibernate-6.4-%2359666C) 
![iTextPDF](https://img.shields.io/badge/iTextPDF-7.2.5-%23FF6C37)  
![Maven](https://img.shields.io/badge/Maven-3.9-%23C71A36) 
![Lombok](https://img.shields.io/badge/Lombok-1.18-%23000000) 
![Postman](https://img.shields.io/badge/Postman-10.0-%23FF6C37)

---

## 🔥 Características Principales  
### 🛍️ Módulo de Productos  
- 📋 CRUD completo de productos con validaciones  
- 🖼️ Gestión de imágenes y stock  
- 🔍 Búsqueda avanzada con filtros (categoría, precio, nombre)  
- 📊 Paginación y ordenamiento  

### 🛒 Sistema de Carrito  
- 🧺 Carrito persistente por usuario  
- 🔄 Sincronización en tiempo real  
- 💰 Cálculo automático de totales  
- 🚨 Validación de stock  

### 📦 Módulo de Pedidos  
- 🧾 Generación de boletas PDF profesionales  
- 📦 Seguimiento de estados (Pendiente → Enviado → Entregado)  
- 📍 Gestión de direcciones de envío  
- 📊 Historial completo de compras  

### 🔐 Seguridad Avanzada  
- 🔑 Autenticación JWT con refresh tokens  
- 👮♂️ Control de acceso por roles (ADMIN/USER)  
- 🛡️ Protección contra CSRF y XSS  
- 🔄 Renovación automática de tokens  

### 👥 Gestión de Usuarios  
- 📝 Registro con validación de email único  
- 🔄 Actualización de perfil segura  
- 🛑 Desactivación de cuentas (borrado lógico)  
- 📊 Panel de administración (solo ADMIN)  


## 🎯 Objetivos del proyecto

- Construir un backend *escalable, mantenible y profesional*.
- Aplicar buenas prácticas como uso de DTOs, inyección de dependencias y separación de responsabilidades.
- Implementar seguridad robusta con *Spring Security y JWT*.
- Documentar endpoints, manejar errores correctamente y prepararlo para producción.
- Crear una API REST lista para integrarse con cualquier cliente frontend (Angular, móvil, etc.)

---

## 📁 Estructura del proyecto
```
📦 com.fescode.novashop
├── 📂 controller/ → 🌐 Controladores REST (API)
│ └── AuthController.java
│
├── 📂 dto/ → ✉️ Objetos de transferencia
│ ├── 📂 request/ → 📥 Solicitudes (Login/Register)
│ │ ├── LoginRequestDTO.java
│ │ └── RegisterRequestDTO.java
│ │
│ └── 📂 response/ → 📤 Respuestas (Login/Register)
│ ├── LoginResponseDTO.java
│ └── RegisterResponseDTO.java
│
├── 📂 entity/ → 🧱 Entidades JPA
│ ├── Role.java
│ └── Usuario.java
│
├── 📂 enums/ → 🏷️ Enumeraciones
│ └── RoleList.java
│
├── 📂 exception/ → ❗ Manejo de errores
│ └── RoleNotFoundException.java
│
├── 📂 repository/ → 🗄️ Repositorios JPA
│ ├── RoleRepository.java
│ └── UsuarioRepository.java
│
├── 📂 security/ → 🔐 Módulo de Seguridad
│ ├── 📂 config/ → ⚙️ Configuraciones
│ │ ├── CorsConfig.java
│ │ └── SecurityConfig.java
│ │
│ ├── 📂 jwt/ → 🎟️ JWT Utilities
│ │ ├── JwtAuthenticationFilter.java
│ │ └── JwtService.java
│ │
│ └── 📂 user/ → 👤 User Details
│ ├── UserDetailsImpl.java
│ └── UserDetailsServiceImpl.java
│
├── 📂 service/ → 🧠 Servicios
│ └── 📂 impl/
│ └── AuthService.java
│
└── 📄 NovaShopApplication.java → 🚀 Clase principal
```
---

## 🧩 Módulos incluidos

| Módulo      | Descripción                                 | Estado      |
|-------------|---------------------------------------------|-------------|
| Categoría   | CRUD con DTOs, validación y pruebas         | ✅ Terminado |
| Producto    | CRUD estructurado y validado                | ✅ Terminado |
| Usuario     | Registro, login (seguridad JWT)             | ✅ Terminado |
| Seguridad   | Protección de rutas con roles y JWT         | ✅ Terminado |
| Carrito     | Gestión de compras                          | ✅ Terminado |
| Rol         | Autorización basada en roles                | ✅ Terminado |

---

## 🔐 Seguridad (JWT + Spring Security)

El sistema manejará:
- Registro y login de usuarios
- Protección de rutas
- Generación y validación de tokens JWT
- Control de acceso por roles (ADMIN, USER)

---

## 🧑‍💻 Login y Registro

- **Login**:  Los usuarios pueden autenticarse mediante un sistema de login utilizando su correo electrónico y contraseña. El sistema genera un JWT que se enviará en las respuestas y se usará para autenticar las futuras peticiones.
- **Registro**: Los usuarios se pueden registrar proporcionando su correo electrónico, nombre, apellido, contraseña, dirección, y teléfono. Después del registro, se les asigna un rol de usuario predeterminado (cliente).

---
## 🛠 ¿Por qué rehacer el backend?

- El código anterior funcionaba, pero no seguía buenas prácticas.
- Esta nueva versión será más limpia, modular y escalable.
- Dará experiencia profesional real.
- Es más fácil integrar herramientas como Swagger, JWT, manejo global de errores, etc.

---

## ✅ Estado actual

- [x] Estructura inicial creada
- [x] CRUD de categoría implementado con DTOs y mapper
- [x] Seguridad con JWT (en desarrollo)
- [x] Documentación de API (pendiente con Swagger)
- [x] Integración con Angular (en curso)

---

## 🧪 Base de Datos

```
CREATE DATABASE IF NOT EXISTS novashop_db_v2;
USE novashop_db_v2;

-- ==========================
-- TABLAS
-- ==========================

-- Tabla roles
CREATE TABLE rol (
    id_rol BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre_rol VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255)
);

-- Tabla usuarios
CREATE TABLE usuario (
    id_usuario BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    direccion VARCHAR(255),
    telefono VARCHAR(20),
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE
);

-- Tabla usuario - rol (relación many to many)
CREATE TABLE usuariorol (
    id_usuario BIGINT,
    id_rol BIGINT,
    PRIMARY KEY (id_usuario, id_rol),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol) ON DELETE CASCADE
);

-- Tabla categorías
CREATE TABLE categoria (
    id_categoria BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre_categoria VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    imagen VARCHAR(255),
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

-- Tabla productos
CREATE TABLE producto (
    id_producto BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    imagen VARCHAR(255),
    id_categoria BIGINT,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria) ON DELETE SET NULL
);

-- Tabla carrito
CREATE TABLE carrito (
    id_carrito BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_usuario BIGINT NOT NULL,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

-- Tabla carrito item
CREATE TABLE carritoitem (
    id_item BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_carrito BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    cantidad INT NOT NULL DEFAULT 1,
    fecha_agregado DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_carrito) REFERENCES carrito(id_carrito) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto) ON DELETE CASCADE,
    UNIQUE KEY (id_carrito, id_producto)
);

-- Tabla pedidos
CREATE TABLE pedido (
    id_pedido BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_usuario BIGINT NOT NULL,
    fecha_pedido DATETIME DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('PENDIENTE', 'PROCESANDO', 'ENVIADO', 'ENTREGADO', 'CANCELADO') DEFAULT 'PENDIENTE',
    direccion_envio VARCHAR(255) NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    metodo_pago VARCHAR(50),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

-- Tabla detalle de pedido
CREATE TABLE detallepedido (
    id_detalle BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_pedido BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

-- ==========================
-- INSERTS INICIALES
-- ==========================

-- Insertar roles
INSERT INTO rol (nombre_rol, descripcion) VALUES
('ADMIN', 'Administrador del sistema con todos los permisos'),
('USER', 'Usuario Regular del sistema');

-- Insertar categorías
INSERT INTO categoria (nombre_categoria, descripcion) VALUES
('Laptops', 'Computadoras portátiles de diferentes marcas'),
('Tablets', 'Dispositivos con pantallas táctiles'),
('Celulares', 'Teléfonos de última generación'),
('Accesorios', 'Accesorios para los dispositivos');

-- Laptops
INSERT INTO producto (nombre, descripcion, precio, stock, imagen, id_categoria) VALUES
('Acer Predator Helios 300', 'Laptop gamer con RTX 3060 y pantalla 144Hz.', 4999.00, 10, 'predator.jpg', 1),
('ASUS ROG Strix G15', 'Laptop gamer Ryzen 7, 16GB RAM y SSD de 512GB.', 5699.00, 8, 'rog_strix.jpg', 1),
('MSI GF63 Thin', 'Gaming laptop con diseño liviano y potencia Intel i5.', 3899.00, 15, 'msi_gf63.jpg', 1),
('Lenovo Legion 5', 'Rendimiento gamer con Ryzen 5 y GTX 1660 Ti.', 4399.00, 12, 'legion_5.jpg', 1),
('HP Omen 16', 'Gaming laptop con Intel i7 y RTX 3050.', 5299.00, 7, 'omen16.jpg', 1),
('Dell G15', 'Laptop gamer con diseño robusto y sistema térmico avanzado.', 4599.00, 9, 'dellg15.jpg', 1),
('Razer Blade 15', 'Laptop premium para gamers exigentes.', 9999.00, 3, 'razerblade.jpg', 1),
('Gigabyte Aorus 5', 'Gaming laptop con alta frecuencia de refresco.', 4799.00, 10, 'aorus5.jpg', 1),
('Acer Nitro 5', 'Opción gamer accesible con buenas prestaciones.', 3799.00, 14, 'nitro5.jpg', 1),
('ASUS TUF Gaming F15', 'Durabilidad y rendimiento para juegos exigentes.', 4299.00, 10, 'tuf_f15.jpg', 1);

-- Tablets
INSERT INTO producto (nombre, descripcion, precio, stock, imagen, id_categoria) VALUES
('Samsung Galaxy Tab S8', 'Tablet premium con S Pen incluido.', 2899.00, 12, 'tabs8.jpg', 2),
('Samsung Galaxy Tab A8', 'Tablet de gama media para entretenimiento.', 1299.00, 20, 'taba8.jpg', 2),
('iPad 10ª Gen', 'Nueva generación con chip A14 Bionic.', 2899.00, 8, 'ipad10.jpg', 2),
('iPad Pro M1', 'Tablet profesional con chip Apple M1.', 4999.00, 5, 'ipadpro.jpg', 2),
('Huawei MatePad 11', 'Tablet Android con stylus.', 2399.00, 7, 'matepad11.jpg', 2),
('Huawei MatePad T10s', 'Tablet económica con buena pantalla.', 999.00, 15, 'matepadt10.jpg', 2),
('Samsung Tab S7 FE', 'Gran pantalla para productividad.', 1999.00, 10, 'tabs7fe.jpg', 2),
('iPad mini 6', 'Compacta, potente y portátil.', 2299.00, 6, 'ipadmini6.jpg', 2),
('Samsung Tab Active3', 'Tablet resistente para trabajo en campo.', 2599.00, 4, 'tabactive3.jpg', 2),
('Huawei MediaPad M5', 'Sonido y rendimiento en un solo equipo.', 1899.00, 9, 'mediapadm5.jpg', 2);

-- Celulares
INSERT INTO producto (nombre, descripcion, precio, stock, imagen, id_categoria) VALUES
('iPhone 15 Pro Max', 'Última generación con chip A17 Pro.', 6399.00, 5, 'iphone15pro.jpg', 3),
('iPhone 14', 'Gran cámara y rendimiento.', 4799.00, 10, 'iphone14.jpg', 3),
('Samsung Galaxy S23 Ultra', 'Poder absoluto en Android.', 5799.00, 7, 's23ultra.jpg', 3),
('Samsung Galaxy A54', 'Excelente opción calidad-precio.', 1899.00, 14, 'a54.jpg', 3),
('Huawei P50 Pro', 'Cámara Leica y diseño elegante.', 2999.00, 6, 'p50pro.jpg', 3),
('Huawei Nova 10', 'Smartphone con carga rápida y buen diseño.', 1599.00, 10, 'nova10.jpg', 3),
('iPhone SE (2022)', 'Potente y compacto.', 2399.00, 12, 'iphonese.jpg', 3),
('Samsung Galaxy Z Flip4', 'Celular plegable de última generación.', 5599.00, 3, 'zflip4.jpg', 3),
('Motorola Edge 40', 'Experiencia fluida con Android limpio.', 1799.00, 10, 'edge40.jpg', 3),
('Xiaomi Redmi Note 12', 'Gran rendimiento a bajo precio.', 899.00, 20, 'note12.jpg', 3);

-- Accesorios
INSERT INTO producto (nombre, descripcion, precio, stock, imagen, id_categoria) VALUES
('Teclado mecánico Redragon', 'RGB y switches rojos para gaming.', 229.00, 30, 'teclado_redragon.jpg', 4),
('Mouse Logitech G502', 'Precisión y diseño ergonómico.', 299.00, 25, 'g502.jpg', 4),
('Headset HyperX Cloud II', 'Sonido envolvente para juegos.', 399.00, 15, 'hyperx.jpg', 4),
('Base refrigerante para laptop', 'Mantén tu equipo fresco.', 99.00, 20, 'refrigerante.jpg', 4),
('Mousepad XL RGB', 'Gran espacio con luces RGB.', 89.00, 18, 'mousepadrgb.jpg', 4),
('Cable USB-C 2m reforzado', 'Carga rápida y resistente.', 29.00, 50, 'cableusb.jpg', 4),
('Soporte para celular', 'Ideal para escritorio.', 19.00, 40, 'soportecel.jpg', 4),
('Hub USB 3.0 4 Puertos', 'Expande tus conexiones.', 49.00, 20, 'hubusb.jpg', 4),
('Cargador inalámbrico Qi', 'Compatible con iPhone y Android.', 79.00, 15, 'wirelesscharger.jpg', 4),
('Luz LED para escritorio', 'Ilumina tu zona de trabajo.', 69.00, 10, 'leddesk.jpg', 4);


-- Insertar un Administrador especial (contraseña encriptada)
INSERT INTO usuario (
    nombre, apellido, email, contrasena, direccion, telefono, fecha_registro, activo
) VALUES (
    'Admin PRUEBA',
    'Principal',
    'administradorPRUEBA@novashop.com',
    '$2a$12$tKUhg/qqRIV9qlsG2TJaaeA8vndJ41atcZi/Me6ODTXT5Agp8Wb2S', -- NovaShop2025!
    'Oficina Central',
    '5551234567',
    CURRENT_TIMESTAMP,
    TRUE
);

-- Asignar rol ADMIN al nuevo administrador
INSERT INTO usuariorol (id_usuario, id_rol)
VALUES (
    (SELECT id_usuario FROM usuario WHERE email = 'administradorPRUEBA@novashop.com'),
    (SELECT id_rol FROM rol WHERE nombre_rol = 'ADMIN')
);

-- ==========================
-- CONSULTAS ÚTILES
-- ==========================

-- Verificar roles de un usuario
SELECT u.email, r.nombre_rol 
FROM usuario u
JOIN usuariorol ur ON u.id_usuario = ur.id_usuario
JOIN rol r ON ur.id_rol = r.id_rol
WHERE u.email = 'administradorPRUEBA@novashop.com';

-- Ver categorías
SELECT * FROM categoria;

-- Ver todos los usuarios
SELECT * FROM usuario;

-- Ver usuarios y sus roles
SELECT * FROM usuariorol;

-- Ver todos los roles
SELECT * FROM rol;

-- Ver todos los productos
SELECT * FROM producto;

-- Ver todos los pedidos
SELECT * FROM pedido;

-- Ver todos los carritos
SELECT * FROM carrito;

-- Ver todos los carritoItem
SELECT * FROM carritoitem;





```
