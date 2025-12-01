package com.geno.springGateway.tumorType.application.service;
import com.geno.springGateway.common.ApiRestTemplateResponse;
import com.geno.springGateway.tumorType.application.dto.TumorTypeInDto;
import com.geno.springGateway.tumorType.application.dto.TumorTypeOutDto;
import com.geno.springGateway.tumorType.application.dto.UpdateTumorTypeNameDto;
import com.geno.springGateway.tumorType.application.dto.UpdateTumorTypeSystemAffectedDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Servicio que actúa como cliente REST para el microservicio de Tipos de Tumor (NestJS).
 * Define las operaciones CRUD y maneja la comunicación con el servicio externo,
 * asegurando que se utilicen los encabezados y DTOs correctos.
 * @author mendez
 */
@Service
@RequiredArgsConstructor
public class TumorTypeService {

    private final RestTemplate restTemplate;

    @Value("${tumortype.service.base-url}")
    private String tumorUrl;

    private HttpHeaders getCustomHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Source-System", "SpringGateway");
        return headers;
    }


    public List<TumorTypeOutDto> findAll() {
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());

        ResponseEntity<ApiRestTemplateResponse<List<TumorTypeOutDto>>> response = restTemplate.exchange(
                tumorUrl, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {}
        );
        return response.getBody().getData();
    }

    public TumorTypeOutDto findById(Long id) {
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());

        ResponseEntity<ApiRestTemplateResponse<TumorTypeOutDto>> response = restTemplate.exchange(
                tumorUrl + "/" + id, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {}
        );
        return response.getBody().getData();
    }

    public TumorTypeOutDto create(TumorTypeInDto dto) {
        HttpEntity<TumorTypeInDto> request = new HttpEntity<>(dto, getCustomHeaders());

        ResponseEntity<ApiRestTemplateResponse<TumorTypeOutDto>> response = restTemplate.exchange(
                tumorUrl, HttpMethod.POST, request, new ParameterizedTypeReference<>() {}
        );
        return response.getBody().getData();
    }



    /**
     * Actualiza el nombre del Tipo de Tumor (PATCH).
     */
    public TumorTypeOutDto patchName(Long id, UpdateTumorTypeNameDto dto) {
        HttpEntity<UpdateTumorTypeNameDto> request = new HttpEntity<>(dto, getCustomHeaders());

        ResponseEntity<ApiRestTemplateResponse<TumorTypeOutDto>> response = restTemplate.exchange(
                tumorUrl + "/" + id, HttpMethod.PATCH, request, new ParameterizedTypeReference<>() {}
        );
        return response.getBody().getData();
    }

    /**
     * Actualiza el sistema afectado del Tipo de Tumor (PATCH).
     */
    public TumorTypeOutDto patchSystemAffected(Long id, UpdateTumorTypeSystemAffectedDto dto) {
        HttpEntity<UpdateTumorTypeSystemAffectedDto> request = new HttpEntity<>(dto, getCustomHeaders());

        ResponseEntity<ApiRestTemplateResponse<TumorTypeOutDto>> response = restTemplate.exchange(
                tumorUrl + "/" + id, HttpMethod.PATCH, request, new ParameterizedTypeReference<>() {}
        );
        return response.getBody().getData();
    }

    public void delete(Long id) {
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());

        restTemplate.exchange(
                tumorUrl + "/" + id, HttpMethod.DELETE, entity, Void.class
        );
    }
}