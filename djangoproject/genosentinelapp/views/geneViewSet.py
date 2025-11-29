from django.shortcuts import render
from ..models.Gene import Gene
from ..serializers.geneSerializer import GeneSerializer
from rest_framework import viewsets

class GeneViewSet(viewsets.ModelViewSet):
    queryset = Gene.objects.all()
    serializer_class = GeneSerializer

    

