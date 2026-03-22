package com.employee.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter

public class EmployeeDto {

    private Long id;
    private String empName;
    private String empEmail;
    private String empCode;
    private String companyName;

    private List<AddressDto> addresses;


}
