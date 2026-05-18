# Sistema de Agencia de Viajes — Grupo 6

Aplicación de escritorio para gestionar clientes, reservas, mascotas y facturación de una agencia de viajes.

---

## ¿Qué necesito instalar?

- [Java 17 o superior](https://www.oracle.com/java/technologies/downloads/)
- [MySQL](https://dev.mysql.com/downloads/mysql/) (solo el servidor MySQL, sin XAMPP)
- [NetBeans](https://netbeans.apache.org/front/main/download/) (para abrir y ejecutar el proyecto)

---

## Pasos para correrlo

**1. Clona o descarga el proyecto**

**2. Asegúrate de que MySQL esté corriendo**

**3. Crea el archivo de conexión**
   - Ve a la carpeta `src/main/resources/`
   - Copia `database.properties.example` y renómbralo a `database.properties`
   - Edita los valores `db.user` y `db.password` según tu instalación de MySQL
   - Si instalaste MySQL con la configuración por defecto (usuario `root` sin contraseña), no necesitas cambiar nada

**4. Abre el proyecto en NetBeans**
   - Archivo → Abrir proyecto → selecciona la carpeta del proyecto
   - Espera que descargue las dependencias (solo la primera vez)
   - Presiona el botón **Run** (el triángulo verde) o F6

> La aplicación **crea la base de datos automáticamente** la primera vez que se ejecuta. No necesitas importar ningún archivo SQL ni usar phpMyAdmin.

---

## Usuario y contraseña para entrar al sistema

```
Usuario:    vendedor@gmail.com
Contraseña: 12345
```

---

## Facturas en PDF

Al generar una factura, el PDF se guarda en la carpeta `pdfs/` dentro del directorio de trabajo del proyecto. NetBeans muestra un mensaje indicando la ruta exacta.

Para cambiar el nombre de la empresa, RUC, teléfono o dirección que aparecen en el encabezado del PDF, edita el archivo `src/main/resources/app.properties`.

---

## ¿Algo no funciona?

- Asegúrate de que MySQL esté encendido antes de abrir la app
- Si te dice error de contraseña, abre `database.properties` y verifica que `db.user` y `db.password` coincidan con tu instalación de MySQL
- Si MySQL tiene contraseña para root, ponla en `db.password`
