package com.ERP.backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ERP.backend.Entity.Purchase;
import com.ERP.backend.Entity.PurchaseItem;



public interface PurchaseItemRepo  extends JpaRepository<PurchaseItem, Long>{

	Optional<Purchase> findByPurchaseId(Long purchaseId);

}
