package com.geno.springGateway.restTemplateNest.patient.application.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) utilizado para peticiones de actualización parcial (PATCH)
 * que modifican únicamente el campo 'name' del Paciente.
 * Su propósito es optimizar la comunicación enviando solo el campo a modificar al microservicio.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para actualizar el nombre del paciente (PATCH)")
public class UpdatePatientNameDto {

    @Schema(description = "Nuevo nombre del paciente", example = "Juana")
    @NotBlank(message = "El nombre no puede estar vacío.")
    private String firstName;
}