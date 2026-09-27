package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import dao.EntregaDAO;
import dao.PedidoDAO;
import dao.RepartidorDAO;
import modelo.Entrega;
import modelo.EstadoPedido;
import modelo.Pedido;
import modelo.Repartidor;

/**
 * Ventana principal del sistema SpeedFast. Ofrece el menú de acciones
 * desde el cual se navega al registro de pedidos, al registro de
 * repartidores, al listado de pedidos y a la asignación de repartidor
 * con simulación de entrega. Todas las operaciones se persisten en la
 * base de datos speedfast_db a través de los DAO correspondientes.
 *
 * @author Nicolás Vargas
 */
public class VentanaPrincipal extends JFrame {

    // 1.0 DAO usados directamente desde esta ventana para la asignación
    private final PedidoDAO pedidoDAO;
    private final RepartidorDAO repartidorDAO;
    private final EntregaDAO entregaDAO;

    // 1.1 Constructor: arma la ventana y sus componentes
    public VentanaPrincipal() {
        this.pedidoDAO = new PedidoDAO();
        this.repartidorDAO = new RepartidorDAO();
        this.entregaDAO = new EntregaDAO();

        setTitle("SpeedFast - Sistema de Gestión de Entregas");
        setSize(460, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 2.0 Título en la zona superior
        JLabel titulo = new JLabel("Gestión de Entregas SpeedFast", SwingConstants.CENTER);
        titulo.setBorder(new EmptyBorder(15, 10, 15, 10));
        add(titulo, BorderLayout.NORTH);

        // 3.0 Panel central con los botones en GridLayout (uno por fila)
        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 10, 10));
        panelBotones.setBorder(new EmptyBorder(10, 30, 20, 30));

        JButton botonRegistrarPedido = new JButton("Registrar pedido");
        JButton botonRegistrarRepartidor = new JButton("Registrar repartidor");
        JButton botonListar = new JButton("Listar pedidos");
        JButton botonAsignar = new JButton("Asignar repartidor / Iniciar entrega");

        panelBotones.add(botonRegistrarPedido);
        panelBotones.add(botonRegistrarRepartidor);
        panelBotones.add(botonListar);
        panelBotones.add(botonAsignar);
        add(panelBotones, BorderLayout.CENTER);

        // 4.0 Navegación entre ventanas
        botonRegistrarPedido.addActionListener(e -> new VentanaRegistroPedido().setVisible(true));
        botonRegistrarRepartidor.addActionListener(e -> new VentanaRegistroRepartidor().setVisible(true));
        botonListar.addActionListener(e -> new VentanaListaPedidos().setVisible(true));
        botonAsignar.addActionListener(e -> asignarRepartidor());

        setVisible(true);
    }

    // 5.0 Asigna un repartidor existente a un pedido pendiente, registra
    // la entrega en la base de datos y actualiza el estado del pedido
    private void asignarRepartidor() {
        try {
            // 5.1 Validación: deben existir repartidores registrados
            List<Repartidor> repartidores = repartidorDAO.listarTodos();
            if (repartidores.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No hay repartidores registrados. Registra uno primero.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String textoId = JOptionPane.showInputDialog(this, "ID del pedido a asignar:");
            if (textoId == null) {
                return;
            }

            // 5.2 Validación: el ID debe ser un número válido
            int idPedido;
            try {
                idPedido = Integer.parseInt(textoId.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "El ID debe ser un número entero.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 5.3 Validación: el pedido debe existir en la base de datos
            Pedido pedido = pedidoDAO.buscarPorId(idPedido);
            if (pedido == null) {
                JOptionPane.showMessageDialog(this,
                        "No existe un pedido con el ID " + idPedido + ".",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 5.4 Validación: solo se asigna repartidor a pedidos PENDIENTE
            if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
                JOptionPane.showMessageDialog(this,
                        "El pedido #" + idPedido + " ya está en estado " + pedido.getEstado() + ".",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 5.5 Selección del repartidor entre los registrados en la BD
            Repartidor repartidorElegido = (Repartidor) JOptionPane.showInputDialog(
                    this, "Selecciona el repartidor:", "Asignar repartidor",
                    JOptionPane.QUESTION_MESSAGE, null,
                    repartidores.toArray(), repartidores.get(0));
            if (repartidorElegido == null) {
                return;
            }

            // 5.6 Registro de la entrega (fecha y hora actuales del sistema)
            long ahoraMs = System.currentTimeMillis();
            Entrega entrega = new Entrega(idPedido, repartidorElegido.getId(),
                    new Date(ahoraMs), new Time(ahoraMs));
            entregaDAO.guardar(entrega);

            // 5.7 El pedido pasa a EN_REPARTO en la base de datos
            pedidoDAO.actualizarEstado(idPedido, EstadoPedido.EN_REPARTO.name());

            JOptionPane.showMessageDialog(this,
                    "Pedido #" + idPedido + " asignado a " + repartidorElegido.getNombre()
                            + ".\nEstado: " + EstadoPedido.EN_REPARTO,
                    "Entrega iniciada", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            // 5.8 Manejo de excepción de base de datos, en un único lugar
            // que cubre cualquiera de las consultas/actualizaciones de arriba
            JOptionPane.showMessageDialog(this,
                    "Ocurrió un error al comunicarse con la base de datos.\n" + e.getMessage(),
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }
}
