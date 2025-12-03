package com.geno.springGateway.restTemplateDjango.geneticVariant.aplication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de salida de la Variante Genética (objeto anidado/poblado)")
public class GeneticVariantPatchDto {
    @Schema(example = "MODERATE")
    private String impact;
}
