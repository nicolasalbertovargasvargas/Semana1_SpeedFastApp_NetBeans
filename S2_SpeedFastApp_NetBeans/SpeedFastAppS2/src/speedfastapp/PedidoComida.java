package speedfastapp;

/**
 * Subclase de Pedido que representa un pedido de comida proveniente de
 * restaurantes. Implementa su propia fórmula de tiempo estimado de
 * entrega según la distancia a recorrer.
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
}
