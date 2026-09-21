package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import modelo.ControladorPedidos;
import modelo.Pedido;

/**
 * Formulario de registro de nuevos pedidos. Valida los datos ingresados
 * antes de crear el Pedido y agregarlo al controlador compartido, y
 * confirma la operación mediante un JOptionPane.
 *
 * @author Nicolás Vargas
 */
public class VentanaRegistroPedido extends JFrame {

    // 1.0 Controlador compartido y campos del formulario
    private final ControladorPedidos controlador;
    private final JTextField campoId;
    private final JTextField campoDireccion;
    private final JComboBox<String> comboTipo;

    // 1.1 Constructor: recibe el controlador compartido y arma el formulario
    public VentanaRegistroPedido(ControladorPedidos controlador) {
        this.controlador = controlador;

        setTitle("Registrar Pedido");
        setSize(420, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 2.0 Panel de campos con GridLayout (etiqueta + campo por fila)
        JPanel panelCampos = new JPanel(new GridLayout(3, 2, 10, 10));
        panelCampos.setBorder(new EmptyBorder(20, 20, 10, 20));

        campoId = new JTextField();
        campoDireccion = new JTextField();
        comboTipo = new JComboBox<>(new String[]{"comida", "encomienda", "express"});

        panelCampos.add(new JLabel("ID:"));
        panelCampos.add(campoId);
        panelCampos.add(new JLabel("Dirección:"));
        panelCampos.add(campoDireccion);
        panelCampos.add(new JLabel("Tipo:"));
        panelCampos.add(comboTipo);

        add(panelCampos, BorderLayout.CENTER);

        // 3.0 Botón Guardar en la zona inferior
        JPanel panelBoton = new JPanel();
        panelBoton.setBorder(new EmptyBorder(0, 20, 15, 20));
        JButton botonGuardar = new JButton("Guardar");
        panelBoton.add(botonGuardar);
        add(panelBoton, BorderLayout.SOUTH);

        botonGuardar.addActionListener(e -> guardarPedido());
    }

    // 4.0 Valida los campos, crea el pedido y lo agrega al controlador
    private void guardarPedido() {
        String textoId = campoId.getText().trim();
        String direccion = campoDireccion.getText().trim();
        String tipo = (String) comboTipo.getSelectedItem();

        // 4.1 Validación: ningún campo puede quedar vacío
        if (textoId.isEmpty() || direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe completar el ID y la dirección.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 4.2 Validación: el ID debe ser un número entero positivo
        int id;
        try {
            id = Integer.parseInt(textoId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "El ID debe ser un número entero.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (id <= 0) {
            JOptionPane.showMessageDialog(this,
                    "El ID debe ser mayor que cero.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 4.3 Validación: no se permiten dos pedidos con el mismo ID
        if (controlador.existeId(id)) {
            JOptionPane.showMessageDialog(this,
                    "Ya existe un pedido con el ID " + id + ".",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 4.4 Creación del pedido y confirmación al usuario
        controlador.agregarPedido(new Pedido(id, direccion, tipo));

        JOptionPane.showMessageDialog(this,
                "Pedido #" + id + " registrado correctamente.",
                "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);

        // 4.5 Limpieza del formulario para permitir un nuevo registro
        campoId.setText("");
        campoDireccion.setText("");
        comboTipo.setSelectedIndex(0);
    }
}
