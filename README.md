# 🧠 AI-Powered Fitness Application (Under Development)
**Microservices-Based Full Stack Project using Spring Boot + React**

Welcome to the AI-Powered Fitness App! This application is designed with a microservices architecture using Spring Boot and integrates AI through the Google Gemini API for personalized fitness insights.

---

## 🚀 Project Status
🚧 Backend Completed (Eureka, Services, RabbitMQ, AI Integration)  
🧩 Work in Progress: API Gateway, Config Server, and Frontend (React)

---

## 📌 Highlights

- 🏗️ Microservices-Based Fitness App
- 🤖 AI-Powered Workout Suggestions using Google Gemini API
- ✅ Step-by-Step Modular Architecture
- 📦 Asynchronous Communication with RabbitMQ

---

## 🛠 Tech Stack

### 🔹 Backend:
- **Spring Boot** – Core framework for all services
- **Eureka Server** – Service Discovery (Spring Cloud Netflix)
- **RabbitMQ** – Asynchronous messaging between services (Spring AMQP)
- **Keycloak** – Authentication & Authorization
- **PostgreSQL / MySQL** – Relational Database

### 🔹 AI Integration:
- **Google Gemini API** – For personalized fitness tips and insights

### 🔹 Frontend:
- **React.js** – (Coming Soon)

### 🔹 Upcoming Components:
- **Spring Cloud Gateway** – Central API Gateway
- **Spring Cloud Config Server** – Centralized Configuration

---

## 📂 Current Project Structure
AI-FITNESS-ADVISOR/
├── activityservice/ # Tracks and manages user fitness activity
├── aiservice/ # Handles AI logic using Google Gemini API
├── eureka/ # Service Registry with Eureka
├── userservice/ # Manages user data and login/auth
├── .idea/ and .vscode/ # IDE-specific config files



> Coming Soon:  
> `apigateway/`, `configserver/`, and `frontend/ (React)`

---

## 🔧 How to Run (Backend)

1. Start the **Eureka Server**
2. Start **RabbitMQ Server** locally or via Docker
3. Run each service: `activityservice`, `aiservice`, `userservice`
4. Test APIs using Postman or Swagger UI

> Frontend setup and API Gateway will be updated once completed.


