from django.urls import path, include
from rest_framework.routers import DefaultRouter
from ..views.patient_variant_report_viewset import PatientVariantReportViewSet

router = DefaultRouter()
router.register(r'', PatientVariantReportViewSet)

urlpatterns = [
    path('', include(router.urls))
]