package modelo;

/**
 * Representa un pedido dentro del sistema de reparto de SpeedFast.
 * Sus atributos corresponden exactamente a las columnas de la tabla
 * "pedido" en la base de datos speedfast_db.
 *
 * @author Nicolás Vargas
 */
public class Pedido {

    // 1.0 Atributos encapsulados, uno por columna de la tabla pedido
    private int id;
    private String direccion;
    private String tipo;
    private EstadoPedido estado;

    // 1.1 Constructor para un pedido nuevo (sin id aun, lo asigna la BD)
    public Pedido(String direccion, String tipo) {
        this.direccion = direccion;
        this.tipo = tipo;
        this.estado = EstadoPedido.PENDIENTE;
    }

    // 1.2 Constructor completo, usado al reconstruir un Pedido leido
    // desde un ResultSet (incluye el id generado por la base de datos)
    public Pedido(int id, String direccion, String tipo, String estado) {
        this.id = id;
        this.direccion = direccion;
        this.tipo = tipo;
        this.setEstado(estado);
    }

    // 1.3 Getters
    public int getId() {
        return id;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTipo() {
        return tipo;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    // 1.4 Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // 1.5 Actualiza el estado a partir de un String, convirtiendolo
    // internamente al enum EstadoPedido
    public void setEstado(String nuevoEstado) {
        try {
            this.estado = EstadoPedido.valueOf(nuevoEstado);
        } catch (IllegalArgumentException e) {
            System.out.println("Estado invalido para el pedido #" + id + ": " + nuevoEstado);
        }
    }

    // 2.0 Representacion en texto del pedido
    @Override
    public String toString() {
        return "Pedido{id=" + id + ", direccion='" + direccion
                + "', tipo='" + tipo + "', estado=" + estado + "}";
    }
}
