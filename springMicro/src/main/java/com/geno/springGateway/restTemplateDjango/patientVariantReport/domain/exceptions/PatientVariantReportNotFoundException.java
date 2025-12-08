package com.geno.springGateway.restTemplateDjango.patientVariantReport.domain.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
/**
 * Excepción lanzada cuando una solicitud (GET, DELETE) a un Reporte de Variante
 * en el microservicio Django devuelve un código de estado 404 Not Found.
 * Esta excepción convierte el error HTTP en una excepción manejable del dominio.
 */
@Getter
public class PatientVariantReportNotFoundException extends RuntimeException {
    private final HttpStatus httpStatus;
    /**
     * Excepción lanzada cuando la respuesta del microservicio Django indica un 404 Not Found.
     */
    public PatientVariantReportNotFoundException(String message) {
        super(message);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }
}
