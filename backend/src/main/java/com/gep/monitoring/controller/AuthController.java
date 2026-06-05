package com.gep.monitoring.controller;

import com.gep.monitoring.dto.LoginRequestDto;
import com.gep.monitoring.dto.LoginResponseDto;
import com.gep.monitoring.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

/**
 * Controller gérant les endpoints d'authentification.
 *
 * Responsabilité unique : recevoir les requêtes de login,
 * déléguer la vérification à Spring Security, et retourner le JWT.
 *
 * Ce controller ne contient AUCUNE logique métier.
 * Il ne parle à aucun Repository directement.
 *
 * Endpoint exposé :
 * POST /api/auth/login → Authentifie l'utilisateur et retourne un token JWT
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager,
                          UserDetailsService userDetailsService,
                          JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Authentifie un utilisateur et retourne un token JWT.
     *
     * Flux :
     * 1. Spring Security vérifie l'email et le mot de passe (BCrypt)
     * 2. Si correct → JwtUtil génère un token signé
     * 3. Le token est retourné dans un LoginResponseDto propre
     * 4. Si incorrect → HTTP 401 Unauthorized
     *
     * @param loginRequest DTO contenant email et mot de passe
     * @return HTTP 200 avec LoginResponseDto (token) ou HTTP 401 si identifiants incorrects
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        try {
            // Délégation à Spring Security pour vérification email + mot de passe BCrypt
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            // Identifiants incorrects → 401 Unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Authentification réussie → génération du token JWT
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        // Retour du token dans un DTO typé (plus propre qu'un HashMap)
        return ResponseEntity.ok(new LoginResponseDto(jwt));
    }
}
