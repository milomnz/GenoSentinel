package com.geno.springGateway.restTemplateNest.clinicalRecord.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de salida para el patch de etapa clínica")
public class UpdateClinicalRecordStageOutDto {
    @Schema(description = "Id de la historia clínica que se actualizó")
    private Long id;
    
    @Schema(description = "Nueva etapa clínica del tumor", example = "Stage III-B")
    @NotBlank(message = "La etapa no puede estar vacía.")
    private String stage;
}

