package com.geno.springGateway.common.exception;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Excepción para envolver errores detallados recibidos de servicios externos.
 * Contiene el status HTTP y el detalle del cuerpo de error.
 */
@Getter
public class ExternalServiceException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final NestJsErrorDto errorDetails;

    public ExternalServiceException(HttpStatus httpStatus, NestJsErrorDto errorDetails) {
        // Usamos el mensaje del servicio externo como mensaje principal de la excepción
        super(errorDetails.getMessage() != null ? errorDetails.getMessage() : "Error desconocido en servicio externo");
        this.httpStatus = httpStatus;
        this.errorDetails = errorDetails;
    }
}