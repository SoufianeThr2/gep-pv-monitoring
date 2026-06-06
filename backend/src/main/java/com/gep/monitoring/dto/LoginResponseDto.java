package com.gep.monitoring.dto;

/**
 * DTO de réponse pour l'authentification (Login réussi).
 * Envoyé par le POST /api/auth/login vers le Frontend.
 */
public class LoginResponseDto {

    private String accessToken;

    public LoginResponseDto() {
    }

    public LoginResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
}
