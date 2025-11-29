import logging
import requests
from django.core.cache import cache # Usar el caché de Django
from requests.adapters import HTTPAdapter, Retry
from decouple import config
from ..exceptions.patient_exceptions import (
    PatientNotFoundException,
    PatientServiceUnavailableException
)

logger = logging.getLogger(__name__)

class PatientService:
    def __init__(self, base_url: str = None, timeout=3):
        self.base_url = base_url or config('CLINICAL_MICRO_URL')
        self.timeout = timeout
        self.cache_ttl = 300 

    def exists(self, patient_id: int) -> bool:
        cache_key = f"patient_exists_{patient_id}"
        
        # 1. Consultar Caché de Django (Persistente entre peticiones)
        cached_result = cache.get(cache_key)
        if cached_result is not None:
            logger.info(f"Patient {patient_id} found in Django cache: {cached_result}")
            return cached_result

        # 2. Consultar Microservicio
        try:
            url = f"{self.base_url}/patients/{patient_id}"
            logger.info(f"Checking patient {patient_id} at {url}")

            response = self.session.get(url, timeout=self.timeout)

            if response.status_code == 200:
                # Guardar en caché True
                cache.set(cache_key, True, self.cache_ttl)
                return True
            
            elif response.status_code == 404:
                # Guardar en caché False (también es útil cachear que NO existe)
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

    def validate_exists(self, patient_id: int) -> None:
        if not self.exists(patient_id):
            raise PatientNotFoundException(f"Patient {patient_id} does not exist")