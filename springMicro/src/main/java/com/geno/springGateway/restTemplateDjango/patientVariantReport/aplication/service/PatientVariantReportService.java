package com.geno.springGateway.restTemplateDjango.patientVariantReport.aplication.service;

import com.geno.springGateway.restTemplateDjango.patientVariantReport.aplication.dto.PatientVariantReportInDto;
import com.geno.springGateway.restTemplateDjango.patientVariantReport.aplication.dto.PatientVariantReportOutDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import com.geno.springGateway.restTemplateDjango.patientVariantReport.domain.exceptions.PatientVariantReportNotFoundException;

/**
 * Servicio que actúa como cliente REST para el microservicio de Reportes de Variantes de Pacientes (Django).
 * Maneja las operaciones CRUD (GET, POST, DELETE) utilizando {@link RestTemplate} para la comunicación HTTP.
 * Su principal función es delegar la persistencia y validación de IDs de referencia (patientId) a Django,
 * y tipificar las respuestas "pobladas" (OutDto).
 * @author mendez
 */
@Service
@RequiredArgsConstructor
public class PatientVariantReportService {
    private final RestTemplate restTemplate;

    @Value("${patientvariantreport.service.base-url}")
    private String reportUrl;

    /**
     * Define los headers personalizados (Content-Type y X-Source-System).
     */
    private HttpHeaders getCustomHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Source-System", "SpringGateway");
        return headers;
    }

    public PatientVariantReportOutDto findById(Long id) {
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());
        String urlWithId = reportUrl + "/" + id + "/";

        try {
            ResponseEntity<PatientVariantReportOutDto> response = restTemplate.exchange(
                    urlWithId,
                    HttpMethod.GET,
                    entity,
                    PatientVariantReportOutDto.class
            );
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            // Convierte el 404 de HTTP en una excepción de dominio
            throw new PatientVariantReportNotFoundException("Reporte con ID " + id + " no encontrado en Django.");
        }
    }

    public List<PatientVariantReportOutDto> findAll() {
        HttpHeaders headers = getCustomHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        // El array se usa para mapear la respuesta JSON de una lista
        ResponseEntity<PatientVariantReportOutDto[]> response = restTemplate.exchange(
                reportUrl + "/",
                HttpMethod.GET,
                entity,
                PatientVariantReportOutDto[].class
        );
        return Arrays.asList(response.getBody());
    }

    public PatientVariantReportOutDto create(PatientVariantReportInDto dto) {
        HttpEntity<PatientVariantReportInDto> request = new HttpEntity<>(dto, getCustomHeaders());

        // Si Django devuelve 400 (por validación de patientId o variant FK),
        // Spring lanzará HttpClientErrorException, que debe ser manejada
        // por el @ControllerAdvice global.
        ResponseEntity<PatientVariantReportOutDto> response = restTemplate.exchange(
                reportUrl + "/",
                HttpMethod.POST,
                request,
                PatientVariantReportOutDto.class
        );
        return response.getBody();
    }

    public void delete(Long id) {
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());
        String urlWithId = reportUrl + "/" + id + "/";

        try {
            restTemplate.exchange(
                    urlWithId,
                    HttpMethod.DELETE,
                    entity,
                    Void.class
            );
        } catch (HttpClientErrorException.NotFound e) {
            throw new PatientVariantReportNotFoundException("Reporte con ID " + id + " no encontrado en Django para eliminar.");
        }
    }

}
