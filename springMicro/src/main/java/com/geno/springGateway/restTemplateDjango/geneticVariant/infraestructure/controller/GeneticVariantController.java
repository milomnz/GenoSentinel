package com.geno.springGateway.restTemplateDjango.geneticVariant.infraestructure.controller;

import com.geno.springGateway.restTemplateDjango.geneticVariant.aplication.dto.*;
import com.geno.springGateway.restTemplateDjango.geneticVariant.aplication.service.IGeneticVariantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Controlador REST para la gestión de Variantes Genéticas.
 * Actúa como Gateway hacia el microservicio de Django.
 *
 * @author mendez
 */
@RestController
@RequestMapping("/genetic-variants")
@RequiredArgsConstructor
@Tag(name = "Variantes Genéticas", description = "Operaciones CRUD sobre Variantes Genéticas (Proxy a Django)")
@Slf4j
    public class GeneticVariantController {

    private final IGeneticVariantService geneticVariantService;

    @Operation(summary = "Obtener todas las variantes", description = "Recupera el listado completo de variantes genéticas.")
    @GetMapping
    public ResponseEntity<List<GeneticVariantOutDto>> getAll() {
        return ResponseEntity.ok(geneticVariantService.findAll());
    }

    @Operation(summary = "Obtener variante por ID", description = "Busca una variante genética específica por su ID.")
    @ApiResponse(responseCode = "200", description = "Variante encontrada.")
    @ApiResponse(responseCode = "404", description = "Variante no encontrada.")
    @GetMapping("/{id}")
    public ResponseEntity<GeneticVariantOutDto> getById(@PathVariable Long id) {
        return geneticVariantService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nueva variante", description = "Registra una nueva variante genética en el sistema.")
    @ApiResponse(responseCode = "201", description = "Variante creada exitosamente.")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos.")
    @PostMapping
    public ResponseEntity<GeneticVariantOutDto> create(@RequestBody GeneticVariantInDto dto,
                                                       UriComponentsBuilder ucb) {
        System.out.println("DTO recibido: " + dto);
        GeneticVariantOutDto created = geneticVariantService.create(dto);
        URI locationUri = ucb.path("/genetic-variants/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(locationUri).body(created);
    }

    @Operation(summary = "Actualizar parcialmente (PATCH)", description = "Actualiza campos específicos de una variante existente.")
    @ApiResponse(responseCode = "200", description = "Variante actualizada correctamente.")
    @ApiResponse(responseCode = "404", description = "Variante no encontrada.")
    @PatchMapping("/{id}")
    public ResponseEntity<GeneticVariantPatchOutDto> patchUpdate(@PathVariable Long id,
                                                            @RequestBody GeneticVariantPatchDto dto) {
        // mantenemos el .orElse por seguridad y consistencia con el tipo Optional.
        return geneticVariantService.patchUpdate(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar variante", description = "Elimina una variante genética por su ID.")
    @ApiResponse(responseCode = "204", description = "Variante eliminada (o no existía).")
    @ApiResponse(responseCode = "404", description = "Variante no encontrada (si el modo estricto está activo).")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        geneticVariantService.delete(id);
        return ResponseEntity.noContent().build();
    }
}