package speedfastapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Clase principal utilizada para simular la optimización de entregas de
 * SpeedFast mediante programación concurrente. Instancia varios pedidos y
 * al menos tres repartidores, cada uno con su propia lista de pedidos, y
 * los ejecuta en paralelo como hilos independientes usando ExecutorService.
 * Al finalizar todas las entregas, se muestra el historial consolidado.
 *
 * @author Nicolás Vargas
 */
public class Main {

    public static void main(String[] args) {

        // 1.0 Instanciación de los pedidos que se repartirán
        Pedido pedido101 = new PedidoComida("101", "Av. Vicuña Mackenna 890", 4);
        Pedido pedido102 = new PedidoExpress("102", "Av. Grecia 2130", 6);
        Pedido pedido103 = new PedidoEncomienda("103", "Av. Santa Rosa 567", 7);
        Pedido pedido104 = new PedidoComida("104", "Av. Apoquindo 4500", 3);
        Pedido pedido105 = new PedidoExpress("105", "Av. Irarrázaval 1200", 2);
        Pedido pedido106 = new PedidoEncomienda("106", "Av. Independencia 123", 5);

        // 2.0 Controlador compartido que registrará el historial de entregas
        // de todos los repartidores (sincronizado internamente)
        ControladorDeEnvios controlador = new ControladorDeEnvios();

        // 3.0 Creación de al menos tres repartidores, cada uno con dos o
        // más pedidos asignados
        Repartidor repartidorCamila = new Repartidor("Camila", Arrays.asList(pedido101, pedido104));
        Repartidor repartidorLuis = new Repartidor("Luis", Arrays.asList(pedido102, pedido105));
        Repartidor repartidorDaniela = new Repartidor("Daniela", Arrays.asList(pedido103, pedido106));

        // 4.0 Ejecución concurrente de los repartidores usando ExecutorService
        System.out.println("=== Iniciando entregas concurrentes ===");
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.execute(repartidorCamila);
        executor.execute(repartidorLuis);
        executor.execute(repartidorDaniela);

        // 5.0 Cierre ordenado del executor: no acepta tareas nuevas y espera
        // a que todos los repartidores terminen sus entregas antes de continuar
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

        System.out.println();
        System.out.println("=== Todas las entregas concurrentes finalizaron ===");
        System.out.println();

        // 6.0 Registro de todos los pedidos despachados en el historial
        List<Pedido> todosLosPedidos = new ArrayList<>(Arrays.asList(
                pedido101, pedido102, pedido103, pedido104, pedido105, pedido106));
        for (Pedido p : todosLosPedidos) {
            controlador.registrarEntrega(p);
        }

        // 7.0 Visualización del historial consolidado de entregas
        controlador.verHistorial();
    }
}
