package Modelo;

public class SoporteDTO {

    private int codigoTicket;
    private String nombreCliente;
    private String correo;
    private String telefono;
    private String tipoProblema;
    private String descripcion;
    private String estado;
    private String responsable;
    private String prioridad;

    public SoporteDTO() {
}

public SoporteDTO(
        int codigoTicket,
        String nombreCliente,
        String correo,
        String telefono,
        String tipoProblema,
        String descripcion,
        String estado,
        String prioridad) {

    this.codigoTicket = codigoTicket;
    this.nombreCliente = nombreCliente;
    this.correo = correo;
    this.telefono = telefono;
    this.tipoProblema = tipoProblema;
    this.descripcion = descripcion;
    this.estado = estado;
    this.responsable = "";
    this.prioridad = prioridad;
}

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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
    
    public String getPrioridad() {
        return prioridad;
    }
    
    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }
}