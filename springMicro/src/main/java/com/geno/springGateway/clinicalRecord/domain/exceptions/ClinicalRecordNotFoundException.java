package com.geno.springGateway.clinicalRecord.domain.exceptions;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ClinicalRecordNotFoundException extends RuntimeException {
    private final HttpStatus httpStatus;

/**
  *  Excepción lanzada cuando una entidad Historia Clínica no se encuentra en el dominio
  *  o cuando la respuesta del microservicio externo indica un 404.
  *  Esta excepción encapsula el estado HTTP de error de negocio.
 */
    public ClinicalRecordNotFoundException(String message) {
        super(message);
        // Fijo el código HTTP para que el Global Handler sepa qué devolver al cliente.
        this.httpStatus = HttpStatus.NOT_FOUND;
    }
}