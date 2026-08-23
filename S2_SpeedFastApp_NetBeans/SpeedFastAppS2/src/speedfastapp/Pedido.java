package speedfastapp;

/**
 * Clase abstracta que representa un pedido genérico dentro del sistema de
 * la empresa de reparto a domicilio SpeedFast. Define los atributos y el
 * comportamiento común a todo pedido (mostrarResumen) y declara el método
 * abstracto calcularTiempoEntrega(), cuya lógica de cálculo depende del
 * tipo específico de pedido y debe ser implementada por cada subclase.
 *
 * @author Nicolás Vargas
 */
public abstract class Pedido {

    // 1.0 Atributos comunes encapsulados
    private String idPedido;
    private String direccionEntrega;
    private double distanciaKm;

    // 1.1 Constructor completo
    public Pedido(String idPedido, String direccionEntrega, double distanciaKm) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.distanciaKm = distanciaKm;
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

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    // 2.0 Método implementado: imprime los datos básicos del pedido
    // (compartido por todas las subclases, no se sobrescribe)
    public void mostrarResumen() {
        // 2.1 getClass().getSimpleName() obtiene el nombre real de la subclase
        // (PedidoComida, PedidoEncomienda, PedidoExpress) en tiempo de ejecución
        System.out.println(getClass().getSimpleName() + " #" + idPedido);
        System.out.println("Dirección: " + direccionEntrega);
        System.out.println("Distancia: " + formatearDistancia() + " km");
    }

    // 2.2 Formatea la distancia sin decimales innecesarios (ej: 4 en vez de 4.0)
    private String formatearDistancia() {
        if (distanciaKm == Math.floor(distanciaKm)) {
            return String.valueOf((int) distanciaKm);
        }
        return String.valueOf(distanciaKm);
    }

    // 3.0 Método abstracto: cada subclase define su propia fórmula
    // de tiempo estimado de entrega según el tipo de pedido
    public abstract int calcularTiempoEntrega();
}
