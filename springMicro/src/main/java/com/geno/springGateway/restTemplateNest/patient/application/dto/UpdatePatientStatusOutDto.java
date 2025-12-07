package com.geno.springGateway.restTemplateNest.patient.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de salida para el patch de status")
public class UpdatePatientStatusOutDto {
    @Schema(description = "Id del usuario que se actualizo")
    private Long id;
    @Schema(description = "Nuevo estado del paciente (Ej: Activo, Inactivo)", example = "Inactivo")
    @NotBlank(message = "El estado no puede estar vacío.")
    private String status;
}
