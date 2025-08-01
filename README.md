# Microservices Gateway with Eureka, JWT, and Monitoring

- Centralized authentication using **JWT**
- API routing and security via **Spring Cloud Gateway**
- Service discovery using **Eureka**
- Protected income/expense API
- System observability via **Spring Boot Actuator**


## 🧱 Tech Stack

- **Java 21**
- **Spring Boot**
- **Spring Cloud Gateway**
- **Spring Security (JWT-based)**
- **Spring Cloud Eureka (Server + Clients)**
- **Spring Boot Actuator**
- **Prometheus + Micrometer**
- **Grafana**
- **Maven**


## 📦 Modules

### 1. `eureka-server`
Service discovery registry. All services register themselves here and use it for inter-service communication.

- Port: `8761`
- Access: `http://localhost:8761`

### 2. `auth-service`
Handles authentication and authorization using JWT.

- Endpoints:
  - `POST /auth/register` — Register new user
  - `POST /auth/authenticate` — Login and receive JWT
- Stores users in PostgreSQL (`auth_user` table)
- Port: `8080`

### 3. `income-expense-service`
Protected service to manage financial transactions.

- Endpoints:
  - `POST /api/transaction` — Add new transaction (income/expense)
  - `GET /api/transaction` — Fetch all transactions
- Requires valid JWT token in `Authorization: Bearer <token>`
- Port: `8081`

### 4. `api-gateway`
Handles routing of all client requests and enforces authentication.

- Routes requests to `auth-service` and `income-expense-service`
- Validates JWT tokens
- Port: `8083`

---

## 🔐 JWT Authentication Flow

1. Register or authenticate at `auth-service`
2. Receive a JWT token
3. Include token in header for protected endpoints:
   ```
   Authorization: Bearer <your_token>
   ```
4. Gateway filters and forwards valid requests

---

## 📊 Monitoring & Actuator

Basic system health checks are enabled via Spring Boot Actuator.

- Visit: `http://localhost:<service-port>/api/actuator`
- Secured via Spring Security

---

## 🔨 Run the App

1 ) Clone project git clone https://github.com/Indeece/Microservices-Gateway-Eureka-Jwt.git

2 ) Go to the project's home directory : Microservices-Gateway-Eureka-Jwt

3 ) Run docker compose docker-compose up -d

4 ) Run Eureka Server

5 ) Run Gateway

6 ) Run other services (auth-service, income-expense-service)

---

## Screenshots

[Click here to show the screenshots of project
](https://github.com/Indeece/Microservices-Gateway-Eureka-Jwt/blob/main/screenshots/)
