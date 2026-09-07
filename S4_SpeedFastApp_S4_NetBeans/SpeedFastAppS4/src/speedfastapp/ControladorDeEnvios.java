package speedfastapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase de control que centraliza el historial de entregas realizadas
 * por el sistema SpeedFast. Implementa la interfaz Rastreable para
 * desacoplar esta responsabilidad de la jerarquía de Pedido, ya que
 * el historial abarca múltiples pedidos y no la lógica de uno solo.
 * Esta clase es compartida entre los distintos hilos Repartidor, por lo
 * que su método de registro está sincronizado para evitar condiciones
 * de carrera al agregar entregas de forma concurrente.
 *
 * @author Nicolás Vargas
 */
public class ControladorDeEnvios implements Rastreable {

    // 1.0 Historial de pedidos despachados, usando ArrayList
    private final List<Pedido> historial;

    // 1.1 Constructor
    public ControladorDeEnvios() {
        this.historial = new ArrayList<>();
    }

    // 2.0 Registra un pedido en el historial, solo si ya fue despachado.
    // Sincronizado porque varios hilos Repartidor pueden llamarlo al
    // mismo tiempo y ArrayList no es seguro para concurrencia.
    public synchronized void registrarEntrega(Pedido pedido) {
        if (pedido.isDespachado()) {
            historial.add(pedido);
        }
    }

    // 3.0 Implementación de la interfaz Rastreable
    @Override
    public synchronized void verHistorial() {
        System.out.println("Historial:");
        for (Pedido p : historial) {
            System.out.println("- " + p.getClass().getSimpleName() + " #" + p.getIdPedido()
                    + " – entregado por " + p.getRepartidorAsignado());
        }
    }
}
