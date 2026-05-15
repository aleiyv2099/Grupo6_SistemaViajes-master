/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

public class ReservaDTO {

    private int reservationCode;
    private int clientCode;
    private String originCity;
    private String destinationCity;
    private String travelDate;
    private String departureTime;
    private String assignedSeat;
    private double ticketPrice;

    
    
    
    public ReservaDTO() {
    }

    public ReservaDTO(int reservationCode, int clientCode, String originCity, String destinationCity, String travelDate, String departureTime, String assignedSeat, double ticketPrice) {
        
        if (reservationCode <= 0) {
            throw new IllegalArgumentException("El código de reserva debe ser mayor a cero.");
        }
        
        if (clientCode <= 0) {
            throw new IllegalArgumentException("El código de cliente debe ser mayor a cero.");
        }
        
        if (originCity == null || originCity.trim().isEmpty()) {
            throw new IllegalArgumentException("El origen no puede estar vacío.");
        }
        
        if (destinationCity == null || destinationCity.trim().isEmpty()) {
            throw new IllegalArgumentException("El destino no puede estar vacío.");
        }
        
        if (originCity.equalsIgnoreCase(destinationCity)) {
            throw new IllegalArgumentException("El origen y el destino no pueden ser iguales.");
        }
    
        if (travelDate == null || travelDate.trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha de viaje no puede estar vacía.");
        }
        
        if (!travelDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("La fecha de viaje debe tener el formato YYYY-MM-DD.");
        }
        
        if (departureTime == null || departureTime.trim().isEmpty()) {
            throw new IllegalArgumentException("La hora de salida no puede estar vacía.");
        }
        
        if (!departureTime.matches("\\d{2}:\\d{2}")) {
            throw new IllegalArgumentException("La hora de salida debe tener el formato HH:mm.");
        }
        
        if (assignedSeat == null || assignedSeat.trim().isEmpty()) {
            throw new IllegalArgumentException("El asiento asignado no puede estar vacío.");
        }
        
        if (ticketPrice <= 0) {
            throw new IllegalArgumentException("El precio del pasaje debe ser mayor a cero.");
        }
        
        
        this.reservationCode = reservationCode;
        this.clientCode = clientCode;
        this.originCity = originCity;
        this.destinationCity = destinationCity;
        this.travelDate = travelDate;
        this.departureTime = departureTime;
        this.assignedSeat = assignedSeat;
        this.ticketPrice = ticketPrice;
    }

    public int getReservationCode() {
        return reservationCode;
    }

    public void setReservationCode(int reservationCode) {
        this.reservationCode = reservationCode;
    }

    public int getClientCode() {
        return clientCode;
    }

    public void setClientCode(int clientCode) {
        this.clientCode = clientCode;
    }

    public String getOriginCity() {
        return originCity;
    }

    public void setOriginCity(String originCity) {
        this.originCity = originCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getAssignedSeat() {
        return assignedSeat;
    }

    public void setAssignedSeat(String assignedSeat) {
        this.assignedSeat = assignedSeat;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
    
    
}
