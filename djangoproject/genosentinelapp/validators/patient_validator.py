# validators/patient_validator.py
import logging

from rest_framework import serializers
from ..services.patient_service import PatientService
from ..exceptions.patient_exceptions import PatientServiceUnavailableException

# Logger para este módulo
logger = logging.getLogger(__name__)

def validate_patient_exists(patient_id):
    """
    Validador independiente.
    """
    # 1. Debug: registrar para asegurar que DRF está llamando a la función
    logger.debug("Validando paciente ID: %s", patient_id)
    
    service = PatientService()

    try:
        exists = service.exists(patient_id)
        
        # 2. Debug: Ver qué responde el servicio realmente
        logger.debug("Respuesta del servicio para %s: %s", patient_id, exists)

        if not exists:
            raise serializers.ValidationError(
                f"El paciente con ID {patient_id} no existe."
            )
            
    except PatientServiceUnavailableException:
        raise serializers.ValidationError(
            "No se pudo validar el paciente: Servicio externo no disponible."
        )

    return patient_id