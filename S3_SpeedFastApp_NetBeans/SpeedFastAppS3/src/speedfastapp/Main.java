package speedfastapp;

/**
 * Clase principal utilizada para simular el sistema integral de entregas
 * de SpeedFast. Demuestra la asignación automática y manual de
 * repartidores (polimorfismo mediante sobreescritura y sobrecarga), el
 * cálculo del tiempo estimado (abstracción), el despacho y la cancelación
 * de pedidos (interfaces Despachable y Cancelable), y la visualización
 * del historial de entregas (interfaz Rastreable) a través de
 * ControladorDeEnvios.
 *
 * @author Nicolás Vargas
 */
public class Main {

    public static void main(String[] args) {

        // 1.0 Instanciación de un pedido de cada subclase
        Pedido pedido1 = new PedidoComida("101", "Av. Vicuña Mackenna 890", 4);
        Pedido pedido2 = new PedidoEncomienda("102", "Av. Santa Rosa 567", 7);
        Pedido pedido3 = new PedidoExpress("103", "Av. Grecia 2130", 6);

        // 2.0 Controlador que centraliza el historial de entregas
        ControladorDeEnvios controlador = new ControladorDeEnvios();

        // 3.0 ASIGNACIÓN AUTOMÁTICA (sobreescritura de asignarRepartidor())
        System.out.println("=== Asignación automática de repartidor ===");
        pedido1.asignarRepartidor();
        System.out.println();

        // 3.1 Resumen, tiempo estimado y despacho del pedido de comida
        pedido1.mostrarResumen();
        System.out.println("Tiempo estimado: " + pedido1.calcularTiempoEntrega() + " minutos");
        pedido1.despachar();
        System.out.println();

        // 4.0 ASIGNACIÓN MANUAL (sobrecarga de asignarRepartidor(String))
        System.out.println("=== Asignación manual de repartidor ===");
        pedido2.asignarRepartidor("Daniela Tapia");
        System.out.println();

        // 4.1 Resumen, tiempo estimado y despacho del pedido de encomienda
        pedido2.mostrarResumen();
        System.out.println("Tiempo estimado: " + pedido2.calcularTiempoEntrega() + " minutos");
        pedido2.despachar();
        System.out.println();

        // 5.0 CANCELACIÓN de un pedido (interfaz Cancelable)
        System.out.println("=== Cancelación de pedido ===");
        pedido3.cancelar();
        System.out.println();

        // 6.0 Registro de entregas despachadas en el historial
        controlador.registrarEntrega(pedido1);
        controlador.registrarEntrega(pedido2);
        // 6.1 pedido3 no se registra: fue cancelado, no despachado

        // 7.0 VISUALIZACIÓN DEL HISTORIAL (interfaz Rastreable, ArrayList)
        controlador.verHistorial();
    }
}
