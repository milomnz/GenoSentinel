from rest_framework import serializers
from ..models.PatientVariantReport import PatientVariantReport
from ..validators.patient_validator import validate_patient_exists

class PatientVariantReportSerializer(serializers.ModelSerializer):
    class Meta:
        model = PatientVariantReport
        fields = '__all__'
        """
        Usamos extra kwargs para 
        pasar argumentos adicionales a serializadores desde una vista.
        En este caso necesario para inyectar el validador que realiza
        la consulta del id al micro servicio clínico.

        -Milomnz
        """
        extra_kwargs = {
            'patient_id': {
                'validators': [validate_patient_exists],
                'required': True 
            }
        }