# Spring Boot Sample Application

You can find the source code for the various testing tools & libraries within `src/test/java/de/rieckpil/blog`. Each tool & library has its dedicated Java package.

## Prerequisites

- Java 11: `java -version`
- a running Docker engine: `docker info`

## Run the tests

1. Execute all tests with `mvn verify`

## Start the application

1. Start the required infrastructure with: `docker-compose up`
2. Run the Spring Boot application with: `./mvnw spring-boot:run` (Mac/Linux) or `mvnw.cmd spring-boot:run` (Windows)

## Run the Gatling performance tests

1. Start the application as mentioned above
2. Run `./mvnw gatling:test` (Mac/Linux) or `mvnw.cmd gatling:test` (Windows)
