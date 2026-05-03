package com.program.appointment.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.program.appointment.dto.RegisterRequest;
import com.program.appointment.model.User;
import com.program.appointment.repository.UserRepository;

import jakarta.validation.Valid;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "password.mismatch", "Passwords do not match");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            bindingResult.rejectValue("username", "username.taken", "Username already taken");
        }
        if (bindingResult.hasErrors()) {
            return "register";
        }
        User user = new User(request.getUsername(), passwordEncoder.encode(request.getPassword()), true);
        userRepository.save(user);
        redirectAttributes.addFlashAttribute("registered", true);
        return "redirect:/login";
    }
}
