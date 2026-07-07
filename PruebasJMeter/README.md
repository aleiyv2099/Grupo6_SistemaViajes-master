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
- MySQL corriendo con la BD `sistemareserva` (usuario `viajes` / `Viajes123#`) — ver README principal.
- **Apache JMeter instalado.** JMeter NO viene en este repositorio; se instala aparte:
  ```bash
  # 1. Descargar y descomprimir (necesita Java 17+)
  wget https://dlcdn.apache.org/jmeter/binaries/apache-jmeter-5.6.3.tgz
  tar xzf apache-jmeter-5.6.3.tgz
  # 2. Copiar el driver JDBC de MySQL a la carpeta lib de JMeter (imprescindible para conectar a la BD)
  #    (el driver está en tu repositorio Maven tras compilar el proyecto)
  cp ~/.m2/repository/com/mysql/mysql-connector-j/8.0.33/mysql-connector-j-8.0.33.jar apache-jmeter-5.6.3/lib/
  ```
  En los comandos de abajo, ajusta la ruta `~/apache-jmeter-5.6.3` a donde lo hayas descomprimido.

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
