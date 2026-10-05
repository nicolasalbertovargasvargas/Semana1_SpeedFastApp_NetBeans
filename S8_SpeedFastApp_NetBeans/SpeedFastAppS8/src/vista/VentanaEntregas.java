package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
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
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import dao.EntregaDAO;
import dao.PedidoDAO;
import dao.RepartidorDAO;
import modelo.Entrega;
import modelo.Pedido;
import modelo.Repartidor;

/**
 * Ventana de gestión (CRUD completo) de entregas: asocia un Pedido con
 * un Repartidor, registrando fecha y hora. Los combos de selección de
 * pedido y repartidor se cargan desde la base de datos, mostrando un
 * texto legible ("id - dirección"/"id - nombre") pero conservando
 * internamente el id real de cada uno.
 *
 * @author Nicolás Vargas
 */
public class VentanaEntregas extends JFrame {

    // 1.0 DAO, modelo de tabla y componentes del formulario
    private final EntregaDAO entregaDAO;
    private final PedidoDAO pedidoDAO;
    private final RepartidorDAO repartidorDAO;
    private final DefaultTableModel modeloTabla;
    private final JComboBox<Pedido> comboPedido;
    private final JComboBox<Repartidor> comboRepartidor;
    private final JTextField campoFecha;
    private final JTextField campoHora;
    private final JTable tabla;

    private int idSeleccionado = 0;

    // 1.1 Constructor
    public VentanaEntregas() {
        this.entregaDAO = new EntregaDAO();
        this.pedidoDAO = new PedidoDAO();
        this.repartidorDAO = new RepartidorDAO();

        setTitle("Gestión de Entregas");
        setSize(700, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 2.0 Formulario superior
        JPanel panelFormulario = new JPanel(new GridLayout(2, 4, 8, 8));
        panelFormulario.setBorder(new EmptyBorder(10, 10, 5, 10));

        comboPedido = new JComboBox<>();
        comboRepartidor = new JComboBox<>();
        campoFecha = new JTextField(LocalDate.now().toString());
        campoHora = new JTextField(LocalTime.now().withNano(0).toString());

        panelFormulario.add(new JLabel("Pedido:"));
        panelFormulario.add(comboPedido);
        panelFormulario.add(new JLabel("Repartidor:"));
        panelFormulario.add(comboRepartidor);
        panelFormulario.add(new JLabel("Fecha (AAAA-MM-DD):"));
        panelFormulario.add(campoFecha);
        panelFormulario.add(new JLabel("Hora (HH:MM:SS):"));
        panelFormulario.add(campoHora);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);

        JPanel panelRefrescoCombos = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton botonRefrescarCombos = new JButton("Refrescar pedidos/repartidores");
        panelRefrescoCombos.add(botonRefrescarCombos);
        panelSuperior.add(panelRefrescoCombos, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);

        // 3.0 Tabla central
        String[] columnas = {"ID", "Pedido", "Repartidor", "Fecha", "Hora"};
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
                campoFecha.setText(modeloTabla.getValueAt(fila, 3).toString());
                campoHora.setText(modeloTabla.getValueAt(fila, 4).toString());
                // 3.1 Selecciona en los combos el pedido/repartidor de la fila
                seleccionarEnCombo(comboPedido, (int) modeloTabla.getValueAt(fila, 0) == idSeleccionado
                        ? extraerIdDeTexto((String) modeloTabla.getValueAt(fila, 1)) : -1);
                seleccionarEnCombo(comboRepartidor, extraerIdDeTexto((String) modeloTabla.getValueAt(fila, 2)));
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
        botonRefrescarCombos.addActionListener(e -> cargarCombos());

        // 5.0 Carga inicial: combos y tabla
        cargarCombos();
        cargarTabla();
    }

    // 5.1 Carga los combos de Pedido y Repartidor desde la base de datos
    private void cargarCombos() {
        comboPedido.removeAllItems();
        comboRepartidor.removeAllItems();
        try {
            List<Pedido> pedidos = pedidoDAO.readAll();
            for (Pedido p : pedidos) {
                comboPedido.addItem(p);
            }
            List<Repartidor> repartidores = repartidorDAO.readAll();
            for (Repartidor r : repartidores) {
                comboRepartidor.addItem(r);
            }
        } catch (SQLException e) {
            mostrarErrorSQL(e);
        }
    }

