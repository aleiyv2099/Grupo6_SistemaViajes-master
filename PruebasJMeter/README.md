# Pruebas de Rendimiento con Apache JMeter — Módulo Soporte (Grupo 6)

JMeter no puede pulsar la interfaz Swing, pero **sí prueba la base de datos** vía JDBC,
ejecutando las mismas consultas que hacen los DAOs con muchos usuarios a la vez.

## Qué hace `Soporte_TestPlan.jmx`
- **Conexión JDBC** a `jdbc:mysql://localhost:3306/sistemareserva` (usuario `viajes`).
- **20 usuarios concurrentes × 10 repeticiones = 400 peticiones.**
- Dos operaciones (como los DAOs):
  - **RF-1 Registrar Ticket** → `INSERT INTO soporte(...)`
  - **RF-2 Listar Tickets** → `SELECT * FROM soporte`
- Listeners: **Reporte Resumen** y **Ver Árbol de Resultados**.

## Requisitos
- MySQL corriendo con la BD `sistemareserva` (usuario `viajes` / `Viajes123#`).
- JMeter ya está en `~/apache-jmeter-5.6.3` con el driver `mysql-connector-j-8.0.33.jar` en `lib/`.

## Abrir en modo gráfico (para la presentación)
```bash
~/apache-jmeter-5.6.3/bin/jmeter -t ~/NetBeansProjects/Grupo6_SistemaViajes-master/PruebasJMeter/Soporte_TestPlan.jmx
```
Luego pulsa el botón verde ▶ (Arrancar). Mira los resultados en "Reporte Resumen".

## Ejecutar sin interfaz (más rápido, genera archivo de resultados)
```bash
cd ~/NetBeansProjects/Grupo6_SistemaViajes-master/PruebasJMeter
~/apache-jmeter-5.6.3/bin/jmeter -n -t Soporte_TestPlan.jmx -l resultados.jtl
```

## Generar reporte HTML (gráficas para el informe)
```bash
cd ~/NetBeansProjects/Grupo6_SistemaViajes-master/PruebasJMeter
rm -rf informe resultados.jtl
~/apache-jmeter-5.6.3/bin/jmeter -n -t Soporte_TestPlan.jmx -l resultados.jtl -e -o informe
# Abre informe/index.html en el navegador
```

## Limpiar los tickets de prueba que genera el INSERT
```bash
mysql -u viajes -p'Viajes123#' sistemareserva -e "DELETE FROM soporte WHERE nombreCliente='Carga JMeter';"
```

## Ajustar la carga
Abre el plan en modo gráfico y en **"Usuarios concurrentes de Soporte"** cambia:
- *Number of Threads (users)* = usuarios simultáneos.
- *Loop Count* = repeticiones por usuario.
