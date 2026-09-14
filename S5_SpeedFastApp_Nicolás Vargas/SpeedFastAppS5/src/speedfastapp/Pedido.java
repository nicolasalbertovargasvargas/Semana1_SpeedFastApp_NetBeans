package speedfastapp;

/**
 * Representa un pedido dentro del sistema de reparto de SpeedFast.
 * Cada pedido nace con estado PENDIENTE y va cambiando de estado a
 * medida que un repartidor lo retira y completa su entrega.
 *
 * @author Nicolás Vargas
 */
public class Pedido {

    // 1.0 Atributos encapsulados
    private int id;
    private String direccionEntrega;
    private EstadoPedido estado;

    // 1.1 Constructor: todo pedido nuevo nace en estado PENDIENTE
    public Pedido(int id, String direccionEntrega) {
        this.id = id;
        this.direccionEntrega = direccionEntrega;
        this.estado = EstadoPedido.PENDIENTE;
    }

    // 1.2 Getters
    public int getId() {
        return id;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    // 1.3 Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    // 1.4 Método solicitado explícitamente: actualiza el estado a partir
    // de un String, convirtiéndolo internamente al enum EstadoPedido
    public void setEstado(String nuevoEstado) {
        try {
            this.estado = EstadoPedido.valueOf(nuevoEstado);
        } catch (IllegalArgumentException e) {
            // 1.5 Manejo de excepción: si llega un texto que no corresponde
            // a ningún estado válido, se informa y no se modifica el estado
            System.out.println("Estado inválido para el pedido #" + id + ": " + nuevoEstado);
        }
    }

    // 2.0 Representación en texto del pedido
    @Override
    public String toString() {
        return "Pedido{id=" + id + ", direccionEntrega='" + direccionEntrega
                + "', estado=" + estado + "}";
    }
}
