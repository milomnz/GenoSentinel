package com.geno.springGateway.auth.application.service.impl;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {

    /**
     *
     * Clave secreta utilizada para firmar los tokens
     */
    private final SecretKey key;

    /**
     * Tiempo de expiracion del token
     */
    private final long expMinutes;

    /**
     *
     * Constructor que inicializa la clave secreta y el tiempo en el que expira el token
     * @param secret clave secreta en bas64
     * @param expMinutes
     */
    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("60") long expMinutes) {


        byte[] raw = secret.matches("^[A-Za-z0-9+/=]+$") ? Decoders.BASE64.decode(secret) : secret.getBytes();
        this.key = Keys.hmacShaKeyFor(raw);
        this.expMinutes = expMinutes;
    }

    /**
     * Genera el token JWT con el sujeto y los roles
     * @param subject id del user (username)
     * @param roles lista de roles
     * @return token JWT
     */
    public String generate(String subject, List<String> roles) {
        Instant now = Instant.now();
        List <String> safeRoles = roles == null ? List.of() : roles;
        return Jwts.builder()
                .subject(subject) // Establece el sujeto (username)
                .claims(Map.of("roles", safeRoles)) // Agrega los roles como claim personalizado
                .issuedAt(Date.from(now)) // Fecha de emisión
                .expiration(Date.from(now.plusSeconds(expMinutes * 60))) // Fecha de expiración
                // Firma el token con la clave y algoritmo seguro
                .signWith(key)
                .signWith(key, Jwts.SIG.HS256)
                .compact(); // Finaliza y retorna el token JWT
    }

    /**
     *
     * Valida y parsea el token, retornando los claims
     *
     * @param token
     * @return claims del token
     */

    public Claims parse(String token) {
        // Elimina el prefijo "Bearer " si está presente
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        // Parsea y valida el token JWT, devolviendo los claims
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
