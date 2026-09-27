package modelo;

/**
 * Representa a un repartidor dentro del sistema SpeedFast. Sus
 * atributos corresponden a las columnas de la tabla "repartidor".
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

    // 2.0 Representacion en texto: se usa tal cual en el JComboBox
    // de asignacion de repartidor, por eso solo muestra el nombre
    @Override
    public String toString() {
        return nombre;
    }
}
