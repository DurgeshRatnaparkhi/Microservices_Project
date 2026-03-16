package com.employee.service;

import com.employee.dto.EmployeeDto;

import java.util.List;

public interface EmplolyeeService {

    EmployeeDto saveEmployee(EmployeeDto employeeDto);

    EmployeeDto getSingleEmployeeBy(Long id);

    EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto);

    void deleteEmployeeBy(Long id);

    List<EmployeeDto> getAllEmployee();

    EmployeeDto getEmployeeByEmpCodeAndCompanyName(String empCode, String companyName);
}
