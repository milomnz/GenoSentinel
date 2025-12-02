package com.geno.springGateway.restTemplateDjango.gene.application.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de entrada utilizado para la actualización parcial (PATCH) de un Gen.
 * Contiene solo los campos permitidos por el GenePatchDtoSerializer de Django
 * (fullName y functionSummary).
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de entrada para actualizar parcialmente el Nombre Completo o Resumen de Función del Gen (PATCH)")
public class GenePatchDto {

    @Schema(example = "Breast Cancer Type 1 Susceptibility Protein V2", description = "Nuevo nombre completo del gen.")
    private String fullName;

    @Schema(example = "Función actualizada: Reparación del ADN y estabilidad genómica.", description = "Nuevo resumen de la función del gen.")
    private String fuctionSummary;



}
