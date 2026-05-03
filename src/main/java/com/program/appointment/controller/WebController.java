package com.program.appointment.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.program.appointment.dto.CreateAppointmentRequest;
import com.program.appointment.dto.UpdateAppointmentRequest;
import com.program.appointment.model.Appointment;
import com.program.appointment.service.AppointmentService;

import jakarta.validation.Valid;

@Controller
public class WebController {

    private final AppointmentService appointmentService;

    public WebController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/")
    public String showHome(Model model, @AuthenticationPrincipal UserDetails principal) {
        String username = principal.getUsername();
        model.addAttribute("appointment", new CreateAppointmentRequest());
        model.addAttribute("appointments", appointmentService.getAllAppointments(username));
        return "index";
    }

    @PostMapping("/book")
    public String bookAppointment(@AuthenticationPrincipal UserDetails principal,
            @Valid @ModelAttribute("appointment") CreateAppointmentRequest request,
            BindingResult result,
            Model model) {
        String username = principal.getUsername();
        if (result.hasErrors()) {
            model.addAttribute("appointments", appointmentService.getAllAppointments(username));
            return "index";
        }
        Appointment appointment = new Appointment(request.getName(), request.getDate(), request.getTime(),
                request.getDescription());
        appointmentService.createAppointment(appointment, username);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editAppointment(@AuthenticationPrincipal UserDetails principal, @PathVariable Long id, Model model) {
        String username = principal.getUsername();
        Appointment apt = appointmentService.getAppointmentById(id, username);
        UpdateAppointmentRequest request = new UpdateAppointmentRequest(id, apt.getName(), apt.getDescription(),
                apt.getDate(), apt.getTime());
        model.addAttribute("appointment", request);
        return "edit";
    }

    @PostMapping("/update/{id}")
    public String updateAppointment(@AuthenticationPrincipal UserDetails principal, @PathVariable Long id,
            @Valid @ModelAttribute("appointment") UpdateAppointmentRequest request,
            BindingResult result) {
        String username = principal.getUsername();
        if (result.hasErrors() || !id.equals(request.getId())) {
            return "edit";
        }
        Appointment appointment = new Appointment(request.getName(), request.getDate(), request.getTime(),
                request.getDescription());
        appointmentService.updateAppointment(id, appointment, username);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteAppointment(@AuthenticationPrincipal UserDetails principal, @PathVariable Long id) {
        appointmentService.deleteAppointment(id, principal.getUsername());
        return "redirect:/";
    }
}
