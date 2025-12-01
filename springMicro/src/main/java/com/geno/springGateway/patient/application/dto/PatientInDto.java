package com.geno.springGateway.patient.application.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;


 /**
  * DTO de entrada utilizado para la creación de un paciente.
  * Contiene los datos necesarios para ser enviado al microservicio NestJS.
  */
@Data
@Schema(description = "DTO de entrada para crear/actualizar un paciente")

public class PatientInDto {
    @Schema(description = "Nombre(s) del paciente.", example = "Camy")
    private String firstName;
    @Schema(description = "Apellido(s) del paciente.", example = "Mendez")
    private String lastName;
     @Schema(description = "Fecha de nacimiento (formato yyyy-MM-dd).", example = "2000-01-01")
    private Date birthDate;
     @Schema(description = "Género del paciente.", example = "Femenino")
    private String gender;
     @Schema(description = "Estado actual del paciente (Ej: Activo, Inactivo, Fallecido).", example = "Activo")
    private String status;
}