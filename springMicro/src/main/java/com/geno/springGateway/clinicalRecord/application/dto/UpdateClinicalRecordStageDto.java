package com.geno.springGateway.clinicalRecord.application.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "DTO para actualizar la etapa clínica de la Historia Clínica (PATCH)")
public class UpdateClinicalRecordStageDto {

    @Schema(description = "Etapa clínica del tumor", example = "Stage III-B")
    @NotBlank(message = "La etapa no puede estar vacía.")
    private String stage;
}