package com.kpshubert.employeemanager_jv.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tEM_Department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String Name;

    @Transient
    private Integer EmployeeCount;

    public Department() {
    }

    // All-args constructor
    public Department(String NameIn) {
        this.Name = NameIn;
    }

    public Integer getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public Integer getEmployeeCount() { return EmployeeCount; }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public void setName(String NameIn) {
        this.Name = NameIn;
    }

    public void setEmployeeCount(Integer EmployeeCountIn) { this.EmployeeCount = EmployeeCountIn; }
}