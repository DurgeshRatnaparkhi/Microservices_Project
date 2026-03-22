package com.address.controller;

import com.address.dto.AddressDto;
import com.address.dto.AddressRequest;
import com.address.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }


    @PostMapping("save")
    public ResponseEntity<List<AddressDto>> saveAddress(@RequestBody AddressRequest addressRequest) {
        List<AddressDto> savedAddresses = addressService.saveAddress(addressRequest);
        return new ResponseEntity<>(savedAddresses, HttpStatus.CREATED);
    }


    @PutMapping("/update/{empId}")
    public ResponseEntity<List<AddressDto>> updateAddress(
            @RequestBody AddressRequest addressDto,
            @PathVariable Long empId) {

        List<AddressDto> response = addressService.updateAddress(empId, addressDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AddressDto>> getAllAddresses() {
        List<AddressDto> addresses = addressService.getAllAddresses();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/{empId}")
    public ResponseEntity<List<AddressDto>> getAddressByEmpId(@PathVariable Long empId) {

        List<AddressDto> addresses = addressService.getAddressByEmpId(empId);

        return ResponseEntity.ok(addresses);
    }

    @DeleteMapping("/delete/{empId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long empId) {
        addressService.deleteAddressById(empId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}