from django.urls import path, include 
from rest_framework.routers import DefaultRouter
from ..views.geneViewSet import GeneViewSet

router = DefaultRouter()
router.register(r'', GeneViewSet)

urlpatterns = [
    path('', include(router.urls))
]
