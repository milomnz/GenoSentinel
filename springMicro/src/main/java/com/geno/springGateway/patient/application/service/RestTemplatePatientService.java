package com.geno.springGateway.patient.application.dto.service;
import com.geno.springGateway.patient.application.dto.PatientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class RestTemplatePatientService {

    @Autowired
    private RestTemplate restTemplate;
    private static final String URLPatient = "";

    //Get - Retorna El paciente por ID y el status
    public ResponseEntity<PatientDTO> getPatient(long id, String token) {
        String url = URLPatient + "/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, entity, PatientDTO.class);

    }

    //Get - Retorna la lista de todos los pacientes ademas del status
    public ResponseEntity<PatientDTO[]> getAllPatient(String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(URLPatient, HttpMethod.GET, entity, PatientDTO[].class);

    }

    //Post - Retorna el status, objeto creado con headers personalizados
    public ResponseEntity<PatientDTO> createPatient(PatientDTO patient, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PatientDTO> entity = new HttpEntity<>(patient, headers);

        return restTemplate.exchange(URLPatient, HttpMethod.POST, entity, PatientDTO.class);
    }

    //Put
    public ResponseEntity<PatientDTO> updatePatient(Long id, PatientDTO patient, String token) {
        String url = URLPatient + "/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PatientDTO> entity = new HttpEntity<>(patient, headers);

        return restTemplate.exchange(url, HttpMethod.PUT, entity, PatientDTO.class);

    }

    //Patch
    public ResponseEntity<PatientDTO> patchPatien(Long id, Map<String, Object> updates, String token) {
        String url = URLPatient + "/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(updates, headers);

        return restTemplate.exchange(url, HttpMethod.PATCH, entity, PatientDTO.class);
    }

    //DELETE
    public ResponseEntity<Void> deletePatient(long id, String token) {
        String url = URLPatient + "/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
    }



}