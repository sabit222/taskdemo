package com.example.taskdemo.service;

import com.example.taskdemo.model.Employee;
import com.example.taskdemo.model.User;
import com.example.taskdemo.model.UserDto;
import com.example.taskdemo.repository.EmployeeRepository;
import com.example.taskdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUserAccount(UserDto accountDto) {
        User user = new User();
        user.setUsername(accountDto.getUsername());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setRole("ROLE_USER");

        Employee employee = new Employee();
        employee.setName(accountDto.getUsername());
        employee.setPosition("New Employee");
        employee.setSalary(0.0);
        employee.setFeature("...");
        employee.setUser(user);

        userRepository.save(user);
        employeeRepository.save(employee);

        return user;
    }
}
