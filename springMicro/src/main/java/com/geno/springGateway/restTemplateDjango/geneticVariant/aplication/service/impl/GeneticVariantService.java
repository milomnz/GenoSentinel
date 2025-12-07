package com.geno.springGateway.restTemplateDjango.geneticVariant.aplication.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.geno.springGateway.restTemplateDjango.geneticVariant.aplication.dto.*;
import com.geno.springGateway.restTemplateDjango.geneticVariant.aplication.service.IGeneticVariantService;
import com.geno.springGateway.restTemplateDjango.geneticVariant.domain.exceptions.GeneticVariantNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

/**
 * Servicio que actúa como cliente REST para el microservicio de Variantes Genéticas (Django).
 * Maneja las operaciones CRUD y la actualización parcial (PATCH) de los recursos GeneticVariant,
 * utilizando {@link RestTemplate} para la comunicación HTTP.
 * Es responsable de la traducción de URLs y el manejo de excepciones 404 de Django.
 * @author mendez
 */
@Service
@RequiredArgsConstructor
public class GeneticVariantService implements IGeneticVariantService {
    private final RestTemplate restTemplate;

    @Value("${genetic-variant.service.base-url}")
    private String geneticVariantUrl;

    /**
     * Define los headers personalizados (Content-Type y X-Source-System).
     */
    private HttpHeaders getCustomHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Source-System", "SpringGateway");
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * Construye la URL completa para una variante genética específica.
     * @param id ID de la variante genética
     * @return URL completa
     */
    public String buildUrl(Long id) {
        return String.format("%s/%d", geneticVariantUrl, id);
    }

    /**
     * Busca una variante genética por su ID.
     *
     * @param id ID de la variante genética
     * @return Optional con la variante genética si existe, Optional.empty() si no existe
     */
    @Override
    public Optional<GeneticVariantOutDto> findById(Long id) {
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());
        try {
            ResponseEntity<GeneticVariantOutDto> response = restTemplate.exchange(
                    buildUrl(id),
                    HttpMethod.GET,
                    entity,
                    GeneticVariantOutDto.class
            );
            // Envolvemos la respuesta. Si el body es null, devuelve Optional.empty()
            return Optional.ofNullable(response.getBody());

        } catch (HttpClientErrorException.NotFound e) {
            // Si la API externa devuelve 404, devolvemos un Optional vacío
            return Optional.empty();
        }
    }

    /**
     * Obtiene todas las variantes genéticas.
     * @return Lista de todas las variantes genéticas
     */
    @Override
    public List<GeneticVariantOutDto> findAll() {
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());

        try {
            ResponseEntity<GeneticVariantOutDto[]> response = restTemplate.exchange(
                    geneticVariantUrl + "/",
                    HttpMethod.GET,
                    entity,
                    GeneticVariantOutDto[].class
            );

            GeneticVariantOutDto[] body = response.getBody();

            // Si es null, devolvemos lista vacía, si no, convertimos
            return body != null ? Arrays.asList(body) : Collections.emptyList();

        } catch (HttpClientErrorException.NotFound e) {
            // Si falla, simplemente devolvemos lista vacía
            return Collections.emptyList();
        }
    }

    /**
     * Crea una nueva variante genética.
     * @param dto DTO con los datos de la variante genética a crear
     * @return La variante genética creada
     */
    public GeneticVariantOutDto create(GeneticVariantInDto dto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<GeneticVariantInDto> request = new HttpEntity<>(dto, headers);

        // ========== DEBUG: Ver el JSON que se está enviando ==========
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
            String jsonDebug = mapper.writeValueAsString(dto);
            System.out.println("╔═══════════════════════════════════════╗");
            System.out.println("║   JSON QUE SE ENVÍA A DJANGO          ║");
            System.out.println("╠═══════════════════════════════════════╣");
            System.out.println(jsonDebug);
            System.out.println("╚═══════════════════════════════════════╝");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // =============================================================

        ResponseEntity<GeneticVariantOutDto> response = restTemplate.postForEntity(
                geneticVariantUrl + "/",
                request,
                GeneticVariantOutDto.class
        );

        return response.getBody();
    }

    /**
     * Actualización parcial (PATCH) de una variante genética.
     * @param id ID de la variante genética a actualizar
     * @param dto DTO con los campos a actualizar
     * @return Optional con el DTO de salida del patch
     * @throws GeneticVariantNotFoundException si la variante no existe
     */
    public Optional<GeneticVariantPatchOutDto> patchUpdate(Long id, GeneticVariantPatchDto dto) {
        HttpEntity<GeneticVariantPatchDto> request = new HttpEntity<>(dto, getCustomHeaders());
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
                GeneticVariantPatchOutDto dtoOut = new GeneticVariantPatchOutDto();
                dtoOut.setId(id);
                dtoOut.setImpact(dto.getImpact());
                return Optional.of(dtoOut);
            }

            return Optional.empty();

        } catch (HttpClientErrorException.NotFound e) {
            throw new GeneticVariantNotFoundException(
                    "Variante genética con ID: " + id + " no encontrada para actualizar"
            );
        }
    }

    /**
     * Elimina una variante genética por su ID.
     * @param id ID de la variante genética a eliminar
     * @throws GeneticVariantNotFoundException si la variante no existe
     */
    @Override
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
            throw new GeneticVariantNotFoundException(
                    "Variante genética con ID: " + id + " no encontrada para eliminar"
            );
        }
    }
}