package com.geno.springGateway.restTemplateDjango.patientVariantReport.aplication.service.impl;

import com.geno.springGateway.restTemplateDjango.patientVariantReport.aplication.dto.PatientVariantReportInDto;
import com.geno.springGateway.restTemplateDjango.patientVariantReport.aplication.dto.PatientVariantReportOutDto;
import com.geno.springGateway.restTemplateDjango.patientVariantReport.aplication.service.IPatientVariantReportService;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
public class PatientVariantReportService implements IPatientVariantReportService {
    private final RestTemplate restTemplate;

    @Value("${patientvariantreport.service.base-url}")
    private String reportUrl;

    /**
     * Define los headers personalizados.
     */
    private HttpHeaders getCustomHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Source-System", "SpringGateway");
        return headers;
    }

    private String buildUrl(Long id) {
        // Aseguramos que no haya dobles slashes o faltantes
        return String.format("%s/%d", reportUrl, id);
    }

    public Optional<PatientVariantReportOutDto> findById(Long id) {
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());
        System.out.println("URL ENVIADA: ");
        System.out.println(buildUrl(id));

        try {
            ResponseEntity<PatientVariantReportOutDto> response = restTemplate.exchange(
                    buildUrl(id),
                    HttpMethod.GET,
                    entity,
                    PatientVariantReportOutDto.class
            );
            // Optional.ofNullable maneja si el body viene null aunque sea
            return Optional.ofNullable(response.getBody());

        } catch (HttpClientErrorException.NotFound e) {
            // Si Django dice 404, nosotros devolvemos Empty para que el Controller decida.
            return Optional.empty();
        }
    }


    public List<PatientVariantReportOutDto> findAll() {
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());
        System.out.println("URL ENVIADA: ");
        System.out.println(reportUrl);

        try {
            // Django suele requerir el slash final para listas
            ResponseEntity<PatientVariantReportOutDto[]> response = restTemplate.exchange(
                    reportUrl,
                    HttpMethod.GET,
                    entity,
                    PatientVariantReportOutDto[].class
            );

            PatientVariantReportOutDto[] body = response.getBody();

            // Retorno seguro: Si es null, devuelve lista vacía. Si hay datos, convierte a lista.
            return body != null ? Arrays.asList(body) : Collections.emptyList();

        } catch (HttpClientErrorException.NotFound e) {
            // Si no hay registros y la API externa devuelve 404, retornamos lista vacía
            return Collections.emptyList();
        }
    }

    public PatientVariantReportOutDto create(PatientVariantReportInDto dto) {
        HttpEntity<PatientVariantReportInDto> request = new HttpEntity<>(dto, getCustomHeaders());

        // Dejamos que las excepciones de validación (400 Bad Request) suban al GlobalHandler
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
        String url = buildUrl(id) + "/";
        try {
            restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    entity,
                    Void.class
            );
        } catch (HttpClientErrorException.NotFound e) {
            throw new PatientVariantReportNotFoundException("Reporte con ID " + id + " no encontrado para eliminar.");
        }
    }
}