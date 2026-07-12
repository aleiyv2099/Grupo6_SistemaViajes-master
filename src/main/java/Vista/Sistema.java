/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

import Controlador.ClienteDAO;
import Modelo.ClienteDTO;
import Controlador.FacturaDAO;
import Modelo.FacturaDTO;
import Controlador.ReservaDAO;
import Modelo.ReservaDTO;
import Modelo.MascotaDTO;
import Controlador.MascotaDAO;
import Controlador.EquipajeDAO;
import Modelo.EquipajeDTO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.formdev.flatlaf.FlatDarkLaf;
import java.util.Collections;
import javax.swing.UIManager;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

/**
 *
 * @author Mini Wernaso
 */
public class Sistema extends javax.swing.JFrame {

    ClienteDTO cl = new ClienteDTO();
    ClienteDAO client = new ClienteDAO();
    ReservaDTO reservadto = new ReservaDTO();
    ReservaDAO reservadao = new ReservaDAO();
    MascotaDTO mascotadto = new MascotaDTO();
    MascotaDAO mascotadao = new MascotaDAO();
    FacturaDTO facturadto = new FacturaDTO();
    FacturaDAO facturadao = new FacturaDAO();
    EquipajeDAO equipajedao = new EquipajeDAO();
    EquipajeDTO equipajedto = new EquipajeDTO();
    DefaultTableModel modelo = new DefaultTableModel();

    public Sistema() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        // Aplicar tema oscuro manualmente a los componentes principales
        jPanel1.setBackground(new java.awt.Color(30, 30, 35));
        jPanel3.setBackground(new java.awt.Color(35, 35, 40));
        jTabbedPane1.setBackground(new java.awt.Color(30, 30, 35));
        
        // Aplicar tema oscuro a las tablas
        jtableCliente.setBackground(new java.awt.Color(35, 35, 40));
        jtableCliente.setForeground(new java.awt.Color(230, 230, 235));
        jtableCliente.setGridColor(new java.awt.Color(50, 53, 58));
        jtableCliente.setSelectionBackground(new java.awt.Color(60, 100, 150));
        jtableCliente.getTableHeader().setBackground(new java.awt.Color(45, 48, 53));
        jtableCliente.getTableHeader().setForeground(new java.awt.Color(240, 240, 245));
        
        TableReservarPasaje.setBackground(new java.awt.Color(35, 35, 40));
        TableReservarPasaje.setForeground(new java.awt.Color(230, 230, 235));
        TableReservarPasaje.setGridColor(new java.awt.Color(50, 53, 58));
        TableReservarPasaje.setSelectionBackground(new java.awt.Color(60, 100, 150));
        TableReservarPasaje.getTableHeader().setBackground(new java.awt.Color(45, 48, 53));
        TableReservarPasaje.getTableHeader().setForeground(new java.awt.Color(240, 240, 245));
        
        TableFacturas.setBackground(new java.awt.Color(35, 35, 40));
        TableFacturas.setForeground(new java.awt.Color(230, 230, 235));
        TableFacturas.setGridColor(new java.awt.Color(50, 53, 58));
        TableFacturas.setSelectionBackground(new java.awt.Color(60, 100, 150));
        TableFacturas.getTableHeader().setBackground(new java.awt.Color(45, 48, 53));
        TableFacturas.getTableHeader().setForeground(new java.awt.Color(240, 240, 245));
        
        // Redimensionar el logo principal a un tamaño fijo
        try {
            javax.swing.ImageIcon logoOriginal = new javax.swing.ImageIcon(getClass().getResource("/LogoP.png"));
            java.awt.Image logoEscalado = logoOriginal.getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
            jLabel3.setIcon(new javax.swing.ImageIcon(logoEscalado));
        } catch (Exception e) {
            System.err.println("Error al cargar el logo principal: " + e.getMessage());
        }
        
        // Redimensionar iconos de botones a tamaño fijo
        try {
            javax.swing.ImageIcon iconoCliente = new javax.swing.ImageIcon(getClass().getResource("/Cliente.png"));
            java.awt.Image clienteEscalado = iconoCliente.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            btnCliente.setIcon(new javax.swing.ImageIcon(clienteEscalado));
            
            javax.swing.ImageIcon iconoReserva = new javax.swing.ImageIcon(getClass().getResource("/Reservar.png"));
            java.awt.Image reservaEscalado = iconoReserva.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            btnReserva.setIcon(new javax.swing.ImageIcon(reservaEscalado));
            
            javax.swing.ImageIcon iconoFactura = new javax.swing.ImageIcon(getClass().getResource("/Pago.png"));
            java.awt.Image facturaEscalado = iconoFactura.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
            btnFactura.setIcon(new javax.swing.ImageIcon(facturaEscalado));
        } catch (Exception e) {
            System.err.println("Error al cargar los iconos de los botones: " + e.getMessage());
        }
        
        txtCodigoCliente.setVisible(false);
        txtCodigoReserva.setVisible(false);
        txtCodigoFactura.setVisible(false);
        txtFechadeEmisionFactura.setVisible(false);
        txtBuscarClientes.putClientProperty("JTextField.placeholderText", "Buscar por Código, DNI o Nombre");
        txtBuscarReservas.putClientProperty("JTextField.placeholderText", "Buscar por Código de reserva o código cliente");
        txtBuscarFactura.putClientProperty("JTextField.placeholderText", "Buscar por Código, Método de Pago o Estado");
        cmbCodigoClienteFactura.setEditable(true);
        txtcodigosReservasFactura.putClientProperty("JTextField.placeholderText", "Ej: 69,60 (auto-calculado al seleccionar cliente)");

        CargarClientesEnComboBox();
        CargarClientesEnComboBoxMascota();
        CargarClientesEnComboBoxClienteFactura();
        CargarClientesEnComboBoxEquipaje();
        ListarReservas();
        ListarCliente();
        ListarFacturas();
        ListarMascotas();

