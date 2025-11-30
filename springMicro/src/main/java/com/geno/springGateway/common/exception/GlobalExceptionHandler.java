package com.geno.springGateway.common.exception;
import com.geno.springGateway.common.ApiResponse;
import com.geno.springGateway.patient.domain.exceptions.PatientNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de excepciones (RestControllerAdvice).
 * Captura excepciones de dominio (ej. PatientNotFoundException) y excepciones
 * de infraestructura (ExternalServiceException) lanzadas por RestTemplate,
 * y las transforma en una respuesta ApiResponse estandarizada para el cliente.
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Manejo de Excepciones del Dominio (Negocio, ej. PatientNotFoundException)
    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handlePatientNotFound(PatientNotFoundException ex) {
        return new ResponseEntity<>(
                new ApiResponse<>("Not Found", ex.getMessage(), null),
                ex.getHttpStatus()
        );
    }

    // 2. Manejo de errores detallados del servicio externo (¡CRUCIAL!)
    // Captura la ExternalServiceException que contiene el cuerpo de error de NestJS
    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<ApiResponse<Void>> handleExternalServiceException(ExternalServiceException ex) {

        // Usamos el texto de error (e.g. "Bad Request") y el mensaje detallado de NestJS
        String statusText = ex.getErrorDetails().getError() != null
                ? ex.getErrorDetails().getError()
                : "Error en servicio externo";
        String message = ex.getMessage(); // Este es el mensaje detallado de NestJS

        return new ResponseEntity<>(
                new ApiResponse<>(statusText, message, null),
                ex.getHttpStatus() // Mantiene el código HTTP original (400, 404, 500)
        );
    }
}