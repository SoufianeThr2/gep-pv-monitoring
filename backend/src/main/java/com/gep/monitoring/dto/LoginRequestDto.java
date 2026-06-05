package com.gep.monitoring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO de requête pour l'authentification (Login).
 * Reçu par le POST /api/auth/login depuis le Frontend.
 * Remplace la classe interne "AuthRequest" qui était dans AuthController.java.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    // Email de l'utilisateur (sert d'identifiant de connexion)
    private String email;

    // Mot de passe en clair (sera comparé au hash BCrypt en base)
    private String password;
}
