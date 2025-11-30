package com.geno.springGateway.common.exception;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geno.springGateway.common.exception.ExternalServiceException;
import com.geno.springGateway.common.exception.NestJsErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * Manejador de errores personalizado para RestTemplate.
 * Intercepta respuestas con código de error (4xx, 5xx) del microservicio NestJS,
 * lee el cuerpo JSON del error y lanza la excepción especializada
 * ExternalServiceException que contiene los detalles de NestJS.
 */
@Component
public class CustomRestResponseErrorHandler implements ResponseErrorHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        // Indica a RestTemplate que considere 4xx y 5xx como errores que DEBEN ser manejados.
        return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        HttpStatus status = (HttpStatus) response.getStatusCode();
        NestJsErrorDto errorDetails = new NestJsErrorDto();

        try {
            // 1. Lee el cuerpo de la respuesta de error
            String responseBody = new BufferedReader(new InputStreamReader(response.getBody()))
                    .lines().collect(Collectors.joining("\n"));

            // 2. Intenta mapear el cuerpo a nuestro DTO de error de NestJS
            errorDetails = objectMapper.readValue(responseBody, NestJsErrorDto.class);

        } catch (Exception e) {
            // 3. Fallback si el cuerpo no es JSON o el mapeo falla
            errorDetails.setStatusCode(status.value());
            errorDetails.setError(status.getReasonPhrase());
            errorDetails.setMessage("Error de conexión o formato inesperado. Código: " + status.value());
        }

        // 4. Lanza la excepción personalizada
        throw new ExternalServiceException(status, errorDetails);
    }
}