package com.geno.springGateway.restTemplateNest.clinicalRecord.application.service;
import com.geno.springGateway.restTemplateNest.clinicalRecord.application.dto.*;
import com.geno.springGateway.restTemplateNest.clinicalRecord.domain.exceptions.ClinicalRecordNotFoundException;
import lombok.RequiredArgsConstructor;
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
 * Servicio que actúa como cliente REST para el microservicio de Historias Clínicas (NestJS).
 * Maneja las operaciones CRUD y la actualización parcial (PATCH) de los recursos,
 * utilizando {@link RestTemplate} para la comunicación HTTP.
 * Es responsable de la traducción de URLs y el manejo de excepciones 404.
 * @author mendez
 */
@Service
@RequiredArgsConstructor
public class ClinicalRecordService {

    private final RestTemplate restTemplate;

    @Value("${clinicalrecord.service.base-url}")
    private String clinicalUrl;

    /**
     * Define los headers personalizados (Content-Type y X-Source-System).
     */
    private HttpHeaders getCustomHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Source-System", "SpringGateway");
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public String buildUrl(long id) {
        return String.format("%s/%d", clinicalUrl, id);
    }

    public Optional<ClinicalRecordOutDto> findById(Long id) {
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());
        try {
            ResponseEntity<ClinicalRecordOutDto> response = restTemplate.exchange(
                    buildUrl(id),
                    HttpMethod.GET,
                    entity,
                    ClinicalRecordOutDto.class
            );
            // Envolvemos la respuesta. Si el body es null, devuelve Optional.empty()
            return Optional.ofNullable(response.getBody());

        } catch (HttpClientErrorException.NotFound e) {
            // IMPORTANTE: Si la API externa devuelve 404, devolvemos un Optional vacío
            // para que el controlador pueda usar .orElse(notFound())
            return Optional.empty();
        }
    }

    public List<ClinicalRecordOutDto> findAll() {
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());

        try {
            ResponseEntity<ClinicalRecordOutDto[]> response = restTemplate.exchange(
                    clinicalUrl, HttpMethod.GET, entity, ClinicalRecordOutDto[].class
            );

            ClinicalRecordOutDto[] body = response.getBody();

            // Si es null, devolvemos lista vacía, si no, convertimos
            return body != null ? Arrays.asList(body) : Collections.emptyList();

        } catch (HttpClientErrorException.NotFound e) {
            // Si falla, simplemente devolvemos lista vacía.
            return Collections.emptyList();
        }
    }

    public ClinicalRecordOutDto create(ClinicalRecordInDto dto) {
        HttpEntity<ClinicalRecordInDto> request = new HttpEntity<>(dto, getCustomHeaders());
        ResponseEntity<ClinicalRecordOutDto> response = restTemplate.exchange(
                clinicalUrl, // NestJS suele usar la raíz para el POST
                HttpMethod.POST,
                request,
                ClinicalRecordOutDto.class
        );
        return response.getBody();
    }

    /**
     * Implementación de PATCH para actualizar la Etapa Clínica.
     * @param id ID de la historia clínica.
     * @param dto DTO que contiene la nueva etapa.
     * @return Optional con el UpdateClinicalRecordStageOutDto actualizado.
     */
    public Optional<UpdateClinicalRecordStageOutDto> patchStage(Long id, UpdateClinicalRecordStageDto dto) {
        HttpEntity<UpdateClinicalRecordStageDto> request = new HttpEntity<>(dto, getCustomHeaders());
        String url = buildUrl(id) + "/stage";

        try {
            // Usamos Void.class porque NestJS puede devolver null
            ResponseEntity<Void> response = restTemplate.exchange(
                    url,
                    HttpMethod.PATCH,
                    request,
                    Void.class
            );

            // Si es exitoso (200 o 204)
            if (response.getStatusCode().is2xxSuccessful()) {
                UpdateClinicalRecordStageOutDto dtoOut = new UpdateClinicalRecordStageOutDto();
                dtoOut.setId(id);
                dtoOut.setStage(dto.getStage());
                return Optional.of(dtoOut);
            }

            return Optional.empty();

        } catch (HttpClientErrorException.NotFound e) {
            throw new ClinicalRecordNotFoundException("Historia Clínica con ID: " + id + " no encontrada para actualizar etapa");
        }
    }

    /**
     * Implementación de PATCH para actualizar el Protocolo de Tratamiento.
     * @param id ID de la historia clínica.
     * @param dto DTO que contiene el nuevo protocolo.
     * @return Optional con el UpdateClinicalRecordTreatmentProtocolOutDto actualizado.
     */
    public Optional<UpdateClinicalRecordTreatmentProtocolOutDto> patchTreatment(Long id, UpdateClinicalRecordTreatmentProtocolDto dto) {
        HttpEntity<UpdateClinicalRecordTreatmentProtocolDto> request = new HttpEntity<>(dto, getCustomHeaders());
        String url = buildUrl(id) + "/treatment-protocol";

        try {
            // Usamos Void.class porque NestJS puede devolver null
            ResponseEntity<Void> response = restTemplate.exchange(
                    url,
                    HttpMethod.PATCH,
                    request,
                    Void.class
            );

            // Si es exitoso (200 o 204)
            if (response.getStatusCode().is2xxSuccessful()) {
                UpdateClinicalRecordTreatmentProtocolOutDto dtoOut = new UpdateClinicalRecordTreatmentProtocolOutDto();
                dtoOut.setId(id);
                dtoOut.setTreatmentProtocol(dto.getTreatmentProtocol());
                return Optional.of(dtoOut);
            }

            return Optional.empty();

        } catch (HttpClientErrorException.NotFound e) {
            throw new ClinicalRecordNotFoundException("Historia Clínica con ID: " + id + " no encontrada para actualizar protocolo");
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
            throw new ClinicalRecordNotFoundException("Historia Clínica con ID: " + id + " no encontrada para eliminar");
        }
    }
}