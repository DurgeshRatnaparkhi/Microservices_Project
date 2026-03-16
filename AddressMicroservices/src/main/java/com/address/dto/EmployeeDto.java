package com.address.dto;

import lombok.Data;

@Data
public class EmployeeDto {

    private Long id;
    private String empName;
    private String empEmail;
    private String empCode;
    private String companyName;
}
