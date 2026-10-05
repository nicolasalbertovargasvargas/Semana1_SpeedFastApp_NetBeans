package modelo;

/**
 * Representa a un repartidor dentro del sistema SpeedFast. Sus
 * atributos corresponden a las columnas de la tabla "repartidores".
 *
 * @author Nicolás Vargas
 */
public class Repartidor {

    // 1.0 Atributos encapsulados
    private int id;
    private String nombre;

    // 1.1 Constructor para un repartidor nuevo (sin id aun)
    public Repartidor(String nombre) {
        this.nombre = nombre;
    }

    // 1.2 Constructor completo, usado al leer desde un ResultSet
    public Repartidor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // 1.3 Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // 2.0 Texto legible usado en los JComboBox de seleccion de
    // repartidor (ej. al registrar una entrega): "id - nombre"
    @Override
    public String toString() {
        return id + " - " + nombre;
    }
}
