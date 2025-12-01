package com.geno.springGateway.clinicalRecord.infraestructure.controller;
import com.geno.springGateway.clinicalRecord.application.dto.UpdateClinicalRecordStageDto;
import com.geno.springGateway.clinicalRecord.application.dto.UpdateClinicalRecordTreatmentProtocolDto;
import com.geno.springGateway.common.ApiRestTemplateResponse;
import com.geno.springGateway.clinicalRecord.application.dto.ClinicalRecordInDto;
import com.geno.springGateway.clinicalRecord.application.dto.ClinicalRecordOutDto;
import com.geno.springGateway.clinicalRecord.application.service.ClinicalRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author mendez
 */
@RestController
@RequestMapping("/api/v1/clinical-records")//Modificarlo
@RequiredArgsConstructor
@Tag(name = "Historias Clínicas", description = "Operaciones CRUD sobre Historias Clínicas (Proxy a NestJS)")
public class ClinicalRecordController {

    private final ClinicalRecordService clinicalRecordService;

    @Operation(summary = "Obtener lista", description = "Recupera todas las Historias Clínicas.")
    @GetMapping
    public ResponseEntity<ApiRestTemplateResponse<List<ClinicalRecordOutDto>>> getAll() {
        List<ClinicalRecordOutDto> records = clinicalRecordService.findAll();
        return ResponseEntity.ok(new ApiRestTemplateResponse<>("Success", "Historias Clínicas encontradas", records));
    }

    @Operation(summary = "Obtener por ID", description = "Busca una Historia Clínica específica.")
    @ApiResponse(responseCode = "200", description = "Historia Clínica encontrada con éxito.")
    @ApiResponse(responseCode = "404", description = "Historia Clínica no encontrada (ID no existe).")
    @GetMapping("/{id}")
    public ResponseEntity<ApiRestTemplateResponse<ClinicalRecordOutDto>> getById(@PathVariable Long id) {
        ClinicalRecordOutDto dto = clinicalRecordService.findById(id);
        return ResponseEntity.ok(new ApiRestTemplateResponse<>("Success", "Historia Clínica encontrada", dto));
    }

    @Operation(summary = "Crear nuevo", description = "Registra una nueva Historia Clínica.")
    @ApiResponse(responseCode = "201", description = "Historia Clínica creada exitosamente.")
    @ApiResponse(responseCode = "400", description = "Solicitud inválida (error de validación en los datos de entrada, ej. ID de paciente o tumor vacío).")
    @PostMapping
    public ResponseEntity<ApiRestTemplateResponse<ClinicalRecordOutDto>> create(@RequestBody ClinicalRecordInDto dto) {
        ClinicalRecordOutDto created = clinicalRecordService.create(dto);
        return new ResponseEntity<>(new ApiRestTemplateResponse<>("Created", "Historia Clínica creada", created), HttpStatus.CREATED);
    }
    @Operation(summary = "Actualizar Etapa Clínica", description = "Actualiza solo el campo 'stage'.")
    @ApiResponse(responseCode = "200", description = "Etapa Clínica actualizada con éxito.")
    @ApiResponse(responseCode = "400", description = "Solicitud inválida (el campo está vacío o es nulo).")
    @ApiResponse(responseCode = "404", description = "Historia Clínica no encontrada (ID no existe).")
    @PatchMapping("/{id}/stage")
    public ResponseEntity<ApiRestTemplateResponse<ClinicalRecordOutDto>> patchStage(
            @PathVariable Long id,
            @RequestBody UpdateClinicalRecordStageDto dto)
    {
        ClinicalRecordOutDto patched = clinicalRecordService.patchStage(id, dto);
        return ResponseEntity.ok(new ApiRestTemplateResponse<>("Success", "Etapa clínica actualizada", patched));
    }

    @Operation(summary = "Actualizar Protocolo de Tratamiento", description = "Actualiza solo el campo 'treatmentProtocol'.")
    @ApiResponses({
            @ApiRestTemplateResponse(responseCode = "200", description = "Protocolo de tratamiento actualizado con éxito."),
            @ApiRestTemplateResponse(responseCode = "400", description = "Solicitud inválida (el campo está vacío o es nulo)."),
            @ApiRestTemplateResponse(responseCode = "404", description = "Historia Clínica no encontrada (ID no existe).")
    })
    @PatchMapping("/{id}/treatment-protocol")
    public ResponseEntity<ApiRestTemplateResponse<ClinicalRecordOutDto>> patchTreatment(
            @PathVariable Long id,
            @RequestBody UpdateClinicalRecordTreatmentProtocolDto dto)
    {
        ClinicalRecordOutDto patched = clinicalRecordService.patchTreatment(id, dto);
        return ResponseEntity.ok(new ApiRestTemplateResponse<>("Success", "Protocolo de tratamiento actualizado", patched));
    }

    @Operation(summary = "Eliminar", description = "Elimina una Historia Clínica por su ID.")
    @ApiResponses({
            @ApiRestTemplateResponse(responseCode = "200", description = "Historia Clínica eliminada con éxito."),
            @ApiRestTemplateResponse(responseCode = "404", description = "Historia Clínica no encontrada (ID no existe).")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiRestTemplateResponse<Void>> delete(@PathVariable Long id) {
        clinicalRecordService.delete(id);
        return new ResponseEntity<>(new ApiRestTemplateResponse<>("Success", "Historia Clínica eliminada", null), HttpStatus.OK);
    }
}