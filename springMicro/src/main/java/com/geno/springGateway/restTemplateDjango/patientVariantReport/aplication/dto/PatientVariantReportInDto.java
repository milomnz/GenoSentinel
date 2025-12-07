package com.geno.springGateway.restTemplateDjango.patientVariantReport.aplication.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO de entrada utilizado para la creación de un Reporte de Variante (POST).
 * Contiene los IDs de referencia: {@code patientId} (referencia externa al MS Clínico)
 * y {@code variant} ID (referencia interna al MS GeneticVariant).
 * El microservicio Django es responsable de validar la existencia de estos IDs.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description ="DTO de entrada para crear un reporte de variante de paciente")
public class PatientVariantReportInDto {

    @Schema(example = "123456789", description = "ID del paciente (Validado por Django contra MS Clínico).")
    @NotNull(message = "El ID del paciente es obligatorio.")
    private Long patientId;

    @Schema(example = "200", description = "ID de la Variante Genética asociada (FK).")
    @NotNull(message = "El ID de la variante es obligatorio.")
    private Long variantId;

    @Schema(example = "0.0125", description = "Frecuencia alélica detectada (máx 4 decimales).")
    @DecimalMin(value = "0.0001", message = "La frecuencia alélica debe ser mayor a cero.")
    @NotNull(message = "La frecuencia alélica es obligatoria.")
    private Double alleleFrequency;

    @Schema(example = "2025-11-20", description = "Fecha de detección de la variante.")
    @NotNull(message = "La fecha de detección es obligatoria.")
    private LocalDate detectionDate;

}
