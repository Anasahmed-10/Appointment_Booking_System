# 🗓️ Appointment Booking System (Spring Boot + JWT)

A complete backend application built using **Spring Boot**, providing a secure and scalable API for managing **appointments**, **providers**, **schedules**, and **services**.  
The system includes **JWT authentication**, **role-based authorization**, and a modular structure following best backend practices.

---

## 🚀 Features

### 🔐 Authentication & Authorization
- User registration & login using **JWT tokens**
- Role-based access:
  - `CUSTOMER` — Can book appointments
  - `PROVIDER` — Can create services, schedules, and manage appointments
- Secured API endpoints using Spring Security

### 👨‍⚕️ Provider Management
- Providers can create their professional profiles
- Add services they offer
- Define weekly schedules/availability

### 📅 Appointment Management
- Customers can book appointments
- Providers can approve/cancel/complete appointments
- Prevents double booking

### 🧱 Clean Architecture
- DTO layer for request/response objects
- Service layer for business logic
- Repository layer (Spring Data JPA)
- Centralized exception handling
- Full separation of concerns

---

## 🛠️ Tech Stack

| Category | Technologies |
|---------|--------------|
| Backend | Spring Boot (3.x), Java 17 |
| Security | Spring Security, JWT (jjwt 0.12.x) |
| Database | MySQL / PostgreSQL |
| Build Tool | Maven |
| APIs | RESTful Architecture |
| Tools | Lombok, Hibernate, JPA |

---

## 📂 Project Structure

src/main/java/com/appointment/booking/
│
├── config/ # Security, JWT config
├── controller/ # REST Controllers
│ ├── dto/ # Request/Response DTOs
│
├── model/ # JPA Entities
├── repository/ # Spring Data Repositories
├── service/ # Business Logic
│ └── impl/ # Implementations
│
└── util/ # JWT utilities

---

## 🔑 Authentication Endpoints

### **REGISTER USER**
`POST /api/auth/register`
json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "PROVIDER"
}

### **LOGIN**
`POST /api/auth/login`
json
{
  "email": "john@example.com",
  "password": "password123"
}
`Response:`
json
{
  "id": 3,
  "name": "John Doe",
  "email": "john@example.com",
  "role": "PROVIDER",
  "token": "your-jwt-token"
}
### **GET CURRENT USER**
`GET /api/auth/me`
Headers:
Authorization: Bearer <token>
### **👨‍⚕️ Provider Endpoints (ROLE = PROVIDER)**
Create Provider Profile
`POST /api/providers`
json
{
  "specialization": "Physiotherapy",
  "description": "Expert physiotherapist",
  "contactInfo": "doctor@example.com"
}
### **Create Service**
`POST /provider/{providerId}/services`
json
{
  "name": "Massage Therapy",
  "description": "Deep tissue massage",
  "price": 5000
}
### **Create Schedule**
`POST /provider/{providerId}/schedules`
json
{
  "dayOfWeek": "MONDAY",
  "startTime": "09:00",
  "endTime": "17:00"
}

---

## 📅 **Appointment Endpoints**
Book an Appointment
`POST /api/appointments`
json
{
  "userId": 1,
  "providerId": 2,
  "serviceId": 5,
  "scheduleId": 3,
  "appointmentDate": "2025-07-20",
  "status": "PENDING"
}
Update Appointment Status
`PUT /api/appointments/{id}/status?status=CONFIRMED`

---

## **⚙️ Configuration**
application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/appointments
spring.datasource.username=root
spring.datasource.password=your-password

jwt.secret=your32bytejwtsecretkeyhere

---

## **🧪 Testing With Postman**
Register user (Provider)
Login → Get JWT token
Create provider profile (Auth required)
Add services & schedules
Register Customer
Customer logs in + books appointment
Provider approves/cancels/updates appointment
You may also create a structured Postman Collection for easier collaboration.

---

## **🏁 Running the Project**
bash
Copy code
mvn spring-boot:run
Default URL:
http://localhost:9090

---

## **🧑‍💻 Author**
**Anas Ahmed**
Backend Developer | Java | Spring Boot | REST APIs

---

## **📜 License**
This project is part of an educational final year project and is free to use for learning purposes.
