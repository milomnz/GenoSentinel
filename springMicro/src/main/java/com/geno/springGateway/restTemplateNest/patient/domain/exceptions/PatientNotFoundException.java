package com.geno.springGateway.restTemplateNest.patient.domain.exceptions;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Excepción lanzada cuando una entidad Paciente no se encuentra en el dominio
 * o cuando la respuesta de NestJS es lógicamente vacía (404).
 * Esta excepción es capturada por el GlobalExceptionHandler.
 */


@Getter // Incluye getter para httpStatus
public class PatientNotFoundException extends RuntimeException {
    private final HttpStatus httpStatus;
    public PatientNotFoundException(String message) {
        super(message);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }
}