package vista;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import modelo.ControladorPedidos;
import modelo.Pedido;

/**
 * Ventana que muestra en una JTable todos los pedidos registrados en el
 * sistema. Los datos se gestionan con un DefaultTableModel y pueden
 * refrescarse para reflejar los pedidos agregados desde el formulario
 * de registro o los cambios de estado por asignación de repartidor.
 *
 * @author Nicolás Vargas
 */
public class VentanaListaPedidos extends JFrame {

    // 1.0 Controlador compartido y modelo de la tabla
    private final ControladorPedidos controlador;
    private final DefaultTableModel modeloTabla;

    // 1.1 Constructor: recibe el controlador compartido y arma la tabla
    public VentanaListaPedidos(ControladorPedidos controlador) {
        this.controlador = controlador;

        setTitle("Listado de Pedidos");
        setSize(620, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 2.0 Modelo de tabla con las columnas del pedido.
        // Se sobrescribe isCellEditable para que la tabla sea solo lectura.
        String[] columnas = {"ID", "Dirección", "Tipo", "Estado", "Repartidor"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        JTable tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // 3.0 Botón para refrescar la tabla con los datos actuales
        JPanel panelBoton = new JPanel();
        panelBoton.setBorder(new EmptyBorder(8, 10, 8, 10));
        JButton botonRefrescar = new JButton("Refrescar");
        panelBoton.add(botonRefrescar);
        add(panelBoton, BorderLayout.SOUTH);

        botonRefrescar.addActionListener(e -> cargarPedidos());

        // 4.0 Carga inicial de los pedidos al abrir la ventana
        cargarPedidos();
    }

    // 5.0 Vacía la tabla y la vuelve a llenar con los pedidos actuales
    private void cargarPedidos() {
        modeloTabla.setRowCount(0);
        for (Pedido p : controlador.getPedidos()) {
            String repartidor = p.getRepartidorAsignado();
            modeloTabla.addRow(new Object[]{
                p.getId(),
                p.getDireccionEntrega(),
                p.getTipo(),
                p.getEstado(),
                repartidor == null ? "-" : repartidor
            });
        }
    }
}
