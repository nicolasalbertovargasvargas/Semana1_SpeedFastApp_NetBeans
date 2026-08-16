package speedfastapp;

/**
 * Subclase de Pedido que representa el envío de documentos o paquetes.
 * Requiere validar el peso y el embalaje antes de asignar un repartidor,
 * por lo que sobrescribe la lógica de asignación heredada.
 *
 * @author Nicolás Vargas
 */
public class PedidoEncomienda extends Pedido {

    // 1.0 Constructor
    public PedidoEncomienda(String idPedido, String direccionEntrega) {
        super(idPedido, direccionEntrega, "Encomienda");
    }

    // 2.0 Sobrescritura del método asignarRepartidor()
    @Override
    public void asignarRepartidor() {
        System.out.println("[Pedido Encomienda]");
        System.out.println("Asignando repartidor...");
        System.out.println("-> Validando peso y embalaje... OK");
    }

    // 3.0 Sobrecarga del método asignarRepartidor(String nombreRepartidor)
    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("[Pedido Encomienda]");
        System.out.println("Asignando repartidor...");
        System.out.println("-> Validando peso y embalaje... OK");
        System.out.println("-> Pedido asignado a " + nombreRepartidor);
    }
}
