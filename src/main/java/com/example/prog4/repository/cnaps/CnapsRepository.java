package com.example.prog4.repository.cnaps;

import com.example.prog4.repository.cnaps.entity.Employee;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CnapsRepository extends JpaRepository<Employee, String> {
  Optional<Employee> findByEndToEndId(String employeeEndToEndId);
  Optional<Employee> findByFirstNameAndLastName(String firstname, String lastname);
}
