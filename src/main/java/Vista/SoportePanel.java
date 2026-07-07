package Vista;

import Controlador.SoporteDAO;
import Modelo.SoporteDTO;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Módulo 5 — Soporte al Cliente.
 * Panel construido según los requerimientos funcionales RF-1..RF-5 (Autor: Nicole Malavé):
 * registro, consulta/búsqueda, actualización y eliminación de tickets de soporte.
 */
public class SoportePanel extends JPanel {

    private static final Pattern SOLO_LETRAS = Pattern.compile("[\\p{L} ]{1,100}");
    private static final Pattern CORREO = Pattern.compile("[\\w.+-]+@[\\w-]+\\.[\\w.-]+");
    private static final Pattern SOLO_NUMEROS = Pattern.compile("\\d{1,10}");

    private final SoporteDAO soporteDAO = new SoporteDAO();

    private JTextField txtNombre;
    private JTextField txtCorreo;
    private JTextField txtContacto;
    private JComboBox<String> cmbTipoProblema;
    private JComboBox<String> cmbEstado;
    private JTextField txtResponsable;
    private JTextArea txtDescripcion;
    private JTextArea txtObservaciones;
    private JTextField txtBuscar;
    private JTable tabla;
    private DefaultTableModel modelo;

    private int codigoTicketSeleccionado = 0;

    public SoportePanel() {
        initUI();
        listarTickets();
    }

