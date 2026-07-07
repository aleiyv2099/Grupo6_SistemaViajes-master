package Modelo;

/**
 * DTO del módulo Soporte al Cliente (tickets).
 * Campos según los requerimientos funcionales RF-1..RF-5 (Autor: Nicole Malavé).
 */
public class SoporteDTO {

    private int codigoTicket;         // Generado automáticamente
    private String nombreCliente;     // Solo letras y espacios, máx 100
    private String correo;            // Formato válido, máx 100
    private String numeroContacto;    // Solo números, máx 10 dígitos
    private String tipoProblema;      // Reserva, Pago, Cancelación, Consulta, Otro
    private String descripcion;       // Máx 300
    private String fechaCreacion;     // Automática
    private String estadoTicket;      // Pendiente, En proceso, Resuelto, Cerrado
    private String observaciones;     // Observaciones del agente
    private String responsable;       // Responsable asignado

    public SoporteDTO() {
    }

    // Getters y Setters
    public int getCodigoTicket() {
        return codigoTicket;
    }

    public void setCodigoTicket(int codigoTicket) {
        this.codigoTicket = codigoTicket;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNumeroContacto() {
        return numeroContacto;
    }

    public void setNumeroContacto(String numeroContacto) {
        this.numeroContacto = numeroContacto;
    }

    public String getTipoProblema() {
        return tipoProblema;
    }

    public void setTipoProblema(String tipoProblema) {
        this.tipoProblema = tipoProblema;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getEstadoTicket() {
        return estadoTicket;
    }

    public void setEstadoTicket(String estadoTicket) {
        this.estadoTicket = estadoTicket;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
}
