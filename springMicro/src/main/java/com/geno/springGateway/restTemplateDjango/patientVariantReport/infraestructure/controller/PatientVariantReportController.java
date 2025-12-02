package com.geno.springGateway.restTemplateDjango.patientVariantReport.infraestructure.controller;


import com.geno.springGateway.common.ApiRestTemplateResponse;
import com.geno.springGateway.restTemplateDjango.patientVariantReport.aplication.dto.PatientVariantReportInDto;
import com.geno.springGateway.restTemplateDjango.patientVariantReport.aplication.dto.PatientVariantReportOutDto;
import com.geno.springGateway.restTemplateDjango.patientVariantReport.aplication.service.PatientVariantReportService;
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
 * Controlador REST que expone los endpoints para la gestión de Reportes de Variantes.
 * Actúa como pasarela (Gateway) para el microservicio de Reportes de Django.
 * Solo expone GET, POST y DELETE, en coherencia con el ViewSet de Django.
 * Es responsable de manejar las validaciones de entrada (@Valid) antes de pasar la solicitud a {@link PatientVariantReportService}.
 * @author mendez
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/patient-reports")
@Tag(name = "Reportes de Variantes de pacientes", description = "Operaciones CRUD sobre reportes ")
public class PatientVariantReportController {

    private final PatientVariantReportService reportService;

    @Operation(summary = "Obtener Reporte por ID", description = "Busca un Reporte específico por su ID (PK).")
    @ApiResponse(responseCode = "200", description = "Reporte encontrado con éxito.")
    @ApiResponse(responseCode = "404", description = "Reporte no encontrado.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiRestTemplateResponse<PatientVariantReportOutDto>> getById(@PathVariable Long id) {
        PatientVariantReportOutDto dto = reportService.findById(id);
        return ResponseEntity.ok(new ApiRestTemplateResponse<>("Success", "Reporte encontrado", dto));
    }

    @Operation(summary = "Obtener todos los Reportes", description = "Recupera todos los Reportes de Variantes.")
    @GetMapping
    public ResponseEntity<ApiRestTemplateResponse<List<PatientVariantReportOutDto>>> getAll() {
        List<PatientVariantReportOutDto> reports = reportService.findAll();
        return ResponseEntity.ok(new ApiRestTemplateResponse<>("Success", "Lista de Reportes encontrada", reports));
    }


    @Operation(summary = "Crear nuevo Reporte",
            description = "Registra un nuevo Reporte. La validación del ID del paciente es delegada a Django.")
    @ApiResponse(responseCode = "201", description = "Reporte creado exitosamente.")
    @ApiResponse(responseCode = "400", description = "Solicitud inválida (error de validación de Django).")
    @PostMapping
    public ResponseEntity<PatientVariantReportOutDto> create(@Valid @RequestBody PatientVariantReportInDto dto, UriComponentsBuilder ucb) {
        PatientVariantReportOutDto created = reportService.create(dto);
        URI locationUri = ucb.path("/patient-reports/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity
                .created(locationUri)
                .body(created);
    }

    @Operation(summary = "Eliminar Reporte", description = "Elimina un Reporte por su ID.")
    @ApiResponse(responseCode = "200", description = "Reporte eliminado con éxito.")
    @ApiResponse(responseCode = "404", description = "Reporte no encontrado.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiRestTemplateResponse<Void>> delete(@PathVariable Long id) {
        reportService.delete(id);
        return ResponseEntity.ok(new ApiRestTemplateResponse<>("Success", "Reporte eliminado", null));
    }
}