    private void initUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 8, 5, 8);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Soporte al Cliente — Registro y seguimiento de tickets");
        titulo.setFont(titulo.getFont().deriveFont(java.awt.Font.BOLD, 16f));
        g.gridx = 0; g.gridy = 0; g.gridwidth = 4;
        add(titulo, g);
        g.gridwidth = 1;

        // Fila 1: Nombre del cliente | Correo
        add(new JLabel("Nombre del cliente:"), pos(g, 0, 1));
        txtNombre = new JTextField(18);
        add(txtNombre, pos(g, 1, 1));
        add(new JLabel("Correo electrónico:"), pos(g, 2, 1));
        txtCorreo = new JTextField(18);
        add(txtCorreo, pos(g, 3, 1));

        // Fila 2: Número de contacto | Tipo de problema
        add(new JLabel("Número de contacto:"), pos(g, 0, 2));
        txtContacto = new JTextField(18);
        add(txtContacto, pos(g, 1, 2));
        add(new JLabel("Tipo de problema:"), pos(g, 2, 2));
        cmbTipoProblema = new JComboBox<>(new String[]{"Reserva", "Pago", "Cancelación", "Consulta", "Otro"});
        add(cmbTipoProblema, pos(g, 3, 2));

        // Fila 3: Estado | Responsable
        add(new JLabel("Estado:"), pos(g, 0, 3));
        cmbEstado = new JComboBox<>(new String[]{"Pendiente", "En proceso", "Resuelto", "Cerrado"});
        add(cmbEstado, pos(g, 1, 3));
        add(new JLabel("Responsable asignado:"), pos(g, 2, 3));
        txtResponsable = new JTextField(18);
        add(txtResponsable, pos(g, 3, 3));

        // Fila 4: Descripción
        add(new JLabel("Descripción del problema:"), pos(g, 0, 4));
        txtDescripcion = new JTextArea(3, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        g.gridx = 1; g.gridy = 4; g.gridwidth = 3; g.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(txtDescripcion), g);
        g.gridwidth = 1; g.fill = GridBagConstraints.HORIZONTAL;

        // Fila 5: Observaciones del agente
        add(new JLabel("Observaciones del agente:"), pos(g, 0, 5));
        txtObservaciones = new JTextArea(3, 20);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        g.gridx = 1; g.gridy = 5; g.gridwidth = 3; g.fill = GridBagConstraints.BOTH;
        add(new JScrollPane(txtObservaciones), g);
        g.gridwidth = 1; g.fill = GridBagConstraints.HORIZONTAL;

        // Fila 6: Botones
        JPanel panelBotones = new JPanel();
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        g.gridx = 0; g.gridy = 6; g.gridwidth = 4;
        add(panelBotones, g);
        g.gridwidth = 1;

        // Fila 7: Buscar (por código, nombre o estado — RF-2)
        add(new JLabel("Buscar:"), pos(g, 0, 7));
        txtBuscar = new JTextField();
        txtBuscar.putClientProperty("JTextField.placeholderText", "Por código de ticket, nombre del cliente o estado");
        g.gridx = 1; g.gridy = 7; g.gridwidth = 3;
        add(txtBuscar, g);
        g.gridwidth = 1;

        // Fila 8: Tabla
        modelo = new DefaultTableModel(new Object[]{
                "Código", "Cliente", "Correo", "Contacto", "Tipo de problema",
                "Descripción", "Fecha", "Estado", "Observaciones", "Responsable"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        JScrollPane spTabla = new JScrollPane(tabla);
        spTabla.setPreferredSize(new Dimension(900, 220));
        g.gridx = 0; g.gridy = 8; g.gridwidth = 4; g.fill = GridBagConstraints.BOTH;
        g.weightx = 1; g.weighty = 1;
        add(spTabla, g);

        // Acciones
        btnRegistrar.addActionListener(e -> registrar());
        btnActualizar.addActionListener(e -> actualizar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiar());
        txtBuscar.getDocument().addDocumentListener(new SimpleDocListener(this::buscar));
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cargarSeleccion();
        });
    }

    // ---------- RF-1: Registrar ----------
    private void registrar() {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contacto = txtContacto.getText().trim();
        String descripcion = txtDescripcion.getText().trim();

        if (!SOLO_LETRAS.matcher(nombre).matches()) {
            JOptionPane.showMessageDialog(this, "El nombre del cliente solo admite letras y espacios (máx. 100).");
            return;
        }
        if (correo.length() > 100 || !CORREO.matcher(correo).matches()) {
            JOptionPane.showMessageDialog(this, "Ingrese un correo electrónico válido (máx. 100 caracteres).");
            return;
        }
        if (!contacto.isEmpty() && !SOLO_NUMEROS.matcher(contacto).matches()) {
            JOptionPane.showMessageDialog(this, "El número de contacto solo admite números (máx. 10 dígitos).");
            return;
        }
        if (descripcion.length() > 300) {
            JOptionPane.showMessageDialog(this, "La descripción no puede superar los 300 caracteres.");
            return;
        }

        SoporteDTO t = new SoporteDTO();
        t.setNombreCliente(nombre);
        t.setCorreo(correo);
        t.setNumeroContacto(contacto);
        t.setTipoProblema((String) cmbTipoProblema.getSelectedItem());
        t.setDescripcion(descripcion);
        t.setEstadoTicket("Pendiente"); // Estado inicial según RF-1
        t.setObservaciones(txtObservaciones.getText().trim());
        t.setResponsable(txtResponsable.getText().trim());

        if (soporteDAO.registerTicket(t)) {
            JOptionPane.showMessageDialog(this, "Ticket registrado correctamente.");
            limpiar();
            listarTickets();
        }
    }

    // ---------- RF-3: Actualizar (estado, observaciones, responsable) ----------
    private void actualizar() {
        if (codigoTicketSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un ticket de la tabla para actualizar.");
            return;
        }
        SoporteDTO t = new SoporteDTO();
        t.setCodigoTicket(codigoTicketSeleccionado);
        t.setEstadoTicket((String) cmbEstado.getSelectedItem());
        t.setObservaciones(txtObservaciones.getText().trim());
        t.setResponsable(txtResponsable.getText().trim());

        if (soporteDAO.updateTicket(t)) {
            JOptionPane.showMessageDialog(this, "Ticket actualizado correctamente.");
            limpiar();
            listarTickets();
        }
    }

    // ---------- RF-4: Eliminar ----------
    private void eliminar() {
        if (codigoTicketSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un ticket de la tabla para eliminar.");
            return;
        }
        int r = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este ticket?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION && soporteDAO.softDeleteTicket(codigoTicketSeleccionado)) {
            JOptionPane.showMessageDialog(this, "Ticket eliminado correctamente.");
            limpiar();
            listarTickets();
        }
    }

    private void limpiar() {
        codigoTicketSeleccionado = 0;
        txtNombre.setText("");
        txtCorreo.setText("");
        txtContacto.setText("");
        txtResponsable.setText("");
        txtDescripcion.setText("");
        txtObservaciones.setText("");
        cmbTipoProblema.setSelectedIndex(0);
        cmbEstado.setSelectedIndex(0);
        tabla.clearSelection();
    }

    // ---------- RF-2: Consulta / búsqueda ----------
    private void buscar() {
        String filtro = txtBuscar.getText().trim();
        pintarTabla(filtro.isEmpty() ? soporteDAO.listActiveTickets() : soporteDAO.searchTickets(filtro));
    }

    private void listarTickets() {
        pintarTabla(soporteDAO.listActiveTickets());
    }

    private void pintarTabla(List<SoporteDTO> lista) {
        modelo.setRowCount(0);
        for (SoporteDTO t : lista) {
            modelo.addRow(new Object[]{
                t.getCodigoTicket(), t.getNombreCliente(), t.getCorreo(), t.getNumeroContacto(),
                t.getTipoProblema(), t.getDescripcion(), t.getFechaCreacion(), t.getEstadoTicket(),
                t.getObservaciones(), t.getResponsable()
            });
        }
    }

    private void cargarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return;
        codigoTicketSeleccionado = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
        txtNombre.setText(valor(modelo.getValueAt(fila, 1)));
        txtCorreo.setText(valor(modelo.getValueAt(fila, 2)));
        txtContacto.setText(valor(modelo.getValueAt(fila, 3)));
        cmbTipoProblema.setSelectedItem(valor(modelo.getValueAt(fila, 4)));
        txtDescripcion.setText(valor(modelo.getValueAt(fila, 5)));
        cmbEstado.setSelectedItem(valor(modelo.getValueAt(fila, 7)));
        txtObservaciones.setText(valor(modelo.getValueAt(fila, 8)));
        txtResponsable.setText(valor(modelo.getValueAt(fila, 9)));
    }

    private String valor(Object o) {
        return o == null ? "" : o.toString();
    }

    private GridBagConstraints pos(GridBagConstraints g, int x, int y) {
        g.gridx = x;
        g.gridy = y;
        return g;
    }

    /** Listener simple para reaccionar a cambios en el campo de búsqueda. */
    private static class SimpleDocListener implements javax.swing.event.DocumentListener {
        private final Runnable accion;
        SimpleDocListener(Runnable accion) { this.accion = accion; }
        public void insertUpdate(javax.swing.event.DocumentEvent e) { accion.run(); }
        public void removeUpdate(javax.swing.event.DocumentEvent e) { accion.run(); }
        public void changedUpdate(javax.swing.event.DocumentEvent e) { accion.run(); }
    }
}
