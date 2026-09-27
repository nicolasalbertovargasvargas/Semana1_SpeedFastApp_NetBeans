package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import dao.PedidoDAO;
import modelo.Pedido;

/**
 * Formulario de registro de nuevos pedidos. Valida los datos ingresados
 * y guarda el pedido directamente en la base de datos speedfast_db a
 * traves de PedidoDAO, mostrando el id autogenerado en la confirmacion.
 *
 * @author Nicolás Vargas
 */
public class VentanaRegistroPedido extends JFrame {

    // 1.0 DAO para persistir el pedido y campos del formulario.
    // Ya no se pide el ID: ahora lo genera automaticamente la base de datos.
    private final PedidoDAO pedidoDAO;
    private final JTextField campoDireccion;
    private final JComboBox<String> comboTipo;

    // 1.1 Constructor
    public VentanaRegistroPedido() {
        this.pedidoDAO = new PedidoDAO();

        setTitle("Registrar Pedido");
        setSize(420, 190);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 2.0 Panel de campos con GridLayout
        JPanel panelCampos = new JPanel(new GridLayout(2, 2, 10, 10));
        panelCampos.setBorder(new EmptyBorder(20, 20, 10, 20));

        campoDireccion = new JTextField();
        comboTipo = new JComboBox<>(new String[]{"COMIDA", "ENCOMIENDA", "EXPRESS"});

        panelCampos.add(new JLabel("Dirección:"));
        panelCampos.add(campoDireccion);
        panelCampos.add(new JLabel("Tipo:"));
        panelCampos.add(comboTipo);

        add(panelCampos, BorderLayout.CENTER);

        // 3.0 Botón Guardar
        JPanel panelBoton = new JPanel();
        panelBoton.setBorder(new EmptyBorder(0, 20, 15, 20));
        JButton botonGuardar = new JButton("Guardar");
        panelBoton.add(botonGuardar);
        add(panelBoton, BorderLayout.SOUTH);

        botonGuardar.addActionListener(e -> guardarPedido());
    }

    // 4.0 Valida los campos y guarda el pedido en la base de datos
    private void guardarPedido() {
        String direccion = campoDireccion.getText().trim();
        String tipo = (String) comboTipo.getSelectedItem();

        // 4.1 Validación: la dirección no puede quedar vacía
        if (direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar la dirección de entrega.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 4.2 Creación y guardado del pedido (estado inicial PENDIENTE)
        Pedido pedido = new Pedido(direccion, tipo);
        try {
            pedidoDAO.guardar(pedido);
        } catch (SQLException e) {
            // 4.3 Manejo de excepción: si falla la conexión o el INSERT,
            // se informa al usuario en vez de dejar la app colgada
            JOptionPane.showMessageDialog(this,
                    "No se pudo guardar el pedido en la base de datos.\n" + e.getMessage(),
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Pedido #" + pedido.getId() + " registrado correctamente en la base de datos.",
                "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);

        // 4.4 Limpieza del formulario para permitir un nuevo registro
        campoDireccion.setText("");
        comboTipo.setSelectedIndex(0);
    }
}
