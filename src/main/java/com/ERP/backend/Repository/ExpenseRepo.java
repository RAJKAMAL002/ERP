package com.ERP.backend.Repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ERP.backend.Entity.Expense;


public interface ExpenseRepo extends JpaRepository<Expense, Long>{
    @Query("""
            SELECT COALESCE(SUM(e.amount),0)
            FROM Expense e
            WHERE e.date BETWEEN :start AND :end
            """)
    Double sumExpenseBetweenDates(LocalDateTime start, LocalDateTime end);
}
