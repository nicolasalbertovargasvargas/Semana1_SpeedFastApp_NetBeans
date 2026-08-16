package speedfastapp;

/**
 * Clase base que representa un pedido genérico dentro del sistema de la
 * empresa de reparto a domicilio SpeedFast. Contiene los atributos comunes
 * a todo pedido y define el comportamiento base de asignación de repartidor,
 * el cual será sobrescrito por cada subclase según su lógica particular.
 *
 * @author Nicolás Vargas
 */
public class Pedido {

    // 1.0 Atributos encapsulados
    private String idPedido;
    private String direccionEntrega;
    private String tipoPedido;

    // 1.1 Constructor completo
    public Pedido(String idPedido, String direccionEntrega, String tipoPedido) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.tipoPedido = tipoPedido;
    }

    // 1.2 Getters y Setters
    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    // 2.0 Método base asignarRepartidor() - será sobrescrito en cada subclase
    public void asignarRepartidor() {
        System.out.println("Asignando repartidor...");
        System.out.println("-> Pedido genérico asignado sin validaciones específicas.");
    }

    // 3.0 Método sobrecargado asignarRepartidor(String nombreRepartidor)
    // Versión base que recibe el nombre del repartidor asignado
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("Asignando repartidor...");
        System.out.println("-> Pedido asignado a " + nombreRepartidor);
    }
}
