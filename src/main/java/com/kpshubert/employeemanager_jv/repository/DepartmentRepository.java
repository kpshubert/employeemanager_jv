package com.kpshubert.employeemanager_jv.repository;

import com.kpshubert.employeemanager_jv.entity.Department;
import com.kpshubert.employeemanager_jv.entity.Employee;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import jakarta.persistence.*;
import java.util.List;

@Repository
public class DepartmentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Create
    @Transactional
    public Department save(Department department) {
        if (department != null && department.getId() != null) {
            entityManager.merge(department);
        } else if (department != null) {
            entityManager.persist(department);
        } else {
            throw new NullPointerException("Department is null");
        }
        return department;
    }

    // Read by ID
    public Department findById(Integer Id) {
        return entityManager.find(Department.class, Id);
    }

    // Read All
    public List<Department> findAll() {
        TypedQuery<Department> query = entityManager.createQuery(
                "SELECT d FROM Department d", Department.class);
        List<Department> returnValue = query.getResultList();

        for (Department d : returnValue) {
           d.setEmployeeCount(getCountByDepartmentId(d.getId()));
        }

        return returnValue;
    }

    // Delete by ID
    @Transactional
    public Department deleteById(Integer Id) {
        Department department = findById(Id);
        if (department != null) {
            entityManager.remove(department);
        }
        return department;
    }

    public List<Department> findByNameContaining(String name) {
        TypedQuery<Department> query = entityManager.createQuery(
                "SELECT d FROM Department d WHERE LOWER(d.Name) LIKE LOWER(CONCAT('%', :name, '%'))",
                Department.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    public Integer getCountByDepartmentId(Integer DepartmentId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);

        criteriaQuery.select(builder.count(employeeRoot))
                .where(builder.equal(employeeRoot.get("Department").get("Id"), DepartmentId));

        return Math.toIntExact(entityManager.createQuery(criteriaQuery).getSingleResult());
    }
}