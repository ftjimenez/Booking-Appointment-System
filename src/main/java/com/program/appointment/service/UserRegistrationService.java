package com.program.appointment.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.program.appointment.dto.ApiRegisterRequest;
import com.program.appointment.dto.RegisterRequest;
import com.program.appointment.exception.EmailTakenException;
import com.program.appointment.exception.PasswordMismatchException;
import com.program.appointment.exception.UsernameTakenException;
import com.program.appointment.model.User;
import com.program.appointment.repository.UserRepository;

@Service
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest request) {
        validateNewAccount(
                request.getUsername(),
                request.getPassword(),
                request.getConfirmPassword(),
                request.getEmail());
        User user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                true,
                request.getDisplayName(),
                request.getEmail());
        return userRepository.save(user);
    }

    public User registerPublicApi(ApiRegisterRequest request) {
        validateNewAccount(
                request.getUsername(),
                request.getPassword(),
                request.getConfirmPassword(),
                request.getEmail());
        User user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                true,
                request.getName(),
                request.getEmail());
        return userRepository.save(user);
    }

    private void validateNewAccount(String username, String password, String confirmPassword, String email) {
        if (!password.equals(confirmPassword)) {
            throw new PasswordMismatchException("Passwords do not match");
        }
        if (userRepository.existsByUsername(username)) {
            throw new UsernameTakenException("Username already taken");
        }
        if (userRepository.existsByEmail(email)) {
            throw new EmailTakenException("Email already registered");
        }
    }
}
