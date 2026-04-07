package com.program.appointment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.program.appointment.model.Appointment;
import com.program.appointment.service.AppointmentService;

@Controller
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/")
    public String showHome(Model model) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        return "index";
    }

    @PostMapping("/book")
    public String book(@ModelAttribute Appointment appointment, Model model) {

        String message = appointmentService.addAppointment(appointment);

        model.addAttribute("appointment", new Appointment());
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        model.addAttribute("message", message);
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Appointment appointment = appointmentService.getAppointment(id);
        model.addAttribute("appointment", appointment);
        return "edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute Appointment appointment,
                         Model model) {

        String message = appointmentService.updateAppointment(id, appointment);

        model.addAttribute("appointment", new Appointment());
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        model.addAttribute("message", message);
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model) {
        String message = appointmentService.deleteAppointment(id);

        model.addAttribute("appointment", new Appointment());
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        model.addAttribute("message", message);
        return "index";
    }
}