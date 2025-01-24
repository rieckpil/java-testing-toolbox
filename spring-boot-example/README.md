# Spring Boot Sample Application

## Prerequisites

- Java 21: `java -version`
- a running Docker engine: `docker info`

## Run all tests

1. Execute all tests with `./mvnw verify` (Mac/Linux) or `mvnw.cmd verify` (Windows)

## Start the application

1. Start the required infrastructure with: `docker-compose up`
2. Run the Spring Boot application with: `./mvnw spring-boot:run` (Mac/Linux) or `mvnw.cmd spring-boot:run` (Windows)

## Run the Gatling performance tests

1. Start the application as mentioned above
2. Run `./mvnw gatling:test` (Mac/Linux) or `mvnw.cmd gatling:test` (Windows)
