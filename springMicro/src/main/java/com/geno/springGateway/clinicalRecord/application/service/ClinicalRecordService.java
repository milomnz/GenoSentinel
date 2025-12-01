package com.geno.springGateway.clinicalRecord.application.service;
import com.geno.springGateway.clinicalRecord.application.dto.UpdateClinicalRecordStageDto;
import com.geno.springGateway.clinicalRecord.application.dto.UpdateClinicalRecordTreatmentProtocolDto;
import com.geno.springGateway.common.ApiRestTemplateResponse;
import com.geno.springGateway.clinicalRecord.application.dto.ClinicalRecordInDto;
import com.geno.springGateway.clinicalRecord.application.dto.ClinicalRecordOutDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Servicio que actúa como cliente REST para el microservicio de Historias Clínicas (NestJS).
 * Maneja las operaciones que involucran DTOs con relaciones (IDs en InDto, objetos en OutDto),
 * asegurando la correcta tipificación de las respuestas REST.
 * @author mendez
 */
@Service
@RequiredArgsConstructor
public class ClinicalRecordService {

    private final RestTemplate restTemplate;

    @Value("${nest.service.url}/clinical-records")
    private String nestUrl;

    private HttpHeaders getCustomHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Source-System", "SpringGateway");
        return headers;
    }


    public List<ClinicalRecordOutDto> findAll() {
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());

        ResponseEntity<ApiRestTemplateResponse<List<ClinicalRecordOutDto>>> response = restTemplate.exchange(
                nestUrl, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {}
        );
        return response.getBody().getData();
    }

    public ClinicalRecordOutDto findById(Long id) {
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());

        ResponseEntity<ApiRestTemplateResponse<ClinicalRecordOutDto>> response = restTemplate.exchange(
                nestUrl + "/" + id, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {}
        );
        return response.getBody().getData();
    }

    public ClinicalRecordOutDto create(ClinicalRecordInDto dto) {
        HttpEntity<ClinicalRecordInDto> request = new HttpEntity<>(dto, getCustomHeaders());

        ResponseEntity<ApiRestTemplateResponse<ClinicalRecordOutDto>> response = restTemplate.exchange(
                nestUrl, HttpMethod.POST, request, new ParameterizedTypeReference<>() {}
        );
        return response.getBody().getData();
    }
    public ClinicalRecordOutDto patchStage(Long id, UpdateClinicalRecordStageDto dto) {
        HttpEntity<UpdateClinicalRecordStageDto> request = new HttpEntity<>(dto, getCustomHeaders());

        ResponseEntity<ApiRestTemplateResponse<ClinicalRecordOutDto>> response = restTemplate.exchange(
                nestUrl + "/" + id, HttpMethod.PATCH, request, new ParameterizedTypeReference<>() {}
        );
        return response.getBody().getData();
    }

    public ClinicalRecordOutDto patchTreatment(Long id, UpdateClinicalRecordTreatmentProtocolDto dto) {
        HttpEntity<UpdateClinicalRecordTreatmentProtocolDto> request = new HttpEntity<>(dto, getCustomHeaders());

        ResponseEntity<ApiRestTemplateResponse<ClinicalRecordOutDto>> response = restTemplate.exchange(
                nestUrl + "/" + id, HttpMethod.PATCH, request, new ParameterizedTypeReference<>() {}
        );
        return response.getBody().getData();
    }

    public void delete(Long id) {
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());

        restTemplate.exchange(
                nestUrl + "/" + id, HttpMethod.DELETE, entity, Void.class
        );
    }
}