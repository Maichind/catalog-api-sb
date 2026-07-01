# NYXN Technical Challenge — Solución Spring Boot

## Descripción General

Este repositorio contiene la implementación de la prueba técnica desarrollada con **Java + Spring Boot**, cubriendo las siguientes secciones:

* **Sección 1 — Java & Spring Boot**
* **Sección 2 — Clean Architecture / Arquitectura Hexagonal**
* **Sección 4 — Bases de Datos (PostgreSQL / OracleSQL / Redis)**

El proyecto está organizado intencionalmente por dominio y alcance del problema para mantener cada solución aislada, clara y fácil de revisar.

---

## Stack Tecnológico

* Java 21
* Spring Boot
* Spring Data JPA
* Bean Validation
* PostgreSQL
* OracleSQL
* Redis
* Maven
* Swagger / OpenAPI

---

## Estructura del Proyecto

```text
src/main/java
└── com
    ├── catalog
    │   └── ...
    ├── orders
    │   └── ...
    └── ...
```

```text
src/main/resources
└── sql
    ├── postgres/
    ├── oracle/
    └── ...
```

---

## Desglose de la Solución

### `com.company`

Implementa la solución correspondiente a la **Sección 1**.

Incluye:

#### Punto 1.1 — API RESTful de Productos

API CRUD para gestión de productos:

* GET `/products`
* GET `/products/{id}`
* POST `/products`
* PUT `/products/{id}`
* DELETE `/products/{id}`

Características:

* Paginación con `Pageable`
* Validaciones con Bean Validation
* Manejo global de excepciones (`@ControllerAdvice`)
* Arquitectura por capas:

  * Controller
  * Service
  * Repository
* Documentación con Swagger

---

#### Punto 1.2 — Refactorización aplicando SOLID

Refactorización de un servicio legacy aplicando:

* SRP (Single Responsibility Principle)
* OCP (Open/Closed Principle)
* DIP (Dependency Inversion Principle)
* ISP (Interface Segregation Principle)

La solución desacopla:

* Procesamiento de pagos
* Notificaciones por email
* Actualización de inventario
* Orquestación de órdenes

mediante abstracciones e inyección de dependencias por constructor.

---

### `nyxn.orders`

Implementa la solución correspondiente a la **Sección 2**.

Contiene la propuesta de **Arquitectura Hexagonal (Ports & Adapters)** para la gestión de órdenes.

Incluye:

* Capa de dominio
* Capa de aplicación
* Puertos de entrada
* Puertos de salida
* Adaptadores primarios (REST)
* Adaptadores secundarios (Persistencia, Email, Pub/Sub)

Este módulo mantiene la lógica de negocio completamente aislada de frameworks e infraestructura.

---

### `com/sql`

Implementa la solución correspondiente a la **Sección 4**.

Contiene scripts SQL para:

* PostgreSQL
* OracleSQL

Incluye:

* Top clientes por volumen de compra
* Reportes de stock crítico
* Reportes diarios de ventas
* Uso de CTEs
* Funciones analíticas
* Filtros temporales
* Compatibilidad multi-motor

---

## Ejecución del Proyecto

Compilar:

```bash
./mvnw clean install
```

Ejecutar:

```bash
./mvnw spring-boot:run
```

O directamente desde el IDE ejecutando la clase principal anotada con `@SpringBootApplication`.

---

## Notas

* Cada paquete/módulo está aislado intencionalmente según el alcance de cada punto de la prueba para facilitar la evaluación.
* Los scripts SQL son assets independientes y no forman parte del ciclo de ejecución de la aplicación.
* Se priorizaron decisiones arquitectónicas orientadas a mantenibilidad, escalabilidad y separación de responsabilidades.

---

## Nota sobre el historial de commits

Por limitaciones de tiempo asociadas a la prueba técnica, se optó por consolidar toda la implementación en un único commit final.

En un flujo productivo habitual, el proceso incluiría:

* Commits atómicos por funcionalidad
* Estrategia de ramas
* Pull Requests
* Revisiones de código
* Pipelines de validación CI/CD

Esta decisión fue puramente pragmática para priorizar la entrega dentro del tiempo establecido y no representa mi flujo habitual de desarrollo.
