# 📦 Proyecto Spring Boot + Git Flow

Este proyecto utiliza [Spring Boot](https://spring.io/projects/spring-boot) y está gestionado con **Git Flow** para mantener un flujo de desarrollo claro y profesional.

---

## 🚀 Estructura del Proyecto

- `main` → Rama de producción (versión estable)
- `develop` → Rama de desarrollo (funcionalidades listas para integración)
- `feature/*` → Nuevas funcionalidades en desarrollo
- `release/*` → Preparación de versiones para producción
- `hotfix/*` → Correcciones críticas en producción

---

## 🔁 Flujo de trabajo Git Flow

```plaintext
                +-----------------------+
                |     main (producción)|
                +----------+------------+
                           ^
                           |
                     +-----+-----+
                     |  release/  |
                     +-----+-----+
                           ^
                           |
                +----------+------------+
                |       develop         |
                +----------+------------+
                           ^
      +--------------------+--------------------+
      |                    |                    |
+-----+-----+        +-----+-----+        +-----+-----+
| feature/x |        | feature/y |        | feature/z |
+-----------+        +-----------+        +-----------+
