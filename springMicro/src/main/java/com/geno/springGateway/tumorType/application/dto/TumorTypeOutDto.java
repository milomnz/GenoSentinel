package com.geno.springGateway.tumorType.application.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
/**
 * DTO de salida que representa un Tipo de Tumor.
 * Utilizado para mapear la respuesta del microservicio NestJS.
 */

@Data
@Schema(description = "DTO de salida con los datos del Tipo de Tumor")
public class TumorTypeOutDto {
    @Schema(description = "Identificador único del tipo de tumor.", example = "10")
    private Long id;
    @Schema(description = "Nombre del tipo de tumor.", example = "Sarcoma")
    private String name;
    @Schema(description = "Sistema o área del cuerpo primariamente afectada.", example = "Sistema Muscular")
    private String systemAffected;
}