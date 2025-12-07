<div align="center">

# 🧬 GenoSentinel

### **Sistema de Gestión Genómica y Clínica para Oncología**

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.5-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Django](https://img.shields.io/badge/Django-5.x-092E20?style=for-the-badge&logo=django&logoColor=white)](https://www.djangoproject.com/)
[![NestJS](https://img.shields.io/badge/NestJS-11.x-E0234E?style=for-the-badge&logo=nestjs&logoColor=white)](https://nestjs.com/)
[![MySQL](https://img.shields.io/badge/MySQL-8.x-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![JWT](https://img.shields.io/badge/JWT-Auth-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)](https://jwt.io/)
[![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-85EA2D?style=for-the-badge&logo=swagger&logoColor=black)](https://swagger.io/)

---

*Plataforma modular, segura y escalable desarrollada para **Breaze Labs** que centraliza información de variantes genéticas somáticas vinculadas al historial clínico de pacientes oncológicos.*

</div>

---

## 📋 Descripción

**GenoSentinel** es un sistema de microservicios que reemplaza archivos de datos dispersos por una arquitectura relacional robusta gestionada a través de APIs RESTful. Diseñado para la gestión integral de:

- 🧬 **Librerías de mutaciones tumorales** (variantes genéticas somáticas)
- 🏥 **Historiales clínicos** de pacientes oncológicos
- 🔐 **Autenticación centralizada** con simulación de API Gateway

---

## 🏗️ Arquitectura

```
┌─────────────────────────────────────────────────────────────────────────────────┐
│                              🌐 CLIENTES                                        │
└───────────────────────────────────┬─────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────┐
│                    ☕ SPRING BOOT - API GATEWAY                                 │
│                         Puerto: 8080                                            │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────────────────────┐  │
│  │   🔐 Auth       │  │   🎫 JWT        │  │      🔀 Proxy/Router            │  │
│  │   Controller    │  │   Service       │  │   (RestTemplate → Servicios)   │  │
│  └─────────────────┘  └─────────────────┘  └─────────────────────────────────┘  │
└───────────────────────────────────┬─────────────────────────────────────────────┘
                                    │
                    ┌───────────────┼───────────────┐
                    │               │               │
                    ▼               ▼               ▼
        ┌───────────────────────┐    ┌───────────────────────────────┐
        │  🏥 NESTJS            │    │  🧬 DJANGO                    │
        │  Microservicio Clínica│    │  Microservicio Genómica       │
        │     Puerto: 3000      │    │     Puerto: 8000              │
        │                       │    │                               │
        │  • Patient            │ <--│  • Gene                       │
        │  • ClinicalRecord     │    │  • GeneticVariant             │
        │  • TumorType          │    │  • PatientVariantReport       │
        │                       │    │                               │
        │  ORM: TypeORM         │    │  ORM: Django ORM              │
        └───────────┬───────────┘    └───────────────┬───────────────┘
                    │                                │
                    └──────────────┬─────────────────┘
                                   ▼
                    ┌──────────────────────────────┐
                    │        🗄️ MySQL              │
                    │        Puerto: 3306          │
                    │                              │
                    │  ┌────────┐  ┌────────────┐  │
                    │  │Clinical│  │  Genomic   │  │
                    │  │  Data  │  │    Data    │  │
                    │  └────────┘  └────────────┘  │
                    └──────────────────────────────┘
```

---

## 🔧 Stack Tecnológico

| Microservicio | Lenguaje | Framework | ORM | Puerto |
|:-------------:|:--------:|:---------:|:---:|:------:|
| **Gateway/Auth** | Java 21 | Spring Boot 3.5.5 | JPA/Hibernate | `8080` |
| **Clínica** | TypeScript | NestJS 11 | TypeORM | `3000` |
| **Genómica** | Python 3.x | Django 5.x | Django ORM | `8000` |

---

## 📦 Microservicios

### ☕ Spring Boot — Gateway & Autenticación

> **Ubicación:** `springMicro/`

Actúa como **API Gateway simulado**, centralizando la seguridad y redirigiendo el tráfico a los servicios internos.

**Características:**
- 🔐 Autenticación JWT con Spring Security
- 👥 Sistema de roles (USER, ADMIN)
- 🔀 Proxy hacia microservicios via RestTemplate
- 📖 Documentación Swagger/OpenAPI

**Endpoints de Autenticación:**
```http
POST /auth/login     → Autenticar usuario, obtener JWT
POST /auth/register  → Registrar nuevo usuario
```

**Endpoints Proxy (requieren JWT):**
```http
# Clínica (NestJS)
/api/patients/**
/api/clinical-records/**
/api/tumor-types/**

# Genómica (Django)
/api/genes/**
/api/genetic-variants/**
/api/patient-variant-reports/**
```

---

### 🏥 NestJS — Microservicio Clínica

> **Ubicación:** `nestMicro/clinical-micro/`

Gestiona la información clínica de pacientes oncológicos.

**Entidades:**

| Entidad | Descripción |
|---------|-------------|
| `Patient` | Datos del paciente (nombre, fecha nacimiento, género, estado) |
| `ClinicalRecord` | Historial clínico (diagnóstico, estadío, protocolo de tratamiento) |
| `TumorType` | Catálogo de tipos tumorales y sistemas afectados |

**Endpoints principales:**
```http
GET/POST       /patients
GET/PATCH/DEL  /patients/:id

GET/POST       /clinical-records
GET/PATCH/DEL  /clinical-records/:id

GET/POST       /tumor-types
GET/PATCH/DEL  /tumor-types/:id
```

---

### 🧬 Django — Microservicio Genómica

> **Ubicación:** `djangoproject/`

Administra las librerías de mutaciones tumorales y variantes genéticas.

**Entidades:**

| Entidad | Descripción |
|---------|-------------|
| `Gene` | Catálogo de genes (símbolo, nombre completo, función) |
| `GeneticVariant` | Variantes genéticas (cromosoma, posición, impacto) |
| `PatientVariantReport` | Reportes de variantes detectadas en pacientes |

**Endpoints principales:**
```http
GET/POST       /api/genes/
GET/PATCH/DEL  /api/genes/:id/

GET/POST       /api/genetic-variants/
GET/PATCH/DEL  /api/genetic-variants/:id/

GET/POST       /api/patient-variant-reports/
GET/PATCH/DEL  /api/patient-variant-reports/:id/
```

---

## 🗄️ Modelo de Datos

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           BASE DE DATOS MySQL                               │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  ┌─────────────┐       ┌──────────────────┐       ┌─────────────────┐       │
│  │   Patient   │──1:N──│  ClinicalRecord  │──N:1──│   TumorType     │       │
│  ├─────────────┤       ├──────────────────┤       ├─────────────────┤       │
│  │ id          │       │ id               │       │ id              │       │
│  │ firstName   │       │ idPatient (FK)   │       │ name            │       │
│  │ lastName    │       │ idTumorType (FK) │       │ systemAffected  │       │
│  │ birthDate   │       │ diagnosticDate   │       └─────────────────┘       │
│  │ gender      │       │ stage            │                                 │
│  │ status      │       │ treatmentProtocol│                                 │
│  └─────────────┘       └──────────────────┘                                 │
│                                                                             │
│  ┌─────────────┐       ┌──────────────────┐       ┌─────────────────────┐   │
│  │    Gene     │──1:N──│  GeneticVariant  │──1:N──│PatientVariantReport │   │
│  ├─────────────┤       ├──────────────────┤       ├─────────────────────┤   │
│  │ id          │       │ id               │       │ id                  │   │
│  │ symbol      │       │ geneId (FK)      │       │ patientId           │   │
│  │ fullName    │       │ chromosome       │       │ variantId (FK)      │   │
│  │ funcSummary │       │ position         │       │ detectionDate       │   │
│  └─────────────┘       │ referenceBase    │       │ alleleFrequency     │   │
│                        │ alternateBase    │       └─────────────────────┘   │
│                        │ impact           │                                 │
│                        └──────────────────┘                                 │
│                                                                             │
│  ┌─────────────┐       ┌──────────────────┐                                 │
│  │   Usuario   │──N:M──│       Rol        │                                 │
│  ├─────────────┤       ├──────────────────┤                                 │
│  │ id          │       │ id               │                                 │
│  │ username    │       │ name             │                                 │
│  │ email       │       └──────────────────┘                                 │
│  │ password    │                                                            │
│  │ activo      │                                                            │
│  └─────────────┘                                                            │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## 🚀 Instalación y Ejecución

### Prerrequisitos

- Java 21+
- Node.js 18+
- Python 3.10+
- MySQL 8.x

### 1️⃣ Base de Datos

```bash
# Importar esquemas
mysql -u root -p < Dbs/microspring_db.sql
mysql -u root -p < Dbs/micronest_db.sql
mysql -u root -p < Dbs/microdjango_db.sql
```

### 2️⃣ Spring Boot (Gateway)

```bash
cd springMicro
./mvnw spring-boot:run
# Disponible en http://localhost:8080
# Swagger: http://localhost:8080/swagger-ui.html
```

### 3️⃣ NestJS (Clínica)

```bash
cd nestMicro/clinical-micro
npm install
npm run start:dev
# Disponible en http://localhost:3000
# Swagger: http://localhost:3000/api
```

### 4️⃣ Django (Genómica)

```bash
cd djangoproject
python -m venv venv
.\venv\Scripts\activate  # Windows
pip install -r requirements.txt
python manage.py runserver 8000
# Disponible en http://localhost:8000
# Swagger: http://localhost:8000/swagger/
```

---

## 📖 Documentación API

| Servicio | Swagger UI |
|----------|------------|
| Gateway (Spring) | `http://localhost:8080/swagger-ui.html` |
| Clínica (NestJS) | `http://localhost:3000/api` |
| Genómica (Django) | `http://localhost:8000/swagger/` |

---

## 🔐 Flujo de Autenticación

```
┌──────────┐         ┌─────────────────┐         ┌─────────────────┐
│  Cliente │──POST──▶│  /auth/login    │         │                 │
│          │         │                 │──JWT───▶│   Respuesta     │
│          │◀────────│  Spring Boot    │         │   { token }     │
└──────────┘         └─────────────────┘         └─────────────────┘
     │
     │ Authorization: Bearer <JWT>
     ▼
┌─────────────────────────────────────────────────────────────────┐
│                      Spring Boot Gateway                        │
│  ┌─────────────┐    ┌───────────────┐    ┌───────────────────┐  │
│  │ JWT Filter  │───▶│ Validate JWT  │───▶│ Forward Request   │  │
│  └─────────────┘    └───────────────┘    └─────────┬─────────┘  │
└────────────────────────────────────────────────────┼────────────┘
                                                     │
                              ┌──────────────────────┼──────────────────────┐
                              ▼                                             ▼
                   ┌─────────────────────┐                      ┌────────────────────┐
                   │   NestJS (Clínica)  │                      │  Django (Genómica) │
                   └─────────────────────┘                      └────────────────────┘
```

---

## 📁 Estructura del Proyecto

```
GenoSentinel/
│
├── 📂 Dbs/                          # Scripts SQL de inicialización
│   ├── microdjango_db.sql
│   ├── micronest_db.sql
│   └── microspring_db.sql
│
├── 📂 springMicro/                  # ☕ Microservicio Gateway (Java)
│   └── src/main/java/com/geno/springGateway/
│       ├── auth/                    # Autenticación JWT
│       ├── restTemplateDjango/      # Proxy → Django
│       ├── restTemplateNest/        # Proxy → NestJS
│       └── user/                    # Entidades Usuario/Rol
│
├── 📂 nestMicro/clinical-micro/     # 🏥 Microservicio Clínica (TypeScript)
│   └── src/modules/
│       ├── patient/                 # Gestión de pacientes
│       ├── clinicalRecord/          # Registros clínicos
│       └── tumorType/               # Tipos de tumor
│
└── 📂 djangoproject/                # 🧬 Microservicio Genómica (Python)
    └── genosentinelapp/
        ├── models/                  # Gene, GeneticVariant, PatientVariantReport
        ├── views/                   # ViewSets REST
        ├── serializers/             # Serializadores DRF
        └── validators/              # Validadores custom
```

---

## 👥 Equipo

**Desarrollado para Breaze Labs**

---

🧬 ❤️ 🏥

</div>

