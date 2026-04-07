package com.program.appointment.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.program.appointment.model.Appointment;

@Service
public class AppointmentService {

    private final Map<Long, Appointment> appointments = new HashMap<>();
    private Long nextId = 1L;

    // Create
    public String addAppointment(Appointment appointment) {
        for (Appointment appt : appointments.values()) {
            if (appt.getDate().equals(appointment.getDate())) {
                return "Error: Date already booked!";
            } 
        }
        appointment.setId(nextId++);
        appointments.put(appointment.getId(), appointment);
        return "Appointment booked successfully!";
    }

    // Read All
    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments.values());
    }

    // Read by Id
    public Appointment getAppointment(Long id) {
        return appointments.get(id);
    }

    // Update
    public String updateAppointment(Long id, Appointment appointment) {
        if (!appointments.containsKey(id)) return "Appointment not found!";
        // Check date conflict
        for (Appointment appt : appointments.values()) {
            if (!appt.getId().equals(id) && appt.getDate().equals(appointment.getDate())) {
                return "Error: Date already booked!";
            }
        }
        appointment.setId(id);
        appointments.put(id, appointment);
        return "Appointment updated successfully!";
    }

    // Delete
    public String deleteAppointment(Long id) {
        if (appointments.remove(id) != null) {
            return "Appointment deleted successfully!";
        }
        return "Appointment not found!";
    }
}