package com.geno.springGateway;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TumorTypeDTO {

    private Long id;
    private String name;
    private String systemAffected;


}
