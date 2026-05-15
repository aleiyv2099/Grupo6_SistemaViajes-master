/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Testing.PruebasUnitarias.MetodosInstancia;

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
    LoginDAO loginDAO = new LoginDAO();


    @BeforeAll
    public static void beforeAll() {
        System.out.println("==> Iniciando pruebas");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("==> Pruebas finalizadas.");
    }

   
    //Login
    
 @Test
    public void TC01_loginCorrecto() {
        String correoValido = "vendedor@gmail.com";
        String passValida = "12345";

        LoginDTO resultado = loginDAO.log(correoValido, passValida);

        if (resultado != null && resultado.getUserId() > 0) {
            System.out.println("TC01_loginCorrecto: Prueba CORRECTA (usuario autenticado)");
        } else {
            System.out.println("TC01_loginCorrecto: Prueba FALLIDA (no autenticó usuario válido)");
        }

        assertNotNull(resultado, "Debe retornar un objeto LoginDTO");
        assertTrue(resultado.getUserId() > 0, "El ID debe ser mayor a 0 para un login válido");
        assertNotNull(resultado.getFullName(), "El nombre no debe ser nulo");
        assertEquals(correoValido, resultado.getEmail(), "El correo debe coincidir");
    }

    // Caso TC02: Login incorrecto (usuario o contraseña no existen)
    @Test
    public void TC02_loginIncorrecto() {
        String correoInvalido = "noexiste@correo.com";
        String passInvalida = "contraseñaInvalida";

        LoginDTO resultado = loginDAO.log(correoInvalido, passInvalida);

        if (resultado == null || resultado.getUserId() == 0) {
            System.out.println("TC02_loginIncorrecto: Prueba CORRECTA (no autenticó usuario inexistente)");
        } else {
            System.out.println("TC02_loginIncorrecto: Prueba FALLIDA (retornó datos de un usuario inexistente)");
        }

        // Validar que no devolvió datos de usuario
        assertNotNull(resultado, "Debe retornar un objeto LoginDTO aunque sea vacío");
        assertEquals(0, resultado.getUserId(), "El ID debe ser 0 si no autenticó");
        assertNull(resultado.getFullName(), "El nombre debe ser null si no autenticó");
    }

    // Caso TC03: Login con datos nulos o vacíos
    @Test
    public void TC03_loginDatosNulosOVacios() {
        LoginDTO resultado = loginDAO.log(null, null);

        if (resultado == null || resultado.getUserId() == 0) {
            System.out.println("TC03_loginDatosNulosOVacios: Prueba CORRECTA (manejó datos nulos sin autenticación)");
        } else {
            System.out.println("TC03_loginDatosNulosOVacios: Prueba FALLIDA (retornó datos con parámetros nulos)");
        }

        assertNotNull(resultado, "Debe retornar un objeto LoginDTO aunque sea vacío");
        assertEquals(0, resultado.getUserId(), "El ID debe ser 0 si no autenticó");
    }
}
