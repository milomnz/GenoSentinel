package com.geno.springGateway.restTemplateDjango.gene.application.service;
import org.springframework.http.MediaType;
import com.geno.springGateway.restTemplateDjango.gene.application.dto.GeneInDto;
import com.geno.springGateway.restTemplateDjango.gene.application.dto.GeneOutDto;
import com.geno.springGateway.restTemplateDjango.gene.application.dto.GenePatchDto;
import com.geno.springGateway.restTemplateDjango.gene.domain.exceptions.GeneNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
/**
 * Servicio que actúa como cliente REST para el microservicio de Genes (Django).
 * Maneja las operaciones CRUD y la actualización parcial (PATCH) de los recursos Gene,
 * utilizando {@link RestTemplate} para la comunicación HTTP.
 * Es responsable de la traducción de URLs y el manejo de excepciones 404 de Django.
 * @author mendez
 */
@Service
@RequiredArgsConstructor
public class GeneService {
    private final RestTemplate restTemplate;

    @Value("${gene.service.base-url}")
    private String geneUrl;

    /**
     * Define los headers personalizados (Content-Type y X-Source-System).
     */
    private HttpHeaders getCustomHeaders(){
        HttpHeaders headers= new HttpHeaders();
        headers.set("X-Source-System", "SpringGateway");
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public GeneOutDto findById(Long id){
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());
        String url= geneUrl+"/"+id + "/";
        try{
            ResponseEntity<GeneOutDto>response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, GeneOutDto.class
            );
            return response.getBody();
        }catch (HttpClientErrorException.NotFound e){
            throw new GeneNotFoundException("Gene con ID: "+id+" no encontrado");
        }

    }

    public List<GeneOutDto> findAll(){
        HttpEntity<Void> entity = new HttpEntity<>(getCustomHeaders());

        ResponseEntity<GeneOutDto[]> response = restTemplate.exchange(
                geneUrl,HttpMethod.GET, entity, GeneOutDto[].class
        );
        return Arrays.asList(response.getBody());
    }

    public GeneOutDto create(GeneInDto dto){
        HttpEntity<GeneInDto> request = new HttpEntity<>(dto, getCustomHeaders());

        ResponseEntity<GeneOutDto> response= restTemplate.exchange(
                geneUrl + "/", HttpMethod.POST, request, GeneOutDto.class

        );
        return response.getBody();
    }

    /**
     * Implementación de PATCH usando el DTO específico GenePatchDto.
     * @param id ID del gen a actualizar.
     * @param dto DTO que contiene fullName y/o functionSummary.
     * @return GeneOutDto actualizado.
     */

    public GeneOutDto patchUpdate(Long id, GenePatchDto dto){
        HttpEntity<GenePatchDto> request = new HttpEntity<>(dto, getCustomHeaders());
        String url= geneUrl+"/"+id + "/";

        try{
            ResponseEntity<GeneOutDto> response = restTemplate.exchange(
                    url, HttpMethod.PATCH, request, GeneOutDto.class
            );
            return response.getBody();
        }catch (HttpClientErrorException.NotFound e){
            throw new GeneNotFoundException("Gen con ID: "+id+ " no encontrado para actualizar");
        }
    }

    public void delete(Long id){
        HttpEntity<Void>entity= new HttpEntity<>(getCustomHeaders());
        String url= geneUrl+"/"+id + "/";

        try{
            restTemplate.exchange(
                    url, HttpMethod.DELETE, entity, Void.class
            );
        }catch (HttpClientErrorException.NotFound e){
            throw new GeneNotFoundException("Gen con ID: "+id+" no encontrado para eliminar");
        }
    }

}
