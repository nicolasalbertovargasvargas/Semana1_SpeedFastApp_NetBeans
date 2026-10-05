package main;

import javax.swing.SwingUtilities;
import vista.VentanaPrincipal;

/**
 * Clase principal que inicia la aplicación de escritorio de SpeedFast
 * abriendo la ventana principal del sistema.
 *
 * @author Nicolás Vargas
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal());
    }
}
