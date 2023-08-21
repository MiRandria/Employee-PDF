package com.example.prog4.repository.base;

import com.example.prog4.repository.base.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "baseEmployeeRepository")
public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
