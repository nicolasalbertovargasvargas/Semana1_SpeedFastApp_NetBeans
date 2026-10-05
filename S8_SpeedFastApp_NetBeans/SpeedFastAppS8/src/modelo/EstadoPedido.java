package modelo;

/**
 * Enum que representa los posibles estados de un pedido, coincidiendo
 * con los valores del tipo ENUM de la columna "estado" en la tabla
 * pedidos.
 *
 * @author Nicolás Vargas
 */
public enum EstadoPedido {
    PENDIENTE,
    EN_REPARTO,
    ENTREGADO
}
