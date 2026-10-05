package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import dao.PedidoDAO;
import modelo.EstadoPedido;
import modelo.Pedido;
import modelo.TipoPedido;

/**
 * Ventana de gestión (CRUD completo) de pedidos: registrar, listar
 * (con filtros opcionales por estado y/o tipo), editar y eliminar,
 * con los datos persistidos en la tabla "pedidos" de speedfast_db.
 *
 * @author Nicolás Vargas
 */
public class VentanaPedidos extends JFrame {

    // 1.0 DAO, modelo de tabla y campos del formulario
    private final PedidoDAO pedidoDAO;
    private final DefaultTableModel modeloTabla;
    private final JTextField campoDireccion;
    private final JComboBox<TipoPedido> comboTipo;
    private final JComboBox<EstadoPedido> comboEstado;
    private final JTable tabla;

    // 1.1 Combos de filtro (incluyen la opción "TODOS")
    private final JComboBox<String> filtroEstado;
    private final JComboBox<String> filtroTipo;

    private int idSeleccionado = 0;

    // 1.2 Constructor
    public VentanaPedidos() {
        this.pedidoDAO = new PedidoDAO();

        setTitle("Gestión de Pedidos");
        setSize(650, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 2.0 Panel superior: formulario + filtros
        JPanel panelSuperior = new JPanel(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(1, 6, 8, 8));
        panelFormulario.setBorder(new EmptyBorder(10, 10, 5, 10));
        campoDireccion = new JTextField();
        comboTipo = new JComboBox<>(TipoPedido.values());
        comboEstado = new JComboBox<>(EstadoPedido.values());
        panelFormulario.add(new JLabel("Dirección:"));
        panelFormulario.add(campoDireccion);
        panelFormulario.add(new JLabel("Tipo:"));
        panelFormulario.add(comboTipo);
        panelFormulario.add(new JLabel("Estado:"));
        panelFormulario.add(comboEstado);
        panelSuperior.add(panelFormulario, BorderLayout.NORTH);

        // 2.1 Panel de filtros opcionales
        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltro.setBorder(new TitledBorder("Filtrar listado (opcional)"));
        filtroEstado = new JComboBox<>(opcionesConTodos(EstadoPedido.values()));
        filtroTipo = new JComboBox<>(opcionesConTodos(TipoPedido.values()));
        JButton botonFiltrar = new JButton("Filtrar");
        JButton botonQuitarFiltro = new JButton("Quitar filtro");
        panelFiltro.add(new JLabel("Estado:"));
        panelFiltro.add(filtroEstado);
        panelFiltro.add(new JLabel("Tipo:"));
        panelFiltro.add(filtroTipo);
        panelFiltro.add(botonFiltrar);
        panelFiltro.add(botonQuitarFiltro);
        panelSuperior.add(panelFiltro, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);

        // 3.0 Tabla central
        String[] columnas = {"ID", "Dirección", "Tipo", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        ListSelectionListener seleccionListener = e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int fila = tabla.getSelectedRow();
                idSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
                campoDireccion.setText((String) modeloTabla.getValueAt(fila, 1));
                comboTipo.setSelectedItem(TipoPedido.valueOf((String) modeloTabla.getValueAt(fila, 2)));
                comboEstado.setSelectedItem(EstadoPedido.valueOf((String) modeloTabla.getValueAt(fila, 3)));
            }
        };
        tabla.getSelectionModel().addListSelectionListener(seleccionListener);

        // 4.0 Botones de acción
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton botonAgregar = new JButton("Agregar");
        JButton botonActualizar = new JButton("Actualizar");
        JButton botonEliminar = new JButton("Eliminar");
        JButton botonLimpiar = new JButton("Limpiar");
        panelBotones.add(botonAgregar);
        panelBotones.add(botonActualizar);
        panelBotones.add(botonEliminar);
        panelBotones.add(botonLimpiar);
        add(panelBotones, BorderLayout.SOUTH);

        botonAgregar.addActionListener(e -> agregar());
        botonActualizar.addActionListener(e -> actualizar());
        botonEliminar.addActionListener(e -> eliminar());
        botonLimpiar.addActionListener(e -> limpiarFormulario());
        botonFiltrar.addActionListener(e -> cargarTabla());
        botonQuitarFiltro.addActionListener(e -> {
            filtroEstado.setSelectedIndex(0);
            filtroTipo.setSelectedIndex(0);
            cargarTabla();
        });

        // 5.0 Carga inicial de la tabla (sin filtro)
        cargarTabla();
    }

