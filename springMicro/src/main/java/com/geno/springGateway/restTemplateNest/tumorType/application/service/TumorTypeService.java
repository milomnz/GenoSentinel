package com.geno.springGateway.restTemplateNest.tumorType.application.service;
import com.geno.springGateway.restTemplateNest.tumorType.domain.exceptions.TumorTypeNotFoundException;
import com.geno.springGateway.restTemplateNest.tumorType.application.dto.*;
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
 * Servicio que actúa como cliente REST para el microservicio de Tipos de Tumor (NestJS).
 * Implementa la lógica de comunicación HTTP a través de RestTemplate, siguiendo el patrón GeneService.
 * @author mendez
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TumorTypeService {

    private final RestTemplate restTemplate;

    @Value("${tumortype.service.base-url}")
    private String tumorUrl;

    /**
     * Construye los headers HTTP personalizados para las solicitudes REST.
     */
    private HttpHeaders getCustomHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Source-System", "SpringGateway");
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public String buildUrl(Long id) {
        return String.format("%s/%d", tumorUrl, id);
    }

    public Optional<TumorTypeOutDto> findById(Long id) {
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());

        try {
            ResponseEntity<TumorTypeOutDto> response = restTemplate.exchange(
                    buildUrl(id),
                    HttpMethod.GET,
                    entity,
                    TumorTypeOutDto.class
            );
            return Optional.ofNullable(response.getBody());

        } catch (HttpClientErrorException.NotFound e) {
            // Manejamos 404 devolviendo Optional.empty()
            return Optional.empty();
        }
    }

    public List<TumorTypeOutDto> findAll() {
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());

        try {
            // Usamos Array como en el ejemplo GeneService
            ResponseEntity<TumorTypeOutDto[]> response = restTemplate.exchange(
                    tumorUrl, HttpMethod.GET, entity, TumorTypeOutDto[].class
            );

            TumorTypeOutDto[] body = response.getBody();
            return body != null ? Arrays.asList(body) : Collections.emptyList();

        } catch (HttpClientErrorException.NotFound e) {
            // Si el endpoint devuelve 404, retornamos lista vacía
            return Collections.emptyList();
        }
    }

    public TumorTypeOutDto create(TumorTypeInDto dto) {
        HttpEntity<TumorTypeInDto> request = new HttpEntity<>(dto, getCustomHeaders());

        ResponseEntity<TumorTypeOutDto> response = restTemplate.exchange(
                tumorUrl,
                HttpMethod.POST,
                request,
                TumorTypeOutDto.class
        );
        return response.getBody();
    }

    public Optional<UpdateTumorTypeNameOutDto> patchName(Long id, UpdateTumorTypeNameDto dto) {
        HttpEntity<UpdateTumorTypeNameDto> request = new HttpEntity<>(dto, getCustomHeaders());
        String url = buildUrl(id) + "/name";

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
                UpdateTumorTypeNameOutDto dtoOut = new UpdateTumorTypeNameOutDto();
                dtoOut.setId(id);
                dtoOut.setName(dto.getName());
                return Optional.of(dtoOut);
            }

            return Optional.empty();

        } catch (HttpClientErrorException.NotFound e) {
            // Se lanza excepción de dominio
            throw new TumorTypeNotFoundException("Tipo de Tumor con ID: " + id + " no encontrado para actualizar nombre");
        }
    }

    public Optional<UpdateTumorTypeSystemAffectedOutDto> patchSystemAffected(Long id, UpdateTumorTypeSystemAffectedDto dto) {
        HttpEntity<UpdateTumorTypeSystemAffectedDto> request = new HttpEntity<>(dto, getCustomHeaders());
        String url = buildUrl(id) + "/system-affected";

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
                UpdateTumorTypeSystemAffectedOutDto dtoOut = new UpdateTumorTypeSystemAffectedOutDto();
                dtoOut.setId(id);
                dtoOut.setSystemAffected(dto.getSystemAffected());
                return Optional.of(dtoOut);
            }

            return Optional.empty();

        } catch (HttpClientErrorException.NotFound e) {
            // Se lanza excepción de dominio
            throw new TumorTypeNotFoundException("Tipo de Tumor con ID: " + id + " no encontrado para actualizar sistema afectado");
        }
    }

    public void delete(Long id) {
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());

        try {
            restTemplate.exchange(
                    buildUrl(id), HttpMethod.DELETE, entity, Void.class
            );
        } catch (HttpClientErrorException.NotFound e) {
            // Lanzamos la excepción para que el GlobalExceptionHandler devuelva un 404
            throw new TumorTypeNotFoundException("Tipo de tumor con ID: " + id + " no encontrado para eliminar");
        }
    }
}