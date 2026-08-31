package speedfastapp;

/**
 * Subclase de Pedido que representa un pedido de comida proveniente de
 * restaurantes. Requiere que el repartidor asignado cuente con mochila
 * térmica, por lo que sobrescribe la lógica de asignación heredada, e
 * implementa su propia fórmula de tiempo estimado de entrega.
 *
 * @author Nicolás Vargas
 */
public class PedidoComida extends Pedido {

    // 1.0 Constructor
    public PedidoComida(String idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    // 2.0 Implementación del método abstracto: 15 min base + 2 min por km
    @Override
    public int calcularTiempoEntrega() {
        return (int) (15 + 2 * getDistanciaKm());
    }

    // 3.0 Sobrescritura del método asignarRepartidor() - asignación AUTOMÁTICA
    @Override
    public void asignarRepartidor() {
        System.out.println("[Pedido " + getTipo() + "]");
        System.out.println("Asignando repartidor...");
        System.out.println("-> Verificando mochila térmica... OK");
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
        System.out.println("-> Verificando mochila térmica... OK");
        System.out.println("-> Pedido asignado a " + nombreRepartidor);
        setRepartidorAsignado(nombreRepartidor);
    }
}
