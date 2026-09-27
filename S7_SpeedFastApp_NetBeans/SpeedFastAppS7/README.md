# SpeedFastApp - Semana 7 (JDBC + MySQL)

## Antes de abrir el proyecto

### 1. Crear la base de datos
Ejecuta el script `sql/speedfast_db.sql` en MySQL Workbench o por consola:

```
mysql -u root -p < sql/speedfast_db.sql
```

Este script corrige dos errores que traía el script del enunciado original:
- Creaba la base como `speedfast` pero luego hacía `USE speedfast_db` (nombre
  distinto). Aquí se usa `speedfast_db` de forma consistente.
- La tabla `entrega` quedaba incompleta (sin la FK hacia `repartidor` ni el
  cierre del `CREATE TABLE`). Aquí está completa.

### 2. Agregar el conector JDBC de MySQL
Este proyecto NO incluye el archivo .jar del conector (no se puede descargar
en el entorno donde se generó). Debes descargarlo tú:

1. Descarga `mysql-connector-j-8.4.0.jar` (o una versión similar) desde
   https://dev.mysql.com/downloads/connector/j/
2. Crea una carpeta `lib/` en la raíz del proyecto y copia el .jar ahí.
3. En NetBeans: clic derecho en el proyecto → Properties → Libraries →
   Add JAR/Folder → selecciona el .jar de `lib/`.
   (El `project.properties` ya está configurado para buscarlo en
   `lib/mysql-connector-j-8.4.0.jar`; si usas otro nombre de archivo,
   ajusta la propiedad `libs.mysql-connector.classpath`.)

### 3. Configurar la contraseña
Abre `src/dao/ConexionDB.java` y reemplaza el valor de `PASSWORD` por la
contraseña real de tu usuario `root` de MySQL.

## Cómo ejecutar
Una vez hecho lo anterior, corre `Main.java` (paquete `main`) desde NetBeans,
o `ant run` desde la carpeta del proyecto.

## Estructura del proyecto
- `modelo/` — Pedido, Repartidor, Entrega, EstadoPedido (reflejan las tablas)
- `dao/` — ConexionDB, PedidoDAO, RepartidorDAO, EntregaDAO
- `vista/` — VentanaPrincipal, VentanaRegistroPedido, VentanaRegistroRepartidor,
  VentanaListaPedidos
- `main/` — Main

## Qué se verificó sin una base de datos real
En el entorno donde se generó este proyecto no hay servidor MySQL disponible,
así que no se pudo probar la conexión real. Sí se verificó:
- Que el proyecto compila limpio con Ant/javac (13 archivos, sin errores).
- Que las tres ventanas que no consultan la BD al abrirse (registro de
  pedido, registro de repartidor, ventana principal) se construyen sin
  errores en un entorno gráfico.
- Que, sin servidor MySQL, `PedidoDAO.listarTodos()` efectivamente lanza
  `SQLException`, y que esa excepción es capturada por la interfaz (se
  muestra un diálogo de error) en vez de detener la aplicación.

**Falta probar en tu máquina:** la conexión real a MySQL, el guardado y
listado de datos reales, y el flujo completo de asignar repartidor.
