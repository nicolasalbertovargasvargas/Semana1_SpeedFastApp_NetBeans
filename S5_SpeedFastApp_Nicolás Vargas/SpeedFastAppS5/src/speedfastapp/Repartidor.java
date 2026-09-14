package speedfastapp;

import java.util.Random;

/**
 * Representa a un repartidor de SpeedFast que retira pedidos de la
 * ZonaDeCarga compartida y los entrega uno por uno. Al implementar
 * Runnable, cada Repartidor se ejecuta en su propio hilo, compitiendo
 * de forma segura con los demás repartidores por los pedidos disponibles
 * en la zona de carga.
 *
 * @author Nicolás Vargas
 */
public class Repartidor implements Runnable {

    // 1.0 Atributos: nombre del repartidor y referencia al recurso compartido
    private final String nombre;
    private final ZonaDeCarga zonaDeCarga;
    private final Random random;

    // 1.1 Constructor
    public Repartidor(String nombre, ZonaDeCarga zonaDeCarga) {
        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
        this.random = new Random();
    }

    public String getNombre() {
        return nombre;
    }

    // 2.0 Lógica que se ejecuta en el hilo: mientras la zona de carga
    // tenga pedidos disponibles, este repartidor sigue retirando y
    // entregando uno a la vez
    @Override
    public void run() {
        while (true) {
            // 2.1 Retiro seguro de un pedido (acceso sincronizado dentro
            // de ZonaDeCarga); null significa que ya no quedan pedidos
            Pedido pedido = zonaDeCarga.retirarPedido();
            if (pedido == null) {
                break;
            }

            System.out.println("[Repartidor - " + nombre + "] Retirando pedido #" + pedido.getId() + "...");

            // 2.2 Cambia el estado a EN_REPARTO y lo informa
            pedido.setEstado(EstadoPedido.EN_REPARTO.name());
            System.out.println("[Repartidor - " + nombre + "] Estado: " + pedido.getEstado());

            System.out.println("[Repartidor - " + nombre + "] Entregando pedido #" + pedido.getId() + "...");

            // 2.3 Simula la entrega con una pausa aleatoria entre 1 y 3 segundos
            try {
                int tiempoSimuladoMs = 1000 + random.nextInt(2000);
                Thread.sleep(tiempoSimuladoMs);
            } catch (InterruptedException e) {
                // 2.4 Manejo de excepciones: si el hilo es interrumpido,
                // se restaura el estado de interrupción y se detiene
                // este repartidor sin afectar a los demás
                System.out.println("[Repartidor - " + nombre + "] Entrega interrumpida.");
                Thread.currentThread().interrupt();
                return;
            }

            // 2.5 Cambia el estado a ENTREGADO y muestra el mensaje final
            pedido.setEstado(EstadoPedido.ENTREGADO.name());
            System.out.println("[Repartidor - " + nombre + "] Estado: " + pedido.getEstado());
        }
    }
}
