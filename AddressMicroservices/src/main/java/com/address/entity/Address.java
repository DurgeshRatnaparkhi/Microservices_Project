package com.address.entity;

import com.address.ennum.AddressType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private long id;
    private long empId;
    private String street;
    private String city;
    private String state;
    private String country;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;

}
