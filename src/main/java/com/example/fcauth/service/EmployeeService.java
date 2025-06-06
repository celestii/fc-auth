package com.example.fcauth.service;

import com.example.fcauth.model.Employee;
import com.example.fcauth.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public List<Employee> listEmployees() {
        return employeeRepository.findAll();
    }
}
