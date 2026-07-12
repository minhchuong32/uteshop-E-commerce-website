# 🛒 UTEShop – Java Servlet E-Commerce Platform

![Java](https://img.shields.io/badge/Java-22-orange)
![Servlet](https://img.shields.io/badge/Java-Servlet-blue)
![JSP](https://img.shields.io/badge/JSP-JSTL-green)
![SQL Server](https://img.shields.io/badge/Database-SQL%20Server-red)
![JPA](https://img.shields.io/badge/JPA-Hibernate-success)
![Bootstrap](https://img.shields.io/badge/UI-Bootstrap%205-purple)
![License](https://img.shields.io/badge/License-MIT-brightgreen)

UTEShop is a mini e-commerce platform developed using **Java Servlet**, **JSP/JSTL**, **JPA**, and **SQL Server**. The application follows the **MVC + DAO + Service Layer** architecture and provides a complete online shopping experience for customers while supporting vendor, shipper, and administrator management.

The project was developed as an academic full-stack web application focusing on scalable architecture, authentication, payment integration, and role-based authorization.

---

# ✨ Features

## 👤 Guest

- Browse products
- Search and filter products
- View product details
- Register an account
- Login with Email or Google

---

## 🛍 Customer

- Manage personal profile
- Manage multiple shipping addresses
- Shopping cart
- Wishlist
- Recently viewed products
- Product reviews
- Product comments
- Order management
- Coupon support
- Checkout with multiple payment methods

Supported payments:

- Cash on Delivery (COD)
- VNPay
- MoMo

---

## 🏪 Vendor

- Register a shop
- Manage products
- Manage orders
- Create promotions
- Revenue dashboard
- Sales statistics

---

## 🚚 Shipper

- Receive assigned orders
- Update delivery status
- Delivery statistics

---

## 👨‍💼 Administrator

- User management
- Shop management
- Product management
- Category management
- Promotion management
- Shipping provider management
- Complaint handling
- System dashboard

---

# 🚀 Highlights

- JWT Authentication
- Google OAuth Login
- OTP Email Verification
- Password Encryption
- VNPay Integration
- PDF Report Export
- WebSocket Live Chat
- AI Chatbot
- Responsive UI
- MVC Architecture
- DAO + Service Layer
- JPA Persistence

---

# 🏗 System Architecture

```text
Browser
    │
    ▼
Java Servlet Controller
    │
    ▼
Service Layer
    │
    ▼
DAO Layer (JPA)
    │
    ▼
SQL Server Database
```

The application follows the **MVC architecture** with a clear separation between presentation, business logic, and data access layers.

---

# 🛠 Tech Stack

| Category | Technology |
|-----------|------------|
| Language | Java 22 |
| Backend | Java Servlet |
| View | JSP, JSTL |
| ORM | JPA |
| Database | SQL Server |
| Authentication | JWT, Google OAuth |
| Security | Password Hashing |
| Payment | VNPay, MoMo |
| Frontend | HTML5, CSS3, Bootstrap 5, JavaScript |
| Template Engine | SiteMesh |
| WebSocket | Jakarta WebSocket |
| Build Tool | Maven |
| IDE | Spring Tool Suite |
| UML | Enterprise Architect |
| Version Control | Git & GitHub |

---

# 📂 Project Structure

```text
UTEShop-Servlet
│
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── config
│   │   │   ├── controller
│   │   │   ├── dao
│   │   │   ├── dao/impl
│   │   │   ├── entity
│   │   │   ├── service
│   │   │   ├── service/impl
│   │   │   ├── utils
│   │   │   └── filter
│   │   │
│   │   ├── resources
│   │   └── webapp
│   │       ├── WEB-INF
│   │       ├── assets
│   │       └── index.jsp
│   │
│   └── test
│
├── db
├── docs
└── pom.xml
```

---

# 🔐 Authentication

The application provides secure authentication using JWT.

Features include:

- User Registration
- Login
- Logout
- Forgot Password
- OTP Email Verification
- Google OAuth Login
- Password Encryption

---

# 🛒 Main Modules

### Product Management

- CRUD Products
- Categories
- Product Images
- Product Search
- Product Filter

### Shopping Cart

- Add to Cart
- Update Quantity
- Remove Item
- Persistent Cart

### Order Management

- Checkout
- Order Tracking
- Order History
- Refund Request

### Promotion

- Coupons
- Shop Promotions
- System Promotions

### Payment

- COD
- VNPay
- MoMo

### Communication

- WebSocket Chat
- AI Chatbot

### Reports

- Revenue Statistics
- PDF Export

---

# ⚙️ Installation

## Requirements

- Java JDK 22
- Apache Tomcat 10
- SQL Server
- Maven

---

## Clone Repository

```bash
git clone https://github.com/your-username/UTEShop-servlet.git
```

---

## Database

Create a database named:

```text
uteshopdb
```

Import SQL scripts located in:

```text
db/
```

or restore:

```text
uteshopdb.bak
```

---

## Configure Database

Update database information inside:

```text
persistence.xml
```

---

## Run Project

Build the project.

```bash
mvn clean install
```

Deploy the generated WAR file to Apache Tomcat.

Open:

```
http://localhost:8080/uteshop
```

---

# 💳 VNPay Sandbox

Use the following sandbox account for testing.

| Field | Value |
|---------|--------|
| Bank | NCB |
| Card Number | 9704198526191432198 |
| Card Holder | NGUYEN VAN A |
| Issue Date | 07/15 |
| OTP | 123456 |

---

# 📸 Screenshots

Add screenshots here.

- Home Page
- Product Detail
- Shopping Cart
- Checkout
- Vendor Dashboard
- Admin Dashboard

---

# 📈 Future Improvements

- Elasticsearch
- Redis Cache
- Docker Deployment
- Microservice Architecture
- Recommendation System
- AI Product Search
- Mobile Application
- CI/CD Pipeline

---

# 👨‍💻 My Contributions

### Backend

- Designed the database schema
- Implemented JWT Authentication
- Built Google OAuth Login
- Developed Admin Management
- Developed User Management
- Implemented Complaint Management
- Integrated VNPay Payment Gateway
- Built WebSocket Chat
- Implemented AI Chatbot Integration
- Optimized JPA queries

### Frontend

- Designed Admin Dashboard
- Developed Authentication UI
- Built User Management Interface
- Developed Shopping Cart
- Responsive Bootstrap UI

---

# 📄 License

This project was developed for educational purposes.

---

# 👤 Author

**Pham Han Minh Chuong**
**Nguyen Thi Thanh Thuy**
**Nguyen Thanh Binh Minh**

- GitHub: https://github.com/chuongminh32
- Email: chuongminh3225@gmail.com
