# ServiceTasks

How to start the ServiceTasks application
---

1. Run `mvn clean install` to build your application
1. Start application with `java -jar target/ServiceTasks-0.0.2.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

The following endpoints are available for domains User and Task
---
- Create a User at: /api/user POST
```
curl -i -H "Content-Type: application/json" -X POST -d '{"username":"jsmith","first_name" : "John", "last_name" : "Smith"}'http://http://localhost:8080/api/user
```
- Update a User at: /api/user/{userId} PUT
```
curl -i -H "Content-Type: application/json" -X PUT -d '{"first_name" : "John", "last_name" : "Doe"}' http://http://localhost:8080/api/user/{id}
```

- Get all users at: /api/user GET
```
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/api/user
```

- Get a User by id at: /api/user/{userId} GET
```
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://http://localhost:8080/api/user/{id}
```

- Create a Task at: /api/user/{userId}/task POST
```
curl -i -H "Content-Type: application/json" -X POST -d '{"name":"My task","description" : "Description of task", "date_time" : "2016-05-25 14:25:00"}' http://http://localhost:8080/api/user/{user_id}/task
```

- Update a Task at: /api/user/task/{taskId} PUT
```
curl -i -H "Content-Type: application/json" -X PUT -d '{"name":"My updated task"}' http://http://localhost:8080/api/user/task/{task_id}
```

- Delete a Task at: /api/user/task/{taskId} DELETE
```
curl -i -H "Content-Type: application/json" -X DELETE http://http://localhost:8080/api/user/task/{task_id}
```

- Get Task information at by Task and User: /api/user/{userId}/task/{taskId} GET
```
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://http://localhost:8080/api/user/{user_id}/task/{task_id}
```

- Get All Task information by User: /api/user/{userId}/task/ GET
```
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://http://localhost:8080/api/user/{user_id}/task/
```

