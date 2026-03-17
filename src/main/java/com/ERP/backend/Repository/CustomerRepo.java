package com.ERP.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ERP.backend.Entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long>{

}