    // 5.1 Arma un arreglo de Strings con "TODOS" como primera opción,
    // seguido del nombre de cada valor del enum recibido
    private String[] opcionesConTodos(Enum<?>[] valores) {
        String[] opciones = new String[valores.length + 1];
        opciones[0] = "TODOS";
        for (int i = 0; i < valores.length; i++) {
            opciones[i + 1] = valores[i].name();
        }
        return opciones;
    }

    // 6.0 Valida los campos del formulario; true si son válidos
    private boolean validarCampos() {
        if (campoDireccion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar la dirección de entrega.",
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // 7.0 CREATE
    private void agregar() {
        if (!validarCampos()) {
            return;
        }
        try {
            Pedido pedido = new Pedido(
                    campoDireccion.getText().trim(),
                    (TipoPedido) comboTipo.getSelectedItem(),
                    (EstadoPedido) comboEstado.getSelectedItem());
            pedidoDAO.create(pedido);
            JOptionPane.showMessageDialog(this,
                    "Pedido #" + pedido.getId() + " registrado correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            cargarTabla();
        } catch (SQLException e) {
            mostrarErrorSQL(e);
        }
    }

    // 8.0 UPDATE
    private void actualizar() {
        if (idSeleccionado == 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un pedido de la tabla para editar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validarCampos()) {
            return;
        }
        try {
            Pedido pedido = new Pedido(
                    idSeleccionado,
                    campoDireccion.getText().trim(),
                    (TipoPedido) comboTipo.getSelectedItem(),
                    (EstadoPedido) comboEstado.getSelectedItem());
            pedidoDAO.update(pedido);
            JOptionPane.showMessageDialog(this,
                    "Pedido #" + idSeleccionado + " actualizado correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            cargarTabla();
        } catch (SQLException e) {
            mostrarErrorSQL(e);
        }
    }

    // 9.0 DELETE
    private void eliminar() {
        if (idSeleccionado == 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un pedido de la tabla para eliminar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el pedido #" + idSeleccionado + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            pedidoDAO.delete(idSeleccionado);
            JOptionPane.showMessageDialog(this,
                    "Pedido eliminado correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            cargarTabla();
        } catch (SQLException e) {
            mostrarErrorSQL(e);
        }
    }

    // 10.0 Limpia el formulario y la selección de la tabla
    private void limpiarFormulario() {
        campoDireccion.setText("");
        comboTipo.setSelectedIndex(0);
        comboEstado.setSelectedIndex(0);
        idSeleccionado = 0;
        tabla.clearSelection();
    }

    // 11.0 Consulta la base de datos (aplicando el filtro vigente, si
    // hay alguno) y vuelve a llenar la tabla
    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        String estadoFiltro = filtroEstado.getSelectedIndex() == 0
                ? null : (String) filtroEstado.getSelectedItem();
        String tipoFiltro = filtroTipo.getSelectedIndex() == 0
                ? null : (String) filtroTipo.getSelectedItem();

        try {
            List<Pedido> pedidos = pedidoDAO.readAllFiltrado(estadoFiltro, tipoFiltro);
            for (Pedido p : pedidos) {
                modeloTabla.addRow(new Object[]{
                    p.getId(), p.getDireccion(), p.getTipo().name(), p.getEstado().name()
                });
            }
        } catch (SQLException e) {
            mostrarErrorSQL(e);
        }
    }

    private void mostrarErrorSQL(SQLException e) {
        JOptionPane.showMessageDialog(this,
                "Ocurrió un error al comunicarse con la base de datos.\n" + e.getMessage(),
                "Error de base de datos", JOptionPane.ERROR_MESSAGE);
    }
}
