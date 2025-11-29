from django.shortcuts import render
from ..models.PatientVariantReport import PatientVariantReport
from ..serializers.PatientVariantReportSerializer import PatientVariantReportSerializer
from rest_framework import viewsets

class PatientVariantReportViewSet(viewsets.ModelViewSet):
    queryset = PatientVariantReport.objects.all()
    serializer_class = PatientVariantReport