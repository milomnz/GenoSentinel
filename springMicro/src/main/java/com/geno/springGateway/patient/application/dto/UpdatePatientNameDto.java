package com.geno.springGateway.patient.application.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para actualizar el nombre del paciente (PATCH)")
public class UpdatePatientNameDto {

    @Schema(description = "Nuevo nombre del paciente", example = "Juana")
    @NotBlank(message = "El nombre no puede estar vacío.")
    private String name;
}