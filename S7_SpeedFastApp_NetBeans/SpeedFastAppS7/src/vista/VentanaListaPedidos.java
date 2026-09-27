package vista;

import java.awt.BorderLayout;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import dao.PedidoDAO;
import modelo.Pedido;

/**
 * Ventana que consulta y muestra en una JTable todos los pedidos
 * almacenados en la base de datos speedfast_db. Los datos se gestionan
 * con un DefaultTableModel y pueden refrescarse volviendo a consultar
 * PedidoDAO en cualquier momento.
 *
 * @author Nicolás Vargas
 */
public class VentanaListaPedidos extends JFrame {

    // 1.0 DAO de consulta y modelo de la tabla
    private final PedidoDAO pedidoDAO;
    private final DefaultTableModel modeloTabla;

    // 1.1 Constructor
    public VentanaListaPedidos() {
        this.pedidoDAO = new PedidoDAO();

        setTitle("Listado de Pedidos (desde base de datos)");
        setSize(620, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 2.0 Modelo de tabla de solo lectura con las columnas del pedido
        String[] columnas = {"ID", "Dirección", "Tipo", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        JTable tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // 3.0 Botón para refrescar la tabla consultando la base de datos
        JPanel panelBoton = new JPanel();
        panelBoton.setBorder(new EmptyBorder(8, 10, 8, 10));
        JButton botonRefrescar = new JButton("Refrescar");
        panelBoton.add(botonRefrescar);
        add(panelBoton, BorderLayout.SOUTH);

        botonRefrescar.addActionListener(e -> cargarPedidos());

        // 4.0 Carga inicial al abrir la ventana
        cargarPedidos();
    }

    // 5.0 Consulta la base de datos y vuelve a llenar la tabla
    private void cargarPedidos() {
        modeloTabla.setRowCount(0);
        try {
            List<Pedido> pedidos = pedidoDAO.listarTodos();
            for (Pedido p : pedidos) {
                modeloTabla.addRow(new Object[]{
                    p.getId(), p.getDireccion(), p.getTipo(), p.getEstado()
                });
            }
        } catch (SQLException e) {
            // 5.1 Manejo de excepción: si falla la consulta, se informa
            // al usuario en vez de dejar la tabla vacía sin explicación
            JOptionPane.showMessageDialog(this,
                    "No se pudo consultar la base de datos.\n" + e.getMessage(),
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
        }
    }
}