        // Módulo 5 — Soporte al Cliente: se agrega como pestaña adicional.
        jTabbedPane1.addTab("Soporte al Cliente", new SoportePanel());
    }

    public void ListarCliente() {
        modelo = (DefaultTableModel) jtableCliente.getModel();
        LimpiarTabla();
        List<ClienteDTO> ListarCl = client.listActiveClients();
        Object[] ob = new Object[5];
        for (int i = 0; i < ListarCl.size(); i++) {
            ob[0] = ListarCl.get(i).getClientCode();
            ob[1] = ListarCl.get(i).getIdentificationNumber();
            ob[2] = ListarCl.get(i).getFullName();
            ob[3] = ListarCl.get(i).getPhoneNumber();
            ob[4] = ListarCl.get(i).getAddress();
            modelo.addRow(ob);
        }
        jtableCliente.setModel(modelo);
    }

    public void ListarReservas() {
        modelo = (DefaultTableModel) TableReservarPasaje.getModel();
        LimpiarTabla();
        List<ReservaDTO> ListarRs = reservadao.listActiveReservations(); // Asegúrate del nombre de tu JTable
        Object[] ob = new Object[8];

        for (int i = 0; i < ListarRs.size(); i++) {
            ob[0] = ListarRs.get(i).getReservationCode();
            ob[1] = ListarRs.get(i).getClientCode();
            ob[2] = ListarRs.get(i).getOriginCity();
            ob[3] = ListarRs.get(i).getDestinationCity();
            ob[4] = ListarRs.get(i).getTravelDate();
            ob[5] = ListarRs.get(i).getDepartureTime();
            ob[6] = ListarRs.get(i).getAssignedSeat();
            ob[7] = ListarRs.get(i).getTicketPrice();
            modelo.addRow(ob);
        }

        TableReservarPasaje.setModel(modelo);
    }
    public void ListarMascotas() {
        modelo = (DefaultTableModel) jtableMascota.getModel();
        LimpiarTabla();

        List<MascotaDTO> ListarMs = mascotadao.listActiveMascotas();

        Object[] ob = new Object[6];

        for (int i = 0; i < ListarMs.size(); i++) {

            ob[0] = ListarMs.get(i).getClientCode();
            ob[1] = ListarMs.get(i).getIdMascota();
            ob[2] = ListarMs.get(i).getNombre();
            ob[3] = ListarMs.get(i).getRaza();
            ob[4] = ListarMs.get(i).getPeso();
            ob[5] = ListarMs.get(i).getObservaciones();

            modelo.addRow(ob);
        }

        jtableMascota.setModel(modelo);
    }
    public void ListarFacturas() {
        List<FacturaDTO> listaFacturas = facturadao.listActiveInvoices();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"Nº Factura", "Reservas incluidas", "Cód. Cliente", "Fecha de emisión", "Monto total ($)", "Método de pago", "Estado de factura"});

        for (FacturaDTO f : listaFacturas) {
            String reservas = facturadao.getReservationCodesForInvoice(f.getInvoiceCode());

            modelo.addRow(new Object[]{
                f.getInvoiceCode(),
                reservas,
                f.getClientCode(),
                f.getIssueDate(),
                f.getTotalAmount(),
                f.getPaymentMethod(),
                f.getInvoiceStatus()
            });
        }

        System.out.println("Total filas cargadas en tabla: " + modelo.getRowCount());

        TableFacturas.setModel(modelo);
    }

    public void LimpiarTabla() {
        while (modelo.getRowCount() > 0) {
            modelo.removeRow(0);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtableCliente = new javax.swing.JTable();
        btnAgregarCliente = new javax.swing.JButton();
        btnNuevoCliente = new javax.swing.JButton();
        btnEditarCliente = new javax.swing.JButton();
        btnEliminarCliente = new javax.swing.JButton();
        txtDireccion = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtNombreCliente = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtDNI = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtBuscarClientes = new javax.swing.JTextField();
        txtCodigoCliente = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableReservarPasaje = new javax.swing.JTable();
        btnEditarReserva = new javax.swing.JButton();
        btnAgregarReserva = new javax.swing.JButton();
        btnNuevaReserva = new javax.swing.JButton();
        btnEliminarReserva = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        cmbCodigoCliente = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtOrigen = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtDestino = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtFechaViaje = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtPrecioPasaje = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        txtBuscarReservas = new javax.swing.JTextField();
        txtCodigoReserva = new javax.swing.JTextField();
        txtAsientoAsignado = new javax.swing.JTextField();
        cmbHoraSalida = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbCodigoClienteM = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtNombreMascota = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtRaza = new javax.swing.JTextField();
        txtObservaciones = new javax.swing.JTextField();
        jsPeso = new javax.swing.JSpinner();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtBuscarMascotas = new javax.swing.JTextField();
        btnAgregarMascota = new javax.swing.JButton();
        btnEditarMascota = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jtableMascota = new javax.swing.JTable();
        txtidMascota = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        cmbCodigoClienteE = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        txtTipoEquipaje = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jsPesoEquipaje = new javax.swing.JSpinner();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtBuscarEquipaje = new javax.swing.JTextField();
        btnAgregarEquipaje = new javax.swing.JButton();
        btnEditarEquipaje = new javax.swing.JButton();
        btnEliminarEquipaje = new javax.swing.JButton();
        btnNuevoEquipaje = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jtableEquipaje = new javax.swing.JTable();
        txtidEquipaje = new javax.swing.JTextField();
        jsCantidadEquipaje = new javax.swing.JSpinner();
        jLabel32 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtMontoTotal = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        btnAgregarFactura = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        btnEliminarFactura = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        TableFacturas = new javax.swing.JTable();
        cmbCodigoClienteFactura = new javax.swing.JComboBox<>();
        btnNuevaFactura = new javax.swing.JButton();
        btnActualizarFactura = new javax.swing.JButton();
        cmbMetodoPago = new javax.swing.JComboBox<>();
        cmbEstadoFactura = new javax.swing.JComboBox<>();
        txtBuscarFactura = new javax.swing.JTextField();
        txtFechadeEmisionFactura = new javax.swing.JTextField();
        txtCodigoFactura = new javax.swing.JTextField();
        txtcodigosReservasFactura = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnCliente = new javax.swing.JButton();
        btnReserva = new javax.swing.JButton();
        btnReserva1 = new javax.swing.JButton();
        btnFactura = new javax.swing.JButton();
        btnEquipaje = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        jtableCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código Cliente", "DNI", "Nombre", "Teléfono", "Dirección"
            }
        ));
        jtableCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtableClienteMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jtableCliente);

        btnAgregarCliente.setText("Agregar Cliente");
        btnAgregarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarClienteActionPerformed(evt);
            }
        });

        btnNuevoCliente.setText("Nuevo");
        btnNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoClienteActionPerformed(evt);
            }
        });

        btnEditarCliente.setText("Editar");
        btnEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarClienteActionPerformed(evt);
            }
        });

        btnEliminarCliente.setText("Eliminar");
        btnEliminarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarClienteActionPerformed(evt);
            }
        });

        jLabel17.setText("Dirección");

        jLabel16.setText("Teléfono");

        jLabel15.setText("Nombre");

        jLabel14.setText("DNI");

        txtBuscarClientes.setToolTipText("");
        txtBuscarClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarClientesActionPerformed(evt);
            }
        });
        txtBuscarClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarClientesKeyReleased(evt);
            }
        });

        txtCodigoCliente.setEnabled(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtDireccion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                        .addComponent(txtTelefono, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtNombreCliente, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtDNI, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnEliminarCliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAgregarCliente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnEditarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNuevoCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel14)
                    .addComponent(txtCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBuscarClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 1041, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1041, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(txtDNI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAgregarCliente)
                            .addComponent(btnEditarCliente))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEliminarCliente)
                            .addComponent(btnNuevoCliente)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(txtBuscarClientes))
                        .addGap(9, 9, 9)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 77, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Cliente", jPanel4);

        TableReservarPasaje.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código", "Cliente", "Origen", "Destino", "Fecha de viaje", "Hora de salida", "Asiento asignado", "Precio del pasaje"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TableReservarPasaje.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableReservarPasajeMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TableReservarPasaje);

        btnEditarReserva.setText("Editar");
        btnEditarReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarReservaActionPerformed(evt);
            }
        });

        btnAgregarReserva.setText("Agregar");
        btnAgregarReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarReservaActionPerformed(evt);
            }
        });

        btnNuevaReserva.setText("Nuevo");
        btnNuevaReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaReservaActionPerformed(evt);
            }
        });

        btnEliminarReserva.setText("Eliminar");
        btnEliminarReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarReservaActionPerformed(evt);
            }
        });

        jLabel5.setText("Cliente ");

        cmbCodigoCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbCodigoClienteMouseClicked(evt);
            }
        });
        cmbCodigoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCodigoClienteActionPerformed(evt);
            }
        });

        jLabel7.setText("Origen");

        jLabel8.setText("Destino\t");

        jLabel9.setText("Fecha del viaje\t");

        jLabel10.setText("Hora de salida\t");

        jLabel13.setText("Precio del pasaje\t");

        jLabel11.setText("Asiento asignado\t");

        txtBuscarReservas.setToolTipText("");
        txtBuscarReservas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarReservasActionPerformed(evt);
            }
        });
        txtBuscarReservas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarReservasKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtBuscarReservas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtBuscarReservas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
        );

        txtCodigoReserva.setEditable(false);

        cmbHoraSalida.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "07:00:00", "08:00:00", "09:00:00", "10:00:00", "11:00:00", "12:00:00", "13:00:00", "14:00:00", "15:00:00", "16:00:00", "17:00:00", "18:00:00", "19:00:00", "20:00:00" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtCodigoReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtFechaViaje, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDestino, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtOrigen, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbCodigoCliente, 0, 141, Short.MAX_VALUE)
                            .addComponent(cmbHoraSalida, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEliminarReserva, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEditarReserva, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAgregarReserva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNuevaReserva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPrecioPasaje)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel13))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtAsientoAsignado))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(cmbCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtAsientoAsignado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(28, 28, 28)))
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPrecioPasaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFechaViaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addComponent(jLabel10))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnAgregarReserva)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminarReserva)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEditarReserva)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNuevaReserva)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbHoraSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(txtCodigoReserva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Reservas", jPanel2);

        jPanel6.setToolTipText("");

        jLabel2.setText("Cliente");

        cmbCodigoClienteM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbCodigoClienteMMouseClicked(evt);
            }
        });

        jLabel4.setText("Nombre de Mascota");

        jLabel6.setText("Raza");

        jLabel12.setText("Peso");

        jLabel18.setText("Observaciones");

        jsPeso.setModel(new javax.swing.SpinnerNumberModel(0.0d, null, null, 0.25d));

        jLabel23.setText("Kg");

        jLabel24.setText("Buscar:");

        txtBuscarMascotas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarMascotasActionPerformed(evt);
            }
        });
        txtBuscarMascotas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarMascotasKeyReleased(evt);
            }
        });

        btnAgregarMascota.setText("Agregar");
        btnAgregarMascota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarMascotaActionPerformed(evt);
            }
        });

        btnEditarMascota.setText("Editar");
        btnEditarMascota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarMascotaActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        jtableMascota.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IdCliente", "Id Mascota", "Nombre de Mascota", "Raza", "Peso", "Observacion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtableMascota.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtableMascotaMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jtableMascota);

        txtidMascota.setEditable(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(117, 117, 117)
                                    .addComponent(jLabel4))
                                .addComponent(jLabel18)
                                .addGroup(jPanel6Layout.createSequentialGroup()
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtRaza, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cmbCodigoClienteM, javax.swing.GroupLayout.Alignment.LEADING, 0, 141, Short.MAX_VALUE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel12)
                                        .addComponent(txtNombreMascota, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                            .addComponent(jsPeso, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel23)
                                            .addGap(12, 12, 12))))
                                .addComponent(txtObservaciones))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(btnAgregarMascota, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditarMascota, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBuscarMascotas))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 933, Short.MAX_VALUE))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtidMascota, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel24)
                    .addComponent(txtBuscarMascotas, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbCodigoClienteM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombreMascota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtRaza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jsPeso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addGap(3, 3, 3)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtObservaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAgregarMascota)
                            .addComponent(btnEditarMascota))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEliminar)
                            .addComponent(btnNuevo)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(txtidMascota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("PETC", jPanel6);

        jPanel8.setToolTipText("");

        jLabel25.setText("Cliente");

        cmbCodigoClienteE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbCodigoClienteEMouseClicked(evt);
            }
        });
        cmbCodigoClienteE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCodigoClienteEActionPerformed(evt);
            }
        });

        jLabel26.setText("Tipo");

        txtTipoEquipaje.setActionCommand("<Not Set>");
        txtTipoEquipaje.setAutoscrolls(false);
        txtTipoEquipaje.setVerifyInputWhenFocusTarget(false);
        txtTipoEquipaje.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTipoEquipajeKeyPressed(evt);
            }
        });

        jLabel27.setText("Cantidad");

        jLabel28.setText("Peso");

        jsPesoEquipaje.setModel(new javax.swing.SpinnerNumberModel(0.25d, 0.25d, null, 0.25d));

        jLabel30.setText("Kg");

        jLabel31.setText("Buscar:");

        txtBuscarEquipaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarEquipajeActionPerformed(evt);
            }
        });
        txtBuscarEquipaje.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarEquipajeKeyReleased(evt);
            }
        });

        btnAgregarEquipaje.setText("Agregar");
        btnAgregarEquipaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarEquipajeActionPerformed(evt);
            }
        });

        btnEditarEquipaje.setText("Editar");
        btnEditarEquipaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarEquipajeActionPerformed(evt);
            }
        });

        btnEliminarEquipaje.setText("Eliminar");
        btnEliminarEquipaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarEquipajeActionPerformed(evt);
            }
        });

        btnNuevoEquipaje.setText("Nuevo");
        btnNuevoEquipaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoEquipajeActionPerformed(evt);
            }
        });

        jtableEquipaje.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtableEquipaje.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtableEquipajeMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(jtableEquipaje);

        txtidEquipaje.setEditable(false);
        txtidEquipaje.setEnabled(false);

        jsCantidadEquipaje.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel32.setText("ID");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addComponent(jLabel25)
                            .addGap(117, 117, 117)
                            .addComponent(jLabel26))
                        .addGroup(jPanel8Layout.createSequentialGroup()
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cmbCodigoClienteE, 0, 141, Short.MAX_VALUE)
                                .addComponent(jLabel27)
                                .addComponent(jsCantidadEquipaje))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel28)
                                .addComponent(txtTipoEquipaje, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                    .addComponent(jsPesoEquipaje, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel30)
                                    .addGap(12, 12, 12))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(btnAgregarEquipaje, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditarEquipaje, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(btnEliminarEquipaje, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnNuevoEquipaje, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(txtidEquipaje, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel32)))
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscarEquipaje))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 933, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(jLabel26)
                    .addComponent(jLabel31)
                    .addComponent(txtBuscarEquipaje, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbCodigoClienteE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTipoEquipaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jsPesoEquipaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30)
                            .addComponent(jsCantidadEquipaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAgregarEquipaje)
                            .addComponent(btnEditarEquipaje))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEliminarEquipaje)
                            .addComponent(btnNuevoEquipaje))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtidEquipaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32)))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(89, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Equipaje", jPanel8);

        jLabel19.setText("Código de Cliente");

        jLabel20.setText("Monto total\t");

        txtMontoTotal.setEditable(false);

        jLabel21.setText("Método de pago\t");

        jLabel22.setText("Estado de factura\t");

        btnAgregarFactura.setText("Emitir Factura");
        btnAgregarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarFacturaActionPerformed(evt);
            }
        });

        btnImprimir.setText("Imprimir");
        btnImprimir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnImprimirMouseClicked(evt);
            }
        });
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        btnEliminarFactura.setText("Anular");
        btnEliminarFactura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarFacturaMouseClicked(evt);
            }
        });
        btnEliminarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarFacturaActionPerformed(evt);
            }
        });

        TableFacturas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Código de factura	", "Código de cliente", "Codigo de reservas", "Fecha de emisión	", "Monto total	", "Método de pago	", "Estado de factura	"
            }
        ));
        TableFacturas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableFacturasMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TableFacturas);

        cmbCodigoClienteFactura.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCodigoClienteFacturaItemStateChanged(evt);
            }
        });
        cmbCodigoClienteFactura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbCodigoClienteFacturaMouseClicked(evt);
            }
        });
        cmbCodigoClienteFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCodigoClienteFacturaActionPerformed(evt);
            }
        });

        btnNuevaFactura.setText("Nuevo");
        btnNuevaFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaFacturaActionPerformed(evt);
            }
        });

        btnActualizarFactura.setText("Actualizar");
        btnActualizarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarFacturaActionPerformed(evt);
            }
        });

        cmbMetodoPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Efectivo", "Tarjeta" }));

        cmbEstadoFactura.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pagado", "Pendiente", " " }));

        txtBuscarFactura.setText("Buscar:");
        txtBuscarFactura.setToolTipText("");
        txtBuscarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarFacturaActionPerformed(evt);
            }
        });

        txtFechadeEmisionFactura.setEditable(false);

        txtCodigoFactura.setEditable(false);

        jLabel29.setText("Cod. Reserva");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtCodigoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel21)
                                    .addComponent(jLabel22)
                                    .addComponent(cmbCodigoClienteFactura, 0, 168, Short.MAX_VALUE)
                                    .addComponent(txtMontoTotal)
                                    .addComponent(cmbMetodoPago, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbEstadoFactura, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtcodigosReservasFactura))
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtFechadeEmisionFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnNuevaFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnActualizarFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnAgregarFactura)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnEliminarFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(6, 6, 6))))
                            .addComponent(jLabel29))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtBuscarFactura, javax.swing.GroupLayout.DEFAULT_SIZE, 996, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 996, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtBuscarFactura, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                    .addComponent(txtCodigoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFechadeEmisionFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbCodigoClienteFactura, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel29)
                        .addGap(4, 4, 4)
                        .addComponent(txtcodigosReservasFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMontoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAgregarFactura))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(btnEliminarFactura))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbMetodoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnImprimir))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(btnActualizarFactura))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbEstadoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNuevaFactura))
                        .addContainerGap(46, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Factura", jPanel5);

        jPanel1.setBackground(new java.awt.Color(255, 204, 153));

        btnCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cliente.png"))); // NOI18N
        btnCliente.setText("Cliente");
        btnCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClienteActionPerformed(evt);
            }
        });

        btnReserva.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Reservar.png"))); // NOI18N
        btnReserva.setText("Reservar de pasajes");
        btnReserva.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReservaMouseClicked(evt);
            }
        });
        btnReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReservaActionPerformed(evt);
            }
        });

        btnReserva1.setText("Mascotas");
        btnReserva1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReserva1ActionPerformed(evt);
            }
        });

        btnFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Pago.png"))); // NOI18N
        btnFactura.setText("Facturador");
        btnFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFacturaActionPerformed(evt);
            }
        });

        btnEquipaje.setIcon(new javax.swing.ImageIcon(getClass().getResource("/equipaje.png"))); // NOI18N
        btnEquipaje.setText("Equipaje");
        btnEquipaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEquipajeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnReserva, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnEquipaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnReserva1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(92, Short.MAX_VALUE)
                .addComponent(btnCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnReserva)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnReserva1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEquipaje, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnFactura)
                .addGap(18, 18, 18))
        );

        jPanel3.setBackground(new java.awt.Color(255, 153, 102));

        jLabel1.setFont(new java.awt.Font("Constantia", 1, 30)); // NOI18N
        jLabel1.setText("Sistema de agencia de viaje");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/LogoP.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel3)
                .addGap(198, 198, 198)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteActionPerformed
        LimpiarTabla();
        ListarCliente();
        LimpiarCliente();
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_btnClienteActionPerformed

    private void btnReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReservaActionPerformed
        // TODO add your handling code here:
        LimpiarTabla();
        ListarReservas();
        LimpiarReserva();
        CargarClientesEnComboBox();
        jTabbedPane1.setSelectedIndex(1);

    }//GEN-LAST:event_btnReservaActionPerformed

    private void btnFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFacturaActionPerformed
        // TODO add your handling code here:
        LimpiarTabla();
        ListarFacturas();
        LimpiarFactura();
        CargarClientesEnComboBoxClienteFactura();
        jTabbedPane1.setSelectedIndex(3);

    }//GEN-LAST:event_btnFacturaActionPerformed

    private void btnAgregarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarClienteActionPerformed
        if (!"".equals(txtDNI.getText()) && !"".equals(txtNombreCliente.getText()) && !"".equals(txtTelefono.getText()) && !"".equals(txtDireccion.getText())) {
            cl.setIdentificationNumber(txtDNI.getText());
            cl.setFullName(txtNombreCliente.getText());
            cl.setPhoneNumber(txtTelefono.getText());
            cl.setAddress(txtDireccion.getText());
            client.registerClient(cl);
            JOptionPane.showMessageDialog(null, "Cliente Registrado");
            LimpiarTabla();
            ListarCliente();
            LimpiarCliente();

        } else {
            JOptionPane.showMessageDialog(null, "Los campos estan vacios");

        }
    }//GEN-LAST:event_btnAgregarClienteActionPerformed

    private void btnNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoClienteActionPerformed
        // TODO add your handling code here:
        LimpiarCliente();
    }//GEN-LAST:event_btnNuevoClienteActionPerformed

    private void btnEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClienteActionPerformed
        // TODO add your handling code here:
        if ("".equals(txtCodigoCliente.getText())) {
            JOptionPane.showMessageDialog(null, "seleccione una fila");
        } else {

            if (!"".equals(txtDNI.getText()) && !"".equals(txtNombreCliente.getText()) && !"".equals(txtTelefono.getText()) && !"".equals(txtDireccion.getText())) {
                cl.setIdentificationNumber(txtDNI.getText());
                cl.setFullName(txtNombreCliente.getText());
                cl.setPhoneNumber(txtTelefono.getText());
                cl.setAddress(txtDireccion.getText());
                cl.setClientCode(Integer.parseInt(txtCodigoCliente.getText()));
                client.updateClient(cl);
                LimpiarTabla();
                LimpiarCliente();
                ListarCliente();
            } else {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            }

        }
    }//GEN-LAST:event_btnEditarClienteActionPerformed

    private void btnEliminarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarClienteActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtCodigoCliente.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int CodigoCliente = Integer.parseInt(txtCodigoCliente.getText());
                client.softDeleteClient(CodigoCliente);
                LimpiarTabla();
                ListarCliente();
                LimpiarCliente();
            }
        }
    }//GEN-LAST:event_btnEliminarClienteActionPerformed

    private void btnAgregarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarFacturaActionPerformed
        Object clienteItem = cmbCodigoClienteFactura.getSelectedItem();
        if (clienteItem == null || clienteItem.toString().trim().isEmpty()
                || cmbEstadoFactura.getSelectedIndex() == -1
                || cmbMetodoPago.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
            return;
        }

        int codigoCliente;
        try {
            codigoCliente = Integer.parseInt(clienteItem.toString().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El código de cliente debe ser un número entero.");
            return;
        }

        String codigosTexto = txtcodigosReservasFactura.getText().trim();
        List<Integer> reservas;
        if (codigosTexto.isEmpty()) {
            reservas = facturadao.getUnbilledReservationsForClient(codigoCliente);
        } else {
            try {
                reservas = java.util.Arrays.stream(codigosTexto.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(Integer::parseInt)
                        .collect(java.util.stream.Collectors.toList());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Los códigos de reserva deben ser números separados por coma.");
                return;
            }
        }

        if (reservas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay reservas activas para este cliente. Ingrese los códigos manualmente.");
            return;
        }

        FacturaDTO fac = new FacturaDTO();
        fac.setIssueDate(new java.sql.Date(new java.util.Date().getTime()));
        fac.setClientCode(codigoCliente);
        fac.setReservationList(reservas);
        fac.setTotalAmount(facturadao.calculateTotalAmount(reservas));
        fac.setPaymentMethod(cmbMetodoPago.getSelectedItem().toString());
        fac.setInvoiceStatus(cmbEstadoFactura.getSelectedItem().toString());

        if (facturadao.registerInvoiceWithTransaction(fac)) {
            JOptionPane.showMessageDialog(null, "Factura registrada correctamente.");
            LimpiarFactura();
            ListarFacturas();
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar la factura.");
        }
    }//GEN-LAST:event_btnAgregarFacturaActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void btnEliminarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarFacturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarFacturaActionPerformed

    private void btnNuevaFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaFacturaActionPerformed
        LimpiarFactura();
    }//GEN-LAST:event_btnNuevaFacturaActionPerformed

    private void btnActualizarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarFacturaActionPerformed
        // Actualiza la factura SELECCIONADA (no crea una nueva).
        if (txtCodigoFactura.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una factura de la lista para actualizar.");
            return;
        }
        if (cmbMetodoPago.getSelectedIndex() == -1 || cmbEstadoFactura.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione el método de pago y el estado de la factura.");
            return;
        }
        FacturaDTO fac = new FacturaDTO();
        fac.setInvoiceCode(Integer.parseInt(txtCodigoFactura.getText().trim()));
        fac.setPaymentMethod(cmbMetodoPago.getSelectedItem().toString());
        fac.setInvoiceStatus(cmbEstadoFactura.getSelectedItem().toString());

        if (facturadao.updateInvoice(fac)) {
            JOptionPane.showMessageDialog(null, "Factura actualizada correctamente.");
            LimpiarFactura();
            ListarFacturas();
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar la factura (verifique que no esté anulada).");
        }
    }//GEN-LAST:event_btnActualizarFacturaActionPerformed

    // Validaciones compartidas de formularios (rechazan vacíos, espacios y números donde no aplican)
    public static boolean soloLetras(String texto) {
        return texto != null && texto.trim().matches("[\\p{L} ]+");
    }

    public static boolean fechaValida(String fecha) {
        return fecha != null && fecha.trim().matches("\\d{4}-\\d{2}-\\d{2}");
    }

    private void btnAgregarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarReservaActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtOrigen.getText()) && !"".equals(txtDestino.getText())
                && !"".equals(txtFechaViaje.getText()) && cmbHoraSalida.getSelectedItem() != null
                && !"".equals(txtPrecioPasaje.getText()) && !"".equals(txtAsientoAsignado.getText())) {

            try {
                int codigoCliente = Integer.parseInt(cmbCodigoCliente.getSelectedItem().toString());

                if (!soloLetras(txtOrigen.getText()) || !soloLetras(txtDestino.getText())) {
                    JOptionPane.showMessageDialog(null, "El origen y el destino solo pueden contener letras.");
                    return;
                }
                if (txtOrigen.getText().trim().equalsIgnoreCase(txtDestino.getText().trim())) {
                    JOptionPane.showMessageDialog(null, "El origen y el destino no pueden ser iguales.");
                    return;
                }
                if (!fechaValida(txtFechaViaje.getText())) {
                    JOptionPane.showMessageDialog(null, "La fecha de viaje debe tener el formato YYYY-MM-DD (ej. 2026-07-15).");
                    return;
                }

                // Validar si el asiento ya está reservado
                boolean ocupado = reservadao.isSeatAlreadyBooked(
                        txtOrigen.getText(),
                        txtDestino.getText(),
                        txtFechaViaje.getText(),
                        cmbHoraSalida.getSelectedItem().toString(),
                        txtAsientoAsignado.getText()
                );

                if (ocupado) {
                    JOptionPane.showMessageDialog(null, "El asiento ya está reservado.");
                    return;
                }

                reservadto.setClientCode(codigoCliente);
                reservadto.setOriginCity(txtOrigen.getText());
                reservadto.setDestinationCity(txtDestino.getText());
                reservadto.setTravelDate(txtFechaViaje.getText());
                reservadto.setDepartureTime(cmbHoraSalida.getSelectedItem().toString());
                reservadto.setAssignedSeat(txtAsientoAsignado.getText());
                reservadto.setTicketPrice(Double.parseDouble(txtPrecioPasaje.getText()));

                reservadao.registerReservation(reservadto);

                JOptionPane.showMessageDialog(null, "Reserva Registrada");
                LimpiarTabla();
                LimpiarReserva();
                ListarReservas();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }

        } else {
            JOptionPane.showMessageDialog(null, "Los campos están vacíos");
        }
    }//GEN-LAST:event_btnAgregarReservaActionPerformed


    private void btnEditarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarReservaActionPerformed
        if ("".equals(txtCodigoReserva.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        } else {
            // Validar campos obligatorios
            if (cmbCodigoCliente.getSelectedItem() != null
                    && !"".equals(txtOrigen.getText())
                    && !"".equals(txtDestino.getText())
                    && !"".equals(txtFechaViaje.getText())
                    && cmbHoraSalida.getSelectedItem() != null
                    && !"".equals(txtAsientoAsignado.getText())
                    && !"".equals(txtPrecioPasaje.getText())) {

                try {
                    int codigoReserva = Integer.parseInt(txtCodigoReserva.getText());
                    int codigoCliente = Integer.parseInt(cmbCodigoCliente.getSelectedItem().toString());

                    if (!soloLetras(txtOrigen.getText()) || !soloLetras(txtDestino.getText())) {
                        JOptionPane.showMessageDialog(null, "El origen y el destino solo pueden contener letras.");
                        return;
                    }
                    if (txtOrigen.getText().trim().equalsIgnoreCase(txtDestino.getText().trim())) {
                        JOptionPane.showMessageDialog(null, "El origen y el destino no pueden ser iguales.");
                        return;
                    }
                    if (!fechaValida(txtFechaViaje.getText())) {
                        JOptionPane.showMessageDialog(null, "La fecha de viaje debe tener el formato YYYY-MM-DD (ej. 2026-07-15).");
                        return;
                    }

                    // Verificar si el asiento ya está reservado por otra reserva
                    boolean ocupado = reservadao.isSeatAlreadyBooked(
                            txtOrigen.getText(),
                            txtDestino.getText(),
                            txtFechaViaje.getText(),
                            cmbHoraSalida.getSelectedItem().toString(),
                            txtAsientoAsignado.getText(),
                            codigoReserva
                    );

                    if (ocupado) {
                        JOptionPane.showMessageDialog(null, "El asiento ya está reservado.");
                        return;
                    }

                    // Crear el objeto ReservaDTO con los nuevos datos
                    reservadto.setReservationCode(codigoReserva);
                    reservadto.setClientCode(codigoCliente);
                    reservadto.setOriginCity(txtOrigen.getText());
                    reservadto.setDestinationCity(txtDestino.getText());
                    reservadto.setTravelDate(txtFechaViaje.getText());
                    reservadto.setDepartureTime(cmbHoraSalida.getSelectedItem().toString());
                    reservadto.setAssignedSeat(txtAsientoAsignado.getText());
                    reservadto.setTicketPrice(Double.parseDouble(txtPrecioPasaje.getText()));

                    // Editar la reserva
                    if (reservadao.updateReservation(reservadto)) {
                        JOptionPane.showMessageDialog(null, "Reserva actualizada correctamente.");
                        LimpiarTabla();
                        ListarReservas();
                        LimpiarReserva();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al actualizar la reserva.");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
                }

            } else {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
            }
        }
    }//GEN-LAST:event_btnEditarReservaActionPerformed

    private void btnNuevaReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaReservaActionPerformed
        // TODO add your handling code here:
        LimpiarReserva();
    }//GEN-LAST:event_btnNuevaReservaActionPerformed

    private void btnEliminarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarReservaActionPerformed
        // TODO add your handling code here:
        if (!"".equals(txtCodigoReserva.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int CodigoReserva = Integer.parseInt(txtCodigoReserva.getText());
                reservadao.softDeleteReservation(CodigoReserva);
                LimpiarTabla();
                ListarReservas();
                LimpiarReserva();
            }
        }
    }//GEN-LAST:event_btnEliminarReservaActionPerformed

    private void txtBuscarReservasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarReservasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarReservasActionPerformed

    private void txtBuscarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarClientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarClientesActionPerformed

    private void txtBuscarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarFacturaActionPerformed
        buscarYActualizarTablaFacturas(txtBuscarFactura.getText().trim());
    }//GEN-LAST:event_txtBuscarFacturaActionPerformed

    private void jtableClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtableClienteMouseClicked
        // TODO add your handling code here:
        int fila = jtableCliente.rowAtPoint(evt.getPoint());
        txtCodigoCliente.setText(jtableCliente.getValueAt(fila, 0).toString());
        txtDNI.setText(jtableCliente.getValueAt(fila, 1).toString());
        txtNombreCliente.setText(jtableCliente.getValueAt(fila, 2).toString());
        txtTelefono.setText(jtableCliente.getValueAt(fila, 3).toString());
        txtDireccion.setText(jtableCliente.getValueAt(fila, 4).toString());
    }//GEN-LAST:event_jtableClienteMouseClicked

    private void cmbCodigoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCodigoClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbCodigoClienteActionPerformed

    private void cmbCodigoClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbCodigoClienteMouseClicked
        // TODO add your handling code here:
        CargarClientesEnComboBox();
    }//GEN-LAST:event_cmbCodigoClienteMouseClicked

    private void TableReservarPasajeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableReservarPasajeMouseClicked
        // TODO add your handling code here:
        try {
            int fila = TableReservarPasaje.rowAtPoint(evt.getPoint());
            txtCodigoReserva.setText(TableReservarPasaje.getValueAt(fila, 0).toString());
            cmbCodigoCliente.setSelectedItem(TableReservarPasaje.getValueAt(fila, 1).toString());
            txtOrigen.setText(TableReservarPasaje.getValueAt(fila, 2).toString());
            txtDestino.setText(TableReservarPasaje.getValueAt(fila, 3).toString());
            txtFechaViaje.setText(TableReservarPasaje.getValueAt(fila, 4).toString());
            cmbHoraSalida.setSelectedItem(TableReservarPasaje.getValueAt(fila, 5).toString());
            txtAsientoAsignado.setText(TableReservarPasaje.getValueAt(fila, 6).toString());
            txtPrecioPasaje.setText(TableReservarPasaje.getValueAt(fila, 7).toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar la fila: " + e.getMessage());
        }

    }//GEN-LAST:event_TableReservarPasajeMouseClicked

    private void txtBuscarClientesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarClientesKeyReleased
        String texto = txtBuscarClientes.getText().trim();
        buscarYActualizarTabla(texto);    buscarYActualizarTabla(texto);    buscarYActualizarTabla(texto);    }//GEN-LAST:event_txtBuscarClientesKeyReleased

    private void txtBuscarReservasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarReservasKeyReleased
        // TODO add your handling code here:
        String texto = txtBuscarReservas.getText().trim();
        buscarYActualizarTablaReservas(texto);
    }//GEN-LAST:event_txtBuscarReservasKeyReleased

    private void cmbCodigoClienteFacturaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCodigoClienteFacturaItemStateChanged
        Object item = cmbCodigoClienteFactura.getSelectedItem();
        if (item == null || item.toString().trim().isEmpty()) {
            return;
        }
        try {
            int codigoCliente = Integer.parseInt(item.toString().trim());
            List<Integer> reservas = facturadao.getUnbilledReservationsForClient(codigoCliente);
            if (!reservas.isEmpty()) {
                String codigosStr = reservas.stream()
                        .map(String::valueOf)
                        .collect(java.util.stream.Collectors.joining(","));
                txtcodigosReservasFactura.setText(codigosStr);
                double total = facturadao.calculateTotalAmount(reservas);
                txtMontoTotal.setText(String.valueOf(total));
            } else {
                txtcodigosReservasFactura.setText("");
                txtMontoTotal.setText("0.0");
            }
        } catch (NumberFormatException e) {
            // Client code typed manually — do not auto-populate
        }
    }//GEN-LAST:event_cmbCodigoClienteFacturaItemStateChanged

    private void cmbCodigoClienteFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCodigoClienteFacturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbCodigoClienteFacturaActionPerformed

    private void cmbCodigoClienteFacturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbCodigoClienteFacturaMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_cmbCodigoClienteFacturaMouseClicked

    private void btnImprimirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImprimirMouseClicked
        // TODO add your handling code here:
        pdf();
    }//GEN-LAST:event_btnImprimirMouseClicked

    private void TableFacturasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableFacturasMouseClicked
        // TODO add your handling code here:
        try {
            int fila = TableFacturas.rowAtPoint(evt.getPoint());
            txtCodigoFactura.setText(TableFacturas.getValueAt(fila, 0).toString());
            txtcodigosReservasFactura.setText(TableFacturas.getValueAt(fila, 1).toString());
            cmbCodigoClienteFactura.setSelectedItem(TableFacturas.getValueAt(fila, 2).toString());
            txtFechadeEmisionFactura.setText(TableFacturas.getValueAt(fila, 3).toString());
            txtMontoTotal.setText(TableFacturas.getValueAt(fila, 4).toString());
            cmbMetodoPago.setSelectedItem(TableFacturas.getValueAt(fila, 5).toString());
            cmbEstadoFactura.setSelectedItem(TableFacturas.getValueAt(fila, 6).toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar la fila: " + e.getMessage());
        }
    }//GEN-LAST:event_TableFacturasMouseClicked

    private void btnEliminarFacturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarFacturaMouseClicked
        if ("".equals(txtCodigoFactura.getText())) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una factura de la lista antes de anular.");
            return;
        }
        int pregunta = JOptionPane.showConfirmDialog(null, "¿Está seguro de anular la factura?");
        if (pregunta == 0) {
            int CodigoFactura = Integer.parseInt(txtCodigoFactura.getText());
            boolean eliminado = facturadao.softDeleteInvoice(CodigoFactura);
            if (eliminado) {
                JOptionPane.showMessageDialog(null, "Factura anulada correctamente.");
                ListarFacturas();
                LimpiarFactura();
            } else {
                JOptionPane.showMessageDialog(null, "La factura ya fue anulada previamente o no existe.");
            }
        }
    }//GEN-LAST:event_btnEliminarFacturaMouseClicked

    private void btnReserva1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReserva1ActionPerformed
            ListarMascotas(); 
            LimpiarMascota();
            CargarClientesEnComboBoxMascota();// TODO add your handling code here:
            jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_btnReserva1ActionPerformed

    private void txtBuscarMascotasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarMascotasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarMascotasActionPerformed

    private void cmbCodigoClienteMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbCodigoClienteMMouseClicked
        CargarClientesEnComboBoxMascota();        // TODO add your handling code here:
    }//GEN-LAST:event_cmbCodigoClienteMMouseClicked

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
            LimpiarMascota();        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void txtBuscarMascotasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarMascotasKeyReleased
        String texto = txtBuscarMascotas.getText().trim();
        buscarYActualizarTablaMascotas(texto);// TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarMascotasKeyReleased

    private void btnAgregarMascotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarMascotaActionPerformed
         if (!"".equals(txtNombreMascota.getText())
            && !"".equals(txtRaza.getText())
            && !"0".equals(jsPeso.getValue())
            && !"".equals(txtObservaciones.getText())) {

        try {

            int codigoCliente = Integer.parseInt(
                    cmbCodigoClienteM.getSelectedItem().toString()
            );

            if (!soloLetras(txtNombreMascota.getText())) {
                JOptionPane.showMessageDialog(null, "El nombre de la mascota solo puede contener letras.");
                return;
            }
            if (!soloLetras(txtRaza.getText())) {
                JOptionPane.showMessageDialog(null, "La raza solo puede contener letras.");
                return;
            }

            // Validar si la mascota ya existe
            boolean existe = mascotadao.existsMascota(
                    txtNombreMascota.getText(),
                    codigoCliente
            );

            if (existe) {
                JOptionPane.showMessageDialog(null,
                        "La mascota ya existe para este cliente.");
                return;
            }

            mascotadto.setClientCode(codigoCliente);
            mascotadto.setNombre(txtNombreMascota.getText());
            mascotadto.setRaza(txtRaza.getText());
            mascotadto.setPeso(
                    Double.parseDouble(jsPeso.getValue().toString())
            );
            mascotadto.setObservaciones(
                    txtObservaciones.getText()
            );

            mascotadao.registerMascota(mascotadto);

            JOptionPane.showMessageDialog(null,
                    "Mascota registrada");

            LimpiarTabla();
            LimpiarMascota();
            ListarMascotas();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null,
                    "Error: " + e.getMessage());

        }

    } else {

        JOptionPane.showMessageDialog(null,
                "Los campos están vacíos");
    }
    }//GEN-LAST:event_btnAgregarMascotaActionPerformed

    private void jtableMascotaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtableMascotaMouseClicked
