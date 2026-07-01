# CATALOG API

## Descripción

Este proyecto contiene la resolución de una prueba técnica implementada con Spring Boot. La solución está organizada por paquetes según el punto evaluado, permitiendo mantener separados los enfoques arquitectónicos de cada ejercicio.

## Estructura del proyecto

```
src/main/java
└── com
    ├── company
    │   └── ...
    ├── nyxn
    │   └── ...
    └── ...
```

```
src/main/resources
└── sql
    ├── postgres/
    ├── oracle/
    └── ...
```

### `com.company`

Implementa la solución correspondiente al **Punto 1** de la prueba técnica.

Incluye la implementación de la API REST y los ejercicios relacionados con principios SOLID y buenas prácticas de desarrollo utilizando Spring Boot.

### `com.nyxn`

Implementa la solución correspondiente al **Punto 2**.

Contiene la propuesta basada en **Clean Architecture / Arquitectura Hexagonal**, separando dominio, aplicación e infraestructura mediante puertos y adaptadores.

### `resources/sql`

Contiene los scripts SQL correspondientes al **Punto 4**.

Se incluyen las consultas requeridas para los diferentes motores de base de datos (PostgreSQL y Oracle), organizadas en archivos independientes para facilitar su revisión y ejecución.

## Ejecución

Compilar el proyecto:

```bash
./mvnw clean install
```

Ejecutar la aplicación:

```bash
./mvnw spring-boot:run
```

o desde el IDE ejecutando la clase principal anotada con `@SpringBootApplication`.

## Notas

- Cada paquete es independiente y responde al punto específico de la prueba técnica.
- Los scripts SQL son únicamente de consulta y no forman parte del ciclo de ejecución de la aplicación.
- La organización busca facilitar la revisión del código por parte del evaluador.
