package com.address.dto;

import com.address.ennum.AddressType;
import lombok.Data;

@Data
public class AddressRequestDto {

    private Long id;
    private String street;
    private String city;
    private String state;
    private String country;
    private AddressType addressType;

}
