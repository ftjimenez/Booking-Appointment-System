package com.program.appointment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.program.appointment.dto.ApiRegisterRequest;
import com.program.appointment.dto.AuthTokenResponse;
import com.program.appointment.security.JwtService;
import com.program.appointment.service.UserRegistrationService;

import jakarta.validation.Valid;

@RestController
public class PublicRegistrationController {

    private final UserRegistrationService userRegistrationService;
    private final JwtService jwtService;

    public PublicRegistrationController(UserRegistrationService userRegistrationService, JwtService jwtService) {
        this.userRegistrationService = userRegistrationService;
        this.jwtService = jwtService;
    }

    /**
     * Public registration — no JWT or session required.
     */
    @PostMapping("/api/register")
    public ResponseEntity<AuthTokenResponse> register(@Valid @RequestBody ApiRegisterRequest request) {
        userRegistrationService.registerPublicApi(request);
        String token = jwtService.generateToken(request.getUsername());
        return ResponseEntity.ok(AuthTokenResponse.bearer(token));
    }
}
