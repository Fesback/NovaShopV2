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
├── 📂 controller/         → 🌐 Controladores REST (API)
├── 📂 dto/                → ✉️  Objetos de transferencia de datos (DTOs)
│   ├── 📂 request/        → 📥 DTOs para solicitudes
│   └── 📂 response/       → 📤 DTOs para respuestas
├── 📂 entity/             → 🧱 Entidades JPA (tablas de la base de datos)
├── 📂 exception/          → ❗ Manejo global de errores y excepciones
├── 📂 mapper/             → 🔁 Conversores entre entidades y DTOs
├── 📂 repository/         → 🗄️  Interfaces JPA para acceso a datos
├── 📂 security/           → 🔐 Seguridad y autenticación
│   ├── 📂 config/         → ⚙️  Configuración principal de Spring Security
│   ├── 📂 jwt/            → 🔑 Generación y validación de tokens JWT
│   └── 📂 service/        → 👤 Servicios de login, register y autorización
├── 📂 service/            → 💡 Interfaces de lógica de negocio
├── 📂 service/impl/       → 🧠 Implementaciones de los servicios
└── 📄 NovaShopApplication → 🚀 Clase principal de arranque
```

---

## 🧩 Módulos incluidos

| Módulo      | Descripción                                 | Estado    |
|-------------|---------------------------------------------|-----------|
| Categoría   | CRUD con DTOs, validación y pruebas         | 🔄 En curso |
| Producto    | CRUD estructurado y validado                | 🔄 En curso |
| Usuario     | Registro, login (seguridad JWT)             | 🔄 En curso |
| Seguridad   | Protección de rutas con roles y JWT         | 🔒 Planeado |
| Carrito     | Gestión de compras                          | 🧺 Planeado |
| Rol         | Autorización basada en roles                | 👥 Planeado |

---

## 🔐 Seguridad (JWT + Spring Security)

El sistema manejará:
- Registro y login de usuarios
- Protección de rutas
- Generación y validación de tokens JWT
- Control de acceso por roles (admin, cliente, etc.)

### 🗂 Paquetes de seguridad:

```
📦 security
├── 📂 config/   → ⚙️  Configuración de filtros y reglas de seguridad (SecurityFilterChain)
├── 📂 jwt/      → 🔐 Manejo de JWT (provider, filters, utils)
└── 📂 service/  → 👤 AuthService con lógica de autenticación (login, register)
```

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
CREATE DATABASE IF NOT EXISTS novashop_db;
USE novashop_db;

-- Tabla roles
CREATE TABLE rol (
	id_rol INT PRIMARY KEY AUTO_INCREMENT,
    nombre_rol VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255)
);

-- Tabla usuarios
CREATE TABLE usuario (
	id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    direccion VARCHAR(255),
    telefono VARCHAR (20),
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE
);

-- tabla usuario - rol (many to many) 
CREATE TABLE usuariorol(
	id_usuario INT,
    id_rol  INT,
    PRIMARY KEY (id_usuario, id_rol),
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_rol) REFERENCES rol (id_rol) ON DELETE CASCADE
);

-- tabla categorias
CREATE TABLE categoria(
	id_categoria INT PRIMARY KEY AUTO_INCREMENT,
    nombre_categoria VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    imagen VARCHAR(255)
);

-- tabla productos 
CREATE TABLE producto (
	id_producto INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    imagen VARCHAR(255),
    id_categoria INT,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_categoria) REFERENCES Categoria(id_categoria) ON DELETE SET NULL
);

-- tabla carrito
CREATE TABLE carrito(
	id_carrito INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario) ON DELETE CASCADE
);

-- tabla carrito item
CREATE TABLE carritoitem (
	id_item INT PRIMARY KEY AUTO_INCREMENT,
    id_carrito INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL DEFAULT 1,
    fecha_agregado DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_carrito) REFERENCES carrito (id_carrito) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES producto (id_producto) ON DELETE CASCADE,
    UNIQUE KEY (id_carrito, id_producto)
);

-- tabla pedidos
CREATE TABLE pedido (
	id_pedido INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    fecha_pedido DATETIME DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('pendiente', 'procesando', 'enviado', 'entregado', 'cancelado') DEFAULT 'pendiente',
    direccion_envio VARCHAR(255) NOT NULL,
    total DECIMAL (10,2) NOT NULL,
    metodo_pago VARCHAR(50),
    FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario) ON DELETE CASCADE
);

-- tablas detalle de pedido
CREATE TABLE detallepedido (
	id_detalle INT PRIMARY KEY AUTO_INCREMENT,
    id_pedido INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_pedido) REFERENCES pedido (id_pedido) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES producto (id_producto)
);

-- INSERTS 

INSERT INTO rol (nombre_rol, descripcion) VALUES
('admin', 'administrador del sistema'),
('cliente', 'usuario regular');

-- insertar categorias 
INSERT INTO categoria (nombre_categoria, descripcion) VALUES
('Laptops', 'Computadoras portatiles de diferentes marcas'),
('Tablets', 'Dispositivos con pantallas tactiles'),
('Celulares', 'telefonos de ultima generacion'),
('Accesorios', 'Accesorios para los dispositivos');

-- Insertanto usuario al administrador
INSERT INTO usuario (nombre, apellido, email, contrasena, direccion, telefono) VALUES 
('admin', 'Sistema', 'admin@novashop.com', 'fesback20000', 'Jr las flores 450', '123456789'),
('Sebastian', 'Bismarck', 'sebasbi@novashop.com', 'fesback20001', 'Av brasil 450', '123456788');

-- Asignamos rol al usuario 
INSERT INTO usuariorol (id_usuario, id_rol) VALUES (1,1);

select * from categoria;
select * from usuario;
select * from usuariorol;
select * from rol;
select * from producto;
```
