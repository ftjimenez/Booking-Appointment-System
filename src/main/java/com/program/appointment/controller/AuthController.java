package com.program.appointment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.program.appointment.dto.RegisterRequest;
import com.program.appointment.exception.EmailTakenException;
import com.program.appointment.exception.PasswordMismatchException;
import com.program.appointment.exception.UsernameTakenException;
import com.program.appointment.service.UserRegistrationService;

import jakarta.validation.Valid;

@Controller
public class AuthController {

    private final UserRegistrationService userRegistrationService;

    public AuthController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerRequest") RegisterRequest request,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        try {
            userRegistrationService.register(request);
        } catch (PasswordMismatchException e) {
            bindingResult.rejectValue("confirmPassword", "password.mismatch", e.getMessage());
            return "register";
        } catch (UsernameTakenException e) {
            bindingResult.rejectValue("username", "username.taken", e.getMessage());
            return "register";
        } catch (EmailTakenException e) {
            bindingResult.rejectValue("email", "email.taken", e.getMessage());
            return "register";
        }
        redirectAttributes.addFlashAttribute("registered", true);
        return "redirect:/login";
    }
}
