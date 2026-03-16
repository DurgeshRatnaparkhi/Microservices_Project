package com.address.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddressRequest {

    private long empId;
    private List<AddressRequestDto> addressRequestDtoList;

}
