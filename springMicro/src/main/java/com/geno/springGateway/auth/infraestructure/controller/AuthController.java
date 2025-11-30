package com.geno.springGateway.auth.infraestructure.controller;


import com.geno.springGateway.auth.application.dto.RegisterRequest;
import com.geno.springGateway.auth.application.service.JwtService;
import com.geno.springGateway.user.entities.Rol;
import com.geno.springGateway.user.entities.User;
import com.geno.springGateway.user.infraestructure.repository.RolRepository;
import com.geno.springGateway.user.infraestructure.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para login y registro de usuarios")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserRepository userRepository;
    private final RolRepository rolRepo;
    private final JwtService jwt;
    private final PasswordEncoder passwordEncoder;

    // --- LOGIN ---------------------------------------------------------------------

    @Operation(
            summary = "Login de usuario",
            description = "Autentica al usuario mediante email y contraseña, retornando un token JWT.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login exitoso"),
                    @ApiResponse(responseCode = "401", description = "Credenciales inválidas",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos",
                            content = @Content(mediaType = "application/json"))
            }
    )
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> req) {
        String email = req.get("email");
        String password = req.get("password");

        authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        var user = userRepository.findByEmail(email).orElseThrow();
        var roles = user.getRoles().stream().map(Rol::getName).toList();
        String token = jwt.generate(user.getUsername(), roles);

        return Map.of(
                "access_token", token,
                "token_type", "Bearer",
                "roles", roles
        );
    }

    @Operation(
            summary = "Registro de nuevo usuario",
            description = "Crea un nuevo usuario, asigna roles (por defecto USER) y retorna un token JWT.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuario creado"),
                    @ApiResponse(responseCode = "400", description = "Datos faltantes o inválidos",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "409", description = "Email ya registrado",
                            content = @Content(mediaType = "application/json"))
            }
    )
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> register(@RequestBody RegisterRequest req) {
        if (req.getEmail() == null || req.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing Email or password");
        }
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        List<String> roleNames = (req.getRoles() == null || req.getRoles().isEmpty())
                ? List.of("USER")
                : req.getRoles();

        Set<Rol> rolEntities = new HashSet<>();
        for (String roleName : roleNames) {
            Rol rol = rolRepo.findByName(roleName).orElseGet(() -> {
                Rol newRol = new Rol();
                newRol.setName(roleName);
                return rolRepo.save(newRol);
            });
            rolEntities.add(rol);
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRoles(rolEntities);

        userRepository.save(user);

        List<String> roles = rolEntities.stream().map(Rol::getName).toList();
        String token = jwt.generate(user.getUsername(), roles);

        return Map.of(
                "access_token", token,
                "token_type", "Bearer",
                "roles", roles
        );
    }

    //Handle exceptions

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    @Operation(
            summary = "Error de autenticación",
            description = "Responde con un mensaje estándar cuando las credenciales son inválidas."
    )
    public Map<String, String> onAuthError(Exception e) {
        return Map.of("error", "Bad credentials");
    }
}
