package com.geno.springGateway.restTemplateNest.patient.domain.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción de dominio lanzada cuando un Paciente no se encuentra (HTTP 404).
 */
@ResponseStatus(HttpStatus.NOT_FOUND) // Asegura que Spring maneje esta excepción como 404
public class PatientNotFoundException extends RuntimeException {

    // Constructor que acepta solo el mensaje (ya existente)
    public PatientNotFoundException(String message) {
        super(message);
    }

}