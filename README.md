
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
/postman/111TaskManagment.postman_collection.json
```

## Personal Comments

#### 1. Data Validation
Implemented intuitive data validation based on specific requirements, which can be modified via configuration. The key validations include:
- Prohibiting the creation/modification of a Task with a past dueDateTime and a status of NOT_DONE.
- Prohibiting the creation/modification of a Task with a future dueDateTime and a status of PAST_DUE.
- Prohibiting the creation/modification of a Task without a description or status through the controller.
- Prohibiting the modification of a Task if it's PAST_DUE or DONE.
- Prohibiting the creation/modification of a Task with a status of Done and a CompletionDateTime in the future.
- Additional requirements can be implemented in the future.

Validation is handled by the interface `com.tasks.taskmanagement.domain.validator.TaskValidator`.

#### 2. Testing
Several tests have been created for validating these rules, located in:
`com.tasks.integration.taskmanagement.presentation.controller.TaskControllerTest`

Additional unit and integration tests are also included in the system.

#### 3. Task Status Updates
The task status is updated every minute, as users usually account for a second's margin of error.
As a result, I have a scheduler that updates all tasks every minute, and it does so with a JPQL update query, in which it updates and affects only records that require updating.

`UPDATE JpaTask t SET t.status = :newStatus WHERE t.status = :oldStatus AND t.dueDateTime < :currentTimestamp`

application.properties(every 60 seconds)

`statusupdater.scheduling.taskStatusUpdateCron=*/60 * * * * *`

Updates are handled by `com.tasks.taskmanagement.application.spring.service.statusupdate.TaskStatusUpdateSchedulerImpl`.

#### 4. Pagination
Implemented pagination using the classes `com.tasks.taskmanagement.domain.valueobject.IPage` and `com.tasks.taskmanagement.domain.valueobject.IPageable`. These are converted into standard Spring classes at the service level. Further improvements could include writing an adapter and moving it to a separate common DDD module.

#### 5. Docker Support
Added a `docker-compose.yml` and `Dockerfile` for container deployment. The entire process can be launched using the script `bash/launchdocker.sh`.

#### 6. Future Improvements
There are numerous areas for potential improvement, including:
- Making the project more extensible, such as adding more status types.
- Considering the adoption of Event Sourcing for task management stages, similar to systems like Jira.
- Various other enhancements.
- I also didn't have time to implement validation when filtering by status.

This initial version is provided as a basis for further development and improvement.

## API Methods Description

This RESTful API provides access to task management functionalities. All methods accept and return data in JSON format.

### Get Task List

**Method**: `GET /api/tasks`

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

**Method**: `DELETE /api/tasks`

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
