# 🛒 NovaShop Backend

*NovaShop, un sistema de e-commerce construido con **Java + Spring Boot, utilizando una arquitectura **profesional por capas (Layered Architecture). Este backend está diseñado para trabajar de forma desacoplada con un frontend desarrollado en **Angular*.

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
´´´
## 📁 Estructura del proyecto
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
´´´

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

´´´ 
📦 security
├── 📂 config/   → ⚙️  Configuración de filtros y reglas de seguridad (SecurityFilterChain)
├── 📂 jwt/      → 🔐 Manejo de JWT (provider, filters, utils)
└── 📂 service/  → 👤 AuthService con lógica de autenticación (login, register)
´´´ 

---

## 🛠 ¿Por qué rehacer el backend?

- El código anterior funcionaba, pero no seguía buenas prácticas.
- Esta nueva versión será más limpia, modular y escalable.
- Dará experiencia profesional real.
- Es más fácil integrar herramientas como Swagger, JWT, manejo global de errores, etc.

---

## ✅ Estado actual

- [ ] Estructura inicial creada
- [ ] CRUD de categoría implementado con DTOs y mapper
- [ ] Seguridad con JWT (en desarrollo)
- [ ] Documentación de API (pendiente con Swagger)
- [ ] Integración con Angular (en curso)

---
