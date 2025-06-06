package com.example.fcauth.controller;

import com.example.fcauth.model.Employee;
import com.example.fcauth.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> listAll() {
        return new ResponseEntity<>(employeeService.listEmployees(), HttpStatus.OK);
    }
}
