package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Entrega;

/**
 * DAO con el CRUD completo de la entidad Entrega sobre la tabla
 * "entregas" de speedfast_db. Incluye lectura con filtro opcional por
 * pedido o por repartidor, uniendo con "pedidos" y "repartidores"
 * (JOIN) solo para traer un texto legible de cada uno.
 *
 * @author Nicolás Vargas
 */
public class EntregaDAO {

    // 0.0 SELECT base reutilizado por los distintos metodos de lectura.
    // Se hace JOIN con pedidos y repartidores unicamente para mostrar
    // texto legible en la JTable (direccion y nombre), sin tener que
    // hacer una consulta aparte por cada fila.
    private static final String SELECT_BASE =
            "SELECT e.id, e.id_pedido, e.id_repartidor, e.fecha, e.hora, "
            + "p.direccion AS pedido_direccion, r.nombre AS repartidor_nombre "
            + "FROM entregas e "
            + "JOIN pedidos p ON e.id_pedido = p.id "
            + "JOIN repartidores r ON e.id_repartidor = r.id";

    // 1.0 CREATE: inserta una entrega, vinculando id_pedido con id_repartidor
    public void create(Entrega entrega) throws SQLException {
        String sql = "INSERT INTO entregas (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, entrega.getIdPedido());
            ps.setInt(2, entrega.getIdRepartidor());
            ps.setDate(3, entrega.getFecha());
            ps.setTime(4, entrega.getHora());
            ps.executeUpdate();

            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    entrega.setId(claves.getInt(1));
                }
            }
        }
    }

    // 2.0 READ ALL: todas las entregas, sin filtro
    public List<Entrega> readAll() throws SQLException {
        return readAllFiltrado(null, null);
    }

    // 2.1 READ ALL con filtro OPCIONAL por id de pedido y/o id de
    // repartidor. Pasar null en cualquiera de los dos significa
    // "sin filtrar por ese criterio".
    public List<Entrega> readAllFiltrado(Integer idPedidoFiltro, Integer idRepartidorFiltro) throws SQLException {
        List<Entrega> entregas = new ArrayList<>();

        StringBuilder sql = new StringBuilder(SELECT_BASE).append(" WHERE 1 = 1");
        if (idPedidoFiltro != null) {
            sql.append(" AND e.id_pedido = ?");
        }
        if (idRepartidorFiltro != null) {
            sql.append(" AND e.id_repartidor = ?");
        }
        sql.append(" ORDER BY e.id");

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql.toString())) {

            int indice = 1;
            if (idPedidoFiltro != null) {
                ps.setInt(indice++, idPedidoFiltro);
            }
            if (idRepartidorFiltro != null) {
                ps.setInt(indice, idRepartidorFiltro);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    entregas.add(mapearFila(rs));
                }
            }
        }
        return entregas;
    }

    // 3.0 UPDATE: actualiza pedido, repartidor, fecha y hora de una entrega
    public void update(Entrega entrega) throws SQLException {
        String sql = "UPDATE entregas SET id_pedido = ?, id_repartidor = ?, fecha = ?, hora = ? WHERE id = ?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, entrega.getIdPedido());
            ps.setInt(2, entrega.getIdRepartidor());
            ps.setDate(3, entrega.getFecha());
            ps.setTime(4, entrega.getHora());
            ps.setInt(5, entrega.getId());
            ps.executeUpdate();
        }
    }

    // 4.0 DELETE: elimina una entrega por su id
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM entregas WHERE id = ?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // 5.0 Convierte una fila del ResultSet en un objeto Entrega,
    // incluyendo el texto legible de pedido y repartidor para la tabla
    private Entrega mapearFila(ResultSet rs) throws SQLException {
        Entrega entrega = new Entrega(
                rs.getInt("id_pedido"),
                rs.getInt("id_repartidor"),
                rs.getDate("fecha"),
                rs.getTime("hora")
        );
        entrega.setId(rs.getInt("id"));
        entrega.setPedidoTexto(rs.getInt("id_pedido") + " - " + rs.getString("pedido_direccion"));
        entrega.setRepartidorTexto(rs.getInt("id_repartidor") + " - " + rs.getString("repartidor_nombre"));
        return entrega;
    }
}
