package com.example.taskdemo.controller;

import com.example.taskdemo.model.Employee;
import com.example.taskdemo.model.Task;
import com.example.taskdemo.service.EmployeeService;
import com.example.taskdemo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tasks" )
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private EmployeeService employeeService;


    @GetMapping
    public String getAllTasks(Model model) {
        List<Task> tasks = taskService.getAllTasks();
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("tasks", tasks);
        model.addAttribute("employees", employees);
        return "tasks"; // Имя HTML-шаблона для отображения задач
    }


    @PostMapping
    public String createOrUpdateTask(
            @ModelAttribute Task task,
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "_method", required = false) String method,
            Model model) {

        if ("DELETE".equalsIgnoreCase(method)) {
            if (id != null) {
                taskService.deleteTask(id);
            }
        } else {
            if (id != null && id > 0) {
                // Обновление существующей задачи
                Optional<Task> existingTask = taskService.getTaskById(id);
                if (existingTask.isPresent()) {
                    Task updateTask = existingTask.get();
                    updateTask.setDescription(task.getDescription());
                    updateTask.setCompleted(task.isCompleted());
                    updateTask.setDueDate(task.getDueDate());
                    updateTask.setPriority(task.getPriority());
                    taskService.saveTask(updateTask);
                }
            } else {
                // Создание новой задачи
                taskService.saveTask(task);
            }
        }

        // Перенаправление на список задач после создания или обновления
        return "redirect:/tasks";
    }


    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}