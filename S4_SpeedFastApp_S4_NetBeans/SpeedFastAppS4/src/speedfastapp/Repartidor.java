package speedfastapp;

import java.util.List;
import java.util.Random;

/**
 * Representa a un repartidor de SpeedFast que procesa su lista de pedidos
 * de forma secuencial dentro de su propio hilo de ejecución. Al implementar
 * Runnable, cada Repartidor puede ejecutarse de forma independiente y
 * concurrente junto con otros repartidores, simulando entregas simultáneas
 * mediante pausas aleatorias con Thread.sleep().
 *
 * @author Nicolás Vargas
 */
public class Repartidor implements Runnable {

    // 1.0 Atributos: nombre del repartidor y su lista de pedidos asignados
    private final String nombre;
    private final List<Pedido> pedidosAsignados;
    private final Random random;

    // 1.1 Constructor
    public Repartidor(String nombre, List<Pedido> pedidosAsignados) {
        this.nombre = nombre;
        this.pedidosAsignados = pedidosAsignados;
        this.random = new Random();
    }

    public String getNombre() {
        return nombre;
    }

    public List<Pedido> getPedidosAsignados() {
        return pedidosAsignados;
    }

    // 2.0 Lógica que se ejecuta en el hilo: entrega secuencial de los
    // pedidos asignados a este repartidor, uno tras otro
    @Override
    public void run() {
        for (Pedido pedido : pedidosAsignados) {
            // 2.1 Asignación silenciosa del repartidor al pedido (acceso de
            // paquete al setter protegido de Pedido, sin imprimir mensajes
            // adicionales de asignación para no ensuciar la simulación)
            pedido.setRepartidorAsignado(nombre);

            // 2.2 Mensaje de avance por consola
            System.out.println("[Repartidor: " + nombre + "] Entregando "
                    + pedido.getClass().getSimpleName() + " #" + pedido.getIdPedido() + "...");

            // 2.3 Simulación de la entrega con una pausa aleatoria entre
            // 1 y 3 segundos, usando Thread.sleep()
            try {
                int tiempoSimuladoMs = 1000 + random.nextInt(2000);
                Thread.sleep(tiempoSimuladoMs);
            } catch (InterruptedException e) {
                // 2.4 Manejo de excepciones: si el hilo es interrumpido,
                // se restaura el estado de interrupción y se detiene
                // este repartidor sin afectar al resto del programa
                System.out.println("[Repartidor: " + nombre + "] Entrega interrumpida.");
                Thread.currentThread().interrupt();
                return;
            }

            // 2.5 Se marca el pedido como despachado (interfaz Despachable)
            pedido.despachar();

            // 2.6 Mensaje de confirmación de entrega
            System.out.println("[Repartidor: " + nombre + "] Pedido #" + pedido.getIdPedido() + " entregado.");
        }
    }
}
