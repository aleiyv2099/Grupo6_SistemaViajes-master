/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Testing.PruebasUnitarias.MetodosInstancia;

import Controlador.ClienteDAO;
import Controlador.FacturaDAO;
import Controlador.LoginDAO;
import Controlador.ReservaDAO;
import Modelo.FacturaDTO;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author MiniWernaso
 */
public class Factura {
    FacturaDAO facturaDAO = new FacturaDAO();


    @BeforeAll
    public static void beforeAll() {
        System.out.println("==> Iniciando pruebas");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("==> Pruebas finalizadas.");
    }

    
    //Factura    
// Caso TC01: Registrar factura con datos válidos
    @Test
    public void TF01_registrarFacturaValida() {
        FacturaDTO factura = new FacturaDTO();
        factura.setIssueDate(new java.sql.Date(System.currentTimeMillis()));

        factura.setTotalAmount(200.0);
        factura.setPaymentMethod("Tarjeta");
        factura.setInvoiceStatus("Pendiente");
        factura.setClientCode(54);  // Cliente válido en BD
        factura.setReservationList(Arrays.asList(57));  // Reservas válidas y activas

        boolean resultado = facturaDAO.registerInvoiceWithTransaction(factura);

        if (resultado) {
            System.out.println("TC01_registrarFacturaValida: Prueba CORRECTA (factura registrada)");
        } else {
            System.out.println("TC01_registrarFacturaValida: Prueba FALLIDA (no se registró la factura)");
        }

        assertTrue(resultado, "Debe registrar correctamente una factura válida");
    }

  

    // Caso TC02: Registrar factura con lista de reservas vacía o nula
    @Test
    public void TF02_registrarFacturaReservasVacias() {
        FacturaDTO factura = new FacturaDTO();
        factura.setIssueDate(new java.sql.Date(System.currentTimeMillis()));

        factura.setTotalAmount(100.0);
        factura.setPaymentMethod("Transferencia");
        factura.setInvoiceStatus("Activa");
        factura.setClientCode(55);  // Cliente válido
        factura.setReservationList(null);    // Lista de reservas nula

        boolean resultado = facturaDAO.registerInvoiceWithTransaction(factura);

        if (resultado) {
            System.out.println("TC02_registrarFacturaReservasVacias: Prueba CORRECTA (manejó lista de reservas nula)");
        } else {
            System.out.println("TC02_registrarFacturaReservasVacias: Prueba FALLIDA (no manejó lista nula correctamente)");
        }

        assertTrue(resultado, "Debe manejar lista de reservas vacía o nula sin excepción");
    }
     // Caso TC01: Eliminar factura existente
    @Test
    public void Tc01_eliminarFacturaExistente() {
        int codigoFacturaValido = 25;

        boolean resultado = facturaDAO.softDeleteInvoice(codigoFacturaValido);

        if (resultado) {
            System.out.println("TC01_eliminarFacturaExistente: Prueba CORRECTA (factura desactivada)");
        } else {
            System.out.println("TC01_eliminarFacturaExistente: Prueba FALLIDA (no se desactivó factura)");
        }

        assertTrue(resultado, "Debe eliminar (desactivar) correctamente una factura existente");
    }

    // Caso TC02: Eliminar factura inexistente
    @Test
    public void TC02_eliminarFacturaInexistente() {
        int codigoFacturaInexistente = 99999; // No existe en BD

        boolean resultado = facturaDAO.softDeleteInvoice(codigoFacturaInexistente);

        if (resultado) {
            System.out.println("TC02_eliminarFacturaInexistente: Prueba CORRECTA (ejecutó query sin afectar filas)");
        } else {
            System.out.println("TC02_eliminarFacturaInexistente: Prueba FALLIDA (no esperaba fallo)");
        }

        assertTrue(resultado, "Eliminar factura inexistente debe retornar true (query ejecutada)");
    }

    // Caso TC03: Eliminar factura con código negativo o cero
    @Test
    public void TC03_eliminarFacturaCodigoInvalido() {
        int codigoFacturaInvalido = -5;

        boolean resultado = facturaDAO.softDeleteInvoice(codigoFacturaInvalido);

        if (resultado) {
            System.out.println("TC03_eliminarFacturaCodigoInvalido: Prueba CORRECTA (query ejecutada sin error con código inválido)");
        } else {
            System.out.println("TC03_eliminarFacturaCodigoInvalido: Prueba FALLIDA (se esperaba true)");
        }

        assertTrue(resultado, "Eliminar factura con código inválido debe retornar true (sin excepción)");
    }

    
    // Caso TC01: Calcular monto con reservas válidas
    @Test
    public void TC01_calcularMontoReservasValidas() {
        double total = facturaDAO.calculateTotalAmount(Arrays.asList(69, 60)); // IDs válidos

        if (total > 0) {
            System.out.println("TC01_calcularMontoReservasValidas: Prueba CORRECTA (total = " + total + ")");
        } else {
            System.out.println("TC01_calcularMontoReservasValidas: Prueba FALLIDA (total esperado > 0)");
        }

        assertTrue(total > 0, "El monto total debe ser mayor que 0 para reservas válidas");
    }

    // Caso TC02: Calcular monto con lista vacía
    @Test
    public void TC02_calcularMontoListaVacia() {
        double total = facturaDAO.calculateTotalAmount(Collections.emptyList());

        if (total == 0.0) {
            System.out.println("TC02_calcularMontoListaVacia: Prueba CORRECTA (total = 0)");
        } else {
            System.out.println("TC02_calcularMontoListaVacia: Prueba FALLIDA (total esperado 0)");
        }

        assertEquals(0.0, total, 0.001, "El monto total debe ser 0 para una lista vacía");
    }

    // Caso TC03: Calcular monto con reservas inexistentes
    @Test
    public void TC03_calcularMontoReservasInexistentes() {
        double total = facturaDAO.calculateTotalAmount(Arrays.asList(9999, 8888)); // IDs inexistentes

        if (total == 0.0) {
            System.out.println("TC03_calcularMontoReservasInexistentes: Prueba CORRECTA (total = 0)");
        } else {
            System.out.println("TC03_calcularMontoReservasInexistentes: Prueba FALLIDA (total esperado 0)");
        }

        assertEquals(0.0, total, 0.001, "El monto total debe ser 0 si no hay reservas válidas");
    }

    // Caso TC04: Calcular monto con lista nula
    @Test
    public void TC04_calcularMontoListaNula() {
        double total = facturaDAO.calculateTotalAmount(null);

        if (total == 0.0) {
            System.out.println("TC04_calcularMontoListaNula: Prueba CORRECTA (total = 0)");
        } else {
            System.out.println("TC04_calcularMontoListaNula: Prueba FALLIDA (total esperado 0)");
        }

        assertEquals(0.0, total, 0.001, "El monto total debe ser 0 si la lista es nula");
    }
}
