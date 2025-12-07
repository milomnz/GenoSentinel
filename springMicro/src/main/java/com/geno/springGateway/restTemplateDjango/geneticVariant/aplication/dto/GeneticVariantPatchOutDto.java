package com.geno.springGateway.restTemplateDjango.geneticVariant.aplication.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de salida para la actualización parcial (PATCH) de una Variante Genética.
 * Contiene el id y el campo que fue actualizado.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de salida para el patch de Variante Genética")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeneticVariantPatchOutDto {
    
    @Schema(description = "Id de la variante genética que se actualizó")
    private Long id;
    
    @Schema(example = "MODERATE", description = "Impacto de la variante genética actualizado.")
    private String impact;
}
