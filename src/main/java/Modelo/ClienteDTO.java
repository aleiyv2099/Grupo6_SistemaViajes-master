/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Mini Wernaso
 */
    public class ClienteDTO {

        private int clientCode;
        private String identificationNumber;
        private String fullName;
        private String phoneNumber;
        private String address;

        public ClienteDTO() {
        }

        public ClienteDTO(int clientCode, String identificationNumber, String fullName, String phoneNumber, String address) {

            // Validar código
            if (clientCode <= 0) {
                throw new IllegalArgumentException("El código debe ser mayor que cero.");
            }

            // Validar nombre
            if (fullName == null || fullName.trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre no puede estar vacío.");
            }
            if (fullName.matches(".*\\d.*")) {
                throw new IllegalArgumentException("El nombre no debe contener números.");
            }

            // Validar cédula (dni)
            if (identificationNumber == null || identificationNumber.trim().isEmpty()) {
                throw new IllegalArgumentException("El DNI no puede estar vacío.");
            }
            if (!identificationNumber.matches("\\d{10}")) {
                throw new IllegalArgumentException("El DNI debe tener exactamente 10 dígitos.");
            }

            // Validar teléfono
            if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
                throw new IllegalArgumentException("El teléfono no puede estar vacío.");
            }
            if (!phoneNumber.matches("\\d{10}")) {
                throw new IllegalArgumentException("El teléfono debe contener solo 10 dígitos.");
            }

            // Validar dirección
            if (address == null || address.trim().isEmpty()) {
                throw new IllegalArgumentException("La dirección no puede estar vacía.");
            }
            
            if (fullName.contains("@") || fullName.contains("_")) {
                throw new IllegalArgumentException("El nombre contiene caracteres especiales.");
            }


            this.clientCode = clientCode;
            this.identificationNumber = identificationNumber;
            this.fullName = fullName;
            this.phoneNumber = phoneNumber;
            this.address = address;
        }

        public int getClientCode() {
            return clientCode;
        }

        public void setClientCode(int clientCode) {
            this.clientCode = clientCode;
        }

        public String getIdentificationNumber() {
            return identificationNumber;
        }

        public void setIdentificationNumber(String identificationNumber) {
            this.identificationNumber = identificationNumber;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

         /**
         *
         * @return
         */
        @Override
        public String toString() {
            return this.getFullName();
        }

    }
