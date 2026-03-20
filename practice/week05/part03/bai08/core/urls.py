from django.http import JsonResponse
from django.urls import path


def home(request):
    return JsonResponse({"message": "Django + Celery + Redis is running"})


urlpatterns = [
    path("", home),
]
