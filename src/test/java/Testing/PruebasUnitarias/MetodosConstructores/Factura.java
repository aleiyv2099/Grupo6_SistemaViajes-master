/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Testing.PruebasUnitarias.MetodosConstructores;

import Controlador.ClienteDAO;
import Controlador.FacturaDAO;
import Controlador.LoginDAO;
import Controlador.ReservaDAO;
import Modelo.FacturaDTO;
import java.sql.Date;
import java.util.Arrays;
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
    



    @BeforeAll
    public static void beforeAll() {
        System.out.println("==> Iniciando pruebas");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("==> Pruebas finalizadas.");
    }

  
     //Modulo factura
    //Metodo constructor
    @Test
    public void TC01_constructorFacturaValido() {
        try {
            FacturaDTO factura = new FacturaDTO(
                1, 1, new Date(System.currentTimeMillis()), 100.0,
                "Tarjeta", "Activa", Arrays.asList(1, 2),
                "0123456789", "Juan Perez"
            );
            System.out.println("TC01_constructorValido: PRUEBA CORRECTA");
        } catch (Exception e) {
            System.out.println("TC01_constructorValido: PRUEBA FALLIDA - " + e.getMessage());
            fail("No debería lanzar excepción");
        }
    }

    @Test
    public void TC02_codigoFacturaInvalido() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new FacturaDTO(
                0, 1, new Date(System.currentTimeMillis()), 100.0,
                "Tarjeta", "Activa", Arrays.asList(1, 2),
                "0123456789", "Juan Perez"
            );
        });
        if ("El código de factura debe ser mayor que cero.".equals(exception.getMessage())) {
            System.out.println("TC02_codigoFacturaInvalido: PRUEBA CORRECTA");
        } else {
            System.out.println("TC02_codigoFacturaInvalido: PRUEBA FALLIDA");
        }
    }

    @Test
    public void TC03_codigoClienteInvalido() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new FacturaDTO(
                1, 0, new Date(System.currentTimeMillis()), 100.0,
                "Tarjeta", "Activa", Arrays.asList(1, 2),
                "0123456789", "Juan Perez"
            );
        });
        if ("El código de cliente debe ser mayor que cero.".equals(exception.getMessage())) {
            System.out.println("TC03_codigoClienteInvalido: PRUEBA CORRECTA");
        } else {
            System.out.println("TC03_codigoClienteInvalido: PRUEBA FALLIDA");
        }
    }

    @Test
    public void TC04_fechaEmisionNula() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new FacturaDTO(
                1, 1, null, 100.0,
                "Tarjeta", "Activa", Arrays.asList(1, 2),
                "0123456789", "Juan Perez"
            );
        });
        if ("La fecha de emisión no puede ser nula.".equals(exception.getMessage())) {
            System.out.println("TC04_fechaEmisionNula: PRUEBA CORRECTA");
        } else {
            System.out.println("TC04_fechaEmisionNula: PRUEBA FALLIDA");
        }
    }

    @Test
    public void TC05_montoTotalNegativo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new FacturaDTO(
                1, 1, new Date(System.currentTimeMillis()), -1.0,
                "Tarjeta", "Activa", Arrays.asList(1, 2),
                "0123456789", "Juan Perez"
            );
        });
        if ("El monto total no puede ser negativo.".equals(exception.getMessage())) {
            System.out.println("TC05_montoTotalNegativo: PRUEBA CORRECTA");
        } else {
            System.out.println("TC05_montoTotalNegativo: PRUEBA FALLIDA");
        }
    }

    @Test
    public void TC06_metodoPagoVacio() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new FacturaDTO(
                1, 1, new Date(System.currentTimeMillis()), 100.0,
                "", "Activa", Arrays.asList(1, 2),
                "0123456789", "Juan Perez"
            );
        });
        if ("El método de pago no puede estar vacío.".equals(exception.getMessage())) {
            System.out.println("TC06_metodoPagoVacio: PRUEBA CORRECTA");
        } else {
            System.out.println("TC06_metodoPagoVacio: PRUEBA FALLIDA");
        }
    }

    @Test
    public void TC07_estadoFacturaVacio() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new FacturaDTO(
                1, 1, new Date(System.currentTimeMillis()), 100.0,
                "Tarjeta", "", Arrays.asList(1, 2),
                "0123456789", "Juan Perez"
            );
        });
        if ("El estado de la factura no puede estar vacío.".equals(exception.getMessage())) {
            System.out.println("TC07_estadoFacturaVacio: PRUEBA CORRECTA");
        } else {
            System.out.println("TC07_estadoFacturaVacio: PRUEBA FALLIDA");
        }
    }
}
