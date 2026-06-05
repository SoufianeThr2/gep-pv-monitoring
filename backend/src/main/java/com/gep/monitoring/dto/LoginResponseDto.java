package com.gep.monitoring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO de réponse pour l'authentification (Login réussi).
 * Envoyé par le POST /api/auth/login vers le Frontend.
 * Contient le token JWT que le Frontend va stocker dans le localStorage.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

    // Le token JWT signé — le Frontend l'attachera dans chaque requête suivante
    // dans le header HTTP : "Authorization: Bearer <access_token>"
    private String accessToken;
}
