package com.ERP.backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ERP.backend.Entity.SaleItem;


public interface SaleItemRepo extends JpaRepository<SaleItem, Long>{

}
