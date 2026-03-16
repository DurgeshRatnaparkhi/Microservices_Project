package com.address.repo;

import com.address.dto.AddressDto;
import com.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepo extends JpaRepository<Address, Long> {

    List<Address> findAllByEmpId(long empId);
}
