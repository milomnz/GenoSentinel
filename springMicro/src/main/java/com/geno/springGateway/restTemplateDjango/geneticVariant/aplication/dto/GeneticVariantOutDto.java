package com.geno.springGateway.restTemplateDjango.geneticVariant.aplication.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de salida de la Variante Genética (objeto anidado/poblado)")
@JsonIgnoreProperties(ignoreUnknown = true) // Ignora campos extra de Django
public class GeneticVariantOutDto {

    @Schema(example = "200")
    private Long id;
    
    @Schema(example = "chr1")
    private String chromosome;
    
    @Schema(example = "1")
    private Object gene; // Puede ser un ID o un objeto anidado de Django
    
    @Schema(example = "15000000")
    private Long position;
    
    @Schema(example = "C")
    private String referenceBase;
    
    @Schema(example = "T")
    private String alternateBase;
    
    @Schema(example = "MODERATE")
    private String impact;
}
