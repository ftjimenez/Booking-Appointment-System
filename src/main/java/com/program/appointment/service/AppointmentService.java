package com.program.appointment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.program.appointment.exception.ResourceNotFoundException;
import com.program.appointment.model.Appointment;
import com.program.appointment.model.User;
import com.program.appointment.repository.AppointmentRepository;
import com.program.appointment.repository.UserRepository;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }

    public Appointment createAppointment(Appointment appointment, String username) {
        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        appointment.setOwner(owner);
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointments(String username) {
        return appointmentRepository.findAllByOwnerUsernameOrderByIdAsc(username);
    }

    public Appointment getAppointmentById(Long id, String username) {
        return appointmentRepository.findByIdAndOwnerUsername(id, username)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id " + id));
    }

    public Appointment updateAppointment(Long id, Appointment appointmentDetails, String username) {
        Appointment existingAppointment = getAppointmentById(id, username);

        existingAppointment.setName(appointmentDetails.getName());
        existingAppointment.setDate(appointmentDetails.getDate());
        existingAppointment.setTime(appointmentDetails.getTime());
        existingAppointment.setDescription(appointmentDetails.getDescription());

        return appointmentRepository.save(existingAppointment);
    }

    public void deleteAppointment(Long id, String username) {
        Appointment existingAppointment = getAppointmentById(id, username);
        appointmentRepository.delete(existingAppointment);
    }
}
