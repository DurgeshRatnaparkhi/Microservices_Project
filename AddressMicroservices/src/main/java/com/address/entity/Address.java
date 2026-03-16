package com.address.entity;

import com.address.ennum.AddressType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long empId;
    private String street;
    private String city;
    private String state;
    private String country;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;

}
