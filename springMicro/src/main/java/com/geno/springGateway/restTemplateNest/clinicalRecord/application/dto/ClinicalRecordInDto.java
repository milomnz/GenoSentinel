package com.geno.springGateway.restTemplateNest.clinicalRecord.application.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * DTO de entrada para la creación y actualización de una Historia Clínica.
 * Incluye las referencias a otras entidades mediante IDs (idPatient, idTumorTypes).
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de entrada para crear/actualizar una Historia Clínica")
public class ClinicalRecordInDto {

    @Schema(example = "15", description = "ID del paciente asociado (FK)")
    private Long idPatient;

    @Schema(example = "[3, 4]", description = "Lista de IDs de Tipos de Tumor asociados")
    private List<Long> idTumorTypes;

    @Schema(example = "2006-03-13", description = "Fecha de diagnóstico")
    private LocalDate diagnosticDate;

    @Schema(example = "Stage II-A")
    private String stage;

    @Schema(example = "Quimioterapia + Radioterapia")
    private String treatmentProtocol;
}