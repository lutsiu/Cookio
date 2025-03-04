# Cookio

Cookio is a dynamic and feature-rich web application built on Spring Boot designed for both regular users and administrators. It provides a seamless experience for managing user accounts, recipes, and related data with a focus on security, functionality, and ease of use.

## Table of Contents
- [Features](#features)
- [Installation](#installation)
- [Environment Variables](#environment-variables)
- [API Documentation](#api-documentation)
- [Contact](#contact)

## Features

### Account Management
- **User Registration** – Easily create an account to start using Cookio.
- **Login & JWT Authentication** – Secure login system using JWT (JSON Web Token). The token is required for authentication and is stored in Postman for API testing.
- **OAuth Login** – Login using GitHub for a faster and more convenient authentication process.
- **Password Reset** – Forgot your password? No worries, you can reset it easily.

### Client Features
- **Profile Management** – View and update your profile information, change your password, etc.
- **Recipe Access** – Browse and filter recipes by cuisine, type, ingredients, author, and more.

### Admin Features
- **User Management** – View all registered users, search users by name, and delete users when necessary.
- **Recipe Management** – Add, update, or delete recipes. Images are stored securely in the cloud.
- **Category Management** – Manage cuisines, recipe types, and other classifications to keep everything organized.

### Security & Authentication
Cookio ensures that all operations require authentication via JWT tokens, protecting user data and preventing unauthorized actions. Admins and users have different levels of access based on their roles.

### Cloud Integration
Recipe images are stored in the cloud to keep the application lightweight and scalable.

## Installation

To get started with Cookio, follow these steps:

1. **Clone the repository**
   ```sh
   git clone https://github.com/your-username/cookio.git
   cd cookio
   ```

2. **Set up environment variables**
   Create a `.env` file in the root directory and add the following environment variables:
   
   ```sh
   SQL_URL=jdbc:mysql://localhost:3306/Cookio
   SQL_USER=root
   SQL_PASSWORD=your_password
   
   MAIL_USERNAME=your_email@gmail.com
   MAIL_PASSWORD=your_email_password
   
   GITHUB_CLIENT=your_github_client_id
   GITHUB_SECRET=your_github_client_secret
   
   GOOGLE_CLIENT=your_google_client_id
   GOOGLE_SECRET=your_google_client_secret
   
   CLOUDINARY_API_KEY=your_cloudinary_api_key
   CLOUDINARY_API_SECRET=your_cloudinary_api_secret
   CLOUDINARY_API_NAME=cookio
   
   CLOUDINARY_URL=cloudinary://${CLOUDINARY_API_KEY}:${CLOUDINARY_API_SECRET}@${CLOUDINARY_API_NAME}
   ```

3. **Configure application properties**
   Update `application.properties` with:
   
   ```properties
   spring.application.name=Cookio
   
   spring.mvc.throw-exception-if-no-handler-found=true
   spring.web.resources.add-mappings=false
   server.error.whitelabel.enabled=false
   server.error.path=/error

   spring.main.banner-mode=off
   
   logging.level.root=warn
   logging.level.org.hibernate.SQL=trace
   logging.level.org.hibernate.orm.jdbc.bind=trace

   spring.mail.host=smtp.gmail.com
   spring.mail.port=587
   spring.mail.username=${MAIL_USERNAME}
   spring.mail.password=${MAIL_PASSWORD}
   spring.mail.properties.mail.smtp.auth=true
   spring.mail.properties.mail.smtp.starttls.enable=true

   spring.datasource.url=${SQL_URL}
   spring.datasource.username=${SQL_USER}
   spring.datasource.password=${SQL_PASSWORD}

   cloudinary.cloud_name=${CLOUDINARY_API_NAME}
   cloudinary.api_key=${CLOUDINARY_API_KEY}
   cloudinary.api_secret=${CLOUDINARY_API_SECRET}

   spring.security.oauth2.client.registration.google.client-id=${GOOGLE_CLIENT}
   spring.security.oauth2.client.registration.google.client-secret=${GOOGLE_SECRET}

   spring.security.oauth2.client.registration.github.client-id=${GITHUB_CLIENT}
   spring.security.oauth2.client.registration.github.client-secret=${GITHUB_SECRET}
   ```

4. **Run the application**
   ```sh
   ./mvnw spring-boot:run
   ```


---

## 📂 Project Structure  

The project follows a clean and organized architecture to ensure maintainability and scalability. Below is an overview of the main directories and their responsibilities:  

```
📦 Cookio
 ┣ 📂 src
 ┃ ┣ 📂 main
 ┃ ┃ ┣ 📂 java/com/cookio
 ┃ ┃ ┃ ┣ 📂 controllers        - Handles HTTP requests and responses  
 ┃ ┃ ┃ ┣ 📂 dao                - Data Access Layer (interfaces for DB operations)  
 ┃ ┃ ┃ ┣ 📂 dto                - Data Transfer Objects for request/response bodies  
 ┃ ┃ ┃ ┣ 📂 exceptions         - Custom exceptions for handling errors  
 ┃ ┃ ┃ ┣ 📂 exceptionHandlers  - Global exception handling (e.g., @ControllerAdvice)  
 ┃ ┃ ┃ ┣ 📂 models             - Entity classes representing database tables  
 ┃ ┃ ┃ ┣ 📂 security           - Security configuration (JWT, OAuth, etc.)  
 ┃ ┃ ┃ ┣ 📂 services           - Business logic layer  
 ┃ ┃ ┃ ┣ 📂 utils              - Utility/helper functions  
 ┃ ┃ ┃ ┣ 📜 CookioApplication.java  - Main Spring Boot Application  
 ┃ ┃ ┣ 📂 resources
 ┃ ┃ ┃ ┣ 📜 application.properties     - App configuration (database, security, etc.)  
 ┃ ┃ ┃ ┣ 📜 data.sql            - Initial data seeding
 ┃ ┃ ┃ ┣ 📜 Cookio.postman_collection.json - Postman collection to test each endpoint





