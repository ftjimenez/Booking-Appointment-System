package com.program.appointment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.program.appointment.exception.ResourceNotFoundException;
import com.program.appointment.model.Appointment;
import com.program.appointment.repository.AppointmentRepository;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id " + id));
    }

    public Appointment updateAppointment(Long id, Appointment appointmentDetails) {
        Appointment existingAppointment = getAppointmentById(id);

        existingAppointment.setName(appointmentDetails.getName());
        existingAppointment.setDate(appointmentDetails.getDate());
        existingAppointment.setTime(appointmentDetails.getTime());
        existingAppointment.setDescription(appointmentDetails.getDescription());

        return appointmentRepository.save(existingAppointment);
    }

    public void deleteAppointment(Long id) {
        Appointment existingAppointment = getAppointmentById(id);
        appointmentRepository.delete(existingAppointment);
    }
}