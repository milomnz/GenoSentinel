package com.geno.springGateway.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuración de RestTemplates para comunicación con microservicios externos.
 * Define dos beans separados para manejar diferentes convenciones de naming:
 * - djangoRestTemplate: usa snake_case para Django
 * - nestRestTemplate: usa camelCase (default) para NestJS
 * 
 * @author milomnz
 */
@Slf4j
@Configuration
public class RestTemplateConfig {

    /**
     * RestTemplate configurado para Django (Python).
     * Serializa/deserializa JSON usando snake_case.
     */
    @Bean(name = "djangoRestTemplate")
    public RestTemplate djangoRestTemplate() {
        RestTemplate restTemplate = createBaseRestTemplate();

        // Configurar ObjectMapper con snake_case para Django
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        
        // Registrar módulo para Java 8 Date/Time (LocalDate, LocalDateTime, etc.)
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Crear el converter con snake_case
        MappingJackson2HttpMessageConverter snakeCaseConverter = new MappingJackson2HttpMessageConverter();
        snakeCaseConverter.setObjectMapper(objectMapper);

        // Crear nueva lista de converters sin el Jackson por defecto
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(snakeCaseConverter); // Agregar primero el de snake_case
        
        for (HttpMessageConverter<?> converter : restTemplate.getMessageConverters()) {
            if (!(converter instanceof MappingJackson2HttpMessageConverter)) {
                converters.add(converter);
            }
        }
        
        restTemplate.setMessageConverters(converters);
        
        log.info("✅ djangoRestTemplate configurado con snake_case y soporte para Java 8 Date/Time");
        return restTemplate;
    }

    /**
     * RestTemplate configurado para NestJS (TypeScript).
     * Usa camelCase por defecto (comportamiento estándar de Jackson).
     */
    @Bean(name = "nestRestTemplate")
    public RestTemplate nestRestTemplate() {
        log.info("✅ nestRestTemplate configurado con camelCase (default)");
        return createBaseRestTemplate();
    }

    /**
     * Crea un RestTemplate base con la configuración común.
     * Usa BufferingClientHttpRequestFactory para permitir leer el body en interceptors.
     */
    private RestTemplate createBaseRestTemplate() {
        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000);
        
        // Wrap con BufferingClientHttpRequestFactory para permitir logging del body
        BufferingClientHttpRequestFactory bufferingFactory = 
                new BufferingClientHttpRequestFactory(requestFactory);
        
        return new RestTemplate(bufferingFactory);
    }
}
