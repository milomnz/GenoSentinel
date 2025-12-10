# Kubernetes Deployment - GenoSentinel

## Requisitos Previos

- [Minikube](https://minikube.sigs.k8s.io/docs/start/) instalado
- [kubectl](https://kubernetes.io/docs/tasks/tools/) instalado
- [Docker](https://docs.docker.com/get-docker/) instalado

## Estructura de Archivos

```
k8s/
├── namespace.yaml           # Namespace genosentinel
├── mysql-secret.yaml        # Credenciales de MySQL
├── mysql-configmap.yaml     # Script de inicialización de BDs
├── mysql-pvc.yaml           # PersistentVolumeClaim para MySQL
├── mysql-statefulset.yaml   # StatefulSet de MySQL
├── mysql-service.yaml       # Service ClusterIP para MySQL
├── django-deployment.yaml   # Deployment + Service de Django
├── nestjs-deployment.yaml   # Deployment + Service de NestJS
├── spring-configmap.yaml    # ConfigMap con URLs de microservicios
├── spring-deployment.yaml   # Deployment + Service de Spring Gateway
└── README.md
```

## Despliegue

### 1. Iniciar Minikube

```bash
minikube start
```

### 2. Configurar Docker para usar el daemon de Minikube

```bash
# En Windows PowerShell:
& minikube -p minikube docker-env --shell powershell | Invoke-Expression

# En Linux/Mac:
eval $(minikube docker-env)
```

### 3. Construir las imágenes Docker

```bash
# Desde la raíz del proyecto GenoSentinel

# Django
cd djangoproject
docker build -t genosentinel/django-genomic:latest .
cd ..

# NestJS
cd nestMicro/clinical-micro
docker build -t genosentinel/nestjs-clinical:latest .
cd ../..

# Spring Boot
cd springMicro
docker build -t genosentinel/spring-gateway:latest .
cd ..
```

### 4. Aplicar los manifiestos de Kubernetes

```bash
# Crear namespace
kubectl apply -f k8s/namespace.yaml

# Desplegar MySQL
kubectl apply -f k8s/mysql-secret.yaml
kubectl apply -f k8s/mysql-configmap.yaml
kubectl apply -f k8s/mysql-pvc.yaml
kubectl apply -f k8s/mysql-statefulset.yaml
kubectl apply -f k8s/mysql-service.yaml

# Esperar a que MySQL esté listo
kubectl wait --for=condition=ready pod -l app=mysql -n genosentinel --timeout=120s

# Desplegar microservicios
kubectl apply -f k8s/spring-configmap.yaml
kubectl apply -f k8s/django-deployment.yaml
kubectl apply -f k8s/nestjs-deployment.yaml
kubectl apply -f k8s/spring-deployment.yaml
```

### 5. Verificar el estado de los pods

```bash
kubectl get pods -n genosentinel
```

### 6. Acceder a la aplicación

```bash
# Obtener la URL del Spring Gateway
minikube service spring-gateway -n genosentinel --url

# O usar port-forward
kubectl port-forward svc/spring-gateway 8080:8080 -n genosentinel
```

La API estará disponible en: `http://localhost:8080/genoSentinel/apidocs`

## Comandos Útiles

```bash
# Ver logs de un pod
kubectl logs -f <pod-name> -n genosentinel

# Ver todos los recursos
kubectl get all -n genosentinel

# Describir un pod (para debugging)
kubectl describe pod <pod-name> -n genosentinel

# Eliminar todo el deployment
kubectl delete namespace genosentinel

# Reiniciar un deployment
kubectl rollout restart deployment/<deployment-name> -n genosentinel
```

## Bases de Datos

El StatefulSet de MySQL contiene 3 bases de datos:

| Base de Datos | Microservicio |
|---------------|---------------|
| `microspring_db` | Spring Gateway |
| `micronest_db` | NestJS Clinical |
| `microdjango_db` | Django Genomic |

**Credenciales:**
- Usuario: `genouser`
- Password: `genopass123`
- Root Password: `admin`

## Arquitectura

```
┌─────────────────────────────────────────────────────────────┐
│                    Kubernetes Cluster                        │
│                                                              │
│  ┌─────────────────┐                                        │
│  │  Spring Gateway │ ◄─── NodePort :30080                   │
│  │     :8080       │                                        │
│  └────────┬────────┘                                        │
│           │                                                  │
│     ┌─────┴─────┐                                           │
│     ▼           ▼                                           │
│  ┌──────────┐ ┌──────────┐                                  │
│  │ NestJS   │ │ Django   │                                  │
│  │ Clinical │ │ Genomic  │                                  │
│  │  :3000   │ │  :8000   │                                  │
│  └────┬─────┘ └────┬─────┘                                  │
│       │            │                                         │
│       └──────┬─────┘                                        │
│              ▼                                               │
│       ┌────────────┐                                        │
│       │   MySQL    │                                        │
│       │   :3306    │                                        │
│       │ ┌────────┐ │                                        │
│       │ │3 DBs   │ │                                        │
│       │ └────────┘ │                                        │
│       └────────────┘                                        │
└─────────────────────────────────────────────────────────────┘
```