// TODO add your handling code here:
       try {
            int fila = jtableMascota.rowAtPoint(evt.getPoint());
            cmbCodigoClienteM.setSelectedItem(jtableMascota.getValueAt(fila, 0).toString());
            txtidMascota.setText(jtableMascota.getValueAt(fila, 1).toString());
            txtNombreMascota.setText(jtableMascota.getValueAt(fila, 2).toString());
            txtRaza.setText(jtableMascota.getValueAt(fila, 3).toString());
            jsPeso.setValue(
                Double.parseDouble(
                    jtableMascota.getValueAt(fila, 4).toString()
                )
            );
            txtObservaciones.setText(jtableMascota.getValueAt(fila, 5).toString());
            

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar la fila: " + e.getMessage());
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jtableMascotaMouseClicked

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
         if (!"".equals(txtidMascota.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar");
            if (pregunta == 0) {
                int CodigoMascota = Integer.parseInt(txtidMascota.getText());
                mascotadao.softDeleteMascota(CodigoMascota);
                LimpiarTabla();
                ListarMascotas();
                LimpiarMascota();
            }
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnEditarMascotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarMascotaActionPerformed
        if ("".equals(txtidMascota.getText())) {

         JOptionPane.showMessageDialog(null, "Seleccione una fila");

        } else {

          if (cmbCodigoClienteM.getSelectedItem() != null
            && !"".equals(txtNombreMascota.getText())
            && !"".equals(txtRaza.getText())
            && !"".equals(txtObservaciones.getText())) {

        try {

            int idMascota = Integer.parseInt(
                    txtidMascota.getText()
            );

            int codigoCliente = Integer.parseInt(
                    cmbCodigoClienteM.getSelectedItem().toString()
            );

            if (!soloLetras(txtNombreMascota.getText())) {
                JOptionPane.showMessageDialog(null, "El nombre de la mascota solo puede contener letras.");
                return;
            }
            if (!soloLetras(txtRaza.getText())) {
                JOptionPane.showMessageDialog(null, "La raza solo puede contener letras.");
                return;
            }

            // Validar si ya existe otra mascota con mismo nombre
            boolean existe = mascotadao.existsMascota(
                    txtNombreMascota.getText(),
                    codigoCliente
            );

            if (existe) {

                JOptionPane.showMessageDialog(null,
                        "Ya existe una mascota con ese nombre para este cliente.");
                return;
            }

            // Cargar nuevos datos
            mascotadto.setIdMascota(idMascota);
            mascotadto.setClientCode(codigoCliente);
            mascotadto.setNombre(txtNombreMascota.getText());
            mascotadto.setRaza(txtRaza.getText());

            mascotadto.setPeso(
                ((Number) jsPeso.getValue()).doubleValue()
            );

            mascotadto.setObservaciones(
                txtObservaciones.getText()
            );

            // Editar
            if (mascotadao.updateMascota(mascotadto)) {

                JOptionPane.showMessageDialog(
                        null,
                        "Mascota actualizada correctamente."
                );

                LimpiarTabla();
                ListarMascotas();
                LimpiarMascota();

            } else {

                JOptionPane.showMessageDialog(
                        null,
                        "Error al actualizar la mascota."
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Error: " + e.getMessage()
            );
        }

    } else {

        JOptionPane.showMessageDialog(
                null,
                "Todos los campos son obligatorios."
        );
          }
        }
    }//GEN-LAST:event_btnEditarMascotaActionPerformed

    private void btnEquipajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEquipajeActionPerformed
        jTabbedPane1.setSelectedIndex(3);
        LimpiarTabla();
        ListarEquipajes();
        LimpiarEquipaje();
        CargarClientesEnComboBoxEquipaje();
        
    }//GEN-LAST:event_btnEquipajeActionPerformed

    private void cmbCodigoClienteEMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbCodigoClienteEMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbCodigoClienteEMouseClicked

    private void txtBuscarEquipajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarEquipajeActionPerformed
        String texto = txtBuscarEquipaje.getText().trim();
        buscarYActualizarTablaEquipajes(texto);
    }//GEN-LAST:event_txtBuscarEquipajeActionPerformed

    private void txtBuscarEquipajeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarEquipajeKeyReleased
        String texto = txtBuscarEquipaje.getText().trim();
        buscarYActualizarTablaEquipajes(texto); 
    }//GEN-LAST:event_txtBuscarEquipajeKeyReleased

    private void btnAgregarEquipajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarEquipajeActionPerformed
        if (!"".equals(txtTipoEquipaje.getText())
            && !"0".equals(jsPesoEquipaje.getValue().toString())
            && !"0".equals(jsCantidadEquipaje.getValue().toString())
            && cmbCodigoClienteE.getSelectedItem() != null) {

            try {
                int codigoCliente = Integer.parseInt(cmbCodigoClienteE.getSelectedItem().toString());

                if (!soloLetras(txtTipoEquipaje.getText())) {
                    JOptionPane.showMessageDialog(null, "El tipo de equipaje solo puede contener letras.");
                    return;
                }

                equipajedto.setCodigoCliente(codigoCliente);
                equipajedto.setTipoEquipaje(txtTipoEquipaje.getText());
                equipajedto.setPeso(Double.parseDouble(jsPesoEquipaje.getValue().toString()));
                equipajedto.setCantidad(Integer.parseInt(jsCantidadEquipaje.getValue().toString()));

                equipajedao.registerEquipaje(equipajedto);
                JOptionPane.showMessageDialog(null, "Equipaje Registrado Correctamente");

                LimpiarTabla();
                ListarEquipajes();
                LimpiarEquipaje();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Los campos están vacíos o el peso/cantidad es 0");
        }
    }//GEN-LAST:event_btnAgregarEquipajeActionPerformed

    private void btnEditarEquipajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarEquipajeActionPerformed
        if ("".equals(txtidEquipaje.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila de la tabla para editar");
        } else {
            if (!"".equals(txtTipoEquipaje.getText())
                && !"0".equals(jsPesoEquipaje.getValue().toString())
                && !"0".equals(jsCantidadEquipaje.getValue().toString())
                && cmbCodigoClienteE.getSelectedItem() != null) {
                
                try {
                    int idEquipaje = Integer.parseInt(txtidEquipaje.getText());
                    int codigoCliente = Integer.parseInt(cmbCodigoClienteE.getSelectedItem().toString());

                    if (!soloLetras(txtTipoEquipaje.getText())) {
                        JOptionPane.showMessageDialog(null, "El tipo de equipaje solo puede contener letras.");
                        return;
                    }

                    equipajedto.setIdEquipaje(idEquipaje);
                    equipajedto.setCodigoCliente(codigoCliente);
                    equipajedto.setTipoEquipaje(txtTipoEquipaje.getText());
                    equipajedto.setPeso(Double.parseDouble(jsPesoEquipaje.getValue().toString()));
                    equipajedto.setCantidad(Integer.parseInt(jsCantidadEquipaje.getValue().toString()));

                    if (equipajedao.updateEquipaje(equipajedto)) {
                        JOptionPane.showMessageDialog(null, "Equipaje actualizado correctamente.");
                        LimpiarTabla();
                        ListarEquipajes();
                        LimpiarEquipaje();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al actualizar el equipaje.");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
            }
        }
    }//GEN-LAST:event_btnEditarEquipajeActionPerformed

    private void btnEliminarEquipajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEquipajeActionPerformed
        if (!"".equals(txtidEquipaje.getText())) {
            int pregunta = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar este registro de equipaje?");
            if (pregunta == 0) {
                int idEquipaje = Integer.parseInt(txtidEquipaje.getText());
                equipajedao.deleteEquipaje(idEquipaje);
                
                LimpiarTabla();
                ListarEquipajes();
                LimpiarEquipaje();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar");
        }
    }//GEN-LAST:event_btnEliminarEquipajeActionPerformed

    private void btnNuevoEquipajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoEquipajeActionPerformed
        LimpiarEquipaje();
    }//GEN-LAST:event_btnNuevoEquipajeActionPerformed

    private void jtableEquipajeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtableEquipajeMouseClicked
        try {
            int fila = jtableEquipaje.rowAtPoint(evt.getPoint());
            
            cmbCodigoClienteE.setSelectedItem(jtableEquipaje.getValueAt(fila, 0).toString());
            txtidEquipaje.setText(jtableEquipaje.getValueAt(fila, 1).toString());
            txtTipoEquipaje.setText(jtableEquipaje.getValueAt(fila, 2).toString());
            
            // CORRECCIÓN: La columna 3 es 'Cantidad' (Entero)
            jsCantidadEquipaje.setValue(Integer.parseInt(jtableEquipaje.getValueAt(fila, 3).toString()));
            
            // CORRECCIÓN: La columna 4 es 'Peso' (Decimal/Double)
            jsPesoEquipaje.setValue(Double.parseDouble(jtableEquipaje.getValueAt(fila, 4).toString()));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar la fila: " + e.getMessage());
        }
    }//GEN-LAST:event_jtableEquipajeMouseClicked

    private void cmbCodigoClienteEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCodigoClienteEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbCodigoClienteEActionPerformed

    private void btnReservaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReservaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnReservaMouseClicked

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        int pestañaSeleccionada = jTabbedPane1.getSelectedIndex();
        
        // 2. Si la pestaña es la número 3 (Equipaje), cargamos todo
        if (pestañaSeleccionada == 3) {
            ListarEquipajes();
            LimpiarEquipaje();
            CargarClientesEnComboBoxEquipaje();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void txtTipoEquipajeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTipoEquipajeKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTipoEquipajeKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sistema.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sistema().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableFacturas;
    private javax.swing.JTable TableReservarPasaje;
    private javax.swing.JButton btnActualizarFactura;
    private javax.swing.JButton btnAgregarCliente;
    private javax.swing.JButton btnAgregarEquipaje;
    private javax.swing.JButton btnAgregarFactura;
    private javax.swing.JButton btnAgregarMascota;
    private javax.swing.JButton btnAgregarReserva;
    private javax.swing.JButton btnCliente;
    private javax.swing.JButton btnEditarCliente;
    private javax.swing.JButton btnEditarEquipaje;
    private javax.swing.JButton btnEditarMascota;
    private javax.swing.JButton btnEditarReserva;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEliminarCliente;
    private javax.swing.JButton btnEliminarEquipaje;
    private javax.swing.JButton btnEliminarFactura;
    private javax.swing.JButton btnEliminarReserva;
    private javax.swing.JButton btnEquipaje;
    private javax.swing.JButton btnFactura;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnNuevaFactura;
    private javax.swing.JButton btnNuevaReserva;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnNuevoCliente;
    private javax.swing.JButton btnNuevoEquipaje;
    private javax.swing.JButton btnReserva;
    private javax.swing.JButton btnReserva1;
    private javax.swing.JComboBox<String> cmbCodigoCliente;
    private javax.swing.JComboBox<String> cmbCodigoClienteE;
    private javax.swing.JComboBox<String> cmbCodigoClienteFactura;
    private javax.swing.JComboBox<String> cmbCodigoClienteM;
    private javax.swing.JComboBox<String> cmbEstadoFactura;
    private javax.swing.JComboBox<String> cmbHoraSalida;
    private javax.swing.JComboBox<String> cmbMetodoPago;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JSpinner jsCantidadEquipaje;
    private javax.swing.JSpinner jsPeso;
    private javax.swing.JSpinner jsPesoEquipaje;
    private javax.swing.JTable jtableCliente;
    private javax.swing.JTable jtableEquipaje;
    private javax.swing.JTable jtableMascota;
    private javax.swing.JTextField txtAsientoAsignado;
    private javax.swing.JTextField txtBuscarClientes;
    private javax.swing.JTextField txtBuscarEquipaje;
    private javax.swing.JTextField txtBuscarFactura;
    private javax.swing.JTextField txtBuscarMascotas;
    private javax.swing.JTextField txtBuscarReservas;
    private javax.swing.JTextField txtCodigoCliente;
    private javax.swing.JTextField txtCodigoFactura;
    private javax.swing.JTextField txtCodigoReserva;
    private javax.swing.JTextField txtDNI;
    private javax.swing.JTextField txtDestino;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtFechaViaje;
    private javax.swing.JTextField txtFechadeEmisionFactura;
    private javax.swing.JTextField txtMontoTotal;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNombreMascota;
    private javax.swing.JTextField txtObservaciones;
    private javax.swing.JTextField txtOrigen;
    private javax.swing.JTextField txtPrecioPasaje;
    private javax.swing.JTextField txtRaza;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTipoEquipaje;
    private javax.swing.JTextField txtcodigosReservasFactura;
    private javax.swing.JTextField txtidEquipaje;
    private javax.swing.JTextField txtidMascota;
    // End of variables declaration//GEN-END:variables
private void LimpiarCliente() {
        txtCodigoCliente.setText("");
        txtDNI.setText("");
        txtNombreCliente.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");

    }

    private void LimpiarReserva() {
        txtOrigen.setText("");
        txtDestino.setText("");
        txtFechaViaje.setText("");
        cmbHoraSalida.setSelectedIndex(-1); // Deselecciona el combo
        txtPrecioPasaje.setText("");
        txtAsientoAsignado.setText("");
        cmbCodigoCliente.setSelectedIndex(-1); // Deselecciona el combo
    }
    private void LimpiarMascota(){
        txtNombreMascota.setText("");
        txtRaza.setText("");
        jsPeso.setValue(0);
        txtObservaciones.setText("");
        cmbCodigoClienteM.setSelectedIndex(-1);
    }
    
    private void LimpiarEquipaje(){
        txtTipoEquipaje.setText("");
        jsPesoEquipaje.setValue(0.25);
        jsCantidadEquipaje.setValue(1);
        cmbCodigoClienteE.setSelectedIndex(-1);
    }

    private void LimpiarFactura() {
        txtMontoTotal.setText("");
        txtcodigosReservasFactura.setText("");
        txtCodigoFactura.setText("");
        txtFechadeEmisionFactura.setText("");
        cmbMetodoPago.setSelectedIndex(0);
        cmbEstadoFactura.setSelectedIndex(-1);
        cmbCodigoClienteFactura.setSelectedIndex(-1);
    }
    
    public void ListarEquipajes() {
        // 1. Creamos un modelo completamente nuevo e independiente
        DefaultTableModel modeloLocal = new DefaultTableModel();
        
        // 2. Le forzamos los nombres de las columnas exactamente como los tienes en tu diseño
        modeloLocal.setColumnIdentifiers(new Object[]{
            "IdCliente", "IdEquipaje", "Tipo de equipaje", "Cantidad", "Peso"
        });

        // 3. Traemos los datos de la base de datos
        List<EquipajeDTO> ListarEq = equipajedao.listEquipaje();

        // 4. Llenamos el modeloLocal fila por fila
        for (int i = 0; i < ListarEq.size(); i++) {
            Object[] ob = new Object[5];
            ob[0] = ListarEq.get(i).getCodigoCliente();
            ob[1] = ListarEq.get(i).getIdEquipaje();
            ob[2] = ListarEq.get(i).getTipoEquipaje();
            ob[3] = ListarEq.get(i).getCantidad(); // Puse Cantidad en la pos 3 según tu captura
            ob[4] = ListarEq.get(i).getPeso();     // Puse Peso en la pos 4 según tu captura
            
            modeloLocal.addRow(ob);
        }
        
        // 5. Le inyectamos este nuevo modelo lleno de datos a tu JTable
        jtableEquipaje.setModel(modeloLocal);
    }

    
    private void buscarYActualizarTablaEquipajes(String filtro) {
        DefaultTableModel modeloLocal = new DefaultTableModel();
        modeloLocal.setColumnIdentifiers(new Object[]{
            "IdCliente", "IdEquipaje", "Tipo de equipaje", "Cantidad", "Peso"
        });

        List<EquipajeDTO> lista = equipajedao.searchEquipajeByClientOrType(filtro); 

        for (EquipajeDTO e : lista) {
            Object[] fila = {
                e.getCodigoCliente(), 
                e.getIdEquipaje(),
                e.getTipoEquipaje(),
                e.getCantidad(),
                e.getPeso()
            };
            modeloLocal.addRow(fila);
        }
        
        jtableEquipaje.setModel(modeloLocal);
    }
    
    public void CargarClientesEnComboBox() {
        cmbCodigoCliente.removeAllItems();
        List<ClienteDTO> listaClientes = client.listActiveClients();

        for (ClienteDTO cl : listaClientes) {
            cmbCodigoCliente.addItem(String.valueOf(cl.getClientCode()));
        }
    }
     public void CargarClientesEnComboBoxMascota() {
        cmbCodigoClienteM.removeAllItems();
        List<ClienteDTO> listaClientes = client.listActiveClients();

        for (ClienteDTO cl : listaClientes) {
            cmbCodigoClienteM.addItem(String.valueOf(cl.getClientCode()));
        }
    }
     
     public void CargarClientesEnComboBoxEquipaje() {
        cmbCodigoClienteE.removeAllItems();
        List<ClienteDTO> listaClientes = client.listActiveClients();

        for (ClienteDTO cl : listaClientes) {
            cmbCodigoClienteE.addItem(String.valueOf(cl.getClientCode()));
        }
    }

    public void CargarClientesEnComboBoxClienteFactura() {
        cmbCodigoClienteFactura.removeAllItems();
        List<ClienteDTO> listaClientes = client.listActiveClients();

        for (ClienteDTO cl : listaClientes) {
            cmbCodigoClienteFactura.addItem(String.valueOf(cl.getClientCode()));
        }
    }

    private void buscarYActualizarTabla(String filtro) {
        List<ClienteDTO> lista = client.searchClientsByCodeIdName(filtro);

        DefaultTableModel model = (DefaultTableModel) jtableCliente.getModel();
        model.setRowCount(0); // limpiar tabla

        for (ClienteDTO c : lista) {
            Object[] fila = {
                c.getClientCode(),
                c.getIdentificationNumber(),
                c.getFullName(),
                c.getPhoneNumber(),
                c.getAddress()
            };
            model.addRow(fila);
        }
    }

    private void buscarYActualizarTablaReservas(String filtro) {
        List<ReservaDTO> lista = reservadao.searchReservationsByClientOrCode(filtro);

        DefaultTableModel model = (DefaultTableModel) TableReservarPasaje.getModel();
        model.setRowCount(0); // limpiar tabla

        for (ReservaDTO r : lista) {
            Object[] fila = {
                r.getReservationCode(),
                r.getClientCode(),
                r.getOriginCity(),
                r.getDestinationCity(),
                r.getTravelDate(),
                r.getDepartureTime(),
                r.getAssignedSeat(),
                r.getTicketPrice()

            };
            model.addRow(fila);
        }
    }
    private void buscarYActualizarTablaMascotas(String filtro) {

        List<MascotaDTO> lista = mascotadao.searchMascotasByClientOrName(filtro);

        DefaultTableModel model = (DefaultTableModel) jtableMascota.getModel();

        model.setRowCount(0); // limpiar tabla

        for (MascotaDTO m : lista) {

            Object[] fila = {
                m.getClientCode(),
                m.getIdMascota(),
                m.getNombre(),
                m.getRaza(),
                m.getPeso(),
                m.getObservaciones()
            };

            model.addRow(fila);
        }
    }
    private void buscarYActualizarTablaFacturas(String filtro) {
        List<FacturaDTO> lista = facturadao.buscarFacturas(filtro);
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Nº Factura", "Reservas incluidas", "Cód. Cliente", "Fecha de emisión", "Monto total ($)", "Método de pago", "Estado de factura"});
        for (FacturaDTO f : lista) {
            String reservas = facturadao.getReservationCodesForInvoice(f.getInvoiceCode());
            model.addRow(new Object[]{
                f.getInvoiceCode(),
                reservas,
                f.getClientCode(),
                f.getIssueDate(),
                f.getTotalAmount(),
                f.getPaymentMethod(),
                f.getInvoiceStatus()
            });
        }
        TableFacturas.setModel(model);
    }

    private List<Integer> obtenerReservasSeleccionadas() {
        Object item = cmbCodigoClienteFactura.getSelectedItem();

        if (item == null || item.toString().isEmpty()) {
            return Collections.emptyList();
        }

        try {
            int codigoCliente = Integer.parseInt(item.toString());
            return facturadao.getUnbilledReservationsForClient(codigoCliente);
        } catch (NumberFormatException e) {
            return Collections.emptyList();
        }
    }

    private void pdf() {
        if ("".equals(txtCodigoFactura.getText())) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        } else {
            try {
                String dest = "src/main/java/pdf/factura" + txtCodigoFactura.getText() + ".pdf";
                File file = new File(dest);
                file.getParentFile().mkdirs(); // Crea los directorios si no existen

                PdfWriter writer = new PdfWriter(dest);
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);

                // Imagen
                ImageData imageData = ImageDataFactory.create("src/main/resources/LogoP.png");
                Image img = new Image(imageData).scaleToFit(80, 80);

                // Datos del encabezado
                String ruc = "1212121212001";
                String nom = "Grupo 6";
                String tel = "094848484";
                String dir = "Ecuador";

                // Fecha
                Paragraph fecha = new Paragraph("Factura: "+txtCodigoFactura.getText()+"\nFecha: " + txtFechadeEmisionFactura.getText())
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(11)
                        .setFontColor(ColorConstants.BLUE);

                // Tabla encabezado
                Table table = new Table(UnitValue.createPercentArray(new float[]{2, 1, 4, 2}))
                        .useAllAvailableWidth();
                table.addCell(new Cell().add(img).setBorder(null));
                table.addCell(new Cell().add(new Paragraph("")).setBorder(null));
                table.addCell(new Cell().add(new Paragraph("Ruc: " + ruc + "\nNombre: " + nom + "\nTeléfono: " + tel + "\nDirección: " + dir)).setBorder(null));
                table.addCell(new Cell().add(fecha).setTextAlignment(TextAlignment.RIGHT).setBorder(null));

                document.add(table);

                // Datos del cliente
                document.add(new Paragraph("\nDatos del cliente:\n")
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(12));

                // Tabla del cliente
                Table tablaCli = new Table(UnitValue.createPercentArray(new float[]{2, 4, 3, 4}))
                        .useAllAvailableWidth();
                int codigoCliente = Integer.parseInt(cmbCodigoClienteFactura.getSelectedItem().toString());

                ClienteDTO cliente = new ClienteDAO().getClientByCode(codigoCliente);

                tablaCli.addHeaderCell(new Cell().add(new Paragraph("DNI").setBold()));
                tablaCli.addHeaderCell(new Cell().add(new Paragraph("Nombre").setBold()));
                tablaCli.addHeaderCell(new Cell().add(new Paragraph("Teléfono").setBold()));
                tablaCli.addHeaderCell(new Cell().add(new Paragraph("Dirección").setBold()));

                tablaCli.addCell(new Cell().add(new Paragraph(cliente.getIdentificationNumber())));
                tablaCli.addCell(new Cell().add(new Paragraph(cliente.getFullName())));
                tablaCli.addCell(new Cell().add(new Paragraph(cliente.getPhoneNumber())));
                tablaCli.addCell(new Cell().add(new Paragraph(cliente.getAddress())));

                document.add(tablaCli);

                // Tabla de reserva
                Table tablaReserva = new Table(UnitValue.createPercentArray(new float[]{4, 4, 3, 3, 3, 3}))
                        .useAllAvailableWidth();

                tablaReserva.addHeaderCell(new Cell().add(new Paragraph("Origen").setBold()));
                tablaReserva.addHeaderCell(new Cell().add(new Paragraph("Destino").setBold()));
                tablaReserva.addHeaderCell(new Cell().add(new Paragraph("Fecha Viaje").setBold()));
                tablaReserva.addHeaderCell(new Cell().add(new Paragraph("Hora Salida").setBold()));
                tablaReserva.addHeaderCell(new Cell().add(new Paragraph("Asiento").setBold()));
                tablaReserva.addHeaderCell(new Cell().add(new Paragraph("Precio").setBold()));

                // Suponiendo que txtcodigosReservasFactura contiene: "34,23,23"
                String codigosTexto = txtcodigosReservasFactura.getText(); // "34,23,23"

// Convertir a lista de enteros
                List<Integer> codigosreservas = Arrays.stream(codigosTexto.split(","))
                        .map(String::trim)
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                List<ReservaDTO> reservas = new ReservaDAO().getReservationsByCodes(codigosreservas);

                double total = 0.0;
                for (ReservaDTO r : reservas) {
                    tablaReserva.addCell(new Cell().add(new Paragraph(r.getOriginCity())));
                    tablaReserva.addCell(new Cell().add(new Paragraph(r.getDestinationCity())));
                    tablaReserva.addCell(new Cell().add(new Paragraph(r.getTravelDate().toString())));
                    tablaReserva.addCell(new Cell().add(new Paragraph(r.getDepartureTime())));
                    tablaReserva.addCell(new Cell().add(new Paragraph(r.getAssignedSeat())));
                    tablaReserva.addCell(new Cell().add(new Paragraph(String.format("%.2f", r.getTicketPrice()))));

                    total += r.getTicketPrice();
                }

// 7. Añadir tabla de reservas al documento PDF
                document.add(new Paragraph("\nDetalle de reservas:\n")
                        .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                        .setFontSize(12));
                document.add(tablaReserva);
                
                
                
                
                // NUEVO: Método de Pago y Estado de Factura
            String metodoPago = cmbMetodoPago.getSelectedItem().toString();
            String estadoFactura = cmbEstadoFactura.getSelectedItem().toString();

            document.add(new Paragraph("\nDetalles de la factura:\n")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                    .setFontSize(12));

            Table tablaDetalles = new Table(UnitValue.createPercentArray(new float[]{3, 3}))
                    .useAllAvailableWidth();

            tablaDetalles.addCell(new Cell().add(new Paragraph("Método de Pago").setBold()));
            tablaDetalles.addCell(new Cell().add(new Paragraph("Estado de Factura").setBold()));

            tablaDetalles.addCell(new Cell().add(new Paragraph(metodoPago)));
            tablaDetalles.addCell(new Cell().add(new Paragraph(estadoFactura)));

            document.add(tablaDetalles);
                
                
                
                
                
                

// 8. Añadir total
                document.add(new Paragraph("\nMonto Total: $" + String.format("%.2f", total))
                        .setFontSize(11)
                        .setBold());

                document.close();
                System.out.println("PDF generado correctamente.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
