## Cypso Labs
### Create a simple Spring Boot application that provides a RESTful API for managing a list of tasks.

### Postman collection = https://documenter.getpostman.com/view/25235261/2sA3dyjBhp

## Running the Application

1. Clone the repository. `git clone 'https://github.com/IT21307058/Cypso_Labs.git'`
3. Navigate to the project directory. `cd project-name`
4. Run the application
   `./mvnw spring-boot:run`
5. Run Unit & integration tests

## API Endpoints
1. POST /tasks: Create a new task.
2. GET /tasks: Retrieve all tasks.
3. GET /tasks/{id}: Retrieve a task by its ID.
4. PUT /tasks/{id}: Update a task by its ID.
5. DELETE /tasks/{id}: Delete a task by its ID.

### Swagger/OpenAPI documentation for the API = http://localhost:8080/swagger-ui/index.html#/task-controller/createTask

## H2 in-memory database
![image](https://github.com/IT21307058/Cypso_Labs/assets/99331889/314c94fb-6d2d-4ac6-883f-d89ff5e568be)

## Implement pagination for the GET /tasks endpoint.
![image](https://github.com/IT21307058/Cypso_Labs/assets/99331889/06ca7e62-654f-4f16-989f-4a6540deaa69)


