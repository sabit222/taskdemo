package com.example.taskdemo.service;

import com.example.taskdemo.Priority;
import com.example.taskdemo.model.Employee;
import com.example.taskdemo.repository.EmployeeRepository;
import com.example.taskdemo.repository.TaskRepository;
import com.example.taskdemo.model.Task;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }


    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }


    @Transactional
    public Task createTask(String description, LocalDate dueDate, Priority priority, Long employeeId) {
        // Найти сотрудника по ID
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Создать новую задачу
        Task task = new Task();
        task.setDescription(description);
        task.setDueDate(dueDate);
        task.setPriority(priority);
        task.setCompleted(false);
        task.setEmployee(employee);

        // Сохранить задачу в базе данных
        return taskRepository.save(task);
    }


    @Transactional
    public Task updateTask(Long id, String description, LocalDate dueDate, Priority priority, boolean completed, Long employeeId) {
        // Найти задачу по ID
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // Найти сотрудника по ID
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Обновить данные задачи
        task.setDescription(description);
        task.setDueDate(dueDate);
        task.setPriority(priority);
        task.setCompleted(completed);
        task.setEmployee(employee);

        // Сохранить обновленную задачу
        return taskRepository.save(task);
    }

    /**
     * Удаляет задачу по её идентификатору.
     */
    @Transactional
    public void deleteTask(Long id) {
        // Убедитесь, что задача существует перед удалением
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        taskRepository.delete(task);
    }

    /**
     * Сохраняет задачу (для создания или обновления).
     */
    @Transactional
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }
}