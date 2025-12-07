package com.geno.springGateway.restTemplateDjango.patientVariantReport.aplication.service;
import com.geno.springGateway.restTemplateDjango.patientVariantReport.aplication.dto.PatientVariantReportInDto;
import com.geno.springGateway.restTemplateDjango.patientVariantReport.aplication.dto.PatientVariantReportOutDto;

import java.util.List;
import java.util.Optional;

public interface IPatientVariantReportService {
    Optional<PatientVariantReportOutDto> findById(Long id);
    List<PatientVariantReportOutDto> findAll();
    PatientVariantReportOutDto create(PatientVariantReportInDto dto);
    void delete(Long id);
}
