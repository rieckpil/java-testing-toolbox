# Full Overview of the Java Testing Ecosystem

[![Maven Build](https://github.com/rieckpil/java-testing-ecosystem/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/rieckpil/java-testing-ecosystem/actions/workflows/build.yml)

<p align="center">
  <a href="https://rieckpil.de/testing-tools-and-libraries-every-java-developer-must-know/">
    <img src="https://rieckpil.de/wp-content/uploads/2021/04/testing-tools-and-libraries-every-java-developer-must-know-book-cover-1-e1617971322966.png" alt=" Book Cover"/>
  </a>
</p>

This repository contains the source code for the book *30 Testing Tools & Libraries Every Java Developer Must Know*.

**Current Status**: 15/30 tools and libraries are covered. The next release (20/30) is scheduled for Q4 2021.

Grab your copy [here](https://rieckpil.de/testing-tools-and-libraries-every-java-developer-must-know/).

## Goals of the Book

- Enrich your existing testing toolbox
- Use the right tool for the job
- Inform about various tools & libraries of the Java testing ecosystem
- Cookbook-style introduction of each tool & library
- Hands-on testing examples that apply for testing any Java application

## Build & Test

Requirements:

- Java 11
- Running Docker engine (required for Testcontainers)

Build the project and run all tests with

```shell
cd spring-boot-example
./mvnw verify

cd jakarta-ee-example
./mvnw verify
```

## Content

The book uses a Spring Boot and Jakarta EE application to demonstrate the different tools & libraries. Only some code examples use a minimal amount of Spring Boot or Jakarta EE specifics so that you can easily apply the learnings to your tech stack.

### Test Frameworks

- [x] JUnit 4
- [x] JUnit 5
- [x] TestNG
- [ ] Spock

### Assertion Libraries

- [x] AssertJ
- [x] JsonPath
- [x] Hamcrest
- [x] XMLUnit
- [x] JSONAssert
- [x] REST Assured

### Mocking Frameworks

- [x] Mockito
- [x] WireMock
- [x] MockWebServer
- [ ] PowerMock

### Test Infrastructure

- [x] Testcontainers
- [x] LocalStack
- [ ] MicroShed Testing (upcoming)
- [ ] Citrus
- [x] GreenMail
- [ ] Selenium (upcoming)

### Utility Libraries

- [x] Selenide
- [ ] Pact (upcoming)
- [ ] Spring Cloud Contract
- [ ] Diffblue
- [ ] FitNesse
- [ ] Awaitility (upcoming)

### Performance Testing

- [ ] JMH
- [ ] JMeter
- [ ] Gatling
- [ ] Quick Perf
