package speedfastapp;

/**
 * Subclase de Pedido que representa un pedido de comida proveniente de
 * restaurantes. Requiere que el repartidor asignado cuente con mochila
 * térmica, por lo que sobrescribe la lógica de asignación heredada.
 *
 * @author Nicolás Vargas
 */
public class PedidoComida extends Pedido {

    // 1.0 Constructor
    public PedidoComida(String idPedido, String direccionEntrega) {
        super(idPedido, direccionEntrega, "Comida");
    }

    // 2.0 Sobrescritura del método asignarRepartidor()
    @Override
    public void asignarRepartidor() {
        System.out.println("[Pedido Comida]");
        System.out.println("Asignando repartidor...");
        System.out.println("-> Verificando mochila térmica... OK");
    }

    // 3.0 Sobrecarga del método asignarRepartidor(String nombreRepartidor)
    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("[Pedido Comida]");
        System.out.println("Asignando repartidor...");
        System.out.println("-> Verificando mochila térmica... OK");
        System.out.println("-> Pedido asignado a " + nombreRepartidor);
    }
}
