from django.urls import path, include
from rest_framework.routers import DefaultRouter
from ..views.PatientVariantReportViewSet import PatientVariantReportViewSet

router = DefaultRouter()
router.register(r'', PatientVariantReportViewSet)

urlpatterns = [
    path('', include(router.urls))
]