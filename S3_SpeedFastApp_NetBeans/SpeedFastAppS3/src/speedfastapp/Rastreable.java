package speedfastapp;

/**
 * Interfaz que define el comportamiento de trazabilidad del historial
 * de entregas. Se implementa en una clase de control separada de la
 * jerarquía de Pedido, ya que su responsabilidad abarca múltiples
 * pedidos y no a uno solo.
 *
 * @author Nicolás Vargas
 */
public interface Rastreable {

    // 1.0 Contrato: cualquier clase que implemente esta interfaz
    // debe definir cómo se muestra el historial de entregas
    void verHistorial();
}
