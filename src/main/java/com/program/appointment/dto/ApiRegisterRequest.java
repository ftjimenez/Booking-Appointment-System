package com.program.appointment.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Public REST registration at {@code POST /api/register} (name and email required).
 */
public class ApiRegisterRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 64, message = "Username must be between 3 and 64 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 120, message = "Password must be between 8 and 120 characters")
    private String password;

    @NotBlank(message = "Please confirm your password")
    private String confirmPassword;

    @NotBlank(message = "Name is required")
    @Size(max = 128)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 128)
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
