package com.ERP.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ERP.backend.Entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long>{

	Optional<Customer> findByEmail(String email);

	Optional<Customer> findByPhone(String email);

	boolean existsByEmail(String email);

	boolean existsByPhone(String phone);

}
