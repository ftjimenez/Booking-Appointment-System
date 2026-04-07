package com.program.appointment.controller;

import com.program.appointment.dto.CreateAppointmentRequest;
import com.program.appointment.dto.UpdateAppointmentRequest;
import com.program.appointment.model.Appointment;
import com.program.appointment.service.AppointmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class WebController {

    private final AppointmentService appointmentService;

    public WebController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/")
    public String showHome(Model model) {
        model.addAttribute("appointment", new CreateAppointmentRequest());
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        return "index";
    }

    @PostMapping("/book")
    public String bookAppointment(@Valid @ModelAttribute("appointment") CreateAppointmentRequest request, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("appointments", appointmentService.getAllAppointments());
            return "index";
        }
        Appointment appointment = new Appointment(request.getName(), request.getDate(), request.getTime(), request.getDescription());
        appointmentService.createAppointment(appointment);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editAppointment(@PathVariable Long id, Model model) {
        Appointment apt = appointmentService.getAppointmentById(id);
        UpdateAppointmentRequest request = new UpdateAppointmentRequest(id, apt.getName(), apt.getDescription(), apt.getDate(), apt.getTime());
        model.addAttribute("appointment", request);
        return "edit";
    }

    @PostMapping("/update/{id}")
    public String updateAppointment(@PathVariable Long id, @Valid @ModelAttribute("appointment") UpdateAppointmentRequest request, BindingResult result) {
        if (result.hasErrors() || !id.equals(request.getId())) {
            return "edit";
        }
        Appointment appointment = new Appointment(request.getName(), request.getDate(), request.getTime(), request.getDescription());
        appointment.setId(id);
        appointmentService.updateAppointment(id, appointment);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/";
    }
}
