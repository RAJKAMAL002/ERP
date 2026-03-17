package com.ERP.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ERP.backend.Entity.Supplier;


public interface SupplierRepo  extends JpaRepository<Supplier, Long>{
	Boolean existsByGstNumber(String GstNumber);
	Boolean existsByPhone(Long GstNumber);
	Boolean existsByAddress(String Address);
	Boolean existsByEmail(String Email);
}
