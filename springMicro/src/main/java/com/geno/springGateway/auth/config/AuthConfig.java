package com.geno.springGateway.auth.config;

import com.geno.springGateway.user.infraestructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
* Configuracion de autenticacion y seguridad para la aplicación.
* Define beans para la codificacion de contraseñas y la carga de detalles de usuario.
* Utiliza BCrypt para la seguridad de contraseñas y la conecta el repositorio de usuarios para la autenticacion.      *
*/

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class AuthConfig{

    /**
     * Repositorio para la gestión de usuarios en la autenticación
     */
    private final UserRepository userRepository;

    /**
     * Bean para la codificacion de contraseñas usando BCrypt.
     * @return PassWordEncoder seguro para almacenar contraseñas.
     */

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean para cargar los detalles de usuario desde la BD.
     * Utilizado por Spring Security para autenticar usuarios.
     * @return UserDatailsService que busca usuarios por nombre de usuario.
     */

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

}



