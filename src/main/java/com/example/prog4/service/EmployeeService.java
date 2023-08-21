package com.example.prog4.service;

import com.example.prog4.model.EmployeeFilter;
import com.example.prog4.model.exception.NotFoundException;
import com.example.prog4.repository.base.EmployeeRepository;
import com.example.prog4.repository.base.dao.EmployeeManagerDao;
import com.example.prog4.repository.base.entity.Employee;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeService {
  private EmployeeRepository baseRepository;
  private com.example.prog4.repository.cnaps.EmployeeRepository cnapsEmployeeRepository;
  private EmployeeManagerDao employeeManagerDao;


  public Employee getOne(String id) {
    Optional<Employee> base = baseRepository.findById(id);
    if (base.isEmpty()) {
      throw new NotFoundException("Employee.Id=" + id + " was not found.");
    }
    Employee result = base.get();
    Optional<com.example.prog4.repository.cnaps.entity.Employee> cnaps =
        cnapsEmployeeRepository.findByEndToEndId(id);
    cnaps.ifPresent(employee -> result.setCnaps(employee.getNumber()));
    return result;
  }

  public List<Employee> getAll(EmployeeFilter filter) {
    Sort sort = Sort.by(filter.getOrderDirection(), filter.getOrderBy().toString());
    Pageable pageable = PageRequest.of(filter.getIntPage() - 1, filter.getIntPerPage(), sort);
    return employeeManagerDao.findByCriteria(
        filter.getLastName(),
        filter.getFirstName(),
        filter.getCountryCode(),
        filter.getSex(),
        filter.getPosition(),
        filter.getEntrance(),
        filter.getDeparture(),
        pageable
    );
  }

  public void saveOne(Employee employee) {
    baseRepository.save(employee);
  }
}
