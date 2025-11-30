package com.geno.springGateway.clinicalRecord.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "DTO para actualizar el protocolo de tratamiento de la Historia Clínica (PATCH)")
public class UpdateClinicalRecordTreatmentProtocolDto {

    @Schema(description = "Protocolo de tratamiento asignado al paciente", example = "Quimioterapia + Inmunoterapia")
    @NotBlank(message = "El protocolo de tratamiento no puede estar vacío.")
    private String treatmentProtocol;
}