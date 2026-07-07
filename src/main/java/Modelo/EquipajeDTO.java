package Modelo;

public class EquipajeDTO {

    private int idEquipaje;
    private int codigoCliente; // FK basada en la captura de la BDD
    private String tipoEquipaje;
    private double peso;
    private int cantidad;

    // Constructor vacío
    public EquipajeDTO() {
    }

    // Constructor con parámetros
    public EquipajeDTO(int idEquipaje, int codigoCliente, String tipoEquipaje, double peso, int cantidad) {
        if (codigoCliente <= 0) {
            throw new IllegalArgumentException("El código de cliente debe ser mayor a cero.");
        }

        if (tipoEquipaje == null || tipoEquipaje.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de equipaje no puede estar vacío.");
        }

        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor a cero.");
        } 
        
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }

        this.idEquipaje = idEquipaje;
        this.codigoCliente = codigoCliente;
        this.tipoEquipaje = tipoEquipaje;
        this.peso = peso;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public int getIdEquipaje() {
        return idEquipaje;
    }

    public void setIdEquipaje(int idEquipaje) {
        this.idEquipaje = idEquipaje;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getTipoEquipaje() {
        return tipoEquipaje;
    }

    public void setTipoEquipaje(String tipoEquipaje) {
        this.tipoEquipaje = tipoEquipaje;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}