# Microservices Architecture Project

This project demonstrates a **production-style Microservices Architecture** built using **Spring Boot and Spring Cloud**.
The system is designed to showcase how multiple independent services communicate with each other using **Service Discovery and REST APIs**.

The goal of this project is to simulate a **real-world enterprise backend system** where services are loosely coupled, independently deployable, and scalable.

---

## Technologies Used

* Java
* Spring Boot
* Spring Cloud
* Eureka Service Discovery
* OpenFeign (Inter-service communication)
* REST APIs
* Maven
* MySQL
* Git & GitHub

Future Enhancements:

* Spring Cloud Gateway (API Gateway)
* Distributed Tracing (Zipkin)
* Centralized Configuration (Config Server)
* Docker Containerization
* CI/CD Pipeline

---

## Microservices Included

### 1. Eureka Server

The **Eureka Server** acts as the **Service Registry**.
All microservices register themselves with Eureka, allowing other services to discover and communicate with them dynamically.

Responsibilities:

* Service registration
* Service discovery
* Health monitoring of services

---

### 2. Employee Service

The **Employee Service** manages employee-related operations.

Features:

* Create employee
* Fetch employee details
* Communicate with Address Service using **OpenFeign Client**

---

### 3. Address Service

The **Address Service** manages employee address data.

Features:

* Store address information
* Provide address details to Employee Service

---

### 4. Auth Service

The **Authentication Service** handles authentication-related operations.

Future scope includes:

* JWT Authentication
* Role-based authorization
* Secure API access

---

## Microservices Communication

Services communicate using **REST APIs** and **Feign Clients**.

Example Flow:

Client Request
↓
Employee Service
↓
Feign Client
↓
Address Service
↓
Response returned to Client

Service locations are dynamically resolved using **Eureka Service Discovery**.

---

## Project Architecture

Client
↓
Employee Service
↓
Feign Client
↓
Address Service
↓
Eureka Server (Service Registry)

Each service is **independently deployable** and can scale separately.

---

## Future Enhancements

To make the system closer to a **production-grade microservices architecture**, the following improvements will be implemented:

* API Gateway using Spring Cloud Gateway
* Centralized Configuration using Spring Cloud Config Server
* Distributed Tracing using Zipkin
* Resilience using Resilience4j
* Docker containerization
* CI/CD integration using Jenkins or GitHub Actions

---

## Purpose of this Project

This project is built to demonstrate:

* Microservices architecture design
* Service discovery using Eureka
* Inter-service communication using Feign Client
* Scalable backend system design

It can serve as a **learning project for developers who want to understand microservices using Spring Boot and Spring Cloud**.

---

## Author

Durgesh Ratnaparkhi
Java Full Stack Developer
