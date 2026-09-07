package speedfastapp;

/**
 * Subclase de Pedido que representa el envío de documentos o paquetes.
 * Requiere validar el peso y el embalaje antes de asignar un repartidor,
 * por lo que sobrescribe la lógica de asignación heredada, e implementa
 * su propia fórmula de tiempo estimado de entrega.
 *
 * @author Nicolás Vargas
 */
public class PedidoEncomienda extends Pedido {

    // 1.0 Constructor
    public PedidoEncomienda(String idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, distanciaKm);
    }

    // 2.0 Implementación del método abstracto: 20 min base + 1.5 min por km,
    // ajustado a entero truncando los decimales (ej: 30.5 -> 30)
    @Override
    public int calcularTiempoEntrega() {
        return (int) (20 + 1.5 * getDistanciaKm());
    }

    // 3.0 Sobrescritura del método asignarRepartidor() - asignación AUTOMÁTICA
    @Override
    public void asignarRepartidor() {
        System.out.println("[Pedido " + getTipo() + "]");
        System.out.println("Asignando repartidor...");
        System.out.println("-> Validando peso y embalaje... OK");
        String repartidorPorDefecto = "Camila Soto";
        setRepartidorAsignado(repartidorPorDefecto);
        System.out.println("-> Repartidor asignado automáticamente: " + repartidorPorDefecto);
    }

    // 4.0 Sobrecarga del método asignarRepartidor(String nombreRepartidor) -
    // asignación MANUAL
    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("[Pedido " + getTipo() + "]");
        System.out.println("Asignando repartidor...");
        System.out.println("-> Validando peso y embalaje... OK");
        System.out.println("-> Pedido asignado a " + nombreRepartidor);
        setRepartidorAsignado(nombreRepartidor);
    }
}
