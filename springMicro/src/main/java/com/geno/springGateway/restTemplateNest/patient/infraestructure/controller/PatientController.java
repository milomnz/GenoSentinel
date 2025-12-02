package com.geno.springGateway.restTemplateNest.patient.infraestructure.controller;
import com.geno.springGateway.common.ApiRestTemplateResponse;
import com.geno.springGateway.restTemplateNest.patient.application.dto.PatientInDto;
import com.geno.springGateway.restTemplateNest.patient.application.dto.PatientOutDto;
import com.geno.springGateway.restTemplateNest.patient.application.dto.UpdatePatientNameDto;
import com.geno.springGateway.restTemplateNest.patient.application.dto.UpdatePatientStatusDto;
import com.geno.springGateway.restTemplateNest.patient.application.service.PatientService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;

/**
 * Controlador REST para la gestión de Pacientes.
 * Actúa como un *Gateway* para el microservicio de Pacientes (NestJS),
 * manejando las peticiones HTTP y adaptando las respuestas.
 *
 * @author mendez
 */

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@Tag(name = "Pacientes", description = "Operaciones CRUD sobre la entidad Paciente (Proxy a NestJS)")
public class PatientController {

    private final PatientService patientService;

    @Operation(summary = "Obtener paciente por ID", description = "Busca un paciente específico.")
    @ApiResponse(responseCode = "404", description = "Paciente no encontrado (ID no existe).")
    @GetMapping("/{id}")
    public ResponseEntity<ApiRestTemplateResponse<PatientOutDto>> getById(@PathVariable Long id) {
        PatientOutDto dto = patientService.findById(id);
        return ResponseEntity.ok(new ApiRestTemplateResponse<>("Success", "Paciente encontrado", dto));
    }

    @Operation(summary = "Obtener lista de pacientes", description = "Recupera todos los pacientes del microservicio NestJS.")
    @GetMapping
    public ResponseEntity<ApiRestTemplateResponse<List<PatientOutDto>>> getAll() {
        List<PatientOutDto> patients = patientService.findAll();
        return ResponseEntity.ok(new ApiRestTemplateResponse<>("Success", "Pacientes encontrados", patients));
    }

    @Operation(summary = "Crear nuevo paciente", description = "Registra un nuevo paciente en el sistema.")
    @ApiResponse(responseCode = "201", description = "Paciente creado exitosamente.")
    @ApiResponse(responseCode = "400", description = "Solicitud inválida (error de validación en los datos de entrada).")
    @PostMapping
    public ResponseEntity<PatientOutDto> create(@RequestBody PatientInDto dto, UriComponentsBuilder ucb ) {

        PatientOutDto created = patientService.create(dto);

        URI locationUri = ucb.path("/patients/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity
                .created(locationUri)
                .body(created);
    }
    @Operation(summary = "Actualizar Nombre", description = "Actualiza solo el campo 'name' del paciente.")
    @ApiResponse(responseCode = "200", description = "Nombre actualizado con éxito.")
    @ApiResponse(responseCode = "400", description = "Solicitud inválida (el nombre está vacío o es nulo).")
    @ApiResponse(responseCode = "404", description = "Paciente no encontrado (ID no existe).")
    @PatchMapping("/{id}/name")
    public ResponseEntity<ApiRestTemplateResponse<PatientOutDto>> patchName(
            @PathVariable Long id,
            @RequestBody UpdatePatientNameDto dto)
    {
        PatientOutDto patched = patientService.patchName(id, dto);
        return ResponseEntity.ok(new ApiRestTemplateResponse<>("Success", "Nombre actualizado", patched));
    }

    @Operation(summary = "Actualizar Estado", description = "Actualiza solo el campo 'status' del paciente.")
    @ApiResponse(responseCode = "200", description = "Estado actualizado con éxito.")
    @ApiResponse(responseCode = "400", description = "Solicitud inválida (el estado está vacío o es nulo).")
    @ApiResponse(responseCode = "404", description = "Paciente no encontrado (ID no existe).")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiRestTemplateResponse<PatientOutDto>> patchStatus(
            @PathVariable Long id,
            @RequestBody UpdatePatientStatusDto dto)
    {
        PatientOutDto patched = patientService.patchStatus(id, dto);
        return ResponseEntity.ok(new ApiRestTemplateResponse<>("Success", "Estado actualizado", patched));
    }

    @Operation(summary = "Eliminar paciente", description = "Elimina un paciente por su ID.")
    @ApiResponse(responseCode = "200", description = "Paciente eliminado con éxito.")
    @ApiResponse(responseCode = "404", description = "Paciente no encontrado (ID no existe).")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiRestTemplateResponse<Void>> delete(@PathVariable Long id) {
        patientService.delete(id);
        return new ResponseEntity<>(new ApiRestTemplateResponse<>("Success", "Paciente eliminado", null), HttpStatus.OK);
    }
}