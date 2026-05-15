/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Testing;

/**
 *
 * @author PERSONAL
 */
public class TestCajaNegra {
    
    // Partición de Equivalencia: DNI
    public static boolean validarDNI(String dni) {
        return dni != null && dni.matches("\\d{10}");
    }

    // Análisis de Valores Límites: Monto de Pago
    public static boolean validarMontoPago(double monto) {
        return monto >= 100;
    }

    // Tablas de Decisión: Login
    public static String validarLogin(boolean usuarioValido, boolean contrasenaValida) {
        if (usuarioValido && contrasenaValida) {
            return "Acceso permitido";
        } else {
            return "Acceso denegado";
        }
    }

    public static void main(String[] args) {

        // Partición de Equivalencia: DNI
        System.out.println("---- Particion de Equivalencia (DNI) ----");
        System.out.println("DNI valido: " + validarDNI("0912345678"));       
        System.out.println("DNI invalido: " + validarDNI("09123"));           
        System.out.println("DNI invalido: " + validarDNI("091234567890")); 

        
        // Análisis de Valores Límites: Monto de Pago
        System.out.println("\n---- Analisis de Valores Limites (Pago) ----");
        System.out.println("Monto 99.99: " + validarMontoPago(99.99));    
        System.out.println("Monto 100: " + validarMontoPago(100));        
        System.out.println("Monto 200.01: " + validarMontoPago(200.01));  
 
        
        // Tablas de Decisión: Login
        System.out.println("\n---- Tablas de Decision (Login) ----");
        System.out.println("Regla 1 (Si, Si): " + validarLogin(true, true));   
        System.out.println("Regla 2 (Si, No): " + validarLogin(true, false)); 
        System.out.println("Regla 3 (No, Si): " + validarLogin(false, true)); 
        System.out.println("Regla 4 (No, No): " + validarLogin(false, false)); 
    }
}
