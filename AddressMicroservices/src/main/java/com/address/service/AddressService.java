package com.address.service;

import com.address.dto.AddressDto;
import com.address.dto.AddressRequest;

import java.util.List;

public interface AddressService {

    List<AddressDto> saveAddress(AddressRequest addressRequest);

       List<AddressDto> getAllAddresses();

       List<AddressDto> getAddressByEmpId(Long empId);

        void deleteAddressById(Long id);

        List<AddressDto> updateAddress(Long id, AddressRequest addressRequest);

}