    // 5.2 Extrae el id numérico del texto "id - descripción" de una celda
    private int extraerIdDeTexto(String texto) {
        try {
            return Integer.parseInt(texto.split(" - ")[0].trim());
        } catch (Exception e) {
            return -1;
        }
    }

    // 5.3 Selecciona en el combo el elemento cuyo id coincide
    private void seleccionarEnCombo(JComboBox<?> combo, int id) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            Object item = combo.getItemAt(i);
            int itemId = (item instanceof Pedido) ? ((Pedido) item).getId() : ((Repartidor) item).getId();
            if (itemId == id) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }

    // 6.0 Valida los campos y devuelve la fecha/hora parseadas, o null
    // si hay un error (y ya mostró el mensaje correspondiente)
    private Object[] validarYObtenerFechaHora() {
        if (comboPedido.getSelectedItem() == null || comboRepartidor.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un pedido y un repartidor. Si las listas están vacías, "
                            + "registra al menos uno y presiona \"Refrescar pedidos/repartidores\".",
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        Date fecha;
        Time hora;
        try {
            fecha = Date.valueOf(campoFecha.getText().trim());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    "La fecha debe tener el formato AAAA-MM-DD (ej: 2026-09-17).",
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        try {
            hora = Time.valueOf(campoHora.getText().trim());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this,
                    "La hora debe tener el formato HH:MM:SS (ej: 14:30:00).",
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return new Object[]{fecha, hora};
    }

    // 7.0 CREATE
    private void agregar() {
        Object[] fechaHora = validarYObtenerFechaHora();
        if (fechaHora == null) {
            return;
        }
        try {
            Pedido pedido = (Pedido) comboPedido.getSelectedItem();
            Repartidor repartidor = (Repartidor) comboRepartidor.getSelectedItem();
            Entrega entrega = new Entrega(pedido.getId(), repartidor.getId(),
                    (Date) fechaHora[0], (Time) fechaHora[1]);
            entregaDAO.create(entrega);
            JOptionPane.showMessageDialog(this,
                    "Entrega #" + entrega.getId() + " registrada correctamente.",
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
                    "Selecciona una entrega de la tabla para editar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Object[] fechaHora = validarYObtenerFechaHora();
        if (fechaHora == null) {
            return;
        }
        try {
            Pedido pedido = (Pedido) comboPedido.getSelectedItem();
            Repartidor repartidor = (Repartidor) comboRepartidor.getSelectedItem();
            Entrega entrega = new Entrega(pedido.getId(), repartidor.getId(),
                    (Date) fechaHora[0], (Time) fechaHora[1]);
            entrega.setId(idSeleccionado);
            entregaDAO.update(entrega);
            JOptionPane.showMessageDialog(this,
                    "Entrega #" + idSeleccionado + " actualizada correctamente.",
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
                    "Selecciona una entrega de la tabla para eliminar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Eliminar la entrega #" + idSeleccionado + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            entregaDAO.delete(idSeleccionado);
            JOptionPane.showMessageDialog(this,
                    "Entrega eliminada correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            cargarTabla();
        } catch (SQLException e) {
            mostrarErrorSQL(e);
        }
    }

    // 10.0 Limpia el formulario y la selección de la tabla
    private void limpiarFormulario() {
        campoFecha.setText(LocalDate.now().toString());
        campoHora.setText(LocalTime.now().withNano(0).toString());
        if (comboPedido.getItemCount() > 0) {
            comboPedido.setSelectedIndex(0);
        }
        if (comboRepartidor.getItemCount() > 0) {
            comboRepartidor.setSelectedIndex(0);
        }
        idSeleccionado = 0;
        tabla.clearSelection();
    }

    // 11.0 Consulta la base de datos y vuelve a llenar la tabla
    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        try {
            List<Entrega> entregas = entregaDAO.readAll();
            for (Entrega en : entregas) {
                modeloTabla.addRow(new Object[]{
                    en.getId(), en.getPedidoTexto(), en.getRepartidorTexto(),
                    en.getFecha(), en.getHora()
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
