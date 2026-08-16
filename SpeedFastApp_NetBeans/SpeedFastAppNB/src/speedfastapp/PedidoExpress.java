package speedfastapp;

/**
 * Subclase de Pedido que representa una compra express de supermercado o
 * farmacia. Debe asignarse al repartidor más cercano con disponibilidad
 * inmediata, por lo que sobrescribe la lógica de asignación heredada.
 *
 * @author Nicolás Vargas
 */
public class PedidoExpress extends Pedido {

    // 1.0 Constructor
    public PedidoExpress(String idPedido, String direccionEntrega) {
        super(idPedido, direccionEntrega, "Compra Express");
    }

    // 2.0 Sobrescritura del método asignarRepartidor()
    @Override
    public void asignarRepartidor() {
        System.out.println("[Pedido Express]");
        System.out.println("Asignando repartidor...");
        System.out.println("-> Repartidor más cercano con disponibilidad inmediata encontrado.");
    }

    // 3.0 Sobrecarga del método asignarRepartidor(String nombreRepartidor)
    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("[Pedido Express]");
        System.out.println("Asignando repartidor...");
        System.out.println("-> Repartidor más cercano con disponibilidad inmediata encontrado.");
        System.out.println("-> Pedido asignado a " + nombreRepartidor);
    }
}
