package com.geno.springGateway.restTemplateDjango.geneticVariant.aplication.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de entrada de la Variante Genética (objeto anidado/poblado)")
public class GeneticVariantInDto {
    @Schema(example = "chr1")
    private String chromosome;
    @Schema(example = "1")
    private long gene;
    @Schema(example = "15000000")
    private Long position;
    @Schema(example = "C")
    @JsonProperty("reference_base")
    @JsonAlias("referenceBase")
    private String referenceBase;
    @Schema(example = "T")
    @JsonProperty("alternate_base")
    @JsonAlias("alternateBase")
    private String alternateBase;
    @Schema(example = "MODERATE")
    private String impact;
}
