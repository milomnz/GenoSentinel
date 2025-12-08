package com.geno.springGateway.restTemplateNest.clinicalRecord.application.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * DTO (Data Transfer Object) utilizado para peticiones de actualización parcial (PATCH)
 * que modifican únicamente el campo de la etapa clínica ('stage') de una Historia Clínica.
 * Se usa para garantizar que solo se envíe la información mínima necesaria al microservicio.
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para actualizar la etapa clínica de la Historia Clínica (PATCH)")
public class UpdateClinicalRecordStageDto {

    @Schema(description = "Etapa clínica del tumor", example = "Stage III-B")
    @NotBlank(message = "La etapa no puede estar vacía.")
    private String stage;
}