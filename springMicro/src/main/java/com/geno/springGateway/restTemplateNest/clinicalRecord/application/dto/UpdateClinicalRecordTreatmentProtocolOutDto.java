package com.geno.springGateway.restTemplateNest.clinicalRecord.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de salida para el patch de protocolo de tratamiento")
public class UpdateClinicalRecordTreatmentProtocolOutDto {
    @Schema(description = "Id de la historia clínica que se actualizó")
    private Long id;
    
    @Schema(description = "Protocolo de tratamiento asignado al paciente", example = "Quimioterapia + Inmunoterapia")
    @NotBlank(message = "El protocolo de tratamiento no puede estar vacío.")
    private String treatmentProtocol;
}

