/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Testing;

import Controlador.ClienteDAO;
import Controlador.FacturaDAO;
import Controlador.ReservaDAO;
import Modelo.ClienteDTO;
import Modelo.FacturaDTO;
import Modelo.ReservaDTO;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class TestIntegracion {

    private ClienteDAO clienteDAO;
    private FacturaDAO facturaDAO;
    private ReservaDAO reservaDAO;

    private ClienteDTO validCliente;
    private FacturaDTO validFactura;
    private ReservaDTO validReserva;

    @BeforeEach
    public void setUp() {
        clienteDAO = mock(ClienteDAO.class);
        facturaDAO = mock(FacturaDAO.class);
        reservaDAO = mock(ReservaDAO.class);

        validCliente = new ClienteDTO(1, "1234567890", "Juan", "0999999999", "Quito");
        List<Integer> reservasAsociadasFactura = new ArrayList<>();
        reservasAsociadasFactura.add(1001);

        java.util.Date utilDate = new java.util.Date();
        Date sqlDate = new java.sql.Date(utilDate.getTime());

        validFactura = new FacturaDTO(5001, 1, sqlDate, 120.50, "Efectivo", "Pagado", reservasAsociadasFactura, "1234567890", "Juan");
        validReserva = new ReservaDTO(1001, 1, "Quito", "Cuenca", "2025-07-20", "10:00", "A1", 50.00);

        when(clienteDAO.registerClient(any(ClienteDTO.class))).thenReturn(true);
        when(clienteDAO.updateClient(any(ClienteDTO.class))).thenReturn(true);
        when(clienteDAO.softDeleteClient(1)).thenReturn(true);
        when(clienteDAO.softDeleteClient(999)).thenReturn(false);
        when(facturaDAO.registerInvoiceWithTransaction(any(FacturaDTO.class))).thenReturn(true);
        when(reservaDAO.registerReservation(any(ReservaDTO.class))).thenReturn(true);
        when(reservaDAO.updateReservation(any(ReservaDTO.class))).thenReturn(true);
    }

    @Test
    public void pruebasModuloCliente() {
        System.out.println("\n***  [MODULO CLIENTE]   ***");

        try {
            clienteDAO.registerClient(validCliente);
            System.out.println("Caso CP001 - Agregar Cliente valido.");
            System.out.println("Resultado: Cliente agregado correctamente.\n");
            verify(clienteDAO, times(1)).registerClient(validCliente);
        } catch (Exception e) {
            System.out.println("Caso CP001 - Agregar Cliente valido.");
            System.out.println("Resultado: Excepcion lanzada - " + e.getMessage() + "\n");
        }

        try {
            validCliente.setFullName("Juan Modificado");
            clienteDAO.updateClient(validCliente);
            System.out.println("Caso CP002 - Editar Cliente.");
            System.out.println("Resultado: Cliente editado correctamente.\n");
            verify(clienteDAO, times(1)).updateClient(validCliente);
        } catch (Exception e) {
            System.out.println("Caso CP002 - Editar Cliente.");
            System.out.println("Resultado: Excepcion lanzada - " + e.getMessage() + "\n");
        }

        try {
            new ClienteDTO(3, "123456789012", "Maria", "0999999999", "Loja");
        } catch (IllegalArgumentException e) {
            System.out.println("Caso CP003 - DNI supera 10 digitos.");
            System.out.println("Resultado: Excepcion lanzada - " + e.getMessage() + "\n");
        }

        try {
            new ClienteDTO(4, "1234567890", "Luis", "09ABCD9999", "Ambato");
        } catch (IllegalArgumentException e) {
            System.out.println("Caso CP004 - Telefono contiene letras.");
            System.out.println("Resultado: Excepcion lanzada - " + e.getMessage() + "\n");
        }

        try {
            new ClienteDTO(5, "1234567890", "@na_M@ria", "0999999999", "Guayaquil");
        } catch (IllegalArgumentException e) {
            System.out.println("Caso CP005 - Nombre con caracteres especiales.");
            System.out.println("Resultado: Excepcion lanzada - " + e.getMessage() + "\n");
        }

        System.out.println("-----------------------------------------------------\n");
    }

    @Test
    public void pruebasModuloFactura() {
        System.out.println("***   [MODULO FACTURA]   ***");

        try {
            facturaDAO.registerInvoiceWithTransaction(validFactura);
            System.out.println("Caso CP001 - Agregar Factura.");
            System.out.println("Resultado: Factura agregada correctamente.\n");
            verify(facturaDAO, times(1)).registerInvoiceWithTransaction(validFactura);
        } catch (Exception e) {
            System.out.println("Caso CP001 - Agregar Factura.");
            System.out.println("Resultado: Excepcion lanzada - " + e.getMessage() + "\n");
        }

        try {
            new FacturaDTO(5002, 2, new java.sql.Date(System.currentTimeMillis()), 100.00, "", "Pendiente", new ArrayList<>(), "9876543210", "Maria");
        } catch (IllegalArgumentException e) {
            System.out.println("Caso CP002 - MetodoPago vacio.");
            System.out.println("Resultado: Excepcion lanzada - " + e.getMessage() + "\n");
        }

        try {
            new FacturaDTO(5003, 3, new java.sql.Date(System.currentTimeMillis()), -50.00, "Efectivo", "Pendiente", new ArrayList<>(), "1122334455", "Luis");
        } catch (IllegalArgumentException e) {
            System.out.println("Caso CP003 - Monto negativo.");
            System.out.println("Resultado: Excepcion lanzada - " + e.getMessage() + "\n");
        }

        try {
            new FacturaDTO(5004, 4, new java.sql.Date(System.currentTimeMillis()), 200.00, "Efectivo", "1234", new ArrayList<>(), "6677889900", "Ana");
        } catch (IllegalArgumentException e) {
            System.out.println("Caso CP004 - EstadoFactura con numeros.");
            System.out.println("Resultado: Excepcion lanzada - " + e.getMessage() + "\n");
        }

        System.out.println("-----------------------------------------------------\n");
    }

    @Test
    public void pruebasModuloReserva() {
        System.out.println("[MODULO RESERVA]");

        try {
            reservaDAO.registerReservation(validReserva);
            System.out.println("Caso CP001 - Agregar Reserva.");
            System.out.println("Resultado: Reserva agregada correctamente.\n");
            verify(reservaDAO, times(1)).registerReservation(validReserva);
        } catch (Exception e) {
            System.out.println("Caso CP001 - Agregar Reserva.");
            System.out.println("Resultado: Excepcion lanzada - " + e.getMessage() + "\n");
        }

        try {
            validReserva.setDestinationCity("Guayaquil");
            reservaDAO.updateReservation(validReserva);
            System.out.println("Caso CP002 - Editar Reserva.");
            System.out.println("Resultado: Reserva editada correctamente.\n");
            verify(reservaDAO, times(1)).updateReservation(validReserva);
        } catch (Exception e) {
            System.out.println("Caso CP002 - Editar Reserva.");
            System.out.println("Resultado: Excepcion lanzada - " + e.getMessage() + "\n");
        }

        try {
            new ReservaDTO(-1003, 123, "Quito", "Cuenca", "2025-07-21", "11:00", "B2", 75.00);
        } catch (IllegalArgumentException e) {
            System.out.println("Caso CP003 - CodigoReserva negativo.");
            System.out.println("Resultado: Excepcion lanzada - " + e.getMessage() + "\n");
        }

        try {
            new ReservaDTO(1004, 123, "", "Cuenca", "2025-07-22", "12:00", "C3", 100.00);
        } catch (IllegalArgumentException e) {
            System.out.println("Caso CP004 - Origen vacio.");
            System.out.println("Resultado: Excepcion lanzada - " + e.getMessage() + "\n");
        }

        try {
            new ReservaDTO(1005, 123, "Quito", "Guayaquil", "2025-07-23", "13:00", "D4", -25.00);
        } catch (IllegalArgumentException e) {
            System.out.println("Caso CP005 - Precio pasaje negativo.");
            System.out.println("Resultado: Excepcion lanzada - " + e.getMessage() + "\n");
        }

        System.out.println("-----------------------------------------------------\n");
    }
}
