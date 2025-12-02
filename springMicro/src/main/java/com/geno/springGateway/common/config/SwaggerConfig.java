package com.geno.springGateway.common.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Gateway Microservice")
                        .version("1.0.0")
                        .description("Gateway para orquestar microservicios (NestJS, Django, etc.)"));
    }
}