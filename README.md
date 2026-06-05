# GEP PV Monitoring Platform

This project is a full-stack web application designed for monitoring 4 photovoltaic systems located at the Green Energy Park (GEP) in Benguerir, Morocco.

## Architecture & Tech Stack

- **Frontend**: React, Vite, Recharts, React-Leaflet
- **Backend**: Spring Boot 3 (Java 17), Spring Security, JWT Auth
- **Database**: PostgreSQL 15
- **Deployment**: Docker & Docker Compose

## Prerequisites

- [Docker](https://docs.docker.com/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/install/)

## Setup Instructions

1. Clone the repository and navigate to the project root.
2. **IMPORTANT**: Due to GitHub file size limits, the large GeoTIFF file (`masque.tif` / `plant_orthomap.tif` - ~190MB) is not included in the repository. Please copy the provided GeoTIFF file into the `frontend/public/` directory and ensure it is named `masque.tif` before starting the application.
3. Start the entire application stack using Docker Compose:

```bash
docker-compose up --build
```

4. Wait for the containers to build and start. The backend will automatically ingest the provided CSV datasets into the PostgreSQL database upon the first startup.
5. Access the web application at `http://localhost`.

### Credentials

A default administrator account is generated automatically at startup:
- **Email:** `admin@gep.ma`
- **Password:** `admin123`

## Assumptions & Technical Decisions

- **Data Ingestion**: The provided CSV data files are placed within the backend's resources. A `CommandLineRunner` service parses these files and populates the PostgreSQL database dynamically upon application boot. This ensures the environment is fully self-contained without requiring manual SQL seed execution.
- **Database Schema**: The SQL schema is generated automatically by Hibernate (Spring Data JPA) based on our Java Entities to ensure perfect synchronization between the code and the database. However, a reference `schema.sql` is provided in the `database/` directory.
- **Thermal Layer Simulation**: The thermal map layer is simulated purely on the client side using Leaflet and GeoRaster to apply a dynamic color scale to the RGB bands of the provided GeoTIFF, avoiding heavy server-side processing.
- **Security**: The application uses stateless JWT authentication. Passwords (like the default admin's) are securely hashed using BCrypt.

## Deliverables Checklist

- [x] Secure login with JWT / hashed tokens
- [x] 4-system summary cards + map
- [x] GeoTIFF RGB + thermal layer + basemaps
- [x] Charts: AC/DC power, irradiance, temp
- [x] Clean code, Docker, README
