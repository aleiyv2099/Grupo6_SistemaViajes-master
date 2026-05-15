package Modelo;

public class MascotaDTO {

    private int idMascota;
    private int clientCode;
    private String nombre;
    private String raza;
    private double peso;
    private String observaciones;

    // Constructor vacío
    public MascotaDTO() {
    }

    // Constructor con parámetros
    public MascotaDTO(int idMascota, int clientCode, String nombre, String raza, double peso, String observaciones) {
       if (clientCode <= 0) {
       throw new IllegalArgumentException("El código de cliente debe ser mayor a cero.");
    }

        if (nombre == null || nombre.trim().isEmpty()) {
        throw new IllegalArgumentException("El nombre no puede estar vacío.");
    }

        if (raza == null || raza.trim().isEmpty()) {
        throw new IllegalArgumentException("La raza no puede estar vacía.");
    }

        if (peso <= 0) {
        throw new IllegalArgumentException("El peso debe ser mayor a cero.");
    }  
         
        this.idMascota = idMascota;
        this.clientCode = clientCode;
        this.nombre = nombre;
        this.raza = raza;
        this.peso = peso;
        this.observaciones = observaciones;
    }

    // Getters y Setters
    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public int getClientCode() {
        return clientCode;
    }

    public void setClientCode(int clientCode) {
        this.clientCode = clientCode;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
