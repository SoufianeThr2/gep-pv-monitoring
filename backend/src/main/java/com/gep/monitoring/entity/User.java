package com.gep.monitoring.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Entité représentant un utilisateur de la plateforme.
 * Correspond à la table "users" en base de données.
 * Le mot de passe est toujours stocké haché (BCrypt).
 * Cette entité est utilisée uniquement pour l'authentification.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // ID auto-généré

    @Column(unique = true, nullable = false)
    private String email;               // Email unique, sert d'identifiant

    @Column(nullable = false)
    private String password;            // Mot de passe haché avec BCrypt (jamais en clair)
}