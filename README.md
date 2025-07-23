# 🛒 Proyecto Final E-Commerce

Proyecto desarrollado con **Java Spring Boot + HTML + JavaScript** como parte del curso de Backend Java.

---

## 🚀 Tecnologías usadas

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- HTML, CSS, JS Vanilla
- Bootstrap 5
- Git

---

## 📦 Estructura del proyecto

- `src/main/java` → Código Java backend
- `src/main/resources/static` → Frontend
- `data.sql` → Productos iniciales
- `application.properties` → Configuración de base de datos

---

## ⚙️ Requisitos

- Java JDK 17+
- Maven
- MySQL
- Un IDE como IntelliJ o VS Code

---

## 🧪 Cómo correr el proyecto

1. **Clonar el repo**

```bash
git clone https://github.com/boedomario/ecommerce-springboot.git
cd ecommerce-springboot

## crear bd
CREATE DATABASE techlab;

## configurar .properties
spring.datasource.url=jdbc:mysql://localhost:3306/techlab
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update
spring.datasource.initialization-mode=always

## ejecutar la app
mvn spring-boot:run

## abri en el navegador
http://localhost:8080

