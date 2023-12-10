
# Task Management

Welcome to "Task Management", a tool designed to help you create and manage your to-do list effectively.

## Getting Started

To get the project up and running, execute the bash file:
```
sh bash/launchdocker.sh
```
This script will build the project and create a container that can be accessed via:
```
http://localhost:8087/api/tasks
```

## Running Tests

To execute tests, run:
```
sh bash/launchtests.sh
```

Additionally, a Postman collection file is provided for testing various methods:
```
/postman/TaskManagment.postman_collection.json
```

## Personal Comments

Due to limited time, there might be some unfinished areas and unresolved issues. However, the main requirements have been addressed. Potential improvements include:

1. Replace `int count` with `Pageable` in `TaskRepository.findByStatusAndDueDateTimeBefore(TaskStatus status, LocalDateTime currentTime, int count)`.

2. Add pagination to controller methods for fetching entries, to avoid potential `OutOfMemory` issues.

3. Implement metrics using Spring Actuator.

4. Add appenders for logging to external storage, and make it asynchronous for better performance.

5. Increase test coverage for more business logic, although several unit and integration tests have been developed.

6. Create a more universal controller for data filtering, not limited to status.

7. Improve code style and formatting.

8. Prepare a glossary for common terms, e.g., defining 'Task' as 'ToDo'.

9. Ensure compliance with REST API standards.

10. Verify code extendibility.

Other tasks and improvements are also planned but yet to be implemented.

## API Methods Description

This RESTful API provides access to task management functionalities. All methods accept and return data in JSON format.

### Get Task List

**Method**: `GET /api/tasks/`

**Description**: Returns a list of all tasks or filters tasks by status if the "status" parameter is provided.

**Parameters**:
- `status` (optional): Filter by task status.

**Response**:
- `200 OK`: List of tasks.

### Get Task by ID

**Method**: `GET /api/tasks/{taskId}`

**Description**: Returns a task by the specified ID.

**Parameters**:
- `taskId` (required): Task ID.

**Responses**:
- `200 OK`: Task details.
- `404 Not Found`: Task not found.

### Create a New Task

**Method**: `POST /api/tasks`

**Description**: Creates a new task based on JSON data provided in the request body.

**Request Body**:
- JSON with task data.

**Response**:
- `200 OK`: Created task.

### Update Task by ID

**Method**: `PUT /api/tasks/{taskId}`

**Description**: Updates an existing task by its ID based on JSON data provided in the request body.

**Parameters**:
- `taskId` (required): Task ID.

**Request Body**:
- JSON with task data.

**Responses**:
- `200 OK`: Task successfully updated.
- `404 Not Found`: Task not found.

### Delete Task by ID

**Method**: `DELETE /api/tasks/{taskId}`

**Description**: Deletes a task by its ID.

**Parameters**:
- `taskId` (required): Task ID.

**Responses**:
- `200 OK`: Task successfully deleted.
- `404 Not Found`: Task not found.

### Delete All Tasks

**Method**: `DELETE /api/tasks/`

**Description**: Deletes all tasks.

**Response**:
- `200 OK`: All tasks successfully deleted.

### Partially Update Task by ID

**Method**: `PATCH /api/tasks/{taskId}`

**Description**: Partially updates a task by its ID.

**Responses**:
- `200 OK`: Task successfully updated.
- `404 Not Found`: Task not found.

**Note**: Data can also be filtered by status at `http://localhost:8087/api/tasks?status=NOT_DONE`.
