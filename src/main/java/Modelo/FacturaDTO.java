/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mini Wernaso
 */
public class FacturaDTO {

   private int invoiceCode;
   private int clientCode;
   private Date issueDate;
   private double totalAmount;
   private String paymentMethod;
   private String invoiceStatus;
   private List<Integer> reservationList;
   private String clientIdentificationNumber;
   private String clientFullName;

    public FacturaDTO() {
    }

    public FacturaDTO(int invoiceCode, int clientCode, Date issueDate, double totalAmount, String paymentMethod, String invoiceStatus, List<Integer> reservationList, String clientIdentificationNumber, String clientFullName) {
        
        // Validar código factura
        if (invoiceCode <= 0) {
            throw new IllegalArgumentException("El código de factura debe ser mayor que cero.");
        }

        // Validar código cliente
        if (clientCode <= 0) {
            throw new IllegalArgumentException("El código de cliente debe ser mayor que cero.");
        }

        // Validar fecha de emisión
        if (issueDate == null) {
            throw new IllegalArgumentException("La fecha de emisión no puede ser nula.");
        }

        // Validar monto total
        if (totalAmount < 0) {
            throw new IllegalArgumentException("El monto total no puede ser negativo.");
        }

        // Validar método de pago
        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            throw new IllegalArgumentException("El método de pago no puede estar vacío.");
        }

        // Validar estado de la factura
        if (invoiceStatus == null || invoiceStatus.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado de la factura no puede estar vacío.");
        }

        // Validar lista de reservas
        if (reservationList == null || reservationList.isEmpty()) {
            throw new IllegalArgumentException("Debe haber al menos una reserva asociada.");
        }

        // Validar DNI del cliente
        if (clientIdentificationNumber == null || !clientIdentificationNumber.matches("\\d{10}")) {
            throw new IllegalArgumentException("El DNI del cliente debe tener exactamente 10 dígitos.");
        }

        // Validar nombre del cliente
        if (clientFullName == null || clientFullName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío.");
        }
        if (clientFullName.matches(".*\\d.*")) {
            throw new IllegalArgumentException("El nombre del cliente no debe contener números.");
        }
        
        //Validar estado de la Factura
        if (!invoiceStatus.matches("[a-zA-Z\\s]+")) {  // Solo letras y espacios permitidos
            throw new IllegalArgumentException("El estado de la factura debe ser un texto descriptivo sin números.");
        }
       
        this.invoiceCode = invoiceCode;
        this.clientCode = clientCode;
        this.issueDate = issueDate;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.invoiceStatus = invoiceStatus;
        this.reservationList = reservationList;
        this.clientIdentificationNumber = clientIdentificationNumber;
        this.clientFullName = clientFullName;
    }

    public int getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(int invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public int getClientCode() {
        return clientCode;
    }

    public void setClientCode(int clientCode) {
        this.clientCode = clientCode;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public List<Integer> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Integer> reservationList) {
        this.reservationList = reservationList;
    }

    public String getClientIdentificationNumber() {
        return clientIdentificationNumber;
    }

    public void setClientIdentificationNumber(String clientIdentificationNumber) {
        this.clientIdentificationNumber = clientIdentificationNumber;
    }

    public String getClientFullName() {
        return clientFullName;
    }

    public void setClientFullName(String clientFullName) {
        this.clientFullName = clientFullName;
    }
    
    

}