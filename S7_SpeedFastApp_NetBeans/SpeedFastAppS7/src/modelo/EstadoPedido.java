package modelo;

/**
 * Enum que representa los posibles estados de un pedido dentro del
 * sistema de reparto de SpeedFast. Sus valores coinciden con los
 * strings almacenados en la columna "estado" de la tabla pedido.
 *
 * @author Nicolás Vargas
 */
public enum EstadoPedido {
    PENDIENTE,
    EN_REPARTO,
    ENTREGADO
}
