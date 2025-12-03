package com.geno.springGateway.restTemplateNest.tumorType.domain.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción de dominio lanzada cuando un Tipo de Tumor no se encuentra (HTTP 404).
 */
@ResponseStatus(HttpStatus.NOT_FOUND) // Esto asegura que Spring maneje la respuesta como 404
public class TumorTypeNotFoundException extends RuntimeException {

    // Constructor que acepta solo el mensaje
    public TumorTypeNotFoundException(String message) {
        super(message);
    }


}