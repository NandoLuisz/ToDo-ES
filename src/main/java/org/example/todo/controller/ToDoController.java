package org.example.todo.controller;


import org.example.todo.domain.TaskCreateDto;
import org.example.todo.domain.TaskCreatedDto;
import org.example.todo.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/task")
@RestController
public class ToDoController {
    private final TaskService taskService;

    public ToDoController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping()
    public ResponseEntity<TaskCreatedDto> createTask(@RequestBody TaskCreateDto taskCreateDto){
        var task = taskService.createTask(taskCreateDto);
        return ResponseEntity.ok(new TaskCreatedDto(task.getId(), task.getTitle(), task.getDescription(), task.getPriority().toString(), task.isCompleted(), task.getCreatedAt()));
    }

    @GetMapping()
    public ResponseEntity<List<TaskCreatedDto>> getTasks(){
        var tasks = taskService.findAll()
                .stream()
                .map(task -> new TaskCreatedDto(task.getId(), task.getTitle(), task.getDescription(), task.getPriority().toString(), task.isCompleted(), task.getCreatedAt())).toList();

        return ResponseEntity.ok(tasks);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return ResponseEntity.ok("Tarefa deletada com sucesso.");
    }

}