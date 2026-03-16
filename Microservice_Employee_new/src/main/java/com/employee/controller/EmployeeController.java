package com.employee.controller;

import com.employee.dto.EmployeeDto;
import com.employee.entity.Employee;
import com.employee.exception.MissingParameterException;
import com.employee.service.EmplolyeeService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    private final EmplolyeeService emplolyeeService;

    @PostMapping
    public ResponseEntity<EmployeeDto> addEmployee(@RequestBody EmployeeDto employeeDto) {
        log.info("POST /api/v1/employees - create employee: {}", employeeDto);
        EmployeeDto savedEmployee = emplolyeeService.saveEmployee(employeeDto);
        log.info("Employee created with id={}", savedEmployee.getId());
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);

    }
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long id) {
        log.info("GET /api/v1/employees/{} - fetch employee", id);
        EmployeeDto employee = emplolyeeService.getSingleEmployeeBy(id);
        log.debug("Fetched employee: {}", employee);
        return ResponseEntity.ok(employee);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        log.info("GET /api/v1/employees - fetch all employees");
        List<EmployeeDto> employees = emplolyeeService.getAllEmployee();
        log.debug("Number of employees fetched: {}", employees != null ? employees.size() : 0);
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto employeeDto) {
        log.info("PUT /api/v1/employees/{} - update employee: {}", id, employeeDto);
        EmployeeDto updatedEmployee = emplolyeeService.updateEmployee(id, employeeDto);
        log.info("Employee updated with id={}", updatedEmployee.getId());
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.info("DELETE /api/v1/employees/{} - delete employee", id);
        emplolyeeService.deleteEmployeeBy(id);
        log.info("Employee deleted with id={}", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employeeEmpCodeAndCompanyName")
    public ResponseEntity<EmployeeDto> getEmployeeByEmpCodeAndCompanyName(
            @RequestParam(required = false) String empCode,
            @RequestParam(required = false) String companyName) {

        List<String> missingParameters = new ArrayList<>();

        if (empCode == null || empCode.trim().isEmpty()) {
            missingParameters.add("empCode");
        }

        if (companyName == null || companyName.trim().isEmpty()) {
            missingParameters.add("companyName");
        }

        if (!missingParameters.isEmpty()) {
            String finalMessage = missingParameters
                    .stream()
                    .collect(Collectors.joining(", "));
            throw new MissingParameterException("Please provide " + finalMessage);
        }

        EmployeeDto response =
                emplolyeeService.getEmployeeByEmpCodeAndCompanyName(empCode, companyName);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
