from django.urls import path, include 
from rest_framework.routers import DefaultRouter
from ..views.GeneticVariantViewSet import GeneticVariantViewSet

router = DefaultRouter()
router.register(r'', GeneticVariantViewSet)

urlpatterns = [
    path('', include(router.urls))
]