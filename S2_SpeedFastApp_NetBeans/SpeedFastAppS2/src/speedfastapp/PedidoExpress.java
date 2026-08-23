package speedfastapp;

/**
 * Subclase de Pedido que representa una compra express de supermercado o
 * farmacia. Implementa su propia fórmula de tiempo estimado de entrega:
 * un tiempo base fijo, más un recargo si la distancia supera los 5 km.
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
}
