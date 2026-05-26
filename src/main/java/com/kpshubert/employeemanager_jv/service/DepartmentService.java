package com.kpshubert.employeemanager_jv.service;

import com.kpshubert.employeemanager_jv.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.kpshubert.employeemanager_jv.entity.Department;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;
}