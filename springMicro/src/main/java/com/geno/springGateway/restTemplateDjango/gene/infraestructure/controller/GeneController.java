package com.geno.springGateway.restTemplateDjango.gene.infraestructure.controller;
import com.geno.springGateway.common.ApiRestTemplateResponse;
import com.geno.springGateway.restTemplateDjango.gene.application.dto.GeneInDto;
import com.geno.springGateway.restTemplateDjango.gene.application.dto.GeneOutDto;
import com.geno.springGateway.restTemplateDjango.gene.application.dto.GenePatchDto; // Importado
import com.geno.springGateway.restTemplateDjango.gene.application.service.GeneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Controlador REST que expone los endpoints para la gestión de Genes.
 * Actúa como pasarela (Gateway) para el microservicio de Genes de Django,
 * delegando toda la lógica de negocio y comunicación HTTP a {@link GeneService}.
 * La documentación Swagger (@Tag, @Operation) refleja el contrato de la API de Django.
 * @author mendez
 */

@RestController
@RequestMapping("/genes")
@RequiredArgsConstructor
@Tag(name = "Genes", description = "Operaciones CRUD sobre Genes de Interés (Proxy a Django)")
public class GeneController {

    private final GeneService geneService;

    @Operation(summary = "Obtener Gen por ID", description = "Busca un Gen específico por su ID (PK).")
    @ApiResponse(responseCode = "200", description = "Gen encontrado con éxito.")
    @ApiResponse(responseCode = "404", description = "Gen no encontrado.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiRestTemplateResponse<GeneOutDto>> getById(@PathVariable Long id){
        GeneOutDto gene = geneService.findById(id);
        return ResponseEntity.ok(new ApiRestTemplateResponse<>("Success", "Gen encontrado", gene));

    }

    @Operation(summary = "Obtener todos los Genes", description = "Recupera todos los Genes de Interés.")
    @GetMapping
    public ResponseEntity<ApiRestTemplateResponse<List<GeneOutDto>>> getAllGenes(){
        List<GeneOutDto>genes = geneService.findAll();
        return ResponseEntity.ok(new ApiRestTemplateResponse<>("Success", "Lista de Genes encontrada", genes));

    }

    @Operation(summary = "Crear nuevo Gen", description = "Registra un nuevo Gen de Interés.")
    @ApiResponse(responseCode = "201", description = "Gen creado exitosamente.")
    @ApiResponse(responseCode = "400", description = "Solicitud inválida (ej. símbolo duplicado).")
    @PostMapping
    public ResponseEntity<GeneOutDto> create(@Valid @RequestBody GeneInDto geneInDto, UriComponentsBuilder ucb){
        GeneOutDto created= geneService.create(geneInDto);
        URI locationUri = ucb.path("/genes/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(locationUri).body(created);

    }

    @Operation(summary = "Actualizar Parcialmente Gen (PATCH)",
            description = "Modifica solo el Nombre Completo o el Resumen de Función (según GenePatchDtoSerializer de Django).")
    @ApiResponse(responseCode = "200", description = "Gen actualizado con éxito.")
    @ApiResponse(responseCode = "404", description = "Gen no encontrado.")
    @PatchMapping("/{id}")
    public ResponseEntity<ApiRestTemplateResponse<GeneOutDto>> patchUpdate(@PathVariable Long id,
                                                                           @RequestBody GenePatchDto genePatchDto){
        GeneOutDto patched = geneService.patchUpdate(id, genePatchDto);
        return ResponseEntity.ok(new ApiRestTemplateResponse<>("Success", "Gen actualizado", patched));

    }

    @Operation(summary = "Eliminar Gen", description = "Elimina un Gen por su ID.")
    @ApiResponse(responseCode = "200", description = "Gen eliminado con éxito.")
    @ApiResponse(responseCode = "404", description = "Gen no encontrado.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiRestTemplateResponse<Void>>delete(@PathVariable Long id){
        geneService.delete(id);
        return ResponseEntity.ok(new ApiRestTemplateResponse<>("Success", "Gen eliminado", null));
    }

}
