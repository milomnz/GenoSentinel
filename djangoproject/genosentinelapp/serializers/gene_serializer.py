from rest_framework import serializers
from ..models.gene import Gene

class GeneSerializer(serializers.ModelSerializer):
    class Meta:
        model = Gene
        fields = '__all__'

class GenePatchDtoSerializer(serializers.ModelSerializer):
    class Meta:
        model = Gene
        fields = ['fullName', 'functionSummary']