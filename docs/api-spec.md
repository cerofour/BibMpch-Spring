---
title: Especificación de la REST API
subtitle: Guía para la creación de endpoints en Spring Boot
authors:
  - name: Diego Llacsahuanga
---

## Sintáxis

Con sintáxis nos referimos a la estructura de URL que utilizaremos para definir endpoints.

>Todas las URL deben ser precedidos por el prefijo `/api/{VERSION}`, los links especificados en esta guía obvian el prefijo por temas de legibilidad, sin embargo deben ser agregados al momento de codificar los endpoints. Ejemplo:
```java
// en la guía
GET "/usuarios/delete?id=1"

// el endpoint utilizado
GET "/api/{VERSION}/usuarios/delete?id=1"
```

- Listar todos los X
    ```js
    GET "/X"
    ```

- Retorna los datos de un X dado su id, username u otros campos.
    ```js
    GET "/X/get?{atributo}={valor}"
    {atributo} 
    // Ejemplo: Conseguir los datos de un usuario dado su documento
    GET "/usuarios/get?document=73266267"
    ```

- Crea un nuevo X
    ```java
    POST "/X"
    // Datos de X enviado como @RequestBody (JSON)
    ```

- Eliminar dado id, username, etc
    ```java
    DELETE "/X/delete?{atributo}={valor}"
    
    // Ejemplo: Eliminar un texto dado su id
    DELETE "/textos/delete?id=4526"
    ```

- Actualizar un objeto X dado su identificador
  **Se deben especificar todos los campos que componen al objeto que se va a actualizar, aunque es posible que muchos de estos no sean utilizados.**
    ```java
    POST "/X/update?{identificador}={valor}"
    // Los valores actualizados se envían como @RequestBody
    
    // Ejemplo: actualizar la contraseña de un usuario dado su documento
    POST "/usuarios/update?documento=73266267"
    BODY:
        {
            // En este caso el documento no será actualizado
            "document": "73266267",
            "psk": "nueva_psk"
        }
    ```