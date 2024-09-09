# 📖 Blog API - Spring Boot

Este proyecto es una API REST que gestiona las funcionalidades básicas de un blog, permitiendo la creación, edición, gestión de publicaciones y usuarios. El objetivo de este proyecto era aprender a trabajar con **Spring Boot**

---

## 🚀 Funcionalidades

### Publicaciones (Posts)
- ✅ Crear, editar y eliminar publicaciones del blog.
- ✅ Obtener una lista de todas las publicaciones.

### Usuarios
- ✅ Autenticación y autorización con **JWT**.

---

## 🛠️ Tecnologías

Este proyecto está construido con las siguientes tecnologías:

- **Java 17**
- **Spring Boot 3.x.x**
- **Spring Data JPA** - Gestión de la base de datos
- **Spring Security con JWT** - Autenticación y Autorización
- **Hibernate** - ORM para el manejo de datos
- **MySQL / PostgreSQL** - Base de datos relacional (puedes cambiar fácilmente la configuración a otra base de datos)
- **Maven** - Gestión de dependencias
- **Swagger** - Documentación interactiva de la API

---

## 📚 Documentación de la API

La API cuenta con documentación generada automáticamente con **Swagger**. Puedes acceder a la interfaz interactiva de Swagger para probar los endpoints:

```plaintext
http://localhost:8080/swagger-ui.html
```
### Rutas principales:

- /api/posts - Gestión de publicaciones
- /api/users - Gestión de usuarios

## 🖥️ Postman Collection

Para facilitar las pruebas de la API, puedes importar la siguiente colección de **Postman**:  
[Descargar colección](https://github.com/voidcram/blog-Angular-2.7.4-Api/blob/main/Blog%20Angular.postman_collection.json) 

