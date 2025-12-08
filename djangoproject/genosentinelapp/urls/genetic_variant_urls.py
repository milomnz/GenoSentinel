from django.urls import path, include 
from rest_framework.routers import DefaultRouter
from ..views.genetic_variant_viewset import GeneticVariantViewSet

router = DefaultRouter()
router.register(r'', GeneticVariantViewSet)

urlpatterns = [
    path('', include(router.urls))
]