package com.kpshubert.employeemanager_jv.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tEM_Employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String FirstName;

    private String LastName;

    private String Email;

    private String Phone;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DepartmentId", nullable=false)
    private Department Department;

    public Employee() {
    }

    // All-args constructor
    public Employee(String FirstNameIn, String LastNameIn, String EmailIn, String PhoneIn, Department DepartmentIn) {
        this.FirstName = FirstNameIn;
        this.LastName = LastNameIn;
        this.Email = EmailIn;
        this.Phone = PhoneIn;
        this.Department = DepartmentIn;
    }

    public Integer getId() {
        return Id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getEmail() { return Email; }

    public String getPhone() {
        return Phone;
    }

    public Department getDepartment() { return Department; }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public void setFirstName(String FirstNameIn) {
        this.FirstName = FirstNameIn;
    }

    public void setLastName(String LastNameIn) {
        this.LastName = LastNameIn;
    }

    public void setEmail(String EmailIn) {
        this.Email = EmailIn;
    }

    public void setPhone(String Phone) { this.Phone = Phone; }

    public void setDepartment(Department DepartmentIn) { this.Department = DepartmentIn; }
}