# 🏦 Brokage Firm Challenge API

This project is a Spring Boot application designed to manage stock orders, customer accounts, and assets for a brokerage firm. It implements core financial transaction logic, including asset reservation, order creation, cancellation, and admin-driven order matching.

The application uses an **H2 in-memory database** for development and testing, and is secured using **Spring Security** with Basic Authentication.

## 🚀 Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

* Java 17+
* Apache Maven 3.x

### 1. Running the Application

The easiest way to start the application is by using the Spring Boot Maven plugin.

### 1. Running the Application

The easiest way to start the application is by using the Spring Boot Maven plugin.

#### cd ./brokage-firm-case/brokerage-firm
####    git bash:
####    mvn clean install
####    mvn spring-boot:run

## Database Access (H2 Console)

http://localhost:8080/h2-console  (username:admin, password:)

JDBC URL: jdbc:h2:mem:brokerage (or as configured in application.properties)

All API endpoints, except for customer registration, require Basic Authentication.

1) Create Customer with Role
2) Create Asset (Add Initial Funds)
3) Create Order (Status: PENDING)
4) Match Order with Admin Role Customer

to see all endpoints, you can use swagger: http://localhost:8080/swagger-ui/index.html


