package com.program.appointment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.program.appointment.dto.CreateAppointmentRequest;
import com.program.appointment.dto.UpdateAppointmentRequest;
import com.program.appointment.model.Appointment;
import com.program.appointment.service.AppointmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public List<Appointment> getAllAppointments(@AuthenticationPrincipal UserDetails principal) {
        return appointmentService.getAllAppointments(principal.getUsername());
    }

    @GetMapping("/{id}")
    public Appointment getAppointmentById(@AuthenticationPrincipal UserDetails principal, @PathVariable Long id) {
        return appointmentService.getAppointmentById(id, principal.getUsername());
    }

    @PostMapping
    public Appointment createAppointment(@AuthenticationPrincipal UserDetails principal,
            @RequestBody CreateAppointmentRequest request) {
        Appointment appointment = new Appointment(request.getName(), request.getDate(), request.getTime(),
                request.getDescription());
        return appointmentService.createAppointment(appointment, principal.getUsername());
    }

    @PutMapping("/{id}")
    public Appointment updateAppointment(@AuthenticationPrincipal UserDetails principal, @PathVariable Long id,
            @Valid @RequestBody UpdateAppointmentRequest request) {
        if (!id.equals(request.getId())) {
            throw new IllegalArgumentException("ID mismatch");
        }
        Appointment appointment = new Appointment(request.getName(), request.getDate(), request.getTime(),
                request.getDescription());
        return appointmentService.updateAppointment(id, appointment, principal.getUsername());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@AuthenticationPrincipal UserDetails principal,
            @PathVariable Long id) {
        appointmentService.deleteAppointment(id, principal.getUsername());
        return ResponseEntity.noContent().build();
    }
}
