# Demostración de la App

A continuación tienes el video que muestra el flujo de carga de contacto.

[Descargar video de demostración](https://github.com/user-attachments/assets/b2d16984-3ab9-4a97-aa1a-c247bfd0916a)

---

## Qué puedes hacer con esta app

- **Iniciar sesión** con credenciales válidas (`admin`/`1234`).  
- **Navegar el listado de contactos** mostrados en tarjetas con estilo MaterialCardView.  
- **Filtrar en tiempo real** la lista escribiendo el nombre en el campo de búsqueda.  
- **Agregar un nuevo contacto** pulsando el botón “+”, completando nombre, apellido, teléfono, domicilio y género.  
- **Ver visualmente actualizado** el listado con el nuevo contacto al regresar al listado.  
---

## Descripción de pantallas

1. **LoginActivity**  
   - Campos de usuario y contraseña.  
   - Mensaje de error si las credenciales son incorrectas.

2. **ContactsActivity**  
   - RecyclerView con tarjetas de contacto.  
   - Campo de filtro en la parte superior.  
   - FloatingActionButton para crear contactos.

3. **NewContactActivity**  
   - Formulario con validaciones de longitud y tipo (teléfono sólo dígitos).  
   - RadioGroup para seleccionar género.  
   - Botón “Guardar” que devuelve los datos a la lista.

---

## Credenciales de prueba

- **Usuario:** `admin`  
- **Contraseña:** `1234`  

---
