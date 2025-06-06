package com.example.fcauth.controller;

import com.example.fcauth.model.Department;
import com.example.fcauth.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Basics", description = "기본 관리 API")
@RestController
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @Operation(description = "전사 부서 조회")
    @GetMapping("/departments")
    public ResponseEntity<List<Department>> listAll() {
        return ResponseEntity.ok(departmentService.listDepartments());
    }
}
