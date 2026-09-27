package modelo;

import java.sql.Date;
import java.sql.Time;

/**
 * Representa la relacion entre un pedido y el repartidor que lo lleva,
 * junto con la fecha y hora en que se inicio la entrega. Corresponde a
 * la tabla "entrega", que vincula pedido(id) y repartidor(id).
 *
 * @author Nicolás Vargas
 */
public class Entrega {

    // 1.0 Atributos encapsulados
    private int id;
    private int idPedido;
    private int idRepartidor;
    private Date fecha;
    private Time hora;

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

    // 2.0 Representacion en texto de la entrega
    @Override
    public String toString() {
        return "Entrega{id=" + id + ", idPedido=" + idPedido
                + ", idRepartidor=" + idRepartidor + ", fecha=" + fecha
                + ", hora=" + hora + "}";
    }
}
