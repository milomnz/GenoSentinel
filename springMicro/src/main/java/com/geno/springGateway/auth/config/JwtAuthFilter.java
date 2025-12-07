package com.geno.springGateway.auth.config;


import com.geno.springGateway.auth.application.service.impl.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 *
 * Filtro de autenticacion JWT para Spring Security
 * Intercepta las peticiones HTTP y valida el token presente en la cabecera Authorization.
 * Si el token es válido, establece la autenticación en el contexto de seguridad con los roles del usuario.
 * Si el token es inválido o no está presente, la petición continúa sin autenticación.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    /**
     * Metodo princiapl que intercepta las peticiones http
     * Extae el token del header, lo valida y autentica
     * @param req peticion http
     * @param res respuesta http
     * @param chain cadena de filtros
     * @throws jakarta.servlet.ServletException si ocurre un error en el filtro
     * @throws java.io.IOException si ocurre un error de IO
     */

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
        throws ServletException, IOException {
        String header = req.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res);
            return;
        }

        String token = header.substring(7);
        try{
            // Parsea y valida el token
            io.jsonwebtoken.Claims claims = jwtService.parse(token);
            String username = claims.getSubject();

            @SuppressWarnings("unchecked")
            List<String> roles = (List<String>) claims.get("roles");

            // Convierte los roles en autoridades de Spring Security
            List<SimpleGrantedAuthority> authorities =
                    (roles == null ? List.<String>of() : roles).stream()
                            .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r)
                            .map(SimpleGrantedAuthority::new)
                            .toList();

            // Crea el token de autenticación y lo establece en el contexto
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(username, "N/A", authorities);

            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e) {
            // Si el token es inválido, limpia el contexto de seguridad
            SecurityContextHolder.clearContext();
        }
        chain.doFilter(req, res);
    }

}
