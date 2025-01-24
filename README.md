# Hands-On Introduction to the Java Testing Ecosystem

[![Maven Build](https://github.com/rieckpil/java-testing-ecosystem/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/rieckpil/java-testing-ecosystem/actions/workflows/build.yml)

<p align="center">
  <a href="https://rieckpil.de/testing-tools-and-libraries-every-java-developer-must-know/">
    <img src="https://rieckpil.de/wp-content/uploads/2021/04/testing-tools-and-libraries-every-java-developer-must-know-book-cover-1-e1617971322966.png" alt=" Book Cover"/>
  </a>
</p>

This repository contains the source code for the book *30 Testing Tools & Libraries Every Java Developer Must Know*.

You can find the source code for the various testing tools & libraries within `spring-boot-example/src/test/java/de/rieckpil/blog`. Each tool & library has its dedicated Java package.

The only exception to this rule is MicroShed Testing, which is part of the `jakarta-ee-example` project.

**Current Status**: 21/30 tools and libraries are covered. The final release (30/30) is scheduled for Q1 2025.

Grab your copy [here](https://rieckpil.de/testing-tools-and-libraries-every-java-developer-must-know/).

## Goals of the Book

- Enrich your existing testing toolbox
- Use the right tool for the job
- Inform about various tools & libraries of the Java testing ecosystem
- Cookbook-style introduction for each testing tool & library
- Hands-on testing examples that apply for testing any Java application

## Build & Test

Requirements:

- Java 21: `java -version`
- A running Docker engine (required for Testcontainers): `docker info`

Build the project and run all tests with:

```shell
cd spring-boot-example
./mvnw verify

cd jakarta-ee-example
./mvnw verify
```

## Content

The book uses a Spring Boot and Jakarta EE application to demonstrate the different tools & libraries. Both sample applications only use a minimal subset of Spring Boot/Jakarta EE features so that you can easily apply the knowledge to your tech stack.

### Test Frameworks

- [x] JUnit 4
- [x] JUnit 5
- [x] TestNG
- [x] Spock

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

### Test Infrastructure

- [x] Testcontainers
- [x] LocalStack
- [x] MicroShed Testing
- [x] GreenMail
- [x] Selenide
- [ ] Selenium (upcoming)

### Utility Libraries

- [ ] Pact (upcoming)
- [ ] Diffblue (upcoming)
- [ ] Pit (upcoming)
- [ ] Instancio (upcoming)
- [ ] ArchUnit (upcoming)
- [ ] Awaitility (upcoming)

### Performance Testing

- [x] Gatling
- [x] ApacheBench
- [ ] JMH (upcoming)
- [ ] JfrUnit (upcoming)
