package com.geno.springGateway.clinicalRecord.application.dto;
import com.geno.springGateway.patient.application.dto.PatientOutDto;
import com.geno.springGateway.tumorType.application.dto.TumorTypeOutDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Date;
import java.util.List;

/**
 * DTO de salida que representa una Historia Clínica.
 * Este DTO está diseñado para recibir datos "poblados" (nested objects)
 * del microservicio NestJS, incluyendo PatientOutDto y List<TumorTypeOutDto>.
 */

@Data
@Schema(description = "DTO de salida de Historia Clínica con objetos anidados (si Nest hace populate)")
public class ClinicalRecordOutDto {
    @Schema(description = "Identificador único de la historia clínica.", example = "100")
    private Long id;
    @Schema(description = "Objeto completo del paciente asociado.")
    private PatientOutDto patient;
    @Schema(description = "Lista de objetos completos de los Tipos de Tumor asociados.")
    private List<TumorTypeOutDto> tumorTypes;
    @Schema(description = "Fecha de diagnóstico.", example = "2006-03-13")
    private Date diagnosticDate;
    @Schema(description = "Etapa clínica del tumor.", example = "Stage II-A")
    private String stage;
    @Schema(description = "Protocolo de tratamiento.", example = "Quimioterapia + Radioterapia")
    private String treatmentProtocol;
}