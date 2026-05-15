/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Testing.PruebasUnitarias.MetodosConstructores;

import Controlador.ClienteDAO;
import Controlador.FacturaDAO;
import Controlador.LoginDAO;
import Controlador.ReservaDAO;
import Modelo.ClienteDTO;
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
public class Cliente {
    
    


    @BeforeAll
    public static void beforeAll() {
        System.out.println("==> Iniciando pruebas");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("==> Pruebas finalizadas.");
    }

   
    
    
     //Modulo cliente
     // TC01: Constructor válido
    @Test
    public void TC01_constructorValido() {
        try {
            ClienteDTO cliente = new ClienteDTO(
                    1,
                    "0123456789",
                    "Juan Perez",
                    "0991234567",
                    "Calle 123"
            );
            
            // Si no lanza excepción
            System.out.println("TC01_constructorValido:  Prueba EXITOSA");
            assertNotNull(cliente);
            assertEquals(1, cliente.getClientCode());
            assertEquals("0123456789", cliente.getIdentificationNumber());
            assertEquals("Juan Perez", cliente.getFullName());
            assertEquals("0991234567", cliente.getPhoneNumber());
            assertEquals("Calle 123", cliente.getAddress());
        } catch (Exception e) {
            System.out.println("TC01_constructorValido:  Prueba FALLIDA (" + e.getMessage() + ")");
            fail("No debía lanzar excepción");
        }
    }

    // TC02: Código inválido
    @Test
    public void TC02_codigoInvalido() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new ClienteDTO(0, "0123456789", "Juan Perez", "0991234567", "Calle 123");
        });
        if (ex.getMessage().equals("El código debe ser mayor que cero.")) {
            System.out.println("TC02_codigoInvalido:  Prueba EXITOSA");
        } else {
            System.out.println("TC02_codigoInvalido:  Prueba FALLIDA (mensaje inesperado)");
        }
    }

    // TC03: Nombre vacío
    @Test
    public void TC03_nombreVacio() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new ClienteDTO(1, "0123456789", "", "0991234567", "Calle 123");
        });
        if (ex.getMessage().equals("El nombre no puede estar vacío.")) {
            System.out.println("TC03_nombreVacio:  Prueba EXITOSA");
        } else {
            System.out.println("TC03_nombreVacio:  Prueba FALLIDA");
        }
    }

    // TC04: Nombre con números
    @Test
    public void TC04_nombreConNumeros() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new ClienteDTO(1, "0123456789", "Juan123", "0991234567", "Calle 123");
        });
        if (ex.getMessage().equals("El nombre no debe contener números.")) {
            System.out.println("TC04_nombreConNumeros:  Prueba EXITOSA");
        } else {
            System.out.println("TC04_nombreConNumeros:  Prueba FALLIDA");
        }
    }

    // TC05: DNI vacío
    @Test
    public void TC05_dniVacio() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new ClienteDTO(1, "", "Juan Perez", "0991234567", "Calle 123");
        });
        if (ex.getMessage().equals("El DNI no puede estar vacío.")) {
            System.out.println("TC05_dniVacio: Prueba EXITOSA");
        } else {
            System.out.println("TC05_dniVacio: Prueba FALLIDA");
        }
    }

    // TC06: DNI inválido
    @Test
    public void TC06_dniInvalidoMenosDe10() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new ClienteDTO(1, "12345", "Juan Perez", "0991234567", "Calle 123");
        });
        if (ex.getMessage().equals("El DNI debe tener exactamente 10 dígitos.")) {
            System.out.println("TC06_dniInvalidoMenosDe10:  Prueba EXITOSA");
        } else {
            System.out.println("TC06_dniInvalidoMenosDe10:  Prueba FALLIDA");
        }
    }

    // TC07: Teléfono vacío
    @Test
    public void TC07_telefonoVacio() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new ClienteDTO(1, "0123456789", "Juan Perez", "", "Calle 123");
        });
        if (ex.getMessage().equals("El teléfono no puede estar vacío.")) {
            System.out.println("TC07_telefonoVacio: Prueba EXITOSA");
        } else {
            System.out.println("TC07_telefonoVacio: Prueba FALLIDA");
        }
    }

    // TC08: Teléfono inválido
    @Test
    public void TC08_telefonoInvalido() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new ClienteDTO(1, "0123456789", "Juan Perez", "09912", "Calle 123");
        });
        if (ex.getMessage().equals("El teléfono debe contener solo 10 dígitos.")) {
            System.out.println("TC08_telefonoInvalido:  Prueba EXITOSA");
        } else {
            System.out.println("TC08_telefonoInvalido:  Prueba FALLIDA");
        }
    }

    // TC09: Dirección vacía
    @Test
    public void TC09_direccionVacia() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new ClienteDTO(1, "0123456789", "Juan Perez", "0991234567", "");
        });
        if (ex.getMessage().equals("La dirección no puede estar vacía.")) {
            System.out.println("TC09_direccionVacia: Prueba EXITOSA");
        } else {
            System.out.println("TC09_direccionVacia: Prueba FALLIDA");
        }
    }
}
