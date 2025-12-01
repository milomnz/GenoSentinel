package com.geno.springGateway.tumorType.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
  * DTO de entrada para la creación o actualización de un Tipo de Tumor.
  * Contiene los campos a enviar al microservicio NestJS.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de entrada para crear/actualizar un Tipo de Tumor")
public class TumorTypeInDto {
    @Schema(example = "Sarcoma")
    private String name;
    @Schema(example = "Sistema Muscular")
    private String systemAffected;
}