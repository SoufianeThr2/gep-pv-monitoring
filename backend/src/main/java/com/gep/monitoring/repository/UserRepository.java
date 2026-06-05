package com.gep.monitoring.repository;

import com.gep.monitoring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository pour accéder aux données des utilisateurs.
 * Utilisé uniquement par le système d'authentification (Spring Security).
 *
 * La clé primaire est de type Long (ID auto-généré).
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Recherche un utilisateur par son email.
     * Utilisé par CustomUserDetailsService lors de la vérification du login.
     *
     * Convention Spring Data JPA :
     * "findBy" + "Email" → WHERE email = ?
     * Spring génère automatiquement : SELECT * FROM users WHERE email = ?
     *
     * @param email L'email de l'utilisateur à rechercher
     * @return Un Optional<User> (vide si l'email n'existe pas en base)
     */
    Optional<User> findByEmail(String email);
}