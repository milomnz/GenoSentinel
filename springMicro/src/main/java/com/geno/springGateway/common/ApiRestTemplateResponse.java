package com.geno.springGateway.common;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  Estructura de respuesta estandarizada para todas las salidas del API Gateway.
 *  Sigue el patrón común de API REST para envolver datos, estado y mensajes.
 *  @param <T> El tipo de dato contenido en la respuesta (Data).
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiRestTemplateResponse<T> {
    private String status;
    private String message;
    private T data;
}