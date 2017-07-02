package com.tasks.repository;

import com.tasks.domain.Task;
import com.tasks.model.TaskModel;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

/**
 * Created by andre on 7/2/17.
 */
public class TasksRepository extends AbstractDAO<Task> {

    public TasksRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Task createTask(Task task) {
        return persist(task);
    }

    public List<Task> getAllByUserId (long userId) {
        return list(namedQuery("com.tasks.domain.Task.findAllByUserId").setParameter("userId", userId));
    }

    public Task getTaskByIdAndUserId(long userId, long taskId) {
        return uniqueResult(namedQuery("com.tasks.domain.Task.findByTaskAndUserId")
                .setParameter("id", taskId)
                .setParameter("userId", userId));
    }

    public void deleteTaskById(long taskId) {
        currentSession().delete(getTaskById(taskId).get());
    }

    public Optional<Task> getTaskById(long taskId) {
        return Optional.ofNullable(get(taskId));
    }

    public Task updateTask(TaskModel task) {
        long taskId = task.getId();
        Task updatedTask = getTaskById(taskId).get();
        if (getTaskById(taskId).isPresent()) {

            if (task.getName() != null) {
                updatedTask.setName(task.getName());
            }

            if (task.getDescription() != null) {
                updatedTask.setDescription(task.getDescription());
            }

            if (task.getDate_time() != null) {
                updatedTask.setDate_timeFromString(task.getDate_time());
            }
        }
        return updatedTask;
    }


}
