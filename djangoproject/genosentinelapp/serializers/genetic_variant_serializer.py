from rest_framework import serializers
from ..models.genetic_variant import GeneticVariant

class GeneticVariantSerializer(serializers.ModelSerializer):
    class Meta:
        model = GeneticVariant
        fields = '__all__'


class GeneticVariantPatchDtoSerializer(serializers.ModelSerializer):
    class Meta:
        model = GeneticVariant
        fields = ['impact']