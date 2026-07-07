# Guion del Video — Testing de Rendimiento (Performance)
### Sistema de Agencia de Viajes — Grupo 6

---

## 1. CONCEPTO (esto lo explicas al inicio, frente a la cámara)

**¿Qué es el testing de rendimiento (performance testing)?**
Es un tipo de prueba de software que **no verifica si el programa funciona bien**, sino
**qué tan rápido y estable responde cuando muchos usuarios lo usan al mismo tiempo**.
Mide el comportamiento del sistema bajo carga.

**¿Qué mide?**
- **Tiempo de respuesta** → cuánto tarda una operación (en milisegundos).
- **Throughput** → cuántas peticiones procesa por segundo.
- **Tasa de errores** → qué porcentaje de peticiones falla bajo carga.
- **Concurrencia** → cuántos usuarios simultáneos soporta sin caerse.

**Tipos de prueba de rendimiento:**
- **Prueba de carga (load):** usuarios esperados normales.
- **Prueba de estrés (stress):** se sube la carga hasta encontrar el límite.
- **Prueba de picos (spike):** subidas bruscas de usuarios.

**¿Por qué es importante en nuestro proyecto?**
Nuestro sistema guarda reservas, facturas y tickets de soporte en una base de datos MySQL.
Si 50 clientes registran tickets al mismo tiempo, necesitamos saber si la base de datos
responde rápido y sin errores. Eso es lo que vamos a medir.

---

## 2. LA HERRAMIENTA (preséntala)

**Apache JMeter** — programa gratuito y de código abierto de la Fundación Apache,
uno de los más usados en la industria para pruebas de rendimiento.

*Aclaración importante para el video:* nuestro sistema es una app de escritorio (Java Swing),
así que JMeter no pulsa la ventana; lo que hace es **conectarse a la base de datos MySQL vía JDBC
y ejecutar las mismas consultas que hace el programa** (registrar y listar tickets), pero
simulando muchos usuarios a la vez. Así medimos el rendimiento de la capa que de verdad se
satura: la base de datos.

---

## 3. DEMOSTRACIÓN EN VIVO (pasos que grabas)

### Paso 1 — Base de datos ANTES
```bash
mysql -u viajes -p'Viajes123#' sistemareserva -e "SELECT COUNT(*) AS antes FROM soporte;"
```
Narra: *"La tabla soporte tiene 2 registros antes de la prueba."*

### Paso 2 — Abrir JMeter
```bash
~/apache-jmeter-5.6.3/bin/jmeter -t ~/NetBeansProjects/Grupo6_SistemaViajes-master/PruebasJMeter/Soporte_TestPlan.jmx
```

### Paso 3 — Explicar el plan (clic en cada elemento del árbol izquierdo)
- **Conexión MySQL:** la conexión JDBC a la base de datos.
- **Usuarios concurrentes de Soporte:** 20 usuarios × 10 repeticiones = 400 peticiones.
- **RF-1 Registrar Ticket (INSERT)** y **RF-2 Listar Tickets (SELECT):** las operaciones a medir.

### Paso 4 — Ejecutar
Pulsa el **botón verde ▶ (Start)**.

### Paso 5 — Mostrar resultados
- **Reporte Resumen:** señala **# Muestras (400)**, **Promedio (~9 ms)**, **Rendimiento (~82/s)** y **% Error = 0.00%**.
  Narra: *"Procesó 400 peticiones a 82 por segundo, con un promedio de 9 milisegundos y cero errores: la base de datos soporta la carga sin problemas."*
- **Ver Árbol de Resultados:** todos los samplers en verde = éxito.

### Paso 6 — Base de datos DESPUÉS (la prueba clave)
```bash
mysql -u viajes -p'Viajes123#' sistemareserva -e "SELECT COUNT(*) AS despues FROM soporte;"
```
Narra: *"Ahora hay 202 registros: JMeter insertó 200 tickets reales en la base de datos. La prueba realmente golpeó el sistema."*

### Paso 7 — (opcional) Reporte con gráficas
Muestra el dashboard HTML si lo generaste (ver README).

---

## 4. CONCLUSIÓN (frente a la cámara)
Resume: *"Aplicamos testing de rendimiento con Apache JMeter sobre la base de datos de nuestro
sistema. Con 20 usuarios concurrentes y 400 peticiones, el sistema respondió en 9 ms promedio,
82 peticiones por segundo y 0% de errores. Concluimos que el módulo de soporte soporta
correctamente la carga esperada."*

---

## 5. CHECKLIST DE ENTREGA
- [ ] Video grabado **mostrando tu rostro** y explicando (concepto + herramienta + demo).
- [ ] Video subido a **YouTube**.
- [ ] Video subido también a un **repositorio** (GitHub / Drive) con permiso de visualización.
- [ ] Link enviado en la asignación.

### Limpiar datos de prueba después de grabar
```bash
mysql -u viajes -p'Viajes123#' sistemareserva -e "DELETE FROM soporte WHERE nombreCliente='Carga JMeter';"
```
