package modelo;

/**
 * Representa un pedido dentro del sistema SpeedFast. Sus atributos
 * corresponden a las columnas de la tabla "pedidos".
 *
 * @author Nicolás Vargas
 */
public class Pedido {

    // 1.0 Atributos encapsulados
    private int id;
    private String direccion;
    private TipoPedido tipo;
    private EstadoPedido estado;

    // 1.1 Constructor para un pedido nuevo (sin id aun, lo asigna la BD)
    public Pedido(String direccion, TipoPedido tipo, EstadoPedido estado) {
        this.direccion = direccion;
        this.tipo = tipo;
        this.estado = estado;
    }

    // 1.2 Constructor completo, usado al reconstruir un Pedido desde
    // un ResultSet (incluye el id generado por la base de datos)
    public Pedido(int id, String direccion, TipoPedido tipo, EstadoPedido estado) {
        this.id = id;
        this.direccion = direccion;
        this.tipo = tipo;
        this.estado = estado;
    }

    // 1.3 Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public TipoPedido getTipo() {
        return tipo;
    }

    public void setTipo(TipoPedido tipo) {
        this.tipo = tipo;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    // 2.0 Texto legible usado en los JComboBox de seleccion de pedido
    // (ej. al registrar una entrega): "id - direccion"
    @Override
    public String toString() {
        return id + " - " + direccion;
    }
}
