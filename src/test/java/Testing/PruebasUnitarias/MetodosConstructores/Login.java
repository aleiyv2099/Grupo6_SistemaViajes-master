/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Testing.PruebasUnitarias.MetodosConstructores;

import Controlador.ClienteDAO;
import Controlador.FacturaDAO;
import Controlador.LoginDAO;
import Controlador.ReservaDAO;
import Modelo.LoginDTO;
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
public class Login {
    
    


    @BeforeAll
    public static void beforeAll() {
        System.out.println("==> Iniciando pruebas");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("==> Pruebas finalizadas.");
    }

    
     //Modelo Login
    //Metodo constructor
    
    
     @Test
    public void TC01_constructorLoginValido() {
        try {
            LoginDTO login = new LoginDTO(1, "Juan", "juan@mail.com", "123456");
            System.out.println("TC01_constructorValido: PRUEBA CORRECTA");
        } catch (Exception e) {
            System.out.println("TC01_constructorValido: PRUEBA FALLIDA - " + e.getMessage());
            fail("No debería lanzar excepción");
        }
    }

    @Test
    public void TC02_idInvalido() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new LoginDTO(0, "Juan", "juan@mail.com", "123456");
        });
        if ("El ID no puede estar vacío ni ser menor o igual a cero.".equals(ex.getMessage())) {
            System.out.println("TC02_idInvalido: PRUEBA CORRECTA");
        } else {
            System.out.println("TC02_idInvalido: PRUEBA FALLIDA");
        }
    }

    @Test
    public void TC03_nombreLoginVacio() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new LoginDTO(1, "", "juan@mail.com", "123456");
        });
        if ("El nombre no puede estar vacío.".equals(ex.getMessage())) {
            System.out.println("TC03_nombreVacio: PRUEBA CORRECTA");
        } else {
            System.out.println("TC03_nombreVacio: PRUEBA FALLIDA");
        }
    }

    @Test
    public void TC04_correoVacio() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new LoginDTO(1, "Juan", "", "123456");
        });
        if ("El correo no puede estar vacío.".equals(ex.getMessage())) {
            System.out.println("TC04_correoVacio: PRUEBA CORRECTA");
        } else {
            System.out.println("TC04_correoVacio: PRUEBA FALLIDA");
        }
    }

    @Test
    public void TC05_correoInvalido() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new LoginDTO(1, "Juan", "abc", "123456");
        });
        if ("El formato del correo no es válido.".equals(ex.getMessage())) {
            System.out.println("TC05_correoInvalido: PRUEBA CORRECTA");
        } else {
            System.out.println("TC05_correoInvalido: PRUEBA FALLIDA");
        }
    }

    @Test
    public void TC06_passVacia() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new LoginDTO(1, "Juan", "juan@mail.com", "");
        });
        if ("La contraseña no puede estar vacía.".equals(ex.getMessage())) {
            System.out.println("TC06_passVacia: PRUEBA CORRECTA");
        } else {
            System.out.println("TC06_passVacia: PRUEBA FALLIDA");
        }
    }

    @Test
    public void TC07_passCorta() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            new LoginDTO(1, "Juan", "juan@mail.com", "123");
        });
        if ("La contraseña debe tener al menos 6 caracteres.".equals(ex.getMessage())) {
            System.out.println("TC07_passCorta: PRUEBA CORRECTA");
        } else {
            System.out.println("TC07_passCorta: PRUEBA FALLIDA");
        }
    }
}
