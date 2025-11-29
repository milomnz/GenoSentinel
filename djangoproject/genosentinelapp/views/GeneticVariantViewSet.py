from django.shortcuts import render
from ..models.GeneticVariant import GeneticVariant
from ..serializers.geneticVariantSerializer import GeneticVariantSerializer
from rest_framework import viewsets 

class GeneticVariantViewSet(viewsets.ModelViewSet):
    queryset = GeneticVariant.objects.all()
    serializer_class = GeneticVariantSerializer