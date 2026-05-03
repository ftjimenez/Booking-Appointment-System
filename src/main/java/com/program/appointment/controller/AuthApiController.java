package com.program.appointment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.program.appointment.dto.ApiRegisterRequest;
import com.program.appointment.dto.AuthTokenResponse;
import com.program.appointment.dto.LoginRequest;
import com.program.appointment.security.JwtService;
import com.program.appointment.service.UserRegistrationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthApiController {

    private final UserRegistrationService userRegistrationService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthApiController(UserRegistrationService userRegistrationService,
            JwtService jwtService,
            AuthenticationManager authenticationManager) {
        this.userRegistrationService = userRegistrationService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthTokenResponse> register(@Valid @RequestBody ApiRegisterRequest request) {
        userRegistrationService.registerPublicApi(request);
        String token = jwtService.generateToken(request.getUsername());
        return ResponseEntity.ok(AuthTokenResponse.bearer(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokenResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = jwtService.generateToken(request.getUsername());
        return ResponseEntity.ok(AuthTokenResponse.bearer(token));
    }
}
