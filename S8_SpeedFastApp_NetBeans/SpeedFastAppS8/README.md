# SpeedFastApp - Semana 8 (CRUD completo con JDBC + Swing)

## ⚠️ Cambio de esquema respecto a la Semana 7
Este esquema usa nombres de tabla en **plural** (`repartidores`, `pedidos`,
`entregas`) y columnas `ENUM`, distinto al de la Semana 7 (singular). Si ya
tenías la base de datos de la semana pasada, **recréala** con el script
`sql/speedfast_db.sql` de esta carpeta (incluye `DROP TABLE IF EXISTS` para
las tres tablas antes de crearlas de nuevo).

## Nota sobre el enunciado
El enunciado pide una clase `ClienteDAO`, pero el caso SpeedFast no tiene
una entidad "Cliente" (es una plantilla genérica mal copiada). Se implementó
`RepartidorDAO` en su lugar, que es la entidad real definida desde la
Semana 7. El resto de los requisitos (PreparedStatement, ResultSet,
create/readAll/update/delete) se cumplen igual sobre esa entidad.

## Antes de abrir el proyecto

1. Ejecuta `sql/speedfast_db.sql` en MySQL Workbench o por consola.
2. Agrega el conector JDBC de MySQL (`mysql-connector-j-8.4.0.jar` o
   similar) en una carpeta `lib/` en la raíz del proyecto, y agrégalo como
   librería del proyecto en NetBeans (clic derecho → Properties → Libraries).
3. Abre `src/dao/ConexionDB.java` y reemplaza `PASSWORD` por tu contraseña
   real de MySQL.

## Cómo ejecutar
Corre `Main.java` (paquete `main`) desde NetBeans, o `ant run`.

## Estructura del proyecto
- `modelo/` — Pedido, Repartidor, Entrega, EstadoPedido, TipoPedido
- `dao/` — ConexionDB, RepartidorDAO, PedidoDAO, EntregaDAO (CRUD completo:
  create, readAll, update, delete; PedidoDAO y EntregaDAO también tienen
  lectura con filtros opcionales)
- `vista/` — VentanaPrincipal, VentanaRepartidores, VentanaPedidos,
  VentanaEntregas (cada una con formulario + JTable + Agregar/Actualizar/
  Eliminar/Limpiar)
- `main/` — Main

## Cómo usar cada ventana
- **Repartidores**: escribe un nombre y "Agregar". Selecciona una fila de
  la tabla para editar su nombre ("Actualizar") o eliminarla.
- **Pedidos**: dirección + tipo + estado. Los combos de "Filtrar listado"
  (arriba) son opcionales — déjalos en "TODOS" para ver todo.
- **Entregas**: elige Pedido y Repartidor de los combos (se cargan desde
  la BD; usa "Refrescar pedidos/repartidores" si registraste uno nuevo en
  otra ventana), y escribe fecha/hora en los formatos indicados
  (AAAA-MM-DD y HH:MM:SS).

## Qué se verificó sin una base de datos real
En el entorno donde se generó este proyecto no hay servidor MySQL
disponible. Sí se verificó:
- Compilación limpia con Ant/javac (14 archivos, sin errores).
- Que `VentanaPrincipal` se construye sin errores en un entorno gráfico.
- Que los tres DAO (`RepartidorDAO`, `PedidoDAO`, `EntregaDAO`) lanzan
  `SQLException` cuando no hay servidor MySQL, confirmando que el manejo
  de errores se activaría correctamente en cada ventana.
- El formato `"id - descripción"` que usan los JComboBox de Pedido y
  Repartidor.

**Falta probar en tu máquina:** el CRUD completo contra MySQL real
(agregar, editar, eliminar, filtrar, y el flujo de registrar una entrega
seleccionando pedido/repartidor desde los combos).
