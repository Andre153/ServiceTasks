package com.tasks.resources;

import com.tasks.model.TaskModel;
import com.tasks.domain.CoreUser;
import com.tasks.domain.Task;
import com.tasks.repository.CoreUserRepository;
import com.tasks.repository.TasksRepository;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.List;

/**
 * Created by andre on 7/1/17.
 */
@Path(value = "/user")
@Produces(MediaType.APPLICATION_JSON)
public class CoreUserResource {

    private CoreUserRepository coreUserRepository;
    private TasksRepository tasksRepository;

    public CoreUserResource(CoreUserRepository coreUserRepository, TasksRepository tasksRepository) {
        this.coreUserRepository = coreUserRepository;
        this.tasksRepository = tasksRepository;
    }

    @POST
    @UnitOfWork
    public CoreUser createUser(CoreUser coreUser) {
        return coreUserRepository.createUser(coreUser);
    }

    @PUT
    @Path("/{userId}")
    @UnitOfWork
    public CoreUser updateUser(@PathParam("userId") long userId, CoreUser user) {
        user.setId(userId);
        return coreUserRepository.updateUser(user);
    }

    @GET
    @UnitOfWork
    public List<CoreUser> getAllUsers() {
        return coreUserRepository.findAll();
    }

    @GET
    @Path("/{userId}")
    @UnitOfWork
    public CoreUser getUserById(@PathParam("userId") long userId) {
        return getSafe(userId);
    }

    @POST
    @Path("/{userId}/task")
    @UnitOfWork
    public Task createTask(@PathParam("userId") long userId, TaskModel task) throws ParseException {
        Task newTask = new Task(task.getName(),
                task.getDescription(),
                task.getDate_time(),
                getSafe(userId));
        return tasksRepository.createTask(newTask);
    }

    @GET
    @Path("/{userId}/task/{taskId}")
    @UnitOfWork
    public Task getTaskByIdAndUser(@PathParam("userId") long userId, @PathParam("taskId") long taskId) {
        return tasksRepository.getTaskByIdAndUserId(userId, taskId);
    }

    @GET
    @Path("/{userId}/task")
    @UnitOfWork
    public List<Task> getAllTasksByUserId(@PathParam("userId") long userId) {
        return tasksRepository.getAllByUserId(userId);
    }

    @DELETE
    @Path("/task/{taskId}")
    @UnitOfWork
    public Response deleteTask(@PathParam("taskId") long taskId) {
        tasksRepository.deleteTaskById(taskId);
        return Response.noContent().build();
    }

    @PUT
    @Path("/task/{taskId}")
    @UnitOfWork
    public Task updateTask(@PathParam("taskId") long taskId, TaskModel taskModel) {
        taskModel.setId(taskId);
        return tasksRepository.updateTask(taskModel);
    }

    private CoreUser getSafe(long personId) {
        return coreUserRepository.findById(personId).orElseThrow(() -> new NotFoundException("No such user."));
    }
}
