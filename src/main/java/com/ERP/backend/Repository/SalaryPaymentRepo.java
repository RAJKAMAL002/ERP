package com.ERP.backend.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ERP.backend.Entity.SalaryPayment;


public interface SalaryPaymentRepo extends JpaRepository<SalaryPayment, Long>{
    List<SalaryPayment> findByEmployeeIdOrderByPaymentDateDesc(Long employeeId);

    @Query("""
            SELECT COALESCE(SUM(sp.amount),0)
            FROM SalaryPayment sp
            WHERE sp.paymentDate BETWEEN :start AND :end
            """)
    Double sumSalaryBetweenDates(LocalDateTime start, LocalDateTime end);
}
