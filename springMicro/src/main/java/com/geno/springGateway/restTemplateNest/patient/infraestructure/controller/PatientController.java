package com.geno.springGateway.restTemplateNest.patient.infraestructure.controller;
import com.geno.springGateway.restTemplateNest.patient.application.dto.*;
import com.geno.springGateway.restTemplateNest.patient.application.service.PatientService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Controlador REST que expone los endpoints para la gestión de Pacientes.
 * Actúa como pasarela (Gateway) para el microservicio de Pacientes de NestJS,
 * delegando toda la lógica de negocio y comunicación HTTP a {@link PatientService}.
 *
 * @author mendez
 */
@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@Tag(name = "Pacientes", description = "Operaciones CRUD sobre la entidad Paciente (Proxy a NestJS)")
public class PatientController {

    private final PatientService patientService;

    @Operation(summary = "Obtener lista de pacientes", description = "Recupera todos los pacientes del microservicio NestJS.")
    @GetMapping
    public ResponseEntity<List<PatientOutDto>> findAll() {
        return ResponseEntity.ok(patientService.findAll());
    }

    @Operation(summary = "Obtener paciente por ID", description = "Busca un paciente específico.")
    @ApiResponse(responseCode = "200", description = "Paciente encontrado con éxito.")
    @ApiResponse(responseCode = "404", description = "Paciente no encontrado (ID no existe).")
    @GetMapping("/{id}")
    public ResponseEntity<PatientOutDto> getById(@PathVariable Long id) {
        return patientService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nuevo paciente", description = "Registra un nuevo paciente en el sistema.")
    @ApiResponse(responseCode = "201", description = "Paciente creado exitosamente.")
    @ApiResponse(responseCode = "400", description = "Solicitud inválida.")
    @PostMapping
    public ResponseEntity<PatientOutDto> create(@RequestBody PatientInDto dto, UriComponentsBuilder ucb) {
        PatientOutDto created = patientService.create(dto);
        URI locationUri = ucb.path("/patients/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(locationUri).body(created);
    }
    /*
    @Operation(summary = "Actualizar Nombre (PATCH)", description = "Actualiza solo el campo 'firstName' y 'lastName' del paciente.")
    @ApiResponse(responseCode = "200", description = "Nombre actualizado con éxito.")
    @ApiResponse(responseCode = "404", description = "Paciente no encontrado.")
    @PatchMapping("/{id}")
    public ResponseEntity<PatientOutDto> patchName(
            @PathVariable Long id,
            @RequestBody UpdatePatientNameDto dto) {
        return patientService.patchName(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    */
    @Operation(summary = "Actualizar Estado (PATCH)", description = "Actualiza solo el campo 'status' del paciente.")
    @ApiResponse(responseCode = "200", description = "Estado actualizado con éxito.")
    @ApiResponse(responseCode = "404", description = "Paciente no encontrado.")
    @PatchMapping("/{id}")
    public ResponseEntity<UpdatePatientStatusOutDto> patchStatus(
            @PathVariable Long id,
            @RequestBody UpdatePatientStatusDto dto) {
        return patientService.patchStatus(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar paciente", description = "Elimina un paciente por su ID.")
    @ApiResponse(responseCode = "204", description = "Paciente eliminado con éxito (No Content).")
    @ApiResponse(responseCode = "404", description = "Paciente no encontrado.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}