package com.geno.springGateway.restTemplateDjango.gene.domain.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
/**
 * Excepción lanzada cuando una solicitud (GET, PATCH, DELETE) a un recurso Gene
 * en el microservicio Django devuelve un código de estado 404 Not Found.
 * Esta excepción convierte el error HTTP en una excepción manejable del dominio.
 */
@Getter
public class GeneNotFoundException extends RuntimeException {

    private final HttpStatus httpStatus;

    public GeneNotFoundException(String message) {
        super(message);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }

}
