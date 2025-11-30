from rest_framework.exceptions import APIException


class PatientServiceException(APIException):
    """Excepción base para errores del servicio de pacientes"""
    status_code = 500
    default_detail = 'Error en la comunicacion con el servidor'
    default_code = 'patient_service_error'


class PatientNotFoundException(PatientServiceException):
    """El paciente no existe en el sistema clínico"""
    status_code = 404
    default_detail = 'Paciente con el ID proporcionado no encontrado'
    default_code = 'patient_not_found'


class PatientServiceUnavailableException(PatientServiceException):
    """El servicio de pacientes no está disponible"""
    status_code = 503
    default_detail = 'El servico de pacientes no esta disponible'
    default_code = 'patient_service_unavailable'