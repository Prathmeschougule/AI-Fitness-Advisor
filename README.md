# 🤖 AI-Powered Fitness Application (Under Development)
**Microservices-Based Full Stack Project using Spring Boot + React**

Welcome to the AI-Powered Fitness App! This application is designed with a microservices architecture using Spring Boot and integrates AI through the Google Gemini API for personalized fitness insights.

---

## 🚀 Project Status

✅ **Frontend Completed (React)**  
✅ **Backend Completed (Eureka, Services, RabbitMQ, AI Integration)**  
🔧 **Work in Progress: API Gateway & Config Server**

---

## 📸 Screenshots

> Below is a preview of the AI Fitness Dashboard and Recommendations:

![Fitness Login](./Screenshot%202025-06-04%20205517.png)
![AI Recommendation](./Screenshot%202025-06-04%20210258.png)
![Recommendation](./Screenshot%2025-06-04%210449.png)




---

## 📌 Highlights

- 🏗️ Microservices-Based Fitness App
- 🤖 AI-Powered Workout Suggestions using Google Gemini API
- ✅ Step-by-Step Modular Architecture
- 📦 Asynchronous Communication with RabbitMQ
- 🔐 Role-based authentication with Keycloak
- 🎯 Personalized Recommendations by AI

---

## 🛠 Tech Stack

### 🔹 Backend:
- **Spring Boot** – Core framework for all services
- **Eureka Server** – Service Discovery (Spring Cloud Netflix)
- **RabbitMQ** – Asynchronous messaging (Spring AMQP)
- **Keycloak** – Authentication & Authorization
- **PostgreSQL / MySQL** – Relational Databases

### 🔹 AI Integration:
- **Google Gemini API** – AI fitness recommendations

### 🔹 Frontend:
- **React.js** – Dynamic Single Page Application

### 🔹 Cloud Infrastructure:
- **Spring Cloud Gateway** – Central API Gateway
- **Spring Cloud Config Server** – Centralized Config Management

---

## 📂 Project Structure

AI-FITNESS-ADVISOR/
├── activityservice/ # Tracks and manages user fitness activity
├── aiservice/ # AI logic using Gemini API
├── eureka/ # Service Registry
├── userservice/ # User login, profile
├── configserver/ # Externalized config for services
├── gateway/ # API Gateway
├── frontend/ # React Frontend (Completed)
├── screenshots/ # Project Screenshots


---

## 🔧 How to Run (Backend + Frontend)

### 1. Backend:
- Start **Eureka Server**
- Run **RabbitMQ** locally or via Docker
- Start `activityservice`, `aiservice`, `userservice`
- Verify services registered with Eureka
- Test APIs using Swagger UI or Postman

### 2. Frontend:
- Navigate to `frontend/`  
- Run:
  ```bash
  npm install
  npm start
