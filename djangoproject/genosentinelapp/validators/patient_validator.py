from ..services.patient_service import PatientService
from ..exceptions.patient_exceptions import (
    PatientNotFoundException,
    PatientServiceUnavailableException
)
from rest_framework import serializers

# .. imports ..

# Instanciamos el servicio UNA VEZ a nivel de módulo si quisiéramos usar
# caché en memoria simple, pero con el cambio a django.core.cache, 
# instanciarlo adentro está bien.
patient_service = PatientService() 

def validate_patient_exists(patient_id: int):
    try:
        # Usamos la instancia global
        patient_service.validate_exists(patient_id)
        return patient_id
    except PatientNotFoundException:
        raise serializers.ValidationError(f"El paciente {patient_id} no existe.")
    except PatientServiceUnavailableException:
        # Nota: Esto devolverá un 400 Bad Request al cliente.
        raise serializers.ValidationError(
            "No se pudo validar el paciente en este momento. Intente más tarde."
        )