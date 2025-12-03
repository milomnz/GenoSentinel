package com.geno.springGateway.restTemplateDjango.geneticVariant.domain.exceptions;

import org.springframework.http.HttpStatus;

public class GeneticVariantNotFoundException extends RuntimeException {

    private final HttpStatus httpStatus;

    public GeneticVariantNotFoundException(String message) {
        super(message);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }
}
