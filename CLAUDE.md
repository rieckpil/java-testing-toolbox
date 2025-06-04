# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Repository Overview

This is the Java Testing Toolbox - a comprehensive educational repository demonstrating 30 testing tools and libraries for Java developers. The codebase contains practical examples for the book "30 Testing Tools & Libraries Every Java Developer Must Know".

## Architecture

### Module Structure
- **spring-boot-example**: Main module containing most testing examples. Each testing tool has its own package under `src/test/java/de/rieckpil/blog/`
- **jakarta-ee-example**: Demonstrates testing in Jakarta EE context, primarily MicroShed Testing

### Test Organization
- Unit tests: `*Test.java` or `*Spec.groovy`
- Integration tests: `*IT.java`
- Web tests: `*WT.java`
- Performance tests: Located in `gatling` package
- All test examples are self-contained within their respective packages

## Essential Commands

### Spring Boot Example

```bash
# Build and run all tests
./mvnw verify

# Run unit tests only
./mvnw test

# Run integration tests only
./mvnw failsafe:integration-test

# Run specific test class
./mvnw test -Dtest=UserRegistrationServiceTest

# Start the application (requires Docker)
docker-compose up -d
./mvnw spring-boot:run

# Run Gatling performance tests (requires running application)
./mvnw gatling:test

# Run mutation testing
./mvnw pitest:mutationCoverage

# Generate JGiven reports
./mvnw jgiven:report
```

### Jakarta EE Example

```bash
# Build and run all tests
./mvnw verify

# Run unit tests only
./mvnw test

# Run integration tests
./mvnw failsafe:integration-test

# Start Liberty server
./mvnw liberty:run
```

## Key Testing Patterns

### Test Infrastructure

- **Testcontainers**: Used extensively for integration tests requiring databases or external services
- **Docker Compose**: Provides PostgreSQL and other services for local development
- **MockWebServer/WireMock**: For mocking external HTTP services
- **LocalStack**: For testing AWS services locally

### Testing Frameworks Coverage

The repository demonstrates these testing categories:

1. Test Runners: JUnit 4/5, TestNG, Spock
2. Assertions: AssertJ, Hamcrest, JsonPath, XMLUnit, JSONAssert
3. Mocking: Mockito, WireMock, MockWebServer
4. Integration: Testcontainers, REST Assured, MicroShed Testing
5. Web Testing: Selenium, Selenide
6. Performance: Gatling, JMH, JfrUnit
7. Contract Testing: Pact
8. Architecture Testing: ArchUnit

### Important Conventions

- Java 21 is required for all modules
- Docker must be running for integration tests
- Test output is redirected to files in CI/CD environments
- Each testing tool example is isolated in its own package
- Examples demonstrate real-world usage patterns, not just basic syntax
