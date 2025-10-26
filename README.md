## Overall system architecture

<img width="1151" height="492" alt="Untitled Diagram drawio" src="https://github.com/user-attachments/assets/f9135db0-61dc-4949-a939-de713373c1df" />

## 🧠 Approach to the Solution

### 1. Problem Understanding
The project aims to develop a simple yet effective **Task Management System** that allows users to:
- Create new tasks with a title and description.
- View the most recent and active (NOT_DONE) tasks.
- Mark tasks as completed (DONE), which then hide automatically from the active list.
- Provide a clean, user-friendly interface with instant feedback.

The system follows a **modular full-stack architecture** to ensure scalability, maintainability, and testability.

---

### 2. System Design Overview
The solution is divided into **Frontend**, **Backend**, and **Database** layers, each with a dedicated responsibility:

| Layer | Technologies | Responsibility |
|-------|---------------|----------------|
| **Frontend (UI)** | React.js, Bootstrap, Toastify | Interactive interface for task management and user feedback. |
| **Backend (API)** | Spring Boot (Java), Spring Data JPA | RESTful APIs for task creation, retrieval, and updates. |
| **Database** | H2 (Dev) / MySQL (Prod) | Stores all task data with timestamps and status flags. |
| **Testing** | JUnit, Mockito, Jest, Cypress | Verifies logic, integration, and user flows. |

---

### 3. Backend Implementation
- **Controller Layer:**  
  Exposes REST endpoints using `@RestController`.  
  Handles routes like:
  - `POST /tasks` → Create a new task.
  - `GET /tasks` → Fetch latest NOT_DONE tasks.
  - `PATCH /tasks/{id}/markCompleted` → Mark a task as completed.

- **Service Layer:**  
  Handles all business logic, mapping between DTOs and Entities.

- **Repository Layer:**  
  Uses JpaRepository for persistence and custom queries:

- **Error Handling:**  
Centralized via @ControllerAdvice for validation and runtime exceptions.

- **Entity & DTO Mapping**  
Task entity includes & TaskDTO is used for API communication to ensure clean data transfer.


### 3. Frontend Implementation Approach
- **UI Components**  
  - TaskForm — collects title & description, validates input, and triggers createTask().

  - TaskList — displays tasks fetched from backend.

  - TaskItem — individual card with “Done” button to mark completion.

  - Home.js — orchestrates data flow and state updates.

- **API Communication**  
  Uses fetch to call backend APIs from taskApi.js. Toast messages (via ToastContext) give real-time user feedback.

- **User Experience (UX)**  
  Loading spinners appear only while fetching tasks.



### 4. Unit Testing
**Tools:** JUnit 5, Mockito  
**Goal:** Achieve **100% line and branch coverage** for core business logic.

### 5. Integration Testing
Integration testing ensures that multiple components (controller, service, and repository) work together as expected in a real runtime environment.

### 6. Frontend Unit Testing
**Tools:** Jest + React Testing Library

### 7. End-to-End Testing (E2E)
**Tools:** Cypress
**Goal:** Simulates the entire user journey

### 8. Deployment Approach
backend, databse and frontend are containerized using Docker.
**Tools:** Docker
Dockerfiles:
- Backend → openjdk:21-jdk-slim
- Frontend → node:18-alpine

- Docker Compose: Combines both into a single environment.



## How to Build and Run the Project

### 🖥️ 1. Backend Setup (Spring Boot)

#### 📁 Navigate to backend folder
```bash
cd server
``` 

####  Build the project
```bash
mvn clean package
```


####  Run the Spring Boot application
```bash
mvn spring-boot:run


### 🖥️ 2. Frontend Setup (React)

#### 📁 Navigate to backend folder
```bash
cd client
```

####  Install dependencies
```bash
npm i
```

####  Run the development server
```bash
npm start
```

### 🖥️ 2. Docker Compose

#### Run backend, database and frontend containers
```bash
docker compose up
```

### 🖥️ 2. Testing Instructions

#### Backend
```bash
cd backend
mvn test
```


#### End to End Testing
```bash
cd frontend
npx cypress open
```








