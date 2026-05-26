package com.kpshubert.employeemanager_jv.controller;

import com.kpshubert.employeemanager_jv.entity.Department;
import com.kpshubert.employeemanager_jv.repository.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/Department")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping
    public List<Department> getAllItems() {
        return departmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable Integer id) {
        return departmentRepository.findById(id);
    }

    @PostMapping
    public Department createDepartment(@RequestBody Department newDepartment) {
        return departmentRepository.save(newDepartment);
    }

    @Transactional
    @PutMapping("/{id}")
    public Department updateDepartment(Integer Id, String newName) {
        // Retrieve existing entity
        Department department = departmentRepository.findById(Id);

        if (department == null) {
            throw new RuntimeException("Department not found");
        }

        // Modify fields
        department.setName(newName);

        // Save changes (update)
        return departmentRepository.save(department);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Integer id) {
        Department department = departmentRepository.findById(id);
        if (department != null) {
            departmentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}