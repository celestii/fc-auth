package com.example.fcauth.controller;

import com.example.fcauth.model.Employee;
import com.example.fcauth.service.EmployeeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Basics", description = "기본 관리 API")
@RestController
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> listAll() {
        return new ResponseEntity<>(employeeService.listEmployees(), HttpStatus.OK);
    }

    @PostMapping("/admin/employees")
    public ResponseEntity<Employee> createEmployee(@RequestParam String firstName,
                                                   @RequestParam String lastName,
                                                   @RequestParam Long departmentId,
                                                   @RequestParam String kakaoNickName) {
        Employee employee = employeeService.createEmployee(firstName, lastName, departmentId, kakaoNickName);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }
}
