package com.geno.springGateway.restTemplateNest.tumorType.application.domain.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción lanzada cuando no se encuentra un Tipo de Tumor
 * en el microservicio NestJS.
 *
 * Mapea directamente al código de respuesta HTTP 404 (Not Found).
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TumorTypeNotFoundException extends RuntimeException {

    public TumorTypeNotFoundException(String message) {
        super(message);
    }

    public TumorTypeNotFoundException(Long id) {
        super("Tipo de Tumor con ID " + id + " no encontrado.");
    }
}