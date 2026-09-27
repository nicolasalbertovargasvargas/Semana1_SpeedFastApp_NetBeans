package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import dao.RepartidorDAO;
import modelo.Repartidor;

/**
 * Formulario de registro de nuevos repartidores. Valida el nombre
 * ingresado y lo guarda directamente en la base de datos speedfast_db
 * a traves de RepartidorDAO.
 *
 * @author Nicolás Vargas
 */
public class VentanaRegistroRepartidor extends JFrame {

    // 1.0 DAO para persistir el repartidor y campo del formulario
    private final RepartidorDAO repartidorDAO;
    private final JTextField campoNombre;

    // 1.1 Constructor
    public VentanaRegistroRepartidor() {
        this.repartidorDAO = new RepartidorDAO();

        setTitle("Registrar Repartidor");
        setSize(380, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelCampos = new JPanel(new GridLayout(1, 2, 10, 10));
        panelCampos.setBorder(new EmptyBorder(20, 20, 10, 20));

        campoNombre = new JTextField();
        panelCampos.add(new JLabel("Nombre:"));
        panelCampos.add(campoNombre);
        add(panelCampos, BorderLayout.CENTER);

        JPanel panelBoton = new JPanel();
        panelBoton.setBorder(new EmptyBorder(0, 20, 15, 20));
        JButton botonGuardar = new JButton("Guardar");
        panelBoton.add(botonGuardar);
        add(panelBoton, BorderLayout.SOUTH);

        botonGuardar.addActionListener(e -> guardarRepartidor());
    }

    // 2.0 Valida el nombre y guarda el repartidor en la base de datos
    private void guardarRepartidor() {
        String nombre = campoNombre.getText().trim();

        // 2.1 Validación: el nombre no puede quedar vacío
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Debe ingresar el nombre del repartidor.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Repartidor repartidor = new Repartidor(nombre);
        try {
            repartidorDAO.guardar(repartidor);
        } catch (SQLException e) {
            // 2.2 Manejo de excepción de base de datos
            JOptionPane.showMessageDialog(this,
                    "No se pudo guardar el repartidor en la base de datos.\n" + e.getMessage(),
                    "Error de base de datos", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Repartidor #" + repartidor.getId() + " (" + repartidor.getNombre()
                        + ") registrado correctamente.",
                "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);

        campoNombre.setText("");
    }
}
