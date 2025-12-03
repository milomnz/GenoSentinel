package com.geno.springGateway.restTemplateDjango.gene.application.service;

import com.geno.springGateway.restTemplateDjango.gene.application.dto.*;

import java.util.List;
import java.util.Optional;

public interface IGeneService {
    Optional<GeneOutDto> findById(Long id);
    List<GeneOutDto> findAll();
    GeneOutDto create(GeneInDto dto);
    Optional<GenePatchOutDto> patchUpdate(Long id, GenePatchDto dto);
    void delete(Long id);
}