package com.example.fcauth.service;

import com.example.fcauth.model.Department;
import com.example.fcauth.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public List<Department> listDepartments() {
        return departmentRepository.findAll();
    }
}
