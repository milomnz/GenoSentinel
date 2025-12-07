package com.geno.springGateway.restTemplateNest.tumorType.infraestructure.controller;
import com.geno.springGateway.restTemplateNest.tumorType.application.dto.*;
import com.geno.springGateway.restTemplateNest.tumorType.application.service.TumorTypeService;
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
 * Controlador REST para la gestión de Tipos de Tumor.
 * Actúa como pasarela (Gateway) para el microservicio de Tipos de Tumor (NestJS).
 * @author mendez
 */
@RestController
@RequestMapping("/tumor-types")
@RequiredArgsConstructor
@Tag(name = "Tipos de Tumor", description = "Operaciones CRUD sobre Tipos de Tumor (Proxy a NestJS)")
public class TumorTypeController {

    private final TumorTypeService tumorTypeService;

    @Operation(summary = "Obtener lista", description = "Recupera todos los Tipos de Tumor.")
    @ApiResponse(responseCode = "200", description = "Lista de tipos de tumor.")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<TumorTypeOutDto>> findAll() {
        return ResponseEntity.ok(tumorTypeService.findAll());
    }

    @Operation(summary = "Obtener por ID", description = "Busca un Tipo de Tumor específico.")
    @ApiResponse(responseCode = "200", description = "Tipo de tumor encontrado con éxito.")
    @ApiResponse(responseCode = "404", description = "Tipo de tumor no encontrado (ID no existe).")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<TumorTypeOutDto> getById(@PathVariable Long id) {
        return tumorTypeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nuevo", description = "Registra un nuevo Tipo de Tumor.")
    @ApiResponse(responseCode = "201", description = "Tipo de tumor creado exitosamente.")
    @ApiResponse(responseCode = "400", description = "Solicitud inválida.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TumorTypeOutDto> create(@RequestBody TumorTypeInDto dto, UriComponentsBuilder ucb) {
        TumorTypeOutDto created = tumorTypeService.create(dto);
        URI locationUri = ucb.path("/tumor-types/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(locationUri).body(created);
    }

    @Operation(summary = "Actualizar Nombre (PATCH)", description = "Actualiza solo el campo 'name' del Tipo de Tumor.")
    @ApiResponse(responseCode = "200", description = "Nombre de tipo de tumor actualizado con éxito.")
    @ApiResponse(responseCode = "404", description = "Tipo de tumor no encontrado.")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/name")
    public ResponseEntity<UpdateTumorTypeNameOutDto> patchName(
            @PathVariable Long id,
            @RequestBody UpdateTumorTypeNameDto dto) {
        return tumorTypeService.patchName(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar Sistema Afectado (PATCH)", description = "Actualiza solo el campo 'systemAffected'.")
    @ApiResponse(responseCode = "200", description = "Sistema afectado actualizado con éxito.")
    @ApiResponse(responseCode = "404", description = "Tipo de tumor no encontrado.")
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/system-affected")
    public ResponseEntity<UpdateTumorTypeSystemAffectedOutDto> patchSystemAffected(
            @PathVariable Long id,
            @RequestBody UpdateTumorTypeSystemAffectedDto dto) {
        return tumorTypeService.patchSystemAffected(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar", description = "Elimina un Tipo de Tumor por su ID.")
    @ApiResponse(responseCode = "204", description = "Tipo de tumor eliminado con éxito (No Content).")
    @ApiResponse(responseCode = "404", description = "Tipo de tumor no encontrado.")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tumorTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}