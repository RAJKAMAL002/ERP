package com.ERP.backend.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ERP.backend.Entity.Purchase;


public interface PurchaseRepo  extends JpaRepository<Purchase, Long>{
	List<Purchase> findBySupplierId(Long supplierId);
    
	@Query("SELECT SUM(p.totalAmount) FROM Purchase p WHERE p.purchaseDate BETWEEN :start AND :end")
	Double sumPurchaseBetweenDates(LocalDateTime start, LocalDateTime end);
}
