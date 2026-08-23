package speedfastapp;

/**
 * Clase principal utilizada para probar el sistema de cálculo de tiempos
 * de entrega de SpeedFast. Instancia un objeto de cada subclase de la
 * clase abstracta Pedido, muestra su resumen y calcula su tiempo estimado
 * de entrega, imprimiendo finalmente una comparación entre los tres.
 *
 * @author Nicolás Vargas
 */
public class Main {

    public static void main(String[] args) {

        // 1.0 Instanciación de un objeto de cada subclase
        PedidoComida pedido1 = new PedidoComida("001", "Av. Italia 456", 4);
        PedidoEncomienda pedido2 = new PedidoEncomienda("002", "Av. Independencia 123", 6);
        PedidoExpress pedido3 = new PedidoExpress("003", "Av. Apoquindo 1500", 7);

        // 2.0 Referencias de tipo Pedido (clase abstracta) para recorrer
        // los tres pedidos de forma polimórfica
        Pedido[] pedidos = { pedido1, pedido2, pedido3 };

        // 3.0 Muestra el resumen y el tiempo estimado de cada pedido
        for (Pedido p : pedidos) {
            // 3.1 mostrarResumen() está implementado en la clase abstracta
            p.mostrarResumen();
            // 3.2 calcularTiempoEntrega() es abstracto: cada subclase
            // resuelve su propia fórmula en tiempo de ejecución
            System.out.println("Tiempo estimado de entrega: " + p.calcularTiempoEntrega() + " minutos");
            System.out.println();
        }

        // 4.0 Comparación final de los tiempos estimados
        System.out.println("=== Comparación de tiempos estimados ===");
        for (Pedido p : pedidos) {
            System.out.println(p.getClass().getSimpleName() + " #" + p.getIdPedido()
                    + " -> " + p.calcularTiempoEntrega() + " min");
        }
    }
}
