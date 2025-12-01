package com.geno.springGateway.common.exception;

import lombok.Data;

/**
 * DTO para mapear la estructura de error estándar devuelta por un microservicio NestJS
 * cuando ocurre un error (ej. 400, 404).
 */
@Data
public class NestJsErrorDto {
    private Integer statusCode;
    private String message;
    private String error;
}