import logging
import requests
from django.core.cache import cache # Usar el caché de Django
from decouple import config
from ..exceptions.patient_exceptions import (
    PatientNotFoundException,
    PatientServiceUnavailableException
)
"""
Servicio para implementar la logica de interaccion con micro servicio clinico
Posee:  - Consulta de existencia de paciente con caché
        - Implementacion de excepciones personalizadas
        - Manejo de url con variables de entorno.
"""

# Logger incluido para debugging y monitoreo
logger = logging.getLogger(__name__)

class PatientService:
    """
    Parametros inciales, base_url del micro servicio y timeout de peticiones.
    """
    def __init__(self, base_url: str = config('CLINICAL_MICRO_URL'), timeout=3):
        self.base_url = base_url
        self.timeout = timeout
        self.cache_ttl = 300 

    # Parametro : patient_id: int -> bool # Comprueba si existe el paciente
    def exists(self, patient_id: int) -> bool:
        # Manejo de cache. 
        cache_key = f"patient_exists_{patient_id}"
        
        # 1. Consulta de cache. 
        cached_result = cache.get(cache_key)
        if cached_result is not None:
            logger.info(f"Patient {patient_id} found in Django cache: {cached_result}")
            return cached_result

        # 2. Consulta al microservicio
        try:
            url = f"{self.base_url}/patients/{patient_id}"
            logger.info(f"Checking patient {patient_id} at {url}")

            response = requests.get(url, timeout=self.timeout)

            if response.status_code == 200:
                # Guardar en caché True
                cache.set(cache_key, True, self.cache_ttl)
                return True
            
            elif response.status_code == 404:
                # Guardar en caché False. Nos permite optimzizar consultas repetidas
                cache.set(cache_key, False, self.cache_ttl)
                logger.warning(f"Patient {patient_id} not found")
                return False
            
            else:
                logger.error(f"Patient service error: {response.status_code}")
                raise PatientServiceUnavailableException(
                    f"Unexpected status: {response.status_code}"
                )
        except requests.exceptions.RequestException as e:
            # Captura Timeout, ConnectionError, etc., gracias a requests
            logger.error(f"Network error checking patient {patient_id}: {str(e)}")
            raise PatientServiceUnavailableException(
                "Service unavailable or connection failed"
            )
    # Parametro : patient_id: int -> None # Valida existencia o lanza excepcion
    def validate_exists(self, patient_id: int) -> None:
        if not self.exists(patient_id):
            raise PatientNotFoundException(f"Patient {patient_id} does not exist")