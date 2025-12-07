package com.geno.springGateway.restTemplateNest.patient.application.service;
import com.geno.springGateway.restTemplateNest.patient.application.dto.*;
import com.geno.springGateway.restTemplateNest.patient.domain.exceptions.PatientNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que actúa como cliente REST para el microservicio de Pacientes (NestJS).
 * Maneja las operaciones CRUD y la actualización parcial (PATCH),
 * replicando la estructura de manejo de Optional y excepciones del GeneService.
 *
 * @author mendez
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PatientService {

    private final RestTemplate restTemplate;

    @Value("${patient.service.base-url}")
    private String patientUrl;

    /**
     * Define los headers personalizados (Content-Type y X-Source-System).
     */
    private HttpHeaders getCustomHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Source-System", "SpringGateway");
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public String buildUrl(Long id) {
        return String.format("%s/%d", patientUrl, id);
    }

    /**
     * URL para operaciones PATCH específicas de este microservicio.
     */
    public String buildPatchUrl(Long id, String endpoint) {
        return String.format("%s/%s/%d", patientUrl, endpoint, id);
    }

    public Optional<PatientOutDto> findById(Long id) {
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());
        try {
            ResponseEntity<PatientOutDto> response = restTemplate.exchange(
                    buildUrl(id),
                    HttpMethod.GET,
                    entity,
                    PatientOutDto.class
            );
            // Si no hay 404, devolvemos el cuerpo envuelto en Optional
            return Optional.ofNullable(response.getBody());

        } catch (HttpClientErrorException.NotFound e) {
            // Manejamos 404 devolviendo Optional.empty()
            return Optional.empty();
        }
    }

    public List<PatientOutDto> findAll() {
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());

        try {
            // Usamos Array como en el ejemplo GeneService
            ResponseEntity<PatientOutDto[]> response = restTemplate.exchange(
                    patientUrl, HttpMethod.GET, entity, PatientOutDto[].class
            );

            PatientOutDto[] body = response.getBody();
            // Si es null, devolvemos lista vacía, si no, convertimos
            return body != null ? Arrays.asList(body) : Collections.emptyList();

        } catch (HttpClientErrorException.NotFound e) {
            return Collections.emptyList();
        }
    }

    public PatientOutDto create(PatientInDto dto) {
        HttpEntity<PatientInDto> request = new HttpEntity<>(dto, getCustomHeaders());
        ResponseEntity<PatientOutDto> response = restTemplate.exchange(
                patientUrl,
                HttpMethod.POST,
                request,
                PatientOutDto.class
        );
        return response.getBody();
    }

    public Optional<PatientOutDto> patchName(Long id, UpdatePatientNameDto dto) {
        HttpEntity<UpdatePatientNameDto> request = new HttpEntity<>(dto, getCustomHeaders());
        String url = buildPatchUrl(id, "updatename");
        log.debug(url);
        try {
            ResponseEntity<PatientOutDto> response = restTemplate.exchange(
                    "http://localhost:3000/patients/updatestatus/"+ id,
                    HttpMethod.PATCH,
                    request,
                    PatientOutDto.class
            );
            return Optional.ofNullable(response.getBody());

        } catch (HttpClientErrorException.NotFound e) {
            // Se lanza excepción de dominio, el controlador devolverá 404
            throw new PatientNotFoundException("Paciente con ID: " + id + " no encontrado para actualizar nombre");
        }
    }

    // AQUI

    public Optional<UpdatePatientStatusOutDto> patchStatus(Long id, UpdatePatientStatusDto dto) {
        HttpEntity<UpdatePatientStatusDto> request = new HttpEntity<>(dto, getCustomHeaders());
        String fullUrl = buildPatchUrl(id, "updatestatus");

        try {
            // Hacemos la petición.
            // Usamos Void.class porque sabemos que NestJS devuelve null y así evitamos confusiones
            ResponseEntity<Void> response = restTemplate.exchange(
                    fullUrl,
                    HttpMethod.PATCH,
                    request,
                    Void.class
            );

            // Si es exitoso (200 o 204)
            if (response.getStatusCode().is2xxSuccessful()) {
                UpdatePatientStatusOutDto dtoOut = new UpdatePatientStatusOutDto();
                dtoOut.setId(id);
                dtoOut.setStatus(dto.getStatus());
                return Optional.of(dtoOut);
            }

            return Optional.empty();

        } catch (HttpClientErrorException.NotFound e) {
            throw new PatientNotFoundException("Paciente con ID: " + id + " no encontrado para actualizar estado");
        }
    }

    public void delete(Long id) {
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());
        try {
            restTemplate.exchange(
                    buildUrl(id),
                    HttpMethod.DELETE,
                    entity,
                    Void.class
            );
        } catch (HttpClientErrorException.NotFound e) {
            // Lanzamos la excepción para que el GlobalExceptionHandler devuelva un 404
            throw new PatientNotFoundException("Paciente con ID: " + id + " no encontrado para eliminar");
        }
    }
}