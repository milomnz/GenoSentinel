package com.geno.springGateway.restTemplateNest.tumorType.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de salida para el patch de nombre del tipo de tumor")
public class UpdateTumorTypeNameOutDto {
    @Schema(description = "Id del tipo de tumor que se actualizó")
    private Long id;
    
    @Schema(description = "Nuevo nombre del tipo de tumor", example = "Carcinoma")
    @NotBlank(message = "El nombre no puede estar vacío.")
    private String name;
}

