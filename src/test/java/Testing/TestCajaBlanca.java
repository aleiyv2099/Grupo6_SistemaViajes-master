/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package Testing;


import Controlador.ClienteDAO;
import Controlador.LoginDAO;
import Controlador.ReservaDAO;
import Modelo.ClienteDTO;
import Modelo.LoginDTO;
import Modelo.ReservaDTO;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author PERSONAL
 */
public class TestCajaBlanca {
 
    ////Criterio de Comandos: Insertar cliente
    @Test
        public void testInsertarCliente() throws Exception {
        ClienteDAO dao = new ClienteDAO();
        ClienteDTO cliente = new ClienteDTO(65, "0987622111", "Lourdes", "0922314411", "Mucho lote");
        boolean resultado = dao.registerClient(cliente);
        assertTrue(resultado);
        System.out.println("Cliente agregado con éxito.");
    }

    //Criterio de Decisiones: Validar credenciales de usuario
    @Test
        public void testLoginConUsuarioCorrecto() {
        LoginDAO dao = new LoginDAO();
        LoginDTO resultado = dao.log("vendedor@gmail.com", "12345");

        // Se considera éxito si el correo no es null (o si el ID > 0 si lo prefieres)
        assertNotNull(resultado.getEmail());
        System.out.println("Login exitoso con credenciales validas.");
    }



    //Condiciones y Decisiones: Verificación antes de registrar
    @Test
        public void testValidarDatosReservaExistente() {
        ReservaDAO dao = new ReservaDAO();

        // Buscar reservas
        List<ReservaDTO> reservas = dao.searchReservationsByClientOrCode("51");
        System.out.println("Cantidad de reservas encontradas: " + reservas.size());

        assertFalse(reservas.isEmpty(), "No se encontraron reservas activas con ese código.");

        ReservaDTO reserva = reservas.get(0);
        assertNotNull(reserva, "La reserva obtenida es null");

        boolean datosValidos = reserva.getClientCode() > 0 &&
        reserva.getDestinationCity() != null && !reserva.getDestinationCity().isEmpty() &&
        reserva.getTicketPrice() > 0;

        assertTrue(datosValidos, "Los datos de la reserva no son válidos");
        System.out.println("Validación de datos de reserva existente exitosa.");
    }
    
        
    //Condiciones múltiples: Buscar reservas por cliente, destino y estado
    @Test
        public void testBuscarReservaPorClienteDestinoYAsiento() {
        ReservaDAO dao = new ReservaDAO();

        String filtro = "50";
        String destinoEsperado = "Guayaquil";
        String asientoEsperado = "22"; 
        
        List<ReservaDTO> reservas = dao.searchReservationsByClientOrCode(filtro);
        assertFalse(reservas.isEmpty(), "No se encontraron reservas con ese código.");

        // Validar que haya una reserva con ese destino y asiento
        boolean encontrada = reservas.stream()
        .anyMatch(r -> r.getDestinationCity().equalsIgnoreCase(destinoEsperado)
                    && r.getAssignedSeat().equalsIgnoreCase(asientoEsperado));

        assertTrue(encontrada, "No se encontró ninguna reserva con ese destino y asiento.");
        System.out.println("Reserva encontrada con cliente/código, destino y asiento.");
    }


}
