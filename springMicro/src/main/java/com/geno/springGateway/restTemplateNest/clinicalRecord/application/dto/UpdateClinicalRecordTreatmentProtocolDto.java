package com.geno.springGateway.restTemplateNest.clinicalRecord.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * DTO (Data Transfer Object) utilizado para peticiones de actualización parcial (PATCH)
 * que modifican únicamente el campo del protocolo de tratamiento ('treatmentProtocol')
 * de una Historia Clínica.
 * Su uso optimiza las llamadas al microservicio enviando solo el campo a modificar.
*/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para actualizar el protocolo de tratamiento de la Historia Clínica (PATCH)")
public class UpdateClinicalRecordTreatmentProtocolDto {

    @Schema(description = "Protocolo de tratamiento asignado al paciente", example = "Quimioterapia + Inmunoterapia")
    @NotBlank(message = "El protocolo de tratamiento no puede estar vacío.")
    private String treatmentProtocol;
}