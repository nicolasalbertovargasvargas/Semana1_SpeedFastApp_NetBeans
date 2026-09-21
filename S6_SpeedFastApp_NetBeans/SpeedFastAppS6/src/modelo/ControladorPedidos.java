package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador que centraliza el almacenamiento en memoria de los pedidos
 * registrados. Todas las ventanas comparten la misma instancia de esta
 * clase, de modo que los pedidos creados en el formulario de registro se
 * reflejan de inmediato en la ventana de listado.
 *
 * @author Nicolás Vargas
 */
public class ControladorPedidos {

    // 1.0 Lista en memoria con todos los pedidos del sistema
    private final List<Pedido> pedidos;

    // 1.1 Constructor
    public ControladorPedidos() {
        this.pedidos = new ArrayList<>();
    }

    // 2.0 Agrega un pedido a la lista
    public void agregarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    // 3.0 Devuelve todos los pedidos registrados
    public List<Pedido> getPedidos() {
        return pedidos;
    }

    // 4.0 Indica si ya existe un pedido con el ID indicado, para evitar
    // que se registren dos pedidos con el mismo identificador
    public boolean existeId(int id) {
        for (Pedido p : pedidos) {
            if (p.getId() == id) {
                return true;
            }
        }
        return false;
    }

    // 5.0 Busca un pedido por su ID; devuelve null si no existe
    public Pedido buscarPorId(int id) {
        for (Pedido p : pedidos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}
