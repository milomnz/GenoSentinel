from rest_framework import serializers
from ..models.GeneticVariant import GeneticVariant

class GeneticVariantSerializer(serializers.ModelSerializer):
    class Meta:
        model = GeneticVariant
        fields = '__all__'


class GeneticVariantPatchDtoSerializer(serializers.ModelSerializer):
    class Meta:
        model = GeneticVariant
        fields = ['impact']