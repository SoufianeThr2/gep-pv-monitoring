package com.gep.monitoring.security;

import com.gep.monitoring.entity.User;
import com.gep.monitoring.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Service Spring Security qui charge les données d'un utilisateur depuis la base.
 * Appelé automatiquement par Spring Security lors de la vérification du login.
 *
 * Corrigé pour utiliser Optional<User> retourné par UserRepository.findByEmail().
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Charge un utilisateur par son email depuis la base de données.
     * Appelé par AuthenticationManager lors de l'authentification.
     *
     * @param email L'email de l'utilisateur (sert d'identifiant)
     * @return UserDetails contenant email + mot de passe haché
     * @throws UsernameNotFoundException si aucun utilisateur trouvé avec cet email
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Utilisation de Optional.orElseThrow() — plus propre que == null
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Utilisateur non trouvé avec l'email : " + email));

        // On retourne un UserDetails standard Spring Security
        // avec une liste de rôles vide (pas de gestion des rôles dans ce projet)
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
}
