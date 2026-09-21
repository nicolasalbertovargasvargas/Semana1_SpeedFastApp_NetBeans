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
        // 1.0 La interfaz se crea dentro del hilo de despacho de eventos
        // de Swing, que es la forma segura de construir componentes gráficos
        SwingUtilities.invokeLater(() -> new VentanaPrincipal());
    }
}
