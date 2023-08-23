package com.example.prog4.controller;

import com.example.prog4.controller.mapper.EmployeeMapper;
import com.example.prog4.controller.validator.EmployeeValidator;
import com.example.prog4.model.Employee;
import com.example.prog4.model.EmployeeFilter;
import com.example.prog4.service.CSVUtils;
import com.example.prog4.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/server/employee")
public class EmployeeController {
    private EmployeeMapper employeeMapper;
    private EmployeeValidator employeeValidator;
    private EmployeeService employeeService;

    @GetMapping("/list/csv")
    public ResponseEntity<byte[]> getCsv(HttpSession session) {
        EmployeeFilter filters = (EmployeeFilter) session.getAttribute("employeeFiltersSession");
        List<Employee> data = employeeService.getAll(filters).stream().map(employeeMapper::toView).toList();

        String csv = CSVUtils.convertToCSV(data);
        byte[] bytes = csv.getBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentDispositionFormData("attachment", "employees.csv");
        headers.setContentLength(bytes.length);
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    @GetMapping("/list/filters/clear")
    public String clearFilters(HttpSession session) {
        session.removeAttribute("employeeFilters");
        return "redirect:/employee/list";
    }

    @PostMapping("/createOrUpdate")
    public String saveOne(@ModelAttribute Employee employee) {
        employeeValidator.validate(employee);
        com.example.prog4.repository.employee.entity.Employee domain = employeeMapper.toDomain(employee);
        Optional<com.example.prog4.repository.cnaps.entity.Employee> tempCnaps = employeeService.getOneCnaps(employee.getCnaps());
        if (tempCnaps.isPresent()){
            com.example.prog4.repository.cnaps.entity.Employee currentCnaps = tempCnaps.get();
            currentCnaps.setCin(employee.getCin());
            currentCnaps.setAddress(employee.getAddress());
            currentCnaps.setFirstName(employee.getFirstName());
            currentCnaps.setLastName(employee.getLastName());
            currentCnaps.setPersonalEmail(employee.getPersonalEmail());
            currentCnaps.setProfessionalEmail(employee.getProfessionalEmail());
            currentCnaps.setRegistrationNumber(employee.getRegistrationNumber());
            currentCnaps.setBirthDate(employee.getBirthDate());
            currentCnaps.setDepartureDate(employee.getDepartureDate());
            currentCnaps.setEntranceDate(employee.getEntranceDate());
            currentCnaps.setChildrenNumber(employee.getChildrenNumber());
            currentCnaps.setSex(employee.getSex());
            currentCnaps.setCsp(employee.getCsp());
            employeeService.saveOneInCNaps(currentCnaps);
        } else {
            com.example.prog4.repository.cnaps.entity.Employee cnapsDomain = employeeMapper.toCnapsDomain(employee);
            employeeService.saveOneInCNaps(cnapsDomain);
        }
        employeeService.saveOneInEmployee(domain);
        return "redirect:/employee/list";
    }
}
