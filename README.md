# Biblioteca Microservicios

[![Java](https://img.shields.io/badge/Java-21-%23ED8B00?logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.6-%236DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-%234169E1?logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-%232496ED?logo=docker&logoColor=white)](https://docs.docker.com/compose/)

Sistema de gestión bibliotecaria basado en una arquitectura de microservicios construida con **Java 21** y **Spring Boot 4.x**. Cada dominio del negocio (usuarios, libros, préstamos, devoluciones y notificaciones) se implementa como un microservicio independiente, desplegable y escalable de forma autónoma.

---

## Arquitectura

```
                      ┌─────────────┐
                      │   Cliente   │
                      │  (HTTP/REST)│
                      └──────┬──────┘
                             │
              ┌──────────────┼──────────────┐
              │              │              │
        ┌─────▼─────┐ ┌─────▼─────┐ ┌─────▼─────┐
        │ ms-       │ │ ms-       │ │ ms-       │
        │ usuarios  │ │ libros    │ │ prestamos │
        │  :8081    │ │  :8082    │ │  :8083    │
        └─────┬─────┘ └─────┬─────┘ └─────┬─────┘
              │              │              │
        ┌─────▼─────┐ ┌─────▼─────┐       │
        │ ms-       │ │ ms-       │       │
        │ devoluc.  │ │ notif.    │       │
        │  :8084    │ │  :8085    │       │
        └─────┬─────┘ └─────┬─────┘       │
              │              │              │
              └──────┬───────┘              │
                     │                      │
              ┌──────▼──────────────────────▼──┐
              │         PostgreSQL 15          │
              │     biblioteca (compartida)    │
              └────────────────────────────────┘
```

Cada microservicio expone una API REST independiente y comparte una única base de datos PostgreSQL. La orquestación de contenedores se realiza mediante **Docker Compose**.

### Diagrama de Domain-Driven Design

| Microservicio | Dominio | Entidad principal |
|---|---|---|
| `ms-usuarios` | Gestión de usuarios | `Usuario` |
| `ms-libros` | Catálogo de libros | `Libro` |
| `ms-prestamos` | Gestión de préstamos | `Prestamo` |
| `ms-devoluciones` | Gestión de devoluciones | `Devolucion` |
| `ms-notificaciones` | Envío de notificaciones | `Notificacion` |

---

## Tecnologías

| Componente | Tecnología |
|---|---|
| **Lenguaje** | Java 21 |
| **Framework** | Spring Boot 4.0.6 |
| **Web** | Spring WebMVC (Servlet) |
| **Persistencia** | Spring Data JPA / Hibernate |
| **Base de datos** | PostgreSQL 15 |
| **Build** | Apache Maven (wrapper 3.9.9) |
| **Contenedores** | Docker + Docker Compose |
| **Monitorización** | Spring Boot Actuator (`ms-usuarios`) |
| **Testing** | Spring Boot Test (JPA + WebMVC) |

---

## Microservicios

### `ms-usuarios` — Puerto `8081`

Gestión del catálogo de usuarios de la biblioteca.

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/usuarios` | Listar todos los usuarios |
| `GET` | `/usuarios/{id}` | Obtener un usuario por ID |
| `POST` | `/usuarios` | Crear un nuevo usuario |
| `PUT` | `/usuarios/{id}` | Actualizar un usuario |
| `DELETE` | `/usuarios/{id}` | Eliminar un usuario |

**Modelo:**
```json
{
  "id": 1,
  "nombre": "Juan Pérez",
  "correo": "juan@example.com"
}
```

---

### `ms-libros` — Puerto `8082`

Gestión del catálogo de libros con control de inventario.

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/libros` | Listar todos los libros |
| `GET` | `/libros/{id}` | Obtener un libro por ID |
| `POST` | `/libros` | Crear un nuevo libro |
| `PUT` | `/libros/{id}` | Actualizar un libro |
| `DELETE` | `/libros/{id}` | Eliminar un libro |

**Modelo:**
```json
{
  "id": 1,
  "titulo": "Cien Años de Soledad",
  "autor": "Gabriel García Márquez",
  "stock": 5
}
```

---

### `ms-prestamos` — Puerto `8083`

Gestión de préstamos de libros a usuarios.

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/prestamos` | Listar todos los préstamos |
| `GET` | `/prestamos/{id}` | Obtener un préstamo por ID |
| `POST` | `/prestamos` | Registrar un nuevo préstamo |
| `PUT` | `/prestamos/{id}` | Actualizar un préstamo |
| `DELETE` | `/prestamos/{id}` | Eliminar un préstamo |

**Modelo:**
```json
{
  "id": 1,
  "usuarioId": 1,
  "libroId": 1,
  "fechaPrestamo": "2025-06-01"
}
```

---

### `ms-devoluciones` — Puerto `8084`

Gestión de devoluciones de libros prestados.

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/devoluciones` | Listar todas las devoluciones |
| `GET` | `/devoluciones/{id}` | Obtener una devolución por ID |
| `POST` | `/devoluciones` | Registrar una devolución |
| `PUT` | `/devoluciones/{id}` | Actualizar una devolución |
| `DELETE` | `/devoluciones/{id}` | Eliminar una devolución |

**Modelo:**
```json
{
  "id": 1,
  "prestamoId": 1,
  "fechaDevolucion": "2025-06-15"
}
```

---

### `ms-notificaciones` — Puerto `8085`

Gestión de notificaciones y comunicaciones.

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/notificaciones` | Listar todas las notificaciones |
| `GET` | `/notificaciones/{id}` | Obtener una notificación por ID |
| `POST` | `/notificaciones` | Crear una notificación |
| `PUT` | `/notificaciones/{id}` | Actualizar una notificación |
| `DELETE` | `/notificaciones/{id}` | Eliminar una notificación |

**Modelo:**
```json
{
  "id": 1,
  "destinatario": "juan@example.com",
  "mensaje": "Tu préstamo vence en 3 días"
}
```

---

## Ejecución

### Requisitos

- [Docker](https://docs.docker.com/engine/install/) + [Docker Compose](https://docs.docker.com/compose/install/)
- (Opcional) [Java 21 JDK](https://adoptium.net/) + [Maven](https://maven.apache.org/) para compilación local

### Con Docker Compose (recomendado)

Compilar todos los servicios e iniciar la infraestructura completa:

```bash
# Compilar los JARs de cada microservicio
cd ms-usuarios && ./mvnw clean package -DskipTests && cd ..
cd ms-libros   && ./mvnw clean package -DskipTests && cd ..
cd ms-prestamos && ./mvnw clean package -DskipTests && cd ..
cd ms-devoluciones && ./mvnw clean package -DskipTests && cd ..
cd ms-notificaciones && ./mvnw clean package -DskipTests && cd ..

# Iniciar todos los servicios
docker-compose up -d
```

Esto levantará:

| Servicio | Puerto | Depende de |
|---|---|---|
| `postgres` | `5432` | — |
| `ms-usuarios` | `8081` | `postgres` |
| `ms-libros` | `8082` | `postgres` |
| `ms-prestamos` | `8083` | `postgres` |
| `ms-devoluciones` | `8084` | `postgres` |
| `ms-notificaciones` | `8085` | `postgres` |

Verificar el estado:

```bash
docker-compose ps
```

Detener los servicios:

```bash
docker-compose down
```

### Local (sin Docker)

Cada microservicio se ejecuta de forma independiente. Requiere una instancia de PostgreSQL accesible en `localhost:5432` con la base de datos `biblioteca`.

```bash
cd ms-usuarios
./mvnw spring-boot:run
```

Repetir para cada microservicio en terminales separadas, o ejecutarlos en segundo plano.

---

## Estructura del proyecto

```
biblioteca-microservicios/
├── docker-compose.yml              # Orquestación de contenedores
├── ms-usuarios/                    # Microservicio de usuarios
│   ├── dockerfile
│   ├── pom.xml
│   └── src/main/java/com/biblioteca/ms_usuarios/
│       ├── MsUsuariosApplication.java
│       ├── model/Usuario.java
│       ├── repository/UsuarioRepository.java
│       ├── service/UsuarioService.java
│       └── controller/UsuarioController.java
├── ms-libros/                      # Microservicio de libros
├── ms-prestamos/                   # Microservicio de préstamos
├── ms-devoluciones/                # Microservicio de devoluciones
├── ms-notificaciones/              # Microservicio de notificaciones
├── .vscode/
│   ├── launch.json                 # Configuración de depuración VS Code
│   └── settings.json
└── .github/
    └── modernize/java-upgrade/     # Scripts de migración
```

Cada microservicio sigue una arquitectura por capas idéntica:

```
ms-{servicio}/
└── src/main/java/com/biblioteca/ms_{servicio}/
    ├── Ms{Servicio}Application.java   # Punto de entrada Spring Boot
    ├── model/                          # Entidades JPA
    ├── repository/                     # Repositorios Spring Data JPA
    ├── service/                        # Lógica de negocio
    └── controller/                     # Controladores REST
```

---

## Configuración

Cada microservicio expone los siguientes parámetros en `application.properties`:

| Propiedad | Valor |
|---|---|
| `spring.datasource.url` | `jdbc:postgresql://postgres:5432/biblioteca` |
| `spring.datasource.username` | `postgres` |
| `spring.datasource.password` | `postgres` |
| `spring.jpa.hibernate.ddl-auto` | `update` |
| `spring.jpa.show-sql` | `true` |
| `spring.jpa.database-platform` | `org.hibernate.dialect.PostgreSQLDialect` |

El esquema de base de datos se genera automáticamente mediante Hibernate DDL (`update`). No se requieren scripts de migración manuales.

---

## Roadmap

- [ ] **API Gateway** — Punto de entrada único con enrutamiento, rate-limiting y autenticación
- [ ] **Service Discovery** — Registro y descubrimiento dinámico de servicios (Eureka / Consul)
- [ ] **Comunicación síncrona** — Integración entre servicios vía HTTP (RestClient / WebClient)
- [ ] **Mensajería asíncrona** — Eventos entre servicios (RabbitMQ / Kafka) para lograr consistencia eventual
- [ ] **Base de datos por servicio** — Separación de esquemas o bases de datos independientes
- [ ] **Migraciones gestionadas** — Flyway / Liquibase para control de versiones del esquema
- [ ] **Seguridad** — Autenticación y autorización (Spring Security + JWT / OAuth2)
- [ ] **Observabilidad** — Logging estructurado, tracing distribuido, métricas (Prometheus + Grafana)
- [ ] **Pruebas de integración** — TestContainers para pruebas con base de datos real
- [ ] **Documentación de API** — OpenAPI / Swagger UI
- [ ] **CI/CD** — Pipeline de integración y despliegue continuo (GitHub Actions)
- [ ] **Secretos** — Gestión de credenciales mediante un vault o variables de entorno seguras

---

## Licencia

Este proyecto está bajo la licencia MIT. Consulte el archivo `LICENSE` para más información.
