package com.ERP.backend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ERP.backend.Entity.Product;


public interface ProductRepo extends JpaRepository<Product, Long>{
	boolean existsByName(String name);
	
	@Query("select p from Product p where p.inventory < p.minStockLevel")
	List<Product> findLowInventoryProduct();
}
