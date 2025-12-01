package com.geno.springGateway.tumorType.infraestructure.controller;
import com.geno.springGateway.common.ApiRestTemplateResponse;
import com.geno.springGateway.tumorType.application.dto.*;
import com.geno.springGateway.tumorType.application.service.TumorTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;
/**
 * @author mendez
 */
@RestController
@RequestMapping("/tumor-types")
@RequiredArgsConstructor
@Tag(name = "Tipos de Tumor", description = "Operaciones CRUD sobre Tipos de Tumor (Proxy a NestJS)")
public class TumorTypeController {

    private final TumorTypeService tumorTypeService;


    @Operation(summary = "Obtener lista", description = "Recupera todos los Tipos de Tumor.")
    @GetMapping
    public ResponseEntity<ApiRestTemplateResponse<List<TumorTypeOutDto>>> getAll() {
        List<TumorTypeOutDto> tumors = tumorTypeService.findAll();
        return ResponseEntity.ok(new ApiRestTemplateResponse<>("Success", "Tipos de tumor encontrados", tumors));
    }

    @Operation(summary = "Obtener por ID", description = "Busca un Tipo de Tumor específico.")
    @ApiResponse(responseCode = "200", description = "Tipo de tumor encontrado con éxito.")
    @ApiResponse(responseCode = "404", description = "Tipo de tumor no encontrado (ID no existe).")
    @GetMapping("/{id}")
    public ResponseEntity<ApiRestTemplateResponse<TumorTypeOutDto>> getById(@PathVariable Long id) {
        TumorTypeOutDto dto = tumorTypeService.findById(id);
        return ResponseEntity.ok(new ApiRestTemplateResponse<>("Success", "Tipo de tumor encontrado", dto));
    }

    @Operation(summary = "Crear nuevo", description = "Registra un nuevo Tipo de Tumor.")
    @ApiResponse(responseCode = "201", description = "Tipo de tumor creado exitosamente.")
    @ApiResponse(responseCode = "400", description = "Solicitud inválida (error de validación en los datos de entrada).")
    @PostMapping
    public ResponseEntity<TumorTypeOutDto> create(@RequestBody TumorTypeInDto dto, UriComponentsBuilder ucb) {
        TumorTypeOutDto created = tumorTypeService.create(dto);
        URI locationUri = ucb.path("/tumor-types/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity
                .created(locationUri)
                .body(created);
    }


    @Operation(summary = "Actualizar Nombre (Parcial)", description = "Actualiza solo el campo 'name' del Tipo de Tumor.")
    @ApiResponse(responseCode = "200", description = "Nombre de tipo de tumor actualizado con éxito.")
    @ApiResponse(responseCode = "400", description = "Solicitud inválida (el nombre está vacío o es nulo).")
    @ApiResponse(responseCode = "404", description = "Tipo de tumor no encontrado (ID no existe).")
    @PatchMapping("/{id}/name")
    public ResponseEntity<ApiRestTemplateResponse<TumorTypeOutDto>> patchName( @PathVariable Long id, @RequestBody UpdateTumorTypeNameDto dto)
    {
        TumorTypeOutDto patched = tumorTypeService.patchName(id, dto);
        return ResponseEntity.ok(new ApiRestTemplateResponse<>("Success", "Nombre de tipo de tumor actualizado", patched));
    }

    @Operation(summary = "Actualizar Sistema Afectado (Parcial)", description = "Actualiza solo el campo 'systemAffected'.")

    @ApiResponse(responseCode = "200", description = "Sistema afectado actualizado con éxito.")
    @ApiResponse(responseCode = "400", description = "Solicitud inválida (el campo está vacío o es nulo).")
    @ApiResponse(responseCode = "404", description = "Tipo de tumor no encontrado (ID no existe).")

            @PatchMapping("/{id}/system-affected")
    public ResponseEntity<ApiRestTemplateResponse<TumorTypeOutDto>> patchSystemAffected(
            @PathVariable Long id,
            @RequestBody UpdateTumorTypeSystemAffectedDto dto)
    {
        TumorTypeOutDto patched = tumorTypeService.patchSystemAffected(id, dto);
        return ResponseEntity.ok(new ApiRestTemplateResponse<>("Success", "Sistema afectado actualizado", patched));
    }

    @Operation(summary = "Eliminar", description = "Elimina un Tipo de Tumor por su ID.")
            @ApiResponse(responseCode = "200", description = "Tipo de tumor eliminado con éxito.")
            @ApiResponse(responseCode = "404", description = "Tipo de tumor no encontrado (ID no existe).")

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiRestTemplateResponse<Void>> delete(@PathVariable Long id) {
        tumorTypeService.delete(id);
        return new ResponseEntity<>(new ApiRestTemplateResponse<>("Success", "Tipo de tumor eliminado", null), HttpStatus.OK);
    }

}