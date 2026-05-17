package Testing;

import Controlador.FacturaDAO;
import Modelo.FacturaDTO;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

public class TestComplejidadCiclomatica {

    // Caso Correcto 1 — Lista null retorna 0.0 (camino C1)
    @Test
    public void test_calculateTotal_listaNull_retornaCero() {
        FacturaDAO dao = new FacturaDAO();
        double resultado = dao.calculateTotalAmount(null);
        assertEquals(0.0, resultado, 0.001,
                "Una lista null debe retornar 0.0 sin lanzar excepción");
    }

    // Caso Correcto 2 — Lista vacía retorna 0.0 (camino C2)
    @Test
    public void test_calculateTotal_listaVacia_retornaCero() {
        FacturaDAO dao = new FacturaDAO();
        double resultado = dao.calculateTotalAmount(new ArrayList<>());
        assertEquals(0.0, resultado, 0.001, "Una lista vacía debe retornar 0.0");
    }

    // Caso Error 1 — Lista null no retorna monto positivo
    @Test
    public void test_calculateTotal_listaNull_noRetornaMonto() {
        FacturaDAO dao = new FacturaDAO();
        double resultado = dao.calculateTotalAmount(null);
        assertFalse(resultado > 0, "Lista null no debe generar monto positivo");
    }

    // Caso Error 2 — Lista vacía no retorna valor negativo
    @Test
    public void test_calculateTotal_listaVacia_noRetornaNegativo() {
        FacturaDAO dao = new FacturaDAO();
        double resultado = dao.calculateTotalAmount(new ArrayList<>());
        assertTrue(resultado >= 0, "El resultado nunca debe ser negativo");
    }

    // ── registerInvoiceWithTransaction ──────────────────────────────

    // Caso Correcto 1 — Factura válida retorna true
    @Test
    public void test_registerInvoice_facturaValida_retornaTrue() {
        FacturaDAO dao = new FacturaDAO();
        FacturaDTO factura = new FacturaDTO();
        factura.setIssueDate(new Date(System.currentTimeMillis()));
        factura.setTotalAmount(150.0);
        factura.setPaymentMethod("Tarjeta");
        factura.setInvoiceStatus("Pendiente");
        factura.setClientCode(54);
        factura.setReservationList(Arrays.asList(57));

        boolean resultado = dao.registerInvoiceWithTransaction(factura);
        assertTrue(resultado, "Una factura con datos válidos debe registrarse correctamente");
    }

    // Caso Error 1 — Monto negativo lanza IllegalArgumentException
    @Test
    public void test_registerInvoice_montoNegativo_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
            new FacturaDTO(1, 54, new Date(System.currentTimeMillis()), -100.0,
                    "Tarjeta", "Activa", Arrays.asList(57), "1234567890", "Juan Perez"),
            "Un monto negativo debe lanzar IllegalArgumentException");
    }

    // Caso Error 2 — Lista de reservas nula lanza IllegalArgumentException
    @Test
    public void test_registerInvoice_sinReservas_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
            new FacturaDTO(1, 54, new Date(System.currentTimeMillis()), 100.0,
                    "Tarjeta", "Activa", null, "1234567890", "Juan Perez"),
            "Una lista de reservas nula debe lanzar IllegalArgumentException");
    }

    // Caso Error 3 — Estado con números lanza IllegalArgumentException
    @Test
    public void test_registerInvoice_estadoConNumeros_lanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
            new FacturaDTO(1, 54, new Date(System.currentTimeMillis()), 100.0,
                    "Tarjeta", "Activa123", Arrays.asList(57), "1234567890", "Juan Perez"),
            "Un estado con números debe lanzar IllegalArgumentException");
    }
}
