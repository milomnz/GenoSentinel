from ..models.GeneticVariant import GeneticVariant
from ..serializers.geneticVariantSerializer import GeneticVariantSerializer, GeneticVariantPatchDtoSerializer
from rest_framework import viewsets 
from drf_spectacular.utils import extend_schema, extend_schema_view

@extend_schema_view(
    list=extend_schema(
        summary="Listar variantes genéticas",
        description="Obtiene el listado completo de variantes genéticas disponibles."
    ),
    retrieve=extend_schema(
        summary="Obtener una variante genética",
        description="Obtiene los detalles de una variante genética específica."
    ),
    create=extend_schema(
        summary="Crear una nueva variante genética",
        description="Crea un nuevo registro de variante genética en la base de datos.",
        request=GeneticVariantSerializer,     
        responses={201: GeneticVariantSerializer}
    ),
    partial_update=extend_schema(
        summary="Actualizar parcialmente una variante genética",
        description="Modifica solo los campos permitidos por el DTO en la variante genética.",
        request=GeneticVariantPatchDtoSerializer, 
        responses={200: GeneticVariantSerializer} 
)
)
class GeneticVariantViewSet(viewsets.ModelViewSet):
    queryset = GeneticVariant.objects.all()
    serializer_class = GeneticVariantSerializer

    # Mantenemos POST y DELETE habilitados
    http_method_names = ['get', 'post', 'patch', 'delete', 'head', 'options']

    def get_serializer_class(self):
        """
        Decide qué serializer usar según la acción.
        """
        if self.action == 'partial_update':
            return GeneticVariantPatchDtoSerializer
        
        return GeneticVariantSerializer