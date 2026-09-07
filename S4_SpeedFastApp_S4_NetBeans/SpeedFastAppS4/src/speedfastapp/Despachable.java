package speedfastapp;

/**
 * Interfaz que define el comportamiento de despacho de un pedido.
 * Permite desacoplar la operación de despacho de la jerarquía de clases
 * de Pedido, favoreciendo la mantenibilidad del sistema.
 *
 * @author Nicolás Vargas
 */
public interface Despachable {

    // 1.0 Contrato: cualquier clase que implemente esta interfaz
    // debe definir cómo se despacha un pedido
    void despachar();
}
