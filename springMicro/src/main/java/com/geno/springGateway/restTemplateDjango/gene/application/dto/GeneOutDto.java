package com.geno.springGateway.restTemplateDjango.gene.application.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de salida que representa un Gen de Interés.
 * Este DTO está diseñado para recibir la respuesta plana (no anidada)
 * de las operaciones GET/POST/PATCH del microservicio Django.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de salida que representa un Gen de Interés recuperado de Django")
public class GeneOutDto {
    @Schema(description = "Identificador único (PK).", example = "101")
    private Long id;

    @Schema(example = "BRCA1")
    private String symbol;

    @Schema(example = "Breast Cancer Type 1 Susceptibility Protein")
    private String fullName;

    @Schema(example = "Involucrado en la reparación del ADN.")
    private String functionSummary;




}
