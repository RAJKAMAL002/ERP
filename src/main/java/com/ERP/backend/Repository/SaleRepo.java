package com.ERP.backend.Repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ERP.backend.Entity.Sale;


public interface SaleRepo extends JpaRepository<Sale, Long>{
    @Query("""
            SELECT COALESCE(SUM(s.totalAmount),0)
            FROM Sale s
            WHERE s.saleDate BETWEEN :start AND :end
            """)
    Double sumSalesBetweenDates(LocalDateTime start, LocalDateTime end);
}
