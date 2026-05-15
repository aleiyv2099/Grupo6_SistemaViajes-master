# Sistema de Agencia de Viajes — Grupo 6

Aplicación de escritorio para gestionar clientes, reservas, mascotas y facturación de una agencia de viajes.

---

## ¿Qué necesito instalar?

- [Java 17 o superior](https://www.oracle.com/java/technologies/downloads/)
- [XAMPP](https://www.apachefriends.org/es/index.html) (incluye MySQL, es lo más fácil)
- [NetBeans](https://netbeans.apache.org/front/main/download/) (para abrir y ejecutar el proyecto)

---

## Pasos para correrlo

**1. Clona o descarga el proyecto**

**2. Abre XAMPP y enciende el módulo MySQL**

**3. Importa la base de datos**
   - Abre tu navegador y entra a `http://localhost/phpmyadmin`
   - Crea una base de datos llamada `sistemareserva`
   - Entra a esa base de datos, ve a la pestaña **Importar**
   - Selecciona el archivo `database/schema.sql` y haz clic en **Importar**

**4. Crea el archivo de conexión**
   - Ve a la carpeta `src/main/resources/`
   - Copia el archivo `database.properties.example`
   - Pégalo en la misma carpeta y renómbralo a `database.properties`
   - No necesitas cambiar nada si usas XAMPP con la configuración por defecto

**5. Abre el proyecto en NetBeans**
   - Archivo → Abrir proyecto → selecciona la carpeta del proyecto
   - Espera que descargue las dependencias (solo la primera vez)
   - Presiona el botón **Run** (el triángulo verde) o F6

---

## Usuario y contraseña para entrar al sistema

```
Usuario:    vendedor@gmail.com
Contraseña: 12345
```

---

## ¿Algo no funciona?

- Asegúrate de que MySQL esté encendido en XAMPP antes de abrir la app
- Si te dice error de contraseña, abre `database.properties` y verifica que diga `db.user=mi_user` y `db.password=admin123`
- Si XAMPP usa un usuario diferente, cambia esos valores en `database.properties`
