#!/bin/bash
# Script de instalación — Sistema de Agencia de Viajes Grupo 6
# Uso: bash setup.sh
#      bash setup.sh -p <contraseña_root>  (si tu root tiene contraseña)

set -e

ROOT_PASS=""

while getopts "p:" opt; do
  case $opt in
    p) ROOT_PASS="$OPTARG" ;;
  esac
done

if [ -n "$ROOT_PASS" ]; then
  MYSQL_CMD="mysql -u root -p$ROOT_PASS"
else
  MYSQL_CMD="mysql -u root"
fi

echo "==> Verificando conexión a MySQL..."
if ! $MYSQL_CMD -e "SELECT 1;" > /dev/null 2>&1; then
  echo "ERROR: No se pudo conectar a MySQL como root."
  echo "Si tu root tiene contraseña, usa: bash setup.sh -p TU_CONTRASEÑA"
  echo "Si necesitas sudo, ejecuta: sudo bash setup.sh"
  exit 1
fi

echo "==> Importando esquema y datos de prueba..."
$MYSQL_CMD < database/schema.sql

echo ""
echo "Instalación completada."
echo ""
echo "  Base de datos : sistemareserva"
echo "  Usuario       : mi_user"
echo "  Contraseña    : admin123"
echo ""
echo "Para cambiar las credenciales edita:"
echo "  - database/schema.sql  (línea CREATE USER)"
echo "  - src/main/resources/database.properties"
echo ""
echo "Para ejecutar la app:"
echo "  mvn package && java -jar target/SistemaAgenciaViajesGrupo6-1.0-SNAPSHOT.jar"
