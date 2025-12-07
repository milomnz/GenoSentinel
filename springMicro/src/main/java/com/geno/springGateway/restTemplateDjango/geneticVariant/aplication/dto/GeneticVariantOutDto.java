package com.geno.springGateway.restTemplateDjango.geneticVariant.aplication.dto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de salida de la Variante Genética (objeto anidado/poblado)")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GeneticVariantOutDto {

    @Schema(example = "200")
    private Long id;
    @Schema(example = "chr1")
    private String chromosome;
    @Schema(example = "15000000")
    private Long position;
    @Schema(example = "C")
    private String referenceBase;
    @Schema(example = "T")
    private String alternateBase;
    @Schema(example = "MODERATE")
    private String impact;
}