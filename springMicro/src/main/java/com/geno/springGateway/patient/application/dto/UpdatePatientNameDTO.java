package com.geno.springGateway.patient.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Schema(description = "DTO para actualizar nombre del paciente")

public class UpdatePatientName {

    @NotBlank(message =  "El nombre no puede estar vacio")
    @Schema(description =  "Nuevo nombre del paciente", example = "Cami", required =true)
    private String name;
}
