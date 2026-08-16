package speedfastapp;

/**
 * Clase principal utilizada para probar el sistema de asignación de
 * repartidores de SpeedFast. Instancia un objeto de cada subclase de
 * Pedido y demuestra el uso de polimorfismo tanto en la sobrescritura
 * como en la sobrecarga del método asignarRepartidor().
 *
 * @author Nicolás Vargas
 */
public class Main {

    public static void main(String[] args) {

        // 1.0 Instanciación de un objeto de cada subclase
        PedidoComida pedido1 = new PedidoComida("PC-001", "Av. Siempre Viva 123, Santiago");
        PedidoEncomienda pedido2 = new PedidoEncomienda("PE-002", "Calle Falsa 456, Providencia");
        PedidoExpress pedido3 = new PedidoExpress("PX-003", "Pasaje Los Aromos 789, Ñuñoa");

        // 2.0 Prueba de polimorfismo: método sobrescrito asignarRepartidor()
        pedido1.asignarRepartidor();
        System.out.println();

        pedido2.asignarRepartidor();
        System.out.println();

        pedido3.asignarRepartidor();
        System.out.println();

        // 3.0 Prueba de sobrecarga: asignarRepartidor(String nombreRepartidor)
        pedido1.asignarRepartidor("Juan Pérez");
        System.out.println();

        pedido2.asignarRepartidor("Camila Soto");
        System.out.println();

        pedido3.asignarRepartidor("Luis Díaz");
    }
}
