from rest_framework import serializers
from ..models.PatientVariantReport import PatientVariantReport
from ..validators.patient_validator import validate_patient_exists

class PatientVariantReportSerializer(serializers.ModelSerializer):
    patient_id = serializers.IntegerField(validators=[validate_patient_exists])
    class Meta:
        model = PatientVariantReport
        fields = '__all__'