package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JButton;
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
import dao.RepartidorDAO;
import modelo.Repartidor;

/**
 * Ventana de gestión (CRUD completo) de repartidores: registrar,
 * listar, editar y eliminar, con los datos persistidos en la tabla
 * "repartidores" de speedfast_db.
 *
 * @author Nicolás Vargas
 */
public class VentanaRepartidores extends JFrame {

    // 1.0 DAO, modelo de tabla y campo del formulario
    private final RepartidorDAO repartidorDAO;
    private final DefaultTableModel modeloTabla;
    private final JTextField campoNombre;
    private final JTable tabla;

    // 1.1 id del repartidor actualmente seleccionado en la tabla
    // (0 significa "ninguno seleccionado", ya que MySQL AUTO_INCREMENT
    // nunca genera el id 0)
    private int idSeleccionado = 0;

    // 1.2 Constructor
    public VentanaRepartidores() {
        this.repartidorDAO = new RepartidorDAO();

        setTitle("Gestión de Repartidores");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 2.0 Formulario superior
        JPanel panelFormulario = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFormulario.setBorder(new EmptyBorder(10, 10, 0, 10));
        campoNombre = new JTextField(20);
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(campoNombre);
        add(panelFormulario, BorderLayout.NORTH);

        // 3.0 Tabla central
        String[] columnas = {"ID", "Nombre"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // 3.1 Al seleccionar una fila, se cargan sus datos en el formulario
        ListSelectionListener seleccionListener = e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int fila = tabla.getSelectedRow();
                idSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
                campoNombre.setText((String) modeloTabla.getValueAt(fila, 1));
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

        // 5.0 Carga inicial de la tabla
        cargarTabla();
    }

    // 6.0 Valida que el nombre no esté vacío; true si es válido
    private boolean validarNombre() {
        if (campoNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar el nombre del repartidor.",
                    "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    // 7.0 CREATE: agrega un nuevo repartidor
    private void agregar() {
        if (!validarNombre()) {
            return;
        }
        try {
            Repartidor repartidor = new Repartidor(campoNombre.getText().trim());
            repartidorDAO.create(repartidor);
            JOptionPane.showMessageDialog(this,
                    "Repartidor #" + repartidor.getId() + " registrado correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            cargarTabla();
        } catch (SQLException e) {
            mostrarErrorSQL(e);
        }
    }

    // 8.0 UPDATE: actualiza el repartidor seleccionado
    private void actualizar() {
        if (idSeleccionado == 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un repartidor de la tabla para editar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validarNombre()) {
            return;
        }
        try {
            Repartidor repartidor = new Repartidor(idSeleccionado, campoNombre.getText().trim());
            repartidorDAO.update(repartidor);
            JOptionPane.showMessageDialog(this,
                    "Repartidor #" + idSeleccionado + " actualizado correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            cargarTabla();
        } catch (SQLException e) {
            mostrarErrorSQL(e);
        }
    }

    // 9.0 DELETE: elimina el repartidor seleccionado, con confirmación
    private void eliminar() {
        if (idSeleccionado == 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un repartidor de la tabla para eliminar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el repartidor #" + idSeleccionado + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            repartidorDAO.delete(idSeleccionado);
            JOptionPane.showMessageDialog(this,
                    "Repartidor eliminado correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            cargarTabla();
        } catch (SQLException e) {
            // 9.1 Caso frecuente: el repartidor tiene entregas asociadas
            // (restricción de clave foránea), se informa con un mensaje claro
            mostrarErrorSQL(e);
        }
    }

    // 10.0 Limpia el formulario y la selección de la tabla
    private void limpiarFormulario() {
        campoNombre.setText("");
        idSeleccionado = 0;
        tabla.clearSelection();
    }

    // 11.0 Consulta la base de datos y vuelve a llenar la tabla
    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        try {
            List<Repartidor> repartidores = repartidorDAO.readAll();
            for (Repartidor r : repartidores) {
                modeloTabla.addRow(new Object[]{r.getId(), r.getNombre()});
            }
        } catch (SQLException e) {
            mostrarErrorSQL(e);
        }
    }

    // 12.0 Muestra un mensaje de error claro ante cualquier SQLException
    private void mostrarErrorSQL(SQLException e) {
        JOptionPane.showMessageDialog(this,
                "Ocurrió un error al comunicarse con la base de datos.\n" + e.getMessage(),
                "Error de base de datos", JOptionPane.ERROR_MESSAGE);
    }
}
