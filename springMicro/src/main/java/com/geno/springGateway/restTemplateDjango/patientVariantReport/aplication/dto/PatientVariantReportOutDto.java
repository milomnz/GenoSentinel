package com.geno.springGateway.restTemplateDjango.patientVariantReport.aplication.dto;


import com.geno.springGateway.restTemplateDjango.geneticVariant.aplication.dto.GeneticVariantOutDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO de salida que representa un Reporte de Variante de Paciente.
 * Está diseñado para recibir la respuesta "poblada" (nested object) de Django,
 * donde el ID de la variante se ha desreferenciado y reemplazado por el objeto
 * {@link GeneticVariantOutDto} completo.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de salida de reporte de variante con variante genetica anidada")
public class PatientVariantReportOutDto {

    @Schema(description = "Identificador único (PK).", example = "500")
    private Long id;

    @Schema(description = "ID del paciente.")
    private Long patientId;

    @Schema(description = "Objeto completo de la Variante Genética asociada (poblado por Django).")
    private PatientVariantReportOutDto variantid;

    @Schema(description = "Frecuencia alélica detectada.")
    private Double alleleFrequency;

    @Schema(description = "Fecha de detección.")
    private LocalDate detectionDate;
}
