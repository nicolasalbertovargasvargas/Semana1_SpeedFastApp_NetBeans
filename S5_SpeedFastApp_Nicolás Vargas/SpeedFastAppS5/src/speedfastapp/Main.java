package speedfastapp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Clase principal que simula la coordinación de entregas concurrentes en
 * SpeedFast. Instancia una ZonaDeCarga compartida, agrega varios pedidos
 * pendientes y crea tres repartidores que compiten de forma segura por
 * retirarlos y entregarlos, demostrando el uso de sincronización para
 * evitar condiciones de carrera sobre el recurso compartido.
 *
 * @author Nicolás Vargas
 */
public class Main {

    public static void main(String[] args) {

        // 1.0 Instancia la zona de carga compartida
        ZonaDeCarga zonaDeCarga = new ZonaDeCarga();

        // 2.0 Agrega al menos 5 pedidos al sistema
        zonaDeCarga.agregarPedido(new Pedido(1, "Santiago Centro"));
        zonaDeCarga.agregarPedido(new Pedido(2, "Providencia"));
        zonaDeCarga.agregarPedido(new Pedido(3, "Ñuñoa"));
        zonaDeCarga.agregarPedido(new Pedido(4, "Recoleta"));
        zonaDeCarga.agregarPedido(new Pedido(5, "Las Condes"));

        System.out.println();

        // 3.0 Crea tres repartidores, todos apuntando a la misma zona de carga
        Repartidor repartidorJuan = new Repartidor("Juan", zonaDeCarga);
        Repartidor repartidorCamila = new Repartidor("Camila", zonaDeCarga);
        Repartidor repartidorPedro = new Repartidor("Pedro", zonaDeCarga);

        // 4.0 Ejecuta los tres repartidores concurrentemente con ExecutorService
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.execute(repartidorJuan);
        executor.execute(repartidorCamila);
        executor.execute(repartidorPedro);

        // 5.0 Cierre ordenado: no acepta tareas nuevas y espera a que los
        // tres repartidores terminen de retirar y entregar todos los pedidos
        executor.shutdown();
        try {
            boolean terminoATiempo = executor.awaitTermination(30, TimeUnit.SECONDS);
            if (!terminoATiempo) {
                // 5.1 Manejo de excepción / caso límite: si algún repartidor
                // no terminó dentro del plazo, se fuerza el cierre
                System.out.println("Algunos repartidores no terminaron a tiempo, forzando cierre...");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            // 5.2 Manejo de excepciones al esperar la finalización de los hilos
            System.out.println("La espera de finalización fue interrumpida.");
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        // 6.0 Mensaje final una vez que todos los pedidos fueron procesados
        System.out.println();
        System.out.println("Todos los pedidos han sido entregados correctamente.");
    }
}
