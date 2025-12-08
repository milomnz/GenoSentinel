from rest_framework import viewsets
from drf_spectacular.utils import extend_schema, extend_schema_view
from ..models.gene import Gene
from ..serializers.gene_serializer import GeneSerializer, GenePatchDtoSerializer

@extend_schema_view(
    list=extend_schema(
        summary="Listar todos los genes",
        description="Obtiene el listado completo de genes disponibles."
    ),
    retrieve=extend_schema(
        summary="Obtener un gen",
        description="Obtiene los detalles de un gen específico."
    ),
    create=extend_schema(
        summary="Crear un nuevo gen",
        description="Crea un nuevo registro de gen en la base de datos. Requiere todos los campos obligatorios.",
        request=GeneSerializer,
        responses={201: GeneSerializer} 
    ),
    partial_update=extend_schema(
        summary="Actualizar parcialmente un gen",
        description="Modifica solo los campos permitidos por el DTO.",
        request=GenePatchDtoSerializer,
        responses={200: GeneSerializer}
    )
)
class GeneViewSet(viewsets.ModelViewSet):
    queryset = Gene.objects.all()
    serializer_class = GeneSerializer
    
    http_method_names = ['get', 'post', 'patch', 'delete', 'head', 'options']

    def get_serializer_class(self):
        if self.action == 'partial_update':
            return GenePatchDtoSerializer
        return GeneSerializer