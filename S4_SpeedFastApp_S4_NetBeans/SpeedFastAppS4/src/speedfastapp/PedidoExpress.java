package speedfastapp;

/**
 * Subclase de Pedido que representa una compra express de supermercado o
 * farmacia. Debe asignarse al repartidor más cercano con disponibilidad
 * inmediata, por lo que sobrescribe la lógica de asignación heredada, e
 * implementa su propia fórmula de tiempo estimado de entrega.
 *
 * @author Nicolás Vargas
 */
public class PedidoExpress extends Pedido {

    // 1.0 Constructor
    public PedidoExpress(String idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    // 2.0 Implementación del método abstracto: 10 min base
    // + 5 min extra si la distancia es mayor a 5 km
    @Override
    public int calcularTiempoEntrega() {
        int tiempoBase = 10;
        if (getDistanciaKm() > 5) {
            tiempoBase += 5;
        }
        return tiempoBase;
    }

    // 3.0 Sobrescritura del método asignarRepartidor() - asignación AUTOMÁTICA
    @Override
    public void asignarRepartidor() {
        System.out.println("[Pedido " + getTipo() + "]");
        System.out.println("Asignando repartidor...");
        System.out.println("-> Repartidor más cercano con disponibilidad inmediata encontrado.");
        String repartidorPorDefecto = "Luis Díaz";
        setRepartidorAsignado(repartidorPorDefecto);
        System.out.println("-> Repartidor asignado automáticamente: " + repartidorPorDefecto);
    }

    // 4.0 Sobrecarga del método asignarRepartidor(String nombreRepartidor) -
    // asignación MANUAL
    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("[Pedido " + getTipo() + "]");
        System.out.println("Asignando repartidor...");
        System.out.println("-> Repartidor más cercano con disponibilidad inmediata encontrado.");
        System.out.println("-> Pedido asignado a " + nombreRepartidor);
        setRepartidorAsignado(nombreRepartidor);
    }
}
