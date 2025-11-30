from rest_framework import viewsets, status
from rest_framework.response import Response
from drf_spectacular.utils import extend_schema, extend_schema_view
from ..models.PatientVariantReport import PatientVariantReport
from ..serializers.PatientVariantReportSerializer import PatientVariantReportSerializer

@extend_schema_view(
    list=extend_schema(
        summary="Listar reportes de variantes",
        description="Obtiene el listado completo de los reportes de variantes de pacientes."
    ),
    retrieve=extend_schema(
        summary="Obtener un reporte específico",
        description="Obtiene los detalles de un reporte de variante por su ID."
    ),
    create=extend_schema(
        summary="Crear un reporte de variante",
        description="Genera un nuevo reporte asociando un paciente con una variante. Requiere validación de datos.",
        request=PatientVariantReportSerializer,
        responses={201: PatientVariantReportSerializer}
    ),
    destroy=extend_schema(
        summary="Eliminar un reporte",
        description="Elimina permanentemente un reporte de variante de la base de datos."
    )
)
class PatientVariantReportViewSet(viewsets.ModelViewSet):
    queryset = PatientVariantReport.objects.all()
    serializer_class = PatientVariantReportSerializer
    
    http_method_names = ['get', 'post', 'delete', 'options']

    def create(self, request, *args, **kwargs):
        serializer = self.get_serializer(data=request.data)
        serializer.is_valid(raise_exception=True)

        self.perform_create(serializer)
        
        return Response(serializer.data, status=status.HTTP_201_CREATED)