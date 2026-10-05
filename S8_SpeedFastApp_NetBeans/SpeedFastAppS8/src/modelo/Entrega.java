package modelo;

import java.sql.Date;
import java.sql.Time;

/**
 * Representa la relacion entre un pedido y el repartidor que lo lleva,
 * junto con la fecha y hora en que se inicio la entrega. Corresponde a
 * la tabla "entregas".
 *
 * @author Nicolás Vargas
 */
public class Entrega {

    // 1.0 Atributos encapsulados. idPedido/idRepartidor guardan solo el
    // id (clave foranea); pedidoTexto/repartidorTexto son campos de
    // solo lectura usados unicamente para mostrar la fila en la JTable
    // de entregas sin tener que hacer un JOIN adicional por cada consulta.
    private int id;
    private int idPedido;
    private int idRepartidor;
    private Date fecha;
    private Time hora;
    private String pedidoTexto;
    private String repartidorTexto;

    // 1.1 Constructor para una entrega nueva (sin id aun)
    public Entrega(int idPedido, int idRepartidor, Date fecha, Time hora) {
        this.idPedido = idPedido;
        this.idRepartidor = idRepartidor;
        this.fecha = fecha;
        this.hora = hora;
    }

    // 1.2 Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdRepartidor() {
        return idRepartidor;
    }

    public void setIdRepartidor(int idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getPedidoTexto() {
        return pedidoTexto;
    }

    public void setPedidoTexto(String pedidoTexto) {
        this.pedidoTexto = pedidoTexto;
    }

    public String getRepartidorTexto() {
        return repartidorTexto;
    }

    public void setRepartidorTexto(String repartidorTexto) {
        this.repartidorTexto = repartidorTexto;
    }

    // 2.0 Representacion en texto de la entrega
    @Override
    public String toString() {
        return "Entrega{id=" + id + ", idPedido=" + idPedido
                + ", idRepartidor=" + idRepartidor + ", fecha=" + fecha
                + ", hora=" + hora + "}";
    }
}
