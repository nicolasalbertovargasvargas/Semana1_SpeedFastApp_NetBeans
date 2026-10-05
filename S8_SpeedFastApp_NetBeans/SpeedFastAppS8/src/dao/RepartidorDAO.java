package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Repartidor;

/**
 * DAO (Data Access Object) con el CRUD completo de la entidad
 * Repartidor sobre la tabla "repartidores" de speedfast_db.
 *
 * Nota: el enunciado de esta semana pide una clase "ClienteDAO", pero
 * el caso SpeedFast no tiene una entidad Cliente (viene de una
 * plantilla generica). La entidad real del caso, ya definida desde la
 * Semana 7, es Repartidor, asi que el CRUD se implementa aqui.
 *
 * Cada metodo abre su propia conexion y la cierra al finalizar
 * (try-with-resources), de forma que nunca queden conexiones abiertas
 * por error.
 *
 * @author Nicolás Vargas
 */
public class RepartidorDAO {

    // 1.0 CREATE: inserta un repartidor nuevo y recupera su id autogenerado
    public void create(Repartidor repartidor) throws SQLException {
        String sql = "INSERT INTO repartidores (nombre) VALUES (?)";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, repartidor.getNombre());
            ps.executeUpdate();

            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) {
                    repartidor.setId(claves.getInt(1));
                }
            }
        }
    }

    // 2.0 READ ALL: devuelve todos los repartidores registrados
    public List<Repartidor> readAll() throws SQLException {
        List<Repartidor> repartidores = new ArrayList<>();
        String sql = "SELECT id, nombre FROM repartidores ORDER BY id";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                repartidores.add(new Repartidor(rs.getInt("id"), rs.getString("nombre")));
            }
        }
        return repartidores;
    }

    // 3.0 UPDATE: actualiza el nombre de un repartidor existente
    public void update(Repartidor repartidor) throws SQLException {
        String sql = "UPDATE repartidores SET nombre = ? WHERE id = ?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, repartidor.getNombre());
            ps.setInt(2, repartidor.getId());
            ps.executeUpdate();
        }
    }

    // 4.0 DELETE: elimina un repartidor por su id
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM repartidores WHERE id = ?";

        try (Connection conexion = ConexionDB.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
