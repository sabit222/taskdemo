package com.example.taskdemo.repository;

import com.example.taskdemo.model.Employee;
import com.example.taskdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
