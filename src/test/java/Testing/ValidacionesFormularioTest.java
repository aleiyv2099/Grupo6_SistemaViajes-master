package Testing;

import Vista.Sistema;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

// Verifica las validaciones que el tester experto marcó como fallo:
// origen/destino y tipo/nombre/raza solo letras, y fecha con formato YYYY-MM-DD.
public class ValidacionesFormularioTest {

    @Test
    void soloLetrasRechazaNumerosVaciosYEspacios() {
        assertTrue(Sistema.soloLetras("Guayaquil"));
        assertTrue(Sistema.soloLetras("San Cristóbal"));
        assertFalse(Sistema.soloLetras("Quito123"));
        assertFalse(Sistema.soloLetras("   "));
        assertFalse(Sistema.soloLetras(""));
        assertFalse(Sistema.soloLetras(null));
    }

    @Test
    void fechaValidaSoloAceptaFormatoISO() {
        assertTrue(Sistema.fechaValida("2026-07-15"));
        assertFalse(Sistema.fechaValida("15/07/2026"));
        assertFalse(Sistema.fechaValida("2026-7-5"));
        assertFalse(Sistema.fechaValida(""));
        assertFalse(Sistema.fechaValida(null));
    }
}
