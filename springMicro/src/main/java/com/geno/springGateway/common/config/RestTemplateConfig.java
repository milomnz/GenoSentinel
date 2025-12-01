package com.geno.springGateway.common.config;
import com.geno.springGateway.common.exception.CustomRestResponseErrorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class RestTemplateConfig {

    private final CustomRestResponseErrorHandler customRestResponseErrorHandler; // Inyección

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // Asigna el manejador de errores personalizado
        restTemplate.setErrorHandler(customRestResponseErrorHandler);
        return restTemplate;
    }
}