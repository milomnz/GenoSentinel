package com.geno.springGateway;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor


public class ClinicalRecordDTO {

    private Long id;
    private Long idTumorType;
    private Date diagnosticDate;
    private String stage;
    private String treatmentProtocol;


}

