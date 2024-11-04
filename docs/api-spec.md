---
title: Especificación de la REST API
subtitle: Guía para la creación de endpoints en Spring Boot
authors:
  - name: Diego Llacsahuanga
---

# CRUD Controllers

Los controladores CRUD son aquellos que soportan las operaciones
- (C)reate: Agregar nuevos registro a una tabla de la base de datos
- (R)read: Leer objetos de la base de datos.
- (U)pdate: Actualizar/editar registros.
- (D)elete: Eliminar registros

## Endpoints

Un endpoint es la localización de algún proceso que realiza la API.

Un CRUD Controller como mínimo debe incluir los siguientes endpoints para operar sobre un objeto de clase X.

```java
// Dado el objeto X
GET "/Xs/"          // Obtiene todas las instancias de X
POST "/Xs/"         // Crea un nuevo X (los argumentos se envían como RequestBody)
POST "/Xs/update"   // Actualiza un X (el identificador se envía como RequestParam)
DELETE "/Xs/delete" // Elimina un X (el identificador se envía como RequestParam)
```

Lo que corresponde a los siguientes métodos de controlador (ejemplo).

```java
  // Dado el objeto T

  // Retorna todos los T
	@GetMapping("/")
	public ResponseEntity<List<T>> getAll() {
    ...
	}

  // Retorna un T dado su id
	@GetMapping("/get")
	public ResponseEntity<T> get(@RequestParam(name = "id") Long id) {
    ...
	}

  // Crea un nuevo T
	@PostMapping("/")
	public ResponseEntity<T> new(@RequestBody TextDTO textDTO) throws Exception {
    ...
	}

  // Elimina un T dado su ID
	@DeleteMapping("/delete")
	public ResponseEntity<Void> delete(@RequestParam(name = "id") Long id ) {
    ...
	}
```

## Sintáxis

Con sintáxis nos referimos a la estructura de URL que utilizaremos para definir endpoints.

:::{important} Prefijo de endpoints
Todas las URL deben ser precedidos por el prefijo `/api/{VERSION}`, los links especificados en esta guía obvian el prefijo por temas de legibilidad, sin embargo deben ser agregados al momento de codificar los endpoints. Ejemplo:
```java
// en la guía
GET "/usuarios/delete?id=1"

// el endpoint utilizado
GET "/api/{VERSION}/usuarios/delete?id=1"
```
:::

:::{important} Convención de nombres
El nombre de los objetos en los endpoints debe ser en plural, es decir, si deseo crear un endpoint para la entidad "Text", escribiré `.../texts/...`. Además, se utilizará la convención **Snake Case** para nombrar endpoints, por ejemplo:
- `.../users/updatePsk` (INCORRECTO)
- `.../users/UpdatePsk` (INCORRECTO)
- `.../users/update_psk` (CORRECTO)
:::


- Tdoas

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
