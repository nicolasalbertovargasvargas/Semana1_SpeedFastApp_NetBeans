package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import modelo.ControladorPedidos;
import modelo.EstadoPedido;
import modelo.Pedido;

/**
 * Ventana principal del sistema SpeedFast. Ofrece el menú de acciones
 * desde el cual se navega al registro de pedidos, al listado de pedidos
 * y a la asignación de repartidor con simulación de entrega. Mantiene la
 * instancia compartida de ControladorPedidos que usan todas las ventanas.
 *
 * @author Nicolás Vargas
 */
public class VentanaPrincipal extends JFrame {

    // 1.0 Controlador compartido: única fuente de datos del sistema
    private final ControladorPedidos controlador;

    // 1.1 Constructor: arma la ventana y sus componentes
    public VentanaPrincipal() {
        this.controlador = new ControladorPedidos();

        setTitle("SpeedFast - Sistema de Gestión de Entregas");
        setSize(460, 260);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 2.0 Título en la zona superior (BorderLayout.NORTH)
        JLabel titulo = new JLabel("Gestión de Entregas SpeedFast", SwingConstants.CENTER);
        titulo.setBorder(new EmptyBorder(15, 10, 15, 10));
        add(titulo, BorderLayout.NORTH);

        // 3.0 Panel central con los botones en GridLayout (uno por fila)
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
        panelBotones.setBorder(new EmptyBorder(10, 30, 20, 30));

        JButton botonRegistrar = new JButton("Registrar pedido");
        JButton botonListar = new JButton("Listar pedidos");
        JButton botonAsignar = new JButton("Asignar repartidor / Iniciar entrega");

        panelBotones.add(botonRegistrar);
        panelBotones.add(botonListar);
        panelBotones.add(botonAsignar);
        add(panelBotones, BorderLayout.CENTER);

        // 4.0 Navegación: cada botón abre su ventana correspondiente,
        // pasándole el controlador compartido
        botonRegistrar.addActionListener(e -> new VentanaRegistroPedido(controlador).setVisible(true));
        botonListar.addActionListener(e -> new VentanaListaPedidos(controlador).setVisible(true));
        botonAsignar.addActionListener(e -> asignarRepartidor());

        setVisible(true);
    }

    // 5.0 Asigna un repartidor a un pedido pendiente y simula su entrega
    private void asignarRepartidor() {
        // 5.1 Validación: debe existir al menos un pedido registrado
        if (controlador.getPedidos().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No hay pedidos registrados.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String textoId = JOptionPane.showInputDialog(this, "ID del pedido a asignar:");
        if (textoId == null) {
            return;
        }

        // 5.2 Validación: el ID debe ser un número válido
        int id;
        try {
            id = Integer.parseInt(textoId.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "El ID debe ser un número entero.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 5.3 Validación: el pedido debe existir
        Pedido pedido = controlador.buscarPorId(id);
        if (pedido == null) {
            JOptionPane.showMessageDialog(this,
                    "No existe un pedido con el ID " + id + ".",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 5.4 Validación: solo se asigna repartidor a pedidos PENDIENTE
        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            JOptionPane.showMessageDialog(this,
                    "El pedido #" + id + " ya está en estado " + pedido.getEstado() + ".",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nombreRepartidor = JOptionPane.showInputDialog(this, "Nombre del repartidor:");
        if (nombreRepartidor == null || nombreRepartidor.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe indicar el nombre del repartidor.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 5.5 Se asigna el repartidor y el pedido pasa a EN_REPARTO
        pedido.setRepartidorAsignado(nombreRepartidor.trim());
        pedido.setEstado(EstadoPedido.EN_REPARTO.name());

        JOptionPane.showMessageDialog(this,
                "Pedido #" + id + " asignado a " + nombreRepartidor.trim()
                        + ".\nEstado: " + pedido.getEstado(),
                "Entrega iniciada", JOptionPane.INFORMATION_MESSAGE);
    }
}
