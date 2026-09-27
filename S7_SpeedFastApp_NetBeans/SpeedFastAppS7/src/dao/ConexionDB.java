package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar la conexion JDBC con la base de datos
 * MySQL speedfast_db. Centraliza los parametros de conexion para que
 * ninguna otra clase del sistema necesite conocerlos directamente.
 *
 * IMPORTANTE: reemplaza el valor de PASSWORD por la contraseña real de
 * tu usuario root de MySQL antes de ejecutar la aplicacion.
 *
 * @author Nicolás Vargas
 */
public class ConexionDB {

    // 1.0 Parametros de conexion
    private static final String URL = "jdbc:mysql://localhost:3306/speedfast_db";
    private static final String USER = "root";
    private static final String PASSWORD = "tu_contraseña";

    // 1.1 Constructor privado: esta clase solo expone metodos estaticos,
    // no tiene sentido instanciarla
    private ConexionDB() {
    }

    // 2.0 Abre y devuelve una nueva conexion a la base de datos.
    // Quien llame a este metodo es responsable de cerrarla (try-with-resources
    // o finally), tal como se hace en cada metodo de los DAO.
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
