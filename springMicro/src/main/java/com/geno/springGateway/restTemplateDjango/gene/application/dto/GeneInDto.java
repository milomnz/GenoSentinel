package com.geno.springGateway.restTemplateDjango.gene.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de entrada utilizado para la creación de un nuevo Gen (POST).
 * Contiene los campos requeridos para el Gen en el microservicio Django.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de entrada para la creación de un Gen de Interes")
public class GeneInDto {

    @Schema(example = "BRCA1", description = "Simbolo de gen (Unico)")
    @NotBlank(message = "El simbolo es obligatorio")
    @Size(max = 20)
    private String symbol;

    @Schema(example = "Breast Cancer Type 1 Susceptibility Protein", description = "Nombre completo del gen.")
    @NotBlank(message = "El nombre completo es obligatorio.")
    private String fullName;

    @Schema(example = "Involucrado en la reparación del ADN.", description = "Resumen de la función del gen. Puede ser nulo/vacío.")
    private String functionSummary;
}
