package org.example.todo.service;

import org.example.todo.domain.*;
import org.example.todo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskEventPublisher eventPublisher;

    public TaskService(TaskRepository taskRepository,
                       TaskEventPublisher eventPublisher) {
        this.taskRepository = taskRepository;
        this.eventPublisher = eventPublisher;
    }

    public Task createTask(TaskCreateDto taskCreateDto) {
        var newTask = new Task();
        newTask.setTitle(taskCreateDto.title());
        newTask.setDescription(taskCreateDto.description());
        newTask.setPriority(Priority.valueOf(taskCreateDto.priority()));

        var savedTask = taskRepository.save(newTask);

        eventPublisher.publish(
                new TaskEventDto(
                        "CREATED",
                        savedTask.getId(),
                        new TaskCreatedDto(
                                savedTask.getId(),
                                savedTask.getTitle(),
                                savedTask.getDescription(),
                                savedTask.getPriority().toString(),
                                savedTask.isCompleted(),
                                savedTask.getCreatedAt()
                        )
                )
        );

        return savedTask;
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);

        eventPublisher.publish(
                new TaskEventDto("DELETED", id,null)
        );
    }

    public Task toggleCompleted(Long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task não encontrada"));

        task.setCompleted(!task.isCompleted());
        taskRepository.save(task);

        eventPublisher.publish(
                new TaskEventDto(
                        "UPDATED",
                        null,
                        new TaskCreatedDto(
                                task.getId(),
                                task.getTitle(),
                                task.getDescription(),
                                task.getPriority().toString(),
                                task.isCompleted(),
                                task.getCreatedAt()
                        )
                )
        );

        return task;
    }

}
