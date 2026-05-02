package com.ERP.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ERP.backend.Entity.SaleItem;


public interface SaleItemRepo extends JpaRepository<SaleItem, Long>{
    List<SaleItem> findBySaleId(Long saleId);
}
