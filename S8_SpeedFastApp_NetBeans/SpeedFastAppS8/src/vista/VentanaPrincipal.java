package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Ventana principal del sistema SpeedFast. Ofrece el menú de acceso a
 * la gestión (CRUD completo) de repartidores, pedidos y entregas.
 *
 * @author Nicolás Vargas
 */
public class VentanaPrincipal extends JFrame {

    // 1.0 Constructor: arma la ventana y sus componentes
    public VentanaPrincipal() {
        setTitle("SpeedFast - Sistema de Gestión de Entregas");
        setSize(420, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Gestión de Entregas SpeedFast", SwingConstants.CENTER);
        titulo.setBorder(new EmptyBorder(15, 10, 15, 10));
        add(titulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
        panelBotones.setBorder(new EmptyBorder(10, 30, 20, 30));

        JButton botonRepartidores = new JButton("Gestionar repartidores");
        JButton botonPedidos = new JButton("Gestionar pedidos");
        JButton botonEntregas = new JButton("Gestionar entregas");

        panelBotones.add(botonRepartidores);
        panelBotones.add(botonPedidos);
        panelBotones.add(botonEntregas);
        add(panelBotones, BorderLayout.CENTER);

        // 2.0 Navegación a cada ventana de gestión CRUD
        botonRepartidores.addActionListener(e -> new VentanaRepartidores().setVisible(true));
        botonPedidos.addActionListener(e -> new VentanaPedidos().setVisible(true));
        botonEntregas.addActionListener(e -> new VentanaEntregas().setVisible(true));

        setVisible(true);
    }
}
