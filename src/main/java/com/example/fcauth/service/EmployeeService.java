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

    public Employee createEmployee(String firstName, String lastName, Long departmentId) {
        Employee employee = Employee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .departmentId(departmentId)
                .build();
        return employeeRepository.save(employee);
    }
}
