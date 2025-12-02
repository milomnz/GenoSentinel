package com.geno.springGateway.restTemplateNest.patient.application.service;

import com.geno.springGateway.common.ApiRestTemplateResponse;
import com.geno.springGateway.restTemplateNest.patient.application.dto.PatientInDto;
import com.geno.springGateway.restTemplateNest.patient.application.dto.PatientOutDto;
import com.geno.springGateway.restTemplateNest.patient.application.dto.UpdatePatientNameDto;
import com.geno.springGateway.restTemplateNest.patient.application.dto.UpdatePatientStatusDto;
import com.geno.springGateway.restTemplateNest.patient.domain.exceptions.PatientNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Servicio que actúa como cliente REST para el microservicio de Pacientes (NestJS).
 * Implementa la lógica de comunicación HTTP a través de RestTemplate
 * y se encarga de manejar los DTOs de entrada y salida, delegando
 * el manejo de errores HTTP a la capa de Infraestructura/Configuración.
 * @author mendez
 */
@Service
@RequiredArgsConstructor
public class PatientService {

    private final RestTemplate restTemplate;

    @Value("${patient.service.base-url}")
    private String patientUrl;

    /**
     * @return Headers personalizados para la solicitud (ej. Content-Type y X-Source).
     */
    private HttpHeaders getCustomHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Source-System", "SpringGateway");
        return headers;
    }


    public PatientOutDto findById(Long id) {
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());

        // Si Nest devuelve 404/400/500, ExternalServiceException se lanza automáticamente
        // y el GlobalExceptionHandler lo maneja.
        ResponseEntity<ApiRestTemplateResponse<PatientOutDto>> response = restTemplate.exchange(
                patientUrl + "/" + id, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {}
        );

        // Verificación de lógica de negocio adicional si el cuerpo es 200 pero vacío
        if (response.getBody() == null || response.getBody().getData() == null) {
            throw new PatientNotFoundException("Paciente con ID " + id + " no encontrado (Cuerpo vacío).");
        }
        return response.getBody().getData();
    }

    public List<PatientOutDto> findAll() {
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());

        ResponseEntity<ApiRestTemplateResponse<List<PatientOutDto>>> response = restTemplate.exchange(
                patientUrl, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {}
        );
        return response.getBody().getData();
    }


    public PatientOutDto create(PatientInDto dto) {
        HttpEntity<PatientInDto> request = new HttpEntity<>(dto, getCustomHeaders());

        ResponseEntity<ApiRestTemplateResponse<PatientOutDto>> response = restTemplate.exchange(
                patientUrl, HttpMethod.POST, request, new ParameterizedTypeReference<>() {}
        );
        return response.getBody().getData();
    }

    /**
     * Actualiza el nombre del paciente.
     */
    public PatientOutDto patchName(Long id, UpdatePatientNameDto dto) {
        HttpEntity<UpdatePatientNameDto> request = new HttpEntity<>(dto, getCustomHeaders());

        // La URL de NestJS debe aceptar este cuerpo con solo el campo 'name'.
        ResponseEntity<ApiRestTemplateResponse<PatientOutDto>> response = restTemplate.exchange(
                patientUrl + "/" + id, HttpMethod.PATCH, request, new ParameterizedTypeReference<>() {}
        );
        return response.getBody().getData();
    }

    /**
     * Actualiza el estado del paciente.
     */
    public PatientOutDto patchStatus(Long id, UpdatePatientStatusDto dto) {
        HttpEntity<UpdatePatientStatusDto> request = new HttpEntity<>(dto, getCustomHeaders());

        // La URL de NestJS debe aceptar este cuerpo con solo el campo 'status'.
        ResponseEntity<ApiRestTemplateResponse<PatientOutDto>> response = restTemplate.exchange(
                patientUrl + "/" + id, HttpMethod.PATCH, request, new ParameterizedTypeReference<>() {}
        );
        return response.getBody().getData();
    }

    public void delete(Long id) {
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());

        restTemplate.exchange(
                patientUrl + "/" + id, HttpMethod.DELETE, entity, Void.class
        );
    }
}