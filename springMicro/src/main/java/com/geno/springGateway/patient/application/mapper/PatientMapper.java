package com.geno.springGateway.patient.application.dto.mapper;


import com.geno.springGateway.clinicalRecord.application.dto.ClinicalRecordDTO;
import com.geno.springGateway.clinicalRecord.application.dto.ClinicalRecordOutDTO;
import com.geno.springGateway.clinicalRecord.application.mapper.ClinicalRecordMapper;
import com.geno.springGateway.patient.application.dto.PatientDTO;
import com.geno.springGateway.patient.application.dto.PatientOutDTO;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@AllArgsConstructor

public class PatientMapper {
    private ClinicalRecordMapper clinicalRecordMapper;

    public PatientOutDTO toDto(PatientDTO patient){
        return PatientOutDTO.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .birthDate(patient.getBirthDate())
                .gender(patient.getGender())
                .status(patient.getStatus()).build();
    }

    private List<ClinicalRecordOutDTO> getListClinicalRecord(List<ClinicalRecordDTO> clinicalRecord){
        return clinicalRecord.stream().map(clinicalRecordMapper::toDo).toList();
    }



}
