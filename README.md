# Sistema de Agencia de Viajes — Grupo 6

Aplicación de escritorio (Java + Swing) para gestionar clientes, reservas, equipaje,
mascotas, facturación y soporte al cliente de una agencia de viajes. Los datos se guardan
en una base de datos **MySQL**.

---

## Tecnologías utilizadas

[![Java](https://img.shields.io/badge/Java-17-E76F00?logo=openjdk&logoColor=white)](https://adoptium.net/)
[![Apache Maven](https://img.shields.io/badge/Apache_Maven-BUILD-C71A36?logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![NetBeans](https://img.shields.io/badge/NetBeans-IDE-1B6AC6?logo=apachenetbeanside&logoColor=white)](https://netbeans.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Apache JMeter](https://img.shields.io/badge/Apache_JMeter-Testing-D22128?logo=apachejmeter&logoColor=white)](https://jmeter.apache.org/)

---

## 1. Requisitos (instalar antes)

- **Java 17 o superior** — https://adoptium.net/ (el proyecto se probó con Java 21)
- **MySQL Server 8** — https://dev.mysql.com/downloads/mysql/
- **Maven** (para compilar por terminal) *o* **NetBeans** (para abrir y ejecutar con un botón)
- **Git** (opcional, solo si lo clonas del repositorio)

---

## 2. Puesta en marcha (clonado o desde el ZIP)

### Paso 1 — Enciende MySQL
Linux:
```bash
sudo systemctl start mysql
```
Windows/Mac: inicia el servicio MySQL (o desde MySQL Workbench).

### Paso 2 — Crea la base de datos y el usuario de la app  ⚠️ IMPORTANTE
La aplicación se conecta con un usuario MySQL llamado **`viajes`**. La app puede crear las
tablas sola, **pero NO puede crear ese usuario**: hay que crearlo una vez. Elige UNA opción:

**Opción A — Script automático (Linux, recomendado):**
```bash
bash setup.sh                    # si tu MySQL root no tiene contraseña
bash setup.sh -p TU_PASS_ROOT    # si tu root tiene contraseña
sudo bash setup.sh               # si tu root requiere sudo
```

**Opción B — Importar el SQL manualmente (cualquier sistema):**
```bash
mysql -u root -p < SistemaReserva.sql
```
(En MySQL Workbench: *Server → Data Import → Import from Self-Contained File* → `SistemaReserva.sql`.)

Cualquiera de las dos crea:
- La base de datos `sistemareserva` con todas las tablas y datos de ejemplo.
- El usuario **`viajes`** con contraseña **`Viajes123#`**.

### Paso 3 — Conexión (ya viene configurada)
El archivo `src/main/resources/database.properties` ya está listo para el usuario `viajes`:
```
db.url=jdbc:mysql://localhost:3306/sistemareserva?serverTimezone=UTC
db.user=viajes
db.password=Viajes123#
```
**No necesitas cambiar nada** si usaste el Paso 2. (Solo edítalo si prefieres otro usuario
de MySQL; en ese caso pon ahí tus credenciales.)

### Paso 4 — Ejecuta la aplicación

**Con NetBeans:** Archivo → Abrir proyecto → selecciona la carpeta → botón **Run** (▶) o F6.

**Por terminal (genera un .jar ejecutable):**
```bash
mvn clean package -DskipTests
java -jar target/SistemaAgenciaViajesGrupo6-1.0-SNAPSHOT.jar
```
> Se usa `-DskipTests` porque el proyecto incluye tests de integración que dependen de datos
> concretos en la base de datos; no afectan el funcionamiento de la app.

---

## 3. Credenciales para entrar al sistema
```
Usuario:    vendedor@gmail.com
Contraseña: 12345
```

---

## 4. Facturas en PDF
Al generar una factura, el PDF se guarda en la carpeta `pdfs/` del proyecto.
Los datos de la empresa (RUC, nombre, etc.) se editan en `src/main/resources/app.properties`.

---

## 5. Pruebas de rendimiento (JMeter)
El proyecto incluye pruebas de rendimiento de la base de datos con Apache JMeter.
Ver la carpeta **`PruebasJMeter/`** y su `README.md` para las instrucciones.

---

## 6. Si algo no funciona
- **`Access denied for user 'viajes'`** → no ejecutaste el Paso 2; corre `setup.sh` o importa el SQL.
- **`Table '...soporte' doesn't exist`** → base de datos vieja; vuelve a importar el SQL del Paso 2.
- **La app no abre / error de conexión** → verifica que MySQL esté encendido.
- **`mvn package` falla en los tests** → usa `mvn clean package -DskipTests`.
