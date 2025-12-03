package com.geno.springGateway.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI gatewayCustomOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservices Health Gateway API (Spring Boot)")
                        .version("v1.0.0")
                        .description("Gateway API para enrutar y estandarizar las llamadas CRUD a los microservicios " +
                                "de Pacientes, Historias Clínicas y Tipos de Tumor (NestJS).")
                        .contact(new Contact()
                                .name("Geno Development Team")
                                .url("https://www.geno-dev.com")
                                .email("support@geno-dev.com"))
                        .license(new License()
                                .name("Licencia Propietaria")
                                .url("http://www.geno-dev.com/licenses")));
    }
}