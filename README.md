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

---

## 🌟 Características Principales

- **Autenticación segura** con JWT (Register/Login) 🔐
- **Roles de usuario** (ADMIN/USER) con permisos diferenciados 👮‍♂️👤
- **API RESTful** documentada y bien estructurada 📚
- **Base de datos relacional** con MySQL 🗃️
- **Prácticas profesionales**: DTOs, inyección de dependencias, validaciones 🏆

---

## 🛠️ Tecnologías Clave

| Categoría       | Tecnologías                                                                 |
|-----------------|-----------------------------------------------------------------------------|
| **Backend**     | ![Java](https://img.shields.io/badge/Java-17-%23ED8B00) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.0-%236DB33F) |
| **Seguridad**   | ![Spring Security](https://img.shields.io/badge/Spring_Security-6.1.0-%236DB33F) ![JWT](https://img.shields.io/badge/JWT-0.11.5-%23000000) |
| **Base Datos**  | ![MySQL](https://img.shields.io/badge/MySQL-8.0-%234479A1) ![Hibernate](https://img.shields.io/badge/Hibernate-6.4-%2359666C) |
| **Herramientas**| ![Maven](https://img.shields.io/badge/Maven-3.9-%23C71A36) ![Lombok](https://img.shields.io/badge/Lombok-1.18-%23000000) ![Postman](https://img.shields.io/badge/Postman-10.0-%23FF6C37) |

---

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
| Categoría   | CRUD con DTOs, validación y pruebas         | 🔄 En curso |
| Producto    | CRUD estructurado y validado                | 🔄 En curso |
| Usuario     | Registro, login (seguridad JWT)             | 🔄 En curso |
| Seguridad   | Protección de rutas con roles y JWT         | ✅ Terminado |
| Carrito     | Gestión de compras                          | 🧺 Planeado |
| Rol         | Autorización basada en roles                | 👥 Planeado |

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
- [ ] CRUD de categoría implementado con DTOs y mapper
- [x] Seguridad con JWT (en desarrollo)
- [ ] Documentación de API (pendiente con Swagger)
- [ ] Integración con Angular (en curso)

---

## 🧪 Base de Datos

```
CREATE DATABASE IF NOT EXISTS novashop_db_v2;
USE novashop_db_v2;

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
    direccion VARCHAR(255) NULL,
    telefono VARCHAR(20) NULL,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE
);

-- tabla usuario - rol (many to many)
CREATE TABLE usuariorol (
    id_usuario BIGINT,
    id_rol BIGINT,
    PRIMARY KEY (id_usuario, id_rol),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol) ON DELETE CASCADE
);

-- tabla categorias
CREATE TABLE categoria (
    id_categoria BIGINT PRIMARY KEY AUTO_INCREMENT,
    nombre_categoria VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    imagen VARCHAR(255)
);

-- tabla productos
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

-- tabla carrito
CREATE TABLE carrito (
    id_carrito BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_usuario BIGINT NOT NULL,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

-- tabla carrito item
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

-- tabla pedidos
CREATE TABLE pedido (
    id_pedido BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_usuario BIGINT NOT NULL,
    fecha_pedido DATETIME DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('pendiente', 'procesando', 'enviado', 'entregado', 'cancelado') DEFAULT 'pendiente',
    direccion_envio VARCHAR(255) NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    metodo_pago VARCHAR(50),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

-- tabla detalle de pedido
CREATE TABLE detallepedido (
    id_detalle BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_pedido BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

-- INSERTS

-- Insertar roles
INSERT INTO rol (nombre_rol, descripcion) VALUES
('ADMIN', 'Administrador del sistema con todos los permisos'),
('USER', 'Usuario Regular del sistema');

-- insertar categorias
INSERT INTO categoria (nombre_categoria, descripcion) VALUES
('Laptops', 'Computadoras portátiles de diferentes marcas'),
('Tablets', 'Dispositivos con pantallas táctiles'),
('Celulares', 'teléfonos de última generación'),
('Accesorios', 'Accesorios para los dispositivos');

-- Insertar usuarios (admin y prueba)
INSERT INTO usuario (nombre, apellido, email, contrasena, direccion, telefono) VALUES 
('ADMIN', 'Sistema', 'admin@novashop.com', 'fesback20000', 'Jr las flores 450', '123456789'),
('Sebastian', 'Bismarck', 'sebasbi@novashop.com', 'fesback20001', 'Av brasil 450', '123456788');

INSERT INTO usuariorol (id_usuario, id_rol) 
VALUES 
((SELECT id_usuario FROM usuario WHERE email = 'admin@novashop.com'), 
 (SELECT id_rol FROM rol WHERE nombre_rol = 'ADMIN'));






select * from categoria;
select * from usuario;
select * from usuariorol;
select * from rol;
select * from producto;


```
