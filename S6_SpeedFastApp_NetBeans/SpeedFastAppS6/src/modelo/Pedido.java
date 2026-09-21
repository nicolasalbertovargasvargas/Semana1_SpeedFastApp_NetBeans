package modelo;

/**
 * Representa un pedido dentro del sistema de reparto de SpeedFast.
 * Cada pedido nace con estado PENDIENTE y sin repartidor asignado.
 *
 * @author Nicolás Vargas
 */
public class Pedido {

    // 1.0 Atributos encapsulados
    private int id;
    private String direccionEntrega;
    private String tipo;
    private EstadoPedido estado;
    private String repartidorAsignado;

    // 1.1 Constructor: todo pedido nuevo nace PENDIENTE y sin repartidor
    public Pedido(int id, String direccionEntrega, String tipo) {
        this.id = id;
        this.direccionEntrega = direccionEntrega;
        this.tipo = tipo;
        this.estado = EstadoPedido.PENDIENTE;
        this.repartidorAsignado = null;
    }

    // 1.2 Getters
    public int getId() {
        return id;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public String getTipo() {
        return tipo;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public String getRepartidorAsignado() {
        return repartidorAsignado;
    }

    // 1.3 Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setRepartidorAsignado(String repartidorAsignado) {
        this.repartidorAsignado = repartidorAsignado;
    }

    // 1.4 Actualiza el estado a partir de un String, convirtiéndolo
    // internamente al enum EstadoPedido
    public void setEstado(String nuevoEstado) {
        try {
            this.estado = EstadoPedido.valueOf(nuevoEstado);
        } catch (IllegalArgumentException e) {
            System.out.println("Estado inválido para el pedido #" + id + ": " + nuevoEstado);
        }
    }

    // 2.0 Representación en texto del pedido
    @Override
    public String toString() {
        return "Pedido{id=" + id + ", direccionEntrega='" + direccionEntrega
                + "', tipo='" + tipo + "', estado=" + estado
                + ", repartidor=" + repartidorAsignado + "}";
    }
}
