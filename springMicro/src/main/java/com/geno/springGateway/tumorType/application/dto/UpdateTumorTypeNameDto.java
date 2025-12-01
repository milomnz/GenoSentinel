package com.geno.springGateway.tumorType.application.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "DTO para actualizar el nombre del Tipo de Tumor (PATCH)")
public class UpdateTumorTypeNameDto {

    @Schema(description = "Nuevo nombre del tipo de tumor", example = "Carcinoma")
    @NotBlank(message = "El nombre no puede estar vacío.")
    private String name;
}