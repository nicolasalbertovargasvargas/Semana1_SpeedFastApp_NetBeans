package speedfastapp;

/**
 * Subclase de Pedido que representa el envío de documentos o paquetes.
 * Implementa su propia fórmula de tiempo estimado de entrega según la
 * distancia a recorrer, ajustando el resultado a un valor entero.
 *
 * @author Nicolás Vargas
 */
public class PedidoEncomienda extends Pedido {

    // 1.0 Constructor
    public PedidoEncomienda(String idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    // 2.0 Implementación del método abstracto: 20 min base + 1.5 min por km
    // Se usa Math.round() para ajustar el resultado a un valor entero
    @Override
    public int calcularTiempoEntrega() {
        return Math.round((float) (20 + 1.5 * getDistanciaKm()));
    }
}
