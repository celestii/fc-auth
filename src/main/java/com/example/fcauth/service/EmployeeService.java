package com.example.fcauth.service;

import com.example.fcauth.model.Employee;
import com.example.fcauth.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public List<Employee> listEmployees() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Current authenticated user: {}", name);
        return employeeRepository.findAll();
    }

    public Employee createEmployee(String firstName, String lastName, Long departmentId, String kakaoNickName) {
        if (employeeRepository.existsByKakaoNickName(kakaoNickName)) {
            throw new DuplicateKeyException("Kakao nickname already exists");
        }

        Employee employee = Employee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .departmentId(departmentId)
                .kakaoNickName(kakaoNickName)
                .build();
        return employeeRepository.save(employee);
    }
}
