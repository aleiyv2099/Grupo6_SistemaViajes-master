package Vista;

import Controlador.SoporteDAO;
import Modelo.SoporteDTO;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Módulo 5 — Soporte al Cliente (tickets).
 * Refleja exactamente el módulo real: tabla `soporte` con
 * codigoTicket, nombreCliente, correo, telefono, tipoProblema,
 * descripcion, prioridad, estado, responsable.
 */
public class SoportePanel extends JPanel {

    private final SoporteDAO dao = new SoporteDAO();

    private final JTextField txtCodigo = new JTextField();
    private final JTextField txtNombre = new JTextField();
    private final JTextField txtCorreo = new JTextField();
    private final JTextField txtTelefono = new JTextField();
    private final JComboBox<String> cmbTipoProblema = new JComboBox<>(
            new String[]{"Error Reserva", "Facturación", "Problema Técnico", "Consulta General"});
    private final JComboBox<String> cmbPrioridad = new JComboBox<>(
            new String[]{"Alta", "Media", "Baja"});
    private final JComboBox<String> cmbEstado = new JComboBox<>(
            new String[]{"Pendiente", "En Proceso", "Resuelto"});
    private final JTextArea txtDescripcion = new JTextArea(4, 20);
    private final JTextField txtBuscar = new JTextField();

    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"Código", "Cliente", "Tipo de Problema", "Descripción", "Prioridad", "Estado"}, 0) {
        @Override
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    };
    private final JTable tabla = new JTable(modelo);

    public SoportePanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        txtCodigo.setEditable(false);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 4, 4, 4);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;
        addRow(g, y++, "Código:", txtCodigo);
        addRow(g, y++, "Nombre:", txtNombre);
        addRow(g, y++, "Correo:", txtCorreo);
        addRow(g, y++, "Teléfono:", txtTelefono);
        addRow(g, y++, "Tipo de problema:", cmbTipoProblema);
        addRow(g, y++, "Prioridad:", cmbPrioridad);
        addRow(g, y++, "Estado:", cmbEstado);

        // Descripción (área)
        g.gridx = 0; g.gridy = y; g.weightx = 0; g.fill = GridBagConstraints.NONE;
        add(new JLabel("Descripción:"), g);
        g.gridx = 1; g.gridy = y++; g.weightx = 1; g.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(txtDescripcion), g);

        // Botones
        JPanel botones = new JPanel();
        JButton btnRegistrar = new JButton("Registrar Ticket");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");
        botones.add(btnRegistrar);
        botones.add(btnEditar);
        botones.add(btnEliminar);
        botones.add(btnLimpiar);
        g.gridx = 0; g.gridy = y++; g.gridwidth = 2; g.weightx = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        add(botones, g);

        // Búsqueda
        g.gridwidth = 1;
        g.gridx = 0; g.gridy = y; g.weightx = 0; g.fill = GridBagConstraints.NONE;
        add(new JLabel("Buscar:"), g);
        g.gridx = 1; g.gridy = y++; g.weightx = 1; g.fill = GridBagConstraints.HORIZONTAL;
        add(txtBuscar, g);

        // Tabla
        g.gridx = 0; g.gridy = y++; g.gridwidth = 2; g.weightx = 1; g.weighty = 1;
        g.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(tabla), g);

        // Listeners
        btnRegistrar.addActionListener(e -> registrar());
        btnEditar.addActionListener(e -> editar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiar());
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                cargarTabla(dao.searchSupportTickets(txtBuscar.getText()));
            }
        });
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarFila();
            }
        });

        listar();
    }

    private void addRow(GridBagConstraints g, int y, String label, java.awt.Component campo) {
        g.gridx = 0; g.gridy = y; g.weightx = 0; g.fill = GridBagConstraints.NONE;
        add(new JLabel(label), g);
        g.gridx = 1; g.gridy = y; g.weightx = 1; g.fill = GridBagConstraints.HORIZONTAL;
        add(campo, g);
    }

    // RF-1: Registro de ticket (estado inicial Pendiente)
    private void registrar() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio");
            return;
        }
        if (!txtNombre.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            JOptionPane.showMessageDialog(this, "El nombre solo debe contener letras");
            return;
        }
        if (txtCorreo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El correo es obligatorio");
            return;
        }
        if (!txtCorreo.getText().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            JOptionPane.showMessageDialog(this, "Ingrese un correo válido");
            return;
        }
        if (txtTelefono.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El teléfono es obligatorio");
            return;
        }
        if (!txtTelefono.getText().matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "El teléfono debe tener 10 dígitos numéricos");
            return;
        }
        if (txtDescripcion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La descripción es obligatoria");
            return;
        }
        if (txtDescripcion.getText().trim().length() < 5) {
            JOptionPane.showMessageDialog(this, "La descripción debe tener mínimo 5 caracteres");
            return;
        }

        SoporteDTO dto = new SoporteDTO();
        dto.setNombreCliente(txtNombre.getText());
        dto.setCorreo(txtCorreo.getText());
        dto.setTelefono(txtTelefono.getText());
        dto.setTipoProblema(cmbTipoProblema.getSelectedItem().toString());
        dto.setDescripcion(txtDescripcion.getText());
        dto.setPrioridad(cmbPrioridad.getSelectedItem().toString());
        dto.setEstado("Pendiente");
        dto.setResponsable("Sin asignar");

        if (dao.registrarTicket(dto)) {
            JOptionPane.showMessageDialog(this, "Ticket registrado correctamente");
            limpiar();
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar ticket");
        }
        listar();
    }

    // RF-3: Actualización del ticket seleccionado
    private void editar() {
        if (txtCodigo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione una fila");
            return;
        }
        try {
            SoporteDTO dto = new SoporteDTO();
            dto.setCodigoTicket(Integer.parseInt(txtCodigo.getText()));
            dto.setNombreCliente(txtNombre.getText());
            dto.setCorreo(txtCorreo.getText());
            dto.setTelefono(txtTelefono.getText());
            dto.setTipoProblema(cmbTipoProblema.getSelectedItem().toString());
            dto.setDescripcion(txtDescripcion.getText());
            dto.setPrioridad(cmbPrioridad.getSelectedItem().toString());
            dto.setEstado(cmbEstado.getSelectedItem().toString());
            dto.setResponsable("Sin asignar");
            if (dao.updateSupportTicket(dto)) {
                JOptionPane.showMessageDialog(this, "Ticket actualizado correctamente");
                limpiar();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar ticket");
            }
            listar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    // RF-4: Eliminación del ticket seleccionado
    private void eliminar() {
        if (txtCodigo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un ticket");
            return;
        }
        int r = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar?");
        if (r == JOptionPane.YES_OPTION) {
            if (dao.deleteSupportTicket(Integer.parseInt(txtCodigo.getText()))) {
                JOptionPane.showMessageDialog(this, "Ticket eliminado correctamente");
                limpiar();
                listar();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar ticket");
            }
        }
    }

    private void seleccionarFila() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            return;
        }
        txtCodigo.setText(tabla.getValueAt(fila, 0).toString());
        txtNombre.setText(tabla.getValueAt(fila, 1).toString());
        cmbTipoProblema.setSelectedItem(tabla.getValueAt(fila, 2).toString());
        txtDescripcion.setText(tabla.getValueAt(fila, 3).toString());
        cmbPrioridad.setSelectedItem(tabla.getValueAt(fila, 4).toString());
        cmbEstado.setSelectedItem(tabla.getValueAt(fila, 5).toString());
    }

    private void limpiar() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtDescripcion.setText("");
        cmbTipoProblema.setSelectedIndex(0);
        cmbPrioridad.setSelectedIndex(0);
        cmbEstado.setSelectedIndex(0);
        txtNombre.requestFocus();
    }

    // RF-2: Consulta / historial
    private void listar() {
        cargarTabla(dao.listSupportTickets());
    }

    private void cargarTabla(List<SoporteDTO> lista) {
        modelo.setRowCount(0);
        for (SoporteDTO s : lista) {
            modelo.addRow(new Object[]{
                s.getCodigoTicket(),
                s.getNombreCliente(),
                s.getTipoProblema(),
                s.getDescripcion(),
                s.getPrioridad(),
                s.getEstado()
            });
        }
    }
}
