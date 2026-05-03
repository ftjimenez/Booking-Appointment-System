package com.program.appointment.dto;

public class AuthTokenResponse {

    private final String accessToken;
    private final String tokenType;

    public AuthTokenResponse(String accessToken, String tokenType) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }

    public static AuthTokenResponse bearer(String accessToken) {
        return new AuthTokenResponse(accessToken, "Bearer");
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }
}
