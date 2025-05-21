# Anti-Plagiarism System

This project is a microservice-based system for analyzing student reports for plagiarism and statistical processing.

## System Architecture

The system consists of the following microservices:

1. **API Gateway** - Responsible for routing requests to the appropriate microservices.
2. **File Storage Service** - Responsible for storing and delivering files.
3. **File Analysis Service** - Responsible for conducting analysis, storing results, and issuing them.
4. **Eureka Server** - Responsible for service discovery and registration.

## API Specification

### API Gateway

The API Gateway routes requests to the appropriate microservices based on the URL path:

- `/api/files/**` - Routed to the File Storage Service
- `/api/analysis/**` - Routed to the File Analysis Service

### File Storage Service

- `POST /files` - Upload a file
  - Request: Multipart form data with a file
  - Response: File metadata including ID, name, size, etc.

- `GET /files/{fileId}` - Get file metadata
  - Response: File metadata including ID, name, size, etc.

- `GET /files/{fileId}/content` - Get file content
  - Response: File content

- `GET /files` - Get all file metadata
  - Response: List of file metadata

### File Analysis Service

- `POST /analysis/files/{fileId}` - Analyze a file
  - Response: Analysis results including paragraph count, word count, character count, plagiarism detection, etc.

- `GET /analysis/files/{fileId}` - Get analysis results for a file
  - Response: Analysis results including paragraph count, word count, character count, plagiarism detection, etc.

- `GET /analysis/files` - Get all analysis results
  - Response: List of analysis results

## Running the System

### Prerequisites

- Docker
- Docker Compose

### Steps

1. Clone the repository
2. Navigate to the project directory
3. Run `docker-compose up -d`
4. Access the API Gateway at http://localhost:8080

## Development

### Building the Project

```bash
./gradlew build
```

### Running Tests

```bash
./gradlew test
```

## Technologies Used

- Java 21
- Spring Boot
- Spring Cloud (Eureka, Gateway, OpenFeign)
- PostgreSQL
- Liquibase
- Docker
- Docker Compose