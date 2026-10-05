package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.EstadoPedido;
import modelo.Pedido;
import modelo.TipoPedido;

/**
 * DAO con el CRUD completo de la entidad Pedido sobre la tabla
 * "pedidos" de speedfast_db, incluyendo un metodo de lectura con
 * filtros opcionales por estado y/o tipo.
 *
 * @author Nicolás Vargas
 */
public class PedidoDAO {

    // 1.0 CREATE: inserta un pedido nuevo y recupera su id autogenerado
    public void create(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO pedidos (direccion, tipo, estado) VALUES (?, ?, ?)";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, pedido.getDireccion());
            ps.setString(2, pedido.getTipo().name());
            ps.setString(3, pedido.getEstado().name());
            ps.executeUpdate();

            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    pedido.setId(claves.getInt(1));
                }
            }
        }
    }

    // 2.0 READ ALL: devuelve todos los pedidos, sin filtro
    public List<Pedido> readAll() throws SQLException {
        return readAllFiltrado(null, null);
    }

    // 2.1 READ ALL con filtros OPCIONALES por estado y/o tipo. Pasar
    // null (o cadena vacia) en cualquiera de los dos significa "sin
    // filtrar por ese criterio". El filtro se arma dinamicamente pero
    // siempre usando PreparedStatement, nunca concatenando el valor
    // directamente en el SQL.
    public List<Pedido> readAllFiltrado(String estadoFiltro, String tipoFiltro) throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT id, direccion, tipo, estado FROM pedidos WHERE 1 = 1");
        if (estadoFiltro != null && !estadoFiltro.isEmpty()) {
            sql.append(" AND estado = ?");
        }
        if (tipoFiltro != null && !tipoFiltro.isEmpty()) {
            sql.append(" AND tipo = ?");
        }
        sql.append(" ORDER BY id");

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql.toString())) {

            int indice = 1;
            if (estadoFiltro != null && !estadoFiltro.isEmpty()) {
                ps.setString(indice++, estadoFiltro);
            }
            if (tipoFiltro != null && !tipoFiltro.isEmpty()) {
                ps.setString(indice, tipoFiltro);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pedidos.add(new Pedido(
                            rs.getInt("id"),
                            rs.getString("direccion"),
                            TipoPedido.valueOf(rs.getString("tipo")),
                            EstadoPedido.valueOf(rs.getString("estado"))
                    ));
                }
            }
        }
        return pedidos;
    }

    // 3.0 Busca un pedido por su id; devuelve null si no existe
    public Pedido buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, direccion, tipo, estado FROM pedidos WHERE id = ?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Pedido(
                            rs.getInt("id"),
                            rs.getString("direccion"),
                            TipoPedido.valueOf(rs.getString("tipo")),
                            EstadoPedido.valueOf(rs.getString("estado"))
                    );
                }
            }
        }
        return null;
    }

    // 4.0 UPDATE: actualiza direccion, tipo y estado de un pedido existente
    public void update(Pedido pedido) throws SQLException {
        String sql = "UPDATE pedidos SET direccion = ?, tipo = ?, estado = ? WHERE id = ?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, pedido.getDireccion());
            ps.setString(2, pedido.getTipo().name());
            ps.setString(3, pedido.getEstado().name());
            ps.setInt(4, pedido.getId());
            ps.executeUpdate();
        }
    }

    // 5.0 DELETE: elimina un pedido por su id
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM pedidos WHERE id = ?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
