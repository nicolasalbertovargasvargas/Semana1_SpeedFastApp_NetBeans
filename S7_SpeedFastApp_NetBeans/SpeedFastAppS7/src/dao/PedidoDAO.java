package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Pedido;

/**
 * DAO (Data Access Object) encargado de las operaciones de persistencia
 * de la clase Pedido sobre la tabla "pedido" de speedfast_db. Cada
 * metodo abre su propia conexion y la cierra al finalizar (try-with-resources),
 * de forma que nunca queden conexiones abiertas por error.
 *
 * @author Nicolás Vargas
 */
public class PedidoDAO {

    // 1.0 Inserta un pedido nuevo usando PreparedStatement y recupera
    // el id autogenerado por MySQL para dejarlo cargado en el objeto
    public void guardar(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO pedido (direccion, tipo, estado) VALUES (?, ?, ?)";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, pedido.getDireccion());
            ps.setString(2, pedido.getTipo());
            ps.setString(3, pedido.getEstado().name());
            ps.executeUpdate();

            // 1.1 Recupera el id generado por AUTO_INCREMENT y lo asigna
            // al objeto Pedido para que quede disponible en memoria
            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    pedido.setId(claves.getInt(1));
                }
            }
        }
    }

    // 2.0 Devuelve todos los pedidos almacenados en la base de datos,
    // usados para llenar la JTable de VentanaListaPedidos
    public List<Pedido> listarTodos() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT id, direccion, tipo, estado FROM pedido ORDER BY id";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                pedidos.add(new Pedido(
                        rs.getInt("id"),
                        rs.getString("direccion"),
                        rs.getString("tipo"),
                        rs.getString("estado")
                ));
            }
        }
        return pedidos;
    }

    // 3.0 Busca un pedido por su id; devuelve null si no existe.
    // Se usa al asignar un repartidor, para validar que el pedido exista.
    public Pedido buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, direccion, tipo, estado FROM pedido WHERE id = ?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Pedido(
                            rs.getInt("id"),
                            rs.getString("direccion"),
                            rs.getString("tipo"),
                            rs.getString("estado")
                    );
                }
            }
        }
        return null;
    }

    // 4.0 Actualiza el estado de un pedido (por ejemplo, a EN_REPARTO
    // cuando se le asigna un repartidor desde VentanaPrincipal)
    public void actualizarEstado(int idPedido, String nuevoEstado) throws SQLException {
        String sql = "UPDATE pedido SET estado = ? WHERE id = ?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, idPedido);
            ps.executeUpdate();
        }
    }
}
