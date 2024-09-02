package com.example.taskdemo.repository;

import com.example.taskdemo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
