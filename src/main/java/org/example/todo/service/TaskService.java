package org.example.todo.service;

import org.example.todo.domain.Priority;
import org.example.todo.domain.Task;
import org.example.todo.domain.TaskCreateDto;
import org.example.todo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(TaskCreateDto taskCreateDto){
        var newTask = new Task();
        newTask.setTitle(taskCreateDto.title());
        newTask.setDescription(taskCreateDto.description());
        newTask.setPriority(Priority.valueOf(taskCreateDto.priority()));
        taskRepository.save(newTask);
        return newTask;
    }

    public List<Task> findAll(){
        return taskRepository.findAll();
    }

    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }
}
