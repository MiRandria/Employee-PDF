package com.example.prog4.repository.cnaps;

import com.example.prog4.repository.cnaps.entity.Employee;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "cnapsEmployeeRepository")
public interface EmployeeRepository extends JpaRepository<Employee, String> {
  Optional<Employee> findByEndToEndId(String employeeEndToEndId);
}
