# Hands-On Introduction to the Java Testing Ecosystem

This repository contains the source code for the book *30 Testing Tools & Libraries Every Java Developer Must Know*.

<p align="center">
  <a href="https://rieckpil.de/testing-tools-and-libraries-every-java-developer-must-know/">
    <img src="https://rieckpil.de/wp-content/uploads/2021/04/testing-tools-and-libraries-every-java-developer-must-know-book-cover-1-e1617971322966.png" alt=" Book Cover"/>
  </a>
</p>

## About the Book

Testing is a critical part of software development, and **having** **the** **right tools** can make all the difference.

This book introduces you to **30 essential testing tools and libraries**, carefully selected to cover a wide range of testing needs—from unit testing and assertions to mocking, integration testing, and performance testing.

Each tool is presented in a **cookbook-style format**, with clear explanations and **hands-on examples** that you can apply directly to your projects.

## What You'll Learn

- Enrich your testing toolbox with 30 carefully curated tools and libraries.
- Choose the right tool for the job by understanding the strengths and use cases of each.
- Explore the Java testing ecosystem through practical, real-world examples.
- Apply testing best practices to your Spring Boot, Jakarta EE, or any Java application.
- Gain hands-on experience with each tool through dedicated examples and exercises.

## Current Status

[![Maven Build](https://github.com/rieckpil/java-testing-ecosystem/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/rieckpil/java-testing-ecosystem/actions/workflows/build.yml)

The book is now complete with 30/30 tools and libraries covered.

You can grab your copy [here](https://rieckpil.de/testing-tools-and-libraries-every-java-developer-must-know/).

## Repository Overview
This repository contains the source code for the examples used in the book. The examples are organized into two projects:

- `spring-boot-example`: Demonstrates testing tools and libraries in the context of a Spring Boot application. You'll find most of the code examples here.
- `jakarta-ee-example`: Showcases testing tools and libraries using a Jakarta EE application.

Each tool and library has its dedicated Java package within the `spring-boot-example/src/test/java/de/rieckpil/blog` directory of the respective project.

The only exception is MicroShed Testing, which is part of the jakarta-ee-example project.

## Build & Test

Requirements:

- Java 21: Verify with `java -version`
- A running Docker engine (required for Testcontainers): `docker info`

Build the project and run all tests with:

```shell
cd spring-boot-example
./mvnw verify

cd jakarta-ee-example
./mvnw verify
```

### Covered Test Frameworks

- [x] JUnit 4
- [x] JUnit 5
- [x] TestNG
- [x] Spock

### Covered Assertion Libraries

- [x] AssertJ
- [x] JsonPath
- [x] Hamcrest
- [x] XMLUnit
- [x] JSONAssert
- [x] REST Assured

### Covered Mocking Frameworks

- [x] Mockito
- [x] WireMock
- [x] MockWebServer

### Covered Test Infrastructure

- [x] Testcontainers
- [x] LocalStack
- [x] MicroShed Testing
- [x] GreenMail
- [x] Selenide
- [x] Selenium

### Covered Behavior Driven Testing (BDT)

- [x] JGiven

### Covered Utility Libraries

- [x] Pact
- [x] Diffblue
- [x] Pit
- [x] Instancio
- [x] ArchUnit
- [x] Awaitility

### Covered Performance Testing Tools

- [x] Gatling
- [x] ApacheBench
- [x] JMH
- [x] JfrUnit
