package com.geno.springGateway.patient.infraestructure.controller;
import com.geno.springGateway.common.ApiResponse;
import com.geno.springGateway.patient.application.dto.PatientInDto;
import com.geno.springGateway.patient.application.dto.PatientOutDto;
import com.geno.springGateway.patient.application.dto.UpdatePatientNameDto;
import com.geno.springGateway.patient.application.dto.UpdatePatientStatusDto;
import com.geno.springGateway.patient.application.service.PatientService;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author mendez
 */
@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
@Tag(name = "Pacientes", description = "Operaciones CRUD sobre la entidad Paciente (Proxy a NestJS)")
public class PatientController {

    private final PatientService patientService;

    @Operation(summary = "Obtener lista de pacientes", description = "Recupera todos los pacientes del microservicio NestJS.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<PatientOutDto>>> getAll() {
        List<PatientOutDto> patients = patientService.findAll();
        return ResponseEntity.ok(new ApiResponse<>("Success", "Pacientes encontrados", patients));
    }

    @Operation(summary = "Obtener paciente por ID", description = "Busca un paciente específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente encontrado con éxito."),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado (ID no existe).")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientOutDto>> getById(@PathVariable Long id) {
        PatientOutDto dto = patientService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>("Success", "Paciente encontrado", dto));
    }

    @Operation(summary = "Crear nuevo paciente", description = "Registra un nuevo paciente en el sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Paciente creado exitosamente."),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida (error de validación en los datos de entrada).")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<PatientOutDto>> create(@RequestBody PatientInDto dto) {
        PatientOutDto created = patientService.create(dto);
        return new ResponseEntity<>(new ApiResponse<>("Created", "Paciente creado", created), HttpStatus.CREATED);
    }
    @Operation(summary = "Actualizar Nombre", description = "Actualiza solo el campo 'name' del paciente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Nombre actualizado con éxito."),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida (el nombre está vacío o es nulo)."),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado (ID no existe).")
    })
    @PatchMapping("/{id}/name")
    public ResponseEntity<ApiResponse<PatientOutDto>> patchName(
            @PathVariable Long id,
            @RequestBody UpdatePatientNameDto dto)
    {
        PatientOutDto patched = patientService.patchName(id, dto);
        return ResponseEntity.ok(new ApiResponse<>("Success", "Nombre actualizado", patched));
    }

    @Operation(summary = "Actualizar Estado", description = "Actualiza solo el campo 'status' del paciente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado actualizado con éxito."),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida (el estado está vacío o es nulo)."),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado (ID no existe).")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<PatientOutDto>> patchStatus(
            @PathVariable Long id,
            @RequestBody UpdatePatientStatusDto dto)
    {
        PatientOutDto patched = patientService.patchStatus(id, dto);
        return ResponseEntity.ok(new ApiResponse<>("Success", "Estado actualizado", patched));
    }

    @Operation(summary = "Eliminar paciente", description = "Elimina un paciente por su ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paciente eliminado con éxito."),
            @ApiResponse(responseCode = "404", description = "Paciente no encontrado (ID no existe).")
    }) @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        patientService.delete(id);
        return new ResponseEntity<>(new ApiResponse<>("Success", "Paciente eliminado", null), HttpStatus.OK);
    }
}