package com.geno.springGateway.restTemplateNest.clinicalRecord.infraestructure.controller;
import com.geno.springGateway.restTemplateNest.clinicalRecord.application.dto.*;
import com.geno.springGateway.restTemplateNest.clinicalRecord.application.service.ClinicalRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Controlador REST que expone los endpoints para la gestión de Historias Clínicas.
 * Actúa como pasarela (Gateway) para el microservicio de Historias Clínicas (NestJS).
 * @author mendez
 */
@RestController
@RequestMapping("/clinical-records")
@RequiredArgsConstructor
@Tag(name = "Historias Clínicas", description = "Operaciones CRUD sobre Historias Clínicas (Proxy a NestJS)")
public class ClinicalRecordController {

    private final ClinicalRecordService clinicalRecordService;

    @Operation(summary = "Obtener todas las Historias Clínicas", description = "Recupera todas las Historias Clínicas.")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<ClinicalRecordOutDto>> findAll() {
        return ResponseEntity.ok(clinicalRecordService.findAll());
    }

    @Operation(summary = "Obtener Historia Clínica por ID", description = "Busca una Historia Clínica específica por su ID.")
    @ApiResponse(responseCode = "200", description = "Encontrada con éxito.")
    @ApiResponse(responseCode = "404", description = "No encontrada.")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ClinicalRecordOutDto> getById(@PathVariable Long id) {
        return clinicalRecordService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nueva Historia Clínica", description = "Registra una nueva Historia Clínica.")
    @ApiResponse(responseCode = "201", description = "Creada exitosamente.")
    @ApiResponse(responseCode = "400", description = "Solicitud inválida.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ClinicalRecordOutDto> create(@RequestBody ClinicalRecordInDto dto, UriComponentsBuilder ucb) {
        ClinicalRecordOutDto created = clinicalRecordService.create(dto);
        URI locationUri = ucb.path("/clinical-records/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(locationUri).body(created);
    }

    @Operation(summary = "Actualizar Etapa Clínica (PATCH)", description = "Actualiza solo el campo 'stage'.")
    @ApiResponse(responseCode = "200", description = "Actualizada con éxito.")
    @ApiResponse(responseCode = "404", description = "No encontrada.")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/stage")
    public ResponseEntity<UpdateClinicalRecordStageOutDto> patchStage(@PathVariable Long id,
                                                           @RequestBody UpdateClinicalRecordStageDto dto) {
        return clinicalRecordService.patchStage(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar Protocolo de Tratamiento (PATCH)", description = "Actualiza solo el campo 'treatmentProtocol'.")
    @ApiResponse(responseCode = "200", description = "Actualizado con éxito.")
    @ApiResponse(responseCode = "404", description = "No encontrada.")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/treatment-protocol")
    public ResponseEntity<UpdateClinicalRecordTreatmentProtocolOutDto> patchTreatment(@PathVariable Long id,
                                                               @RequestBody UpdateClinicalRecordTreatmentProtocolDto dto) {
        return clinicalRecordService.patchTreatment(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar Historia Clínica", description = "Elimina una Historia Clínica por su ID.")
    @ApiResponse(responseCode = "204", description = "Eliminada con éxito.")
    @ApiResponse(responseCode = "404", description = "No encontrada.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clinicalRecordService.delete(id);
        return ResponseEntity.noContent().build();
    }
}