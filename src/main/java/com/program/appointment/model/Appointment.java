package com.program.appointment.model;

public class Appointment {
    private Long id;
    private String customerName;
    private String serviceType;
    private String date;

    public Appointment() {}
    
    public Appointment(Long id, String customerName, String serviceType, String date) {
        this.id = id;
        this.customerName = customerName;
        this.serviceType = serviceType;
        this.date = date;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}