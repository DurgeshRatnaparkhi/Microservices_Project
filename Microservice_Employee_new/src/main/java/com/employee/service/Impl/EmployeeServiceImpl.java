package com.employee.service.Impl;

import com.employee.client.AddressClient;
import com.employee.dto.AddressDto;
import com.employee.dto.EmployeeDto;
import com.employee.entity.Employee;
import com.employee.exception.ResourceNotFoundException;
import com.employee.repository.EmployeeRepository;
import com.employee.service.EmplolyeeService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.nio.file.ReadOnlyFileSystemException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmplolyeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    private final AddressClient addressClient;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        log.info("Saving new employee: {}", employeeDto);

        if (employeeDto.getId()!= null) {
            log.error("Attempt to create employee with pre-existing id={}", employeeDto.getId());
            throw new RuntimeException("New employee cannot have an ID");
        }
        Employee entity = modelMapper.map(employeeDto, Employee.class);
        Employee savedEntity = employeeRepository.save(entity);
        log.info("Employee saved with id={}", savedEntity.getId());
        return modelMapper.map(savedEntity, EmployeeDto.class);
    }

    @Override
    public EmployeeDto getSingleEmployeeBy(Long id) {

        log.info("Fetching employee with id={}", id);

        // Fetch employee from database
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee not found with id={}", id);
                    return new ResourceNotFoundException("Employee not found with id: " + id);
                });

        // Call Address Microservice using Feign
        List<AddressDto> addresses = addressClient.getAddressById(employee.getId());

        if (addresses.isEmpty()) {
            log.info("No addresses found for employee id={}", employee.getId());
        }

        // Convert Entity → DTO
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);

        // Attach address list
        employeeDto.setAddresses(addresses);

        return employeeDto;
    }



    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        log.info("Updating employee id={} with data={}", id, employeeDto);
        if (id == null || employeeDto.getId() == null) {
            log.error("Null id provided for update - pathId={}, dtoId={}", id, employeeDto.getId());
            throw new ResourceNotFoundException("Employee ID must not be null for update");
        }

        if (!Objects.equals(id, employeeDto.getId())) {
            log.error("Path id and DTO id mismatch - pathId={}, dtoId={}", id, employeeDto.getId());
            throw new RuntimeException("Path ID and Employee DTO ID must match for update");
        }
        employeeRepository.findById(id).orElseThrow(() -> {
            log.error("Employee not found for update with id={}", id);
            return new RuntimeException("Employee not found with id: " + id);
        });
        Employee entity = modelMapper.map(employeeDto, Employee.class);
        Employee updatedEmployee = employeeRepository.save(entity);
        log.info("Employee updated with id={}", updatedEmployee.getId());
        return modelMapper.map(updatedEmployee, EmployeeDto.class);

    }
    @Override
    public void deleteEmployeeBy(Long id) {
        log.info("Deleting employee with id={}", id);
        if (!employeeRepository.existsById(id)) {
            log.error("Employee not found for delete with id={}", id);
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
        log.info("Employee deleted with id={}", id);

    }

    @CircuitBreaker(name = "ADDRESSMICROSERVICES", fallbackMethod = "addressFallback")
    @Override
    public List<EmployeeDto> getAllEmployee() {

        List<Employee> employees = employeeRepository.findAll();

        return employees.stream().map(employee -> {

            EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);

            List<AddressDto> addresses = addressClient.getAddressById(employee.getId());

            employeeDto.setAddresses(addresses);

            return employeeDto;

        }).toList();
    }

    public List<EmployeeDto> addressFallback(Exception ex) {

        List<Employee> employees = employeeRepository.findAll();

        return employees.stream().map(employee -> {

            EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);

            // return empty address list when service is down
            employeeDto.setAddresses(new ArrayList<>());

            return employeeDto;

        }).toList();
    }

    @Override
    public EmployeeDto getEmployeeByEmpCodeAndCompanyName(String empCode, String companyName) {

        Employee employee = employeeRepository.findByEmpCodeAndCompanyName(empCode, companyName)
                .orElseThrow(() -> {
                    log.error("Employee not found with empCode={} and companyName={}", empCode, companyName);
                    return new ResourceNotFoundException("Employee not found with empCode: " + empCode + " and companyName: " + companyName);
                });
        return modelMapper.map(employee,EmployeeDto.class);
    }
}
