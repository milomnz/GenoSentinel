package com.geno.springGateway.patient.application.infraestructure.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class PatientDTO {

    private long id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private char gender;
    private String status;




}
