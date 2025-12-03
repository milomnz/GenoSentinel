package com.geno.springGateway.restTemplateNest.clinicalRecord.domain.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción de dominio lanzada cuando una Historia Clínica no se encuentra (HTTP 404).
 */
@ResponseStatus(HttpStatus.NOT_FOUND) // Asegura que Spring maneje esta excepción como 404
public class ClinicalRecordNotFoundException extends RuntimeException {

    // Constructor que acepta solo el mensaje (probablemente el que ya tienes)
    public ClinicalRecordNotFoundException(String message) {
        super(message);
    }


}