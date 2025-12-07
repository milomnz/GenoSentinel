package com.geno.springGateway.restTemplateDjango.gene.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de salida para la actualización parcial (PATCH) de un Gen.
 * Contiene el id y los campos que fueron actualizados.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de salida para el patch de Gen")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenePatchOutDto {
    
    @Schema(description = "Id del gen que se actualizó")
    private Long id;

    @Schema(example = "Breast Cancer Type 1 Susceptibility Protein V2", description = "Nombre completo del gen actualizado.")
    private String fullName;

    @Schema(example = "Función actualizada: Reparación del ADN y estabilidad genómica.", description = "Resumen de la función del gen actualizado.")
    private String fuctionSummary;
}
