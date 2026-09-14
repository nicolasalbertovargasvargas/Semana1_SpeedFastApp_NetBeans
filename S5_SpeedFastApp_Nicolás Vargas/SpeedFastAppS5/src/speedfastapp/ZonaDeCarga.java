package speedfastapp;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Recurso compartido que representa la zona de carga común de SpeedFast,
 * donde llegan los pedidos pendientes y desde donde los repartidores los
 * retiran para iniciar su entrega. Sus dos operaciones son synchronized
 * para que, aunque varios hilos Repartidor la consulten al mismo tiempo,
 * solo uno a la vez pueda modificar la cola interna. Esto evita que dos
 * repartidores retiren el mismo pedido (condición de carrera).
 *
 * @author Nicolás Vargas
 */
public class ZonaDeCarga {

    // 1.0 Cola interna de pedidos pendientes por retirar
    private final Queue<Pedido> pedidosPendientes;

    // 1.1 Constructor
    public ZonaDeCarga() {
        this.pedidosPendientes = new LinkedList<>();
        System.out.println("[Zona de carga inicializada]");
    }

    // 2.0 Agrega un pedido a la zona de carga. Sincronizado porque, aunque
    // en este sistema los pedidos se agregan antes de iniciar los hilos,
    // el método debe ser seguro ante cualquier acceso concurrente futuro.
    public synchronized void agregarPedido(Pedido p) {
        pedidosPendientes.add(p);
        System.out.println("Pedido #" + p.getId() + " agregado. Destino: " + p.getDireccionEntrega());
    }

    // 3.0 Retira un único pedido de la zona de carga de forma segura.
    // Al ser synchronized, si dos repartidores llaman a este método al
    // mismo tiempo, uno espera a que el otro termine, garantizando que
    // cada pedido salga exactamente una vez de la cola.
    // Devuelve null cuando ya no quedan pedidos pendientes.
    public synchronized Pedido retirarPedido() {
        return pedidosPendientes.poll();
    }
}
