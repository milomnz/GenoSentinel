package com.geno.springGateway.restTemplateNest.patient.application.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;


/**
 * DTO de salida que representa un paciente con su ID.
 * Utilizado para mapear la respuesta (cuerpo 200) del microservicio NestJS
 * hacia el cliente del Gateway.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de salida con los datos completos del paciente")

public class PatientOutDto {
    @Schema(description = "Identificador único del paciente.", example = "1")
    private Long id;
    @Schema(description = "Nombre(s) del paciente.", example = "Camy")
    private String firstName;
    @Schema(description = "Apellido(s) del paciente.", example = "Mendez")
    private String lastName;
    @Schema(description = "Fecha de nacimiento (formato yyyy-MM-dd).", example = "2000-01-01")
    private LocalDate birthDate;
    @Schema(description = "Género del paciente.", example = "Femenino")
    private String gender;
    @Schema(description = "Estado actual del paciente.", example = "Activo")
    private String status;
}