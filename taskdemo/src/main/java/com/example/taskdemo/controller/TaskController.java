package com.example.taskdemo.controller;

import com.example.taskdemo.Priority;
import com.example.taskdemo.model.Employee;
import com.example.taskdemo.model.Task;

import com.example.taskdemo.service.EmployeeService;
import com.example.taskdemo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private EmployeeService employeeService;

    // Отображение всех задач и формы для создания новой задачи
    @GetMapping
    public String getAllTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("task", new Task());  // Новый объект Task для формы создания
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "tasks"; // Название шаблона
    }

    // Отображение задачи для редактирования
    @GetMapping("/edit/{id}")
    public String showEditTaskForm(@PathVariable Long id, Model model) {
        Task task = taskService.getTaskById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        model.addAttribute("tasks", taskService.getAllTasks());  // Список всех задач
        model.addAttribute("task", task);  // Текущая задача для редактирования
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "tasks"; // Название шаблона
    }

    // Обработка создания новой задачи
    @PostMapping
    public String createTask(@RequestParam String description,
                             @RequestParam LocalDate dueDate,
                             @RequestParam Priority priority,
                             @RequestParam Long employeeId) {
        taskService.createTask(description, dueDate, priority, employeeId);
        return "redirect:/tasks";
    }

    // Обработка обновления задачи
    @PostMapping("/update")
    public String updateTask(@RequestParam Long id,
                             @RequestParam String description,
                             @RequestParam LocalDate dueDate,
                             @RequestParam Priority priority,
                             @RequestParam boolean completed,
                             @RequestParam Long employeeId) {
        Task task = taskService.getTaskById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // Обновляем поля задачи
        task.setDescription(description);
        task.setDueDate(dueDate);
        task.setPriority(priority);
        task.setCompleted(completed);

        // Получаем сотрудника по ID и устанавливаем его как назначенного на задачу
        Employee employee = employeeService.getEmployeeById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        task.setEmployee(employee);

        // Сохраняем изменения в задаче
        taskService.saveTask(task);

        // Перенаправляем обратно на список задач
        return "redirect:/tasks";
    }

    // Обработка удаления задачи
    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }
}
