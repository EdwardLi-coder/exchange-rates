# Exchange Rates Application

## Introduction

This project is an Exchange Rates Application built with Spring Boot and PostgreSQL. It uses Docker for running the
database and Maven for building and running the Spring Boot application.

## Getting Started

To run this application, you need to start the database using Docker Compose, build the application with Maven, and then
run it.

### Step 1: Start the Database

First, ensure you have Docker installed. Then, use the provided `docker-compose.yml` file to start the PostgreSQL
database:

```bash
docker-compose up -d
```

### Step 2: Build the Application

Next, build the application using Maven. Navigate to the root directory of the project and run the following command:

```bash
mvn clean install
```

### Step 3: Run the Application

After building the application, you can run it using:

```bash
mvn spring-boot:run
```

This will start the application on the default port.

### Step 4: Accessing Swagger UI

The application includes Swagger for API documentation and testing. Once the application is running, you can access the
Swagger UI to interact with the API:

```url
http://localhost:8080/swagger-ui.html
```
