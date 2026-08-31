package speedfastapp;

/**
 * Interfaz que define el comportamiento de cancelación de un pedido.
 * Desacopla la lógica de cancelación de la jerarquía de clases de
 * Pedido, favoreciendo la mantenibilidad del sistema.
 *
 * @author Nicolás Vargas
 */
public interface Cancelable {

    // 1.0 Contrato: cualquier clase que implemente esta interfaz
    // debe definir cómo se cancela un pedido
    void cancelar();
}
