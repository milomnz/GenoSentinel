package com.geno.springGateway.restTemplateDjango.geneticVariant.aplication.service;

import com.geno.springGateway.restTemplateDjango.geneticVariant.aplication.dto.*;

import java.util.List;
import java.util.Optional;

public interface IGeneticVariantService {
    Optional<GeneticVariantOutDto> findById(Long id);
    List<GeneticVariantOutDto> findAll();
    GeneticVariantOutDto create(GeneticVariantInDto dto);
    Optional<GeneticVariantPatchOutDto> patchUpdate(Long id, GeneticVariantPatchDto dto);
    void delete(Long id);
}
