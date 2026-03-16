package com.address.service.Impl;

import com.address.client.EmployeeClient;
import com.address.dto.AddressDto;
import com.address.dto.AddressRequest;
import com.address.dto.AddressRequestDto;
import com.address.dto.EmployeeDto;
import com.address.entity.Address;
import com.address.exception.ResourceNotFoundException;
import com.address.repo.AddressRepo;
import com.address.service.AddressService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepo addressRepository;
    private final ModelMapper modelMapper;
    private final EmployeeClient employeeClient;

    @Override
    public List<AddressDto> saveAddress(AddressRequest addressRequest) {
        EmployeeDto employee;

        try {
            employee = employeeClient.getEmployeeById(addressRequest.getEmpId());
        } catch (Exception ex) {
            throw new ResourceNotFoundException(
                    "Employee not found with id: " + addressRequest.getEmpId()
            );
        }

        List<Address> listToSave =  this.saveOrUpdateAddressRequest(addressRequest);

        List<Address> savedAddress = addressRepository.saveAll(listToSave);

        return savedAddress.stream()
                .map(address -> modelMapper.map(address, AddressDto.class))
                .toList();
    }

    @Override
    public List<AddressDto> getAddressByEmpId(Long empId) {

        List<Address> addresses = addressRepository.findAllByEmpId(empId);

        return addresses.stream()
                .map(address -> modelMapper.map(address, AddressDto.class))
                .toList();
    }


    @Override
    public List<AddressDto> getAllAddresses() {
        List<Address> all = addressRepository.findAll();
       return all.stream().map(address -> modelMapper.map(address, AddressDto.class)).toList();

    }

    @Override
    public void deleteAddressById(Long id) {

        if(!addressRepository.existsById(id)){
            log.info("Address not found with id {}", id);
            return;
        }

        addressRepository.deleteById(id);

    }
    @Override
    public List<AddressDto> updateAddress(Long id, AddressRequest addressRequest) {

        // TODO: check if employee exists

        List<Address> addressByEmpId = addressRepository.findAllByEmpId(id);

        if(addressByEmpId.isEmpty()){
            log.info("No address found for employee id {}", id);
            log.info("Creating new address for employee id {}", id);
        }

        // set empId from path variable
        addressRequest.setEmpId(id);

        List<Address> listToUpdate = this.saveOrUpdateAddressRequest(addressRequest);

        List<Long> upcomingNonNullIds = listToUpdate.stream()
                .map(Address::getId)
                .filter(Objects::nonNull)
                .toList();

        List<Long> existingIds = addressByEmpId.stream()
                .map(Address::getId)
                .toList();

        List<Long> idsToDelete = existingIds.stream()
                .filter(existingId -> !upcomingNonNullIds.contains(existingId))
                .toList();

        if(!idsToDelete.isEmpty()){
            addressRepository.deleteAllById(idsToDelete);
        }

        List<Address> updatedAddress = addressRepository.saveAll(listToUpdate);

        return updatedAddress.stream()
                .map(address -> modelMapper.map(address, AddressDto.class))
                .toList();
    }


    private List<Address> saveOrUpdateAddressRequest(AddressRequest addressRequest) {

        List<Address> listToSave = new ArrayList<>();

        for (AddressRequestDto addressRequestDto : addressRequest.getAddressRequestDtoList()) {

            Address address = new Address();

            // only set id if present (for update)
            if (addressRequestDto.getId() != null) {
                address.setId(addressRequestDto.getId());
            }

            address.setEmpId(addressRequest.getEmpId());
            address.setCity(addressRequestDto.getCity());
            address.setState(addressRequestDto.getState());
            address.setCountry(addressRequestDto.getCountry());
            address.setAddressType(addressRequestDto.getAddressType());
            address.setStreet(addressRequestDto.getStreet());

            listToSave.add(address);
        }

        return listToSave;
    }
}