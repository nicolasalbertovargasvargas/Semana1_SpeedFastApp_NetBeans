package speedfastapp;

/**
 * Clase abstracta que representa un pedido genérico dentro del sistema de
 * la empresa de reparto a domicilio SpeedFast. Define los atributos y el
 * comportamiento común a todo pedido, declara el método abstracto
 * calcularTiempoEntrega() (personalizado en cada subclase) e implementa
 * las interfaces Despachable y Cancelable, ya que despachar y cancelar
 * son operaciones que aplican a cualquier tipo de pedido por igual.
 *
 * @author Nicolás Vargas
 */
public abstract class Pedido implements Despachable, Cancelable {

    // 1.0 Atributos comunes encapsulados
    private String idPedido;
    private String direccionEntrega;
    private double distanciaKm;
    private String repartidorAsignado;
    private boolean despachado;
    private boolean cancelado;

    // 1.1 Constructor completo
    public Pedido(String idPedido, String direccionEntrega, double distanciaKm) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
        this.repartidorAsignado = null;
        this.despachado = false;
        this.cancelado = false;
    }

    // 1.2 Getters y Setters
    public String getIdPedido() {
        return idPedido;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public String getRepartidorAsignado() {
        return repartidorAsignado;
    }

    // 1.3 Setter protegido: solo las subclases pueden fijar el repartidor,
    // ya que la asignación depende de la lógica propia de cada una
    protected void setRepartidorAsignado(String repartidorAsignado) {
        this.repartidorAsignado = repartidorAsignado;
    }

    public boolean isDespachado() {
        return despachado;
    }

    public boolean isCancelado() {
        return cancelado;
    }

    // 1.4 Devuelve el nombre del tipo de pedido a partir del nombre de la
    // subclase real (ej: "PedidoComida" -> "Comida"), usado para mensajes
    protected String getTipo() {
        return getClass().getSimpleName().replace("Pedido", "");
    }

    // 1.5 Formatea la distancia sin decimales innecesarios (ej: 4 en vez de 4.0)
    private String formatearDistancia() {
        if (distanciaKm == Math.floor(distanciaKm)) {
            return String.valueOf((int) distanciaKm);
        }
        return String.valueOf(distanciaKm);
    }

    // 2.0 Método implementado: imprime los datos básicos del pedido
    // (compartido por todas las subclases, no se sobrescribe)
    public void mostrarResumen() {
        System.out.println("[Pedido " + getTipo() + "]");
        System.out.println("Pedido #" + idPedido);
        System.out.println("Dirección: " + direccionEntrega);
        System.out.println("Distancia: " + formatearDistancia() + " km");
        if (repartidorAsignado != null) {
            System.out.println("Repartidor asignado: " + repartidorAsignado);
        }
    }

    // 3.0 Método abstracto: cada subclase define su propia fórmula
    // de tiempo estimado de entrega según el tipo de pedido
    public abstract int calcularTiempoEntrega();

    // 4.0 Método base asignarRepartidor() (asignación AUTOMÁTICA) -
    // será SOBRESCRITO en cada subclase con su propia lógica de negocio
    public void asignarRepartidor() {
        System.out.println("Asignando repartidor...");
        System.out.println("-> Pedido genérico asignado sin validaciones específicas.");
    }

    // 5.0 Método SOBRECARGADO asignarRepartidor(String nombreRepartidor)
    // (asignación MANUAL) - también será sobrescrito en cada subclase
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("Asignando repartidor...");
        System.out.println("-> Pedido asignado a " + nombreRepartidor);
        setRepartidorAsignado(nombreRepartidor);
    }

    // 6.0 Implementación de la interfaz Despachable
    @Override
    public void despachar() {
        if (repartidorAsignado == null) {
            System.out.println("No se puede despachar: repartidor no asignado.");
            return;
        }
        despachado = true;
        System.out.println("Pedido despachado correctamente.");
    }

    // 7.0 Implementación de la interfaz Cancelable
    @Override
    public void cancelar() {
        System.out.println("Cancelando Pedido " + getTipo() + " #" + idPedido + "...");
        cancelado = true;
        System.out.println("-> Pedido cancelado exitosamente.");
    }
}
