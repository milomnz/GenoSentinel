package com.geno.springGateway.restTemplateDjango.gene.application.service.impl;

import com.geno.springGateway.restTemplateDjango.gene.application.service.IGeneService;
import org.springframework.http.MediaType;
import com.geno.springGateway.restTemplateDjango.gene.application.dto.*;
import com.geno.springGateway.restTemplateDjango.gene.domain.exceptions.GeneNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que actúa como cliente REST para el microservicio de Genes (Django).
 * Maneja las operaciones CRUD y la actualización parcial (PATCH) de los recursos Gene,
 * utilizando {@link RestTemplate} para la comunicación HTTP.
 * Es responsable de la traducción de URLs y el manejo de excepciones 404 de Django.
 * @author milomnz
 */
@Service
public class GeneService implements IGeneService {
    
    private final RestTemplate restTemplate;

    @Value("${gene.service.base-url}")
    private String geneUrl;

    public GeneService(@Qualifier("djangoRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

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
        return String.format("%s/%d", geneUrl, id);
    }

    public Optional<GeneOutDto> findById(Long id) {
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());
        try {
            ResponseEntity<GeneOutDto> response = restTemplate.exchange(
                    buildUrl(id),
                    HttpMethod.GET,
                    entity,
                    GeneOutDto.class
            );
            // Envolvemos la respuesta. Si el body es null, devuelve Optional.empty()
            return Optional.ofNullable(response.getBody());

        } catch (HttpClientErrorException.NotFound e) {
            // IMPORTANTE: Si la API externa devuelve 404, devolvemos un Optional vacío
            // para que el controlador pueda usar .orElse(notFound())
            return Optional.empty();
        }
    }

    // Cambia el retorno a List<GeneOutDto> directo
    public List<GeneOutDto> findAll() {
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());

        try {
            ResponseEntity<GeneOutDto[]> response = restTemplate.exchange(
                    geneUrl + "/", HttpMethod.GET, entity, GeneOutDto[].class
            );

            GeneOutDto[] body = response.getBody();

            // Si es null, devolvemos lista vacía, si no, convertimos
            return body != null ? Arrays.asList(body) : Collections.emptyList();

        } catch (HttpClientErrorException.NotFound e) {
            // Si falla, simplemente devolvemos lista vacía.
            return Collections.emptyList();
        }
    }

    public GeneOutDto create(GeneInDto dto) {
        HttpEntity<GeneInDto> request = new HttpEntity<>(dto, getCustomHeaders());
        ResponseEntity<GeneOutDto> response = restTemplate.exchange(
                geneUrl + "/",
                HttpMethod.POST,
                request,
                GeneOutDto.class
        );
        return response.getBody();
    }

    /**
     * Implementación de PATCH usando el DTO específico GenePatchDto.
     * @param id ID del gen a actualizar.
     * @param dto DTO que contiene fullName y/o functionSummary.
     * @return GenePatchOutDto con el id y los campos actualizados.
     */
    public Optional<GenePatchOutDto> patchUpdate(Long id, GenePatchDto dto) {
        HttpEntity<GenePatchDto> request = new HttpEntity<>(dto, getCustomHeaders());
        String url = buildUrl(id) + "/";

        try {
            // Usamos Void.class porque Django puede devolver null
            ResponseEntity<Void> response = restTemplate.exchange(
                    url,
                    HttpMethod.PATCH,
                    request,
                    Void.class
            );

            // Si es exitoso (200 o 204)
            if (response.getStatusCode().is2xxSuccessful()) {
                GenePatchOutDto dtoOut = new GenePatchOutDto();
                dtoOut.setId(id);
                dtoOut.setFullName(dto.getFullName());
                dtoOut.setFuctionSummary(dto.getFuctionSummary());
                return Optional.of(dtoOut);
            }

            return Optional.empty();

        } catch (HttpClientErrorException.NotFound e) {
            throw new GeneNotFoundException("Gen con ID: " + id + " no encontrado para actualizar");
        }
    }

    public void delete(Long id) {
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());
        String url = buildUrl(id) + "/";
        try {
            restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    entity,
                    Void.class
            );
        } catch (HttpClientErrorException.NotFound e) {
            // Lanzamos la excepción para que el GlobalExceptionHandler devuelva un 404
            throw new GeneNotFoundException("Gen con ID: " + id + " no encontrado para eliminar");
        }
    }
}
