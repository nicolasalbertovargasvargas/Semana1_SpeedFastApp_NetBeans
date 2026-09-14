package speedfastapp;

/**
 * Enum que representa los posibles estados de un pedido dentro del
 * sistema de reparto de SpeedFast. Usar un enum en vez de String
 * evita errores de tipeo y mejora la legibilidad y seguridad del código.
 *
 * @author Nicolás Vargas
 */
public enum EstadoPedido {
    PENDIENTE,
    EN_REPARTO,
    ENTREGADO
}
