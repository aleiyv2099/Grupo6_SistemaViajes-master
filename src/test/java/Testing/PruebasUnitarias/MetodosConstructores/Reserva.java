/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Testing.PruebasUnitarias.MetodosConstructores;

import Controlador.ClienteDAO;
import Controlador.FacturaDAO;
import Controlador.LoginDAO;
import Controlador.ReservaDAO;
import Modelo.ReservaDTO;
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
public class Reserva {
    
 


    @BeforeAll
    public static void beforeAll() {
        System.out.println("==> Iniciando pruebas");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("==> Pruebas finalizadas.");
    }

    //Modelo Reservas
    //Metodo constructor
     @Test
    public void TC01_constructorReservaValido() {
        try {
            ReservaDTO r = new ReservaDTO(1, 10, "Quito", "Guayaquil", "2025-07-20", "08:30", "A1", 50.0);
            System.out.println("TC01_constructorValido: PRUEBA CORRECTA");
        } catch (Exception e) {
            System.out.println("TC01_constructorValido: PRUEBA FALLIDA - " + e.getMessage());
            fail("No debería lanzar excepción");
        }
    }

    @Test
    public void TC02_codigoReservaInvalido() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new ReservaDTO(0, 10, "Quito", "Guayaquil", "2025-07-20", "08:30", "A1", 50.0);
        });
        if ("El código de reserva debe ser mayor a cero.".equals(ex.getMessage())) {
            System.out.println("TC02_codigoReservaInvalido: PRUEBA CORRECTA");
        } else {
            System.out.println("TC02_codigoReservaInvalido: PRUEBA FALLIDA");
        }
    }

    @Test
    public void TC03_origenVacio() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new ReservaDTO(1, 10, "", "Guayaquil", "2025-07-20", "08:30", "A1", 50.0);
        });
        if ("El origen no puede estar vacío.".equals(ex.getMessage())) {
            System.out.println("TC03_origenVacio: PRUEBA CORRECTA");
        } else {
            System.out.println("TC03_origenVacio: PRUEBA FALLIDA");
        }
    }

    @Test
    public void TC04_origenDestinoIguales() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new ReservaDTO(1, 10, "Quito", "Quito", "2025-07-20", "08:30", "A1", 50.0);
        });
        if ("El origen y el destino no pueden ser iguales.".equals(ex.getMessage())) {
            System.out.println("TC04_origenDestinoIguales: PRUEBA CORRECTA");
        } else {
            System.out.println("TC04_origenDestinoIguales: PRUEBA FALLIDA");
        }
    }

    @Test
    public void TC05_fechaFormatoIncorrecto() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new ReservaDTO(1, 10, "Quito", "Guayaquil", "20-07-2025", "08:30", "A1", 50.0);
        });
        if ("La fecha de viaje debe tener el formato YYYY-MM-DD.".equals(ex.getMessage())) {
            System.out.println("TC05_fechaFormatoIncorrecto: PRUEBA CORRECTA");
        } else {
            System.out.println("TC05_fechaFormatoIncorrecto: PRUEBA FALLIDA");
        }
    }
}
