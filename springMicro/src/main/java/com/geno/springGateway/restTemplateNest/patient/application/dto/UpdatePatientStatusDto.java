package com.geno.springGateway.restTemplateNest.patient.application.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) utilizado para peticiones de actualización parcial (PATCH)
 * que modifican únicamente el campo 'status' del Paciente.
 * Se utiliza para cambiar el estado (ej. Activo/Inactivo) del paciente.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para actualizar el estado del paciente (PATCH)")
public class UpdatePatientStatusDto {

    @Schema(description = "Nuevo estado del paciente (Ej: Activo, Inactivo)", example = "Inactivo")
    @NotBlank(message = "El estado no puede estar vacío.")
    private String status;
}