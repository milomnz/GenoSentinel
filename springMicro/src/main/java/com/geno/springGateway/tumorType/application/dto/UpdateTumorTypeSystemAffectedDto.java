package com.geno.springGateway.tumorType.application.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para actualizar el sistema afectado del Tipo de Tumor (PATCH)")
public class UpdateTumorTypeSystemAffectedDto {

    @Schema(description = "Nuevo sistema afectado", example = "Sistema Digestivo")
    @NotBlank(message = "El sistema afectado no puede estar vacío.")
    private String systemAffected;
}